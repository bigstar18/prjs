package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.model.WithdrawOrder;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.Request;
import gnnt.MEBS.timebargain.tradeweb.model.RequestCDQ;
import gnnt.MEBS.timebargain.tradeweb.model.RequestMCDQ;
import gnnt.MEBS.timebargain.tradeweb.model.RequestSTQ;
import gnnt.MEBS.timebargain.tradeweb.model.Response;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseCDQ;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseMCDQ;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import gnnt.MEBS.timebargain.tradeweb.model.ResponseSTQ;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HttpXmlServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 3906934490856239410L;
  private final Log log = LogFactory.getLog(HttpXmlServlet.class);
  private TradeRMI tradeRMI = null;
  private static ApplicationContext ctx = null;
  private String strTradeRMI = null;
  private ServerRMI serverRMI = null;
  private String strServerRMI = null;
  private TradeService taken = null;
  private HashMap sortKeyMap = new HashMap();
  private HashMap orderKeyMap;
  private HashMap holdKeyMap;
  private OrdersManager ordersManager;
  
  public void init()
    throws ServletException
  {
    initRMI();
    createLogonManager();
    
    OrdersManager mgr = getOrdersManager();
    
    this.taken = TradeService.getInstance(mgr, this);
    initOrderKeyMap();
    initHoldKeyMap();
    this.sortKeyMap.put("01", this.orderKeyMap);
    this.sortKeyMap.put("02", this.holdKeyMap);
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
  {
    execute(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
  {
    execute(request, response);
  }
  
  public void execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException
  {
    if (request.getContentType().equals("binary"))
    {
      Request requestVO = readBinaryFromRequestBody(request);
      this.log.debug("userID=" + requestVO.getUserID() + " cookies sessionID=" + 
        request.getSession().getId() + " IP=" + 
        request.getRemoteAddr());
      this.log.debug(requestVO.toString());
      Response responseVO = null;
      if (requestVO != null) {
        switch (requestVO.getCMD())
        {
        case 1: 
          responseVO = commodity_data_query(request, 
            (RequestCDQ)requestVO);
          break;
        case 2: 
          responseVO = sys_time_query(request, (RequestSTQ)requestVO);
          break;
        case 3: 
          responseVO = m_commodity_data_query(request, 
            (RequestMCDQ)requestVO);
        }
      }
      if (responseVO == null)
      {
        responseVO = new ResponseDefault();
        responseVO.setRetCode(-203L);
        responseVO.setMessage("未知包名");
      }
      this.log.debug(responseVO.toString());
      Response.RenderBinary(response, responseVO);
    }
    else
    {
      ResponseResult rr = new ResponseResult();
      String reqName = null;
      String xml = null;
      try
      {
        xml = readXMLFromRequestBody(request);
        reqName = getReqNameByXml(xml);
      }
      catch (Exception e)
      {
        this.log.error("Servlet出错:" + e);
        errorException(e);
        e.printStackTrace();
        rr.setRetCode(-203);
        rr.setMessage("未知异常！");
      }
      if (reqName == null) {
        reqName = "";
      }
      rr.setName(reqName);
      
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.debug("userID=" + userID + " cookies sessionID=" + 
        request.getSession().getId() + " IP=" + 
        request.getRemoteAddr());
      if (reqName.equals("logon"))
      {
        logon(request, response, xml, rr);
      }
      else if (reqName.equals("check_user"))
      {
        check_user(request, response, xml, rr);
      }
      else
      {
        long sessionID = parseLong(getValueByTagName(xml, "SESSION_ID"));
        
        Privilege privilege = null;
        try
        {
          privilege = getPrivilege(request, userID, sessionID, true);
        }
        catch (ConnectException ec)
        {
          this.log.error("sys_time_query rmi conection exception" + ec);
          int ret = -202;
          String message = "RMI连接失败！";
          initRMI();
          renderXML(response, ResponseXml.responseXml(reqName, ret, 
            message));
          return;
        }
        catch (TimeOutException e)
        {
          this.log.error("getPrivilege  exception " + e.getMessage());
          
          int ret = -2001;
          String message = e.getMessage();
          renderXML(response, ResponseXml.responseXml(reqName, ret, 
            message));
          return;
        }
        catch (Exception e)
        {
          this.log.error("execute getPrivilege  exception" + e);
          int ret = -203;
          String message = "未知异常！";
          renderXML(response, ResponseXml.responseXml(reqName, ret, 
            message));
          return;
        }
        if (privilege == null)
        {
          int ret = -201;
          String message = "身份不合法，请重新登陆！";
          this.log.info("userid=" + userID + "forbidden query ,cause" + 
            message);
          renderXML(response, ResponseXml.responseXml(reqName, ret, 
            message));
          return;
        }
        if (reqName.equals("logoff"))
        {
          logoff(request, response, xml, rr, privilege, sessionID);
        }
        else if (reqName.equals("firm_info"))
        {
          firm_info(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("my_order_query"))
        {
          my_order_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("tradequery"))
        {
          tradequery(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("holding_query"))
        {
          holding_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("holding_detail_query"))
        {
          holding_detail_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("c_d_q"))
        {
          commodity_data_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("commodity_query"))
        {
          commodity_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("sys_time_query"))
        {
          sys_time_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("firm_funds_info"))
        {
          firm_funds_info(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("customer_order_query"))
        {
          customer_order_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("other_firm_query"))
        {
          other_firm_query(request, response, xml, rr, privilege);
        }
        else if (reqName.equals("firm_holdsum"))
        {
          firm_holdsum(request, response, xml, rr, privilege);
        }
        else
        {
          OrdersManager mgr = getOrdersManager();
          String status = mgr.getStatus(privilege);
          privilege.setStatus(status);
          if (privilege.getFirmType().equals("0"))
          {
            if (status.equals("F"))
            {
              int ret = -1000;
              String message = "您的账户被冻结！";
              this.log.info("userid=" + userID + " 禁止交易 ,原因:" + 
                message);
              renderXML(response, ResponseXml.responseXml(
                reqName, ret, message));
              return;
            }
            if (status.equals("D"))
            {
              int ret = -1001;
              String message = "您的账户被终止！";
              this.log.info("userid=" + userID + " 禁止交易 ,原因:" + 
                message);
              renderXML(response, ResponseXml.responseXml(
                reqName, ret, message));
            }
          }
          else if ((privilege.getFirmType().equals("1")) && 
            (status.equals("D")))
          {
            int ret = -1001;
            String message = "您的账户被终止！";
            this.log
              .info("userid=" + userID + " 禁止交易 ,原因" + 
              message);
            renderXML(response, ResponseXml.responseXml(
              reqName, ret, message));
            return;
          }
          if (reqName.equals("change_password"))
          {
            change_password(request, response, xml, rr, privilege);
          }
          else if (reqName.equals("order_s"))
          {
            order_s(request, response, xml, rr, privilege, 
              sessionID);
          }
          else if (reqName.equals("order_x"))
          {
            order_x(request, response, xml, rr, privilege, 
              sessionID);
          }
          else if (reqName.equals("order_wd"))
          {
            order_wd(request, response, xml, rr, privilege, 
              sessionID);
          }
          else if (reqName.equals("set_loss_profit"))
          {
            set_loss_profit(request, response, xml, rr, privilege);
          }
          else if (reqName.equals("withdraw_loss_profit"))
          {
            withdraw_loss_profit(request, response, xml, rr, 
              privilege);
          }
          else if (reqName.equals("agency_logon"))
          {
            agency_logon(request, response, xml, rr, privilege);
          }
          else
          {
            if ((rr.getMessage() == null) || 
              (rr.getMessage().equals("")))
            {
              rr.setRetCode(-203);
              rr.setMessage("未知包名！");
            }
            renderXML(response, ResponseXml.responseXml("", rr
              .getRetCode(), rr.getMessage()));
          }
        }
      }
    }
  }
  
  private OrdersManager getOrdersManager()
  {
    if (this.ordersManager == null) {
      this.ordersManager = ((OrdersManager)getBean("ordersManager"));
    }
    return this.ordersManager;
  }
  
  private boolean checkRmi(ResponseResult rr)
  {
    if (this.tradeRMI == null) {
      initRMI();
    }
    if (this.tradeRMI == null)
    {
      rr.setLongRetCode(-1001L);
      rr.setMessage("RMI连接为空");
      return false;
    }
    return true;
  }
  
  protected void logon(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'logon' method");
    }
    long ret = 0L;
    String message = "";
    Privilege privilege = null;
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      
      this.log.info("userid=" + userID + "logon!!");
      if (checkRmi(rr))
      {
        String password = 
          StringEscapeUtils.unescapeXml(getValueByTagName(xml, "PASSWORD"));
        String regWord = getValueByTagName(xml, "REGISTER_WORD");
        String logonIP = request.getRemoteAddr();
        Trader trader = new Trader();
        
        trader.setTraderID(userID);
        trader.setPassword(password);
        trader.setKeyCode(regWord);
        trader.setLogonIP(logonIP);
        
        TraderInfo info = this.tradeRMI.logon(trader);
        
        long sessionID = info.auSessionId;
        if (sessionID > 0L)
        {
          OrdersManager mgr = getOrdersManager();
          privilege = mgr.getradePrivilege(info);
          privilege.setLogonIP(logonIP);
          if ((privilege.getFirmType().equals("0")) && 
            (privilege.getStatus().equals("F")))
          {
            ret = -1000L;
            message = "客户冻结状态禁止登录！";
            
            this.tradeRMI.logoff(privilege.getTraderID(), sessionID, privilege
              .getLogonIP(), "客户退出交易服务器");
            privilege.setLogon(false);
            request.getSession().invalidate();
            this.log.info("userid=" + privilege.getTraderID() + "logoff success!!");
          }
          else if (privilege.getStatus().equals("D"))
          {
            ret = -1001L;
            message = "终止状态禁止登录！";
            
            this.tradeRMI.logoff(privilege.getTraderID(), sessionID, privilege
              .getLogonIP(), "客户退出交易服务器");
            privilege.setLogon(false);
            request.getSession().invalidate();
            this.log.info("userid=" + privilege.getTraderID() + "logoff success!!");
          }
          else
          {
            this.log.info("PrivilegeObj:" + privilege);
            request.getSession().setAttribute("preIsLogonTime", 
              new Date());
            request.getSession().setAttribute("privilege", 
              privilege);
            
            ret = sessionID;
            List list = new ArrayList();
            list.add(info);
            rr.setResultList(list);
            if ((info.message != null) && (!"".equals(info.message))) {
              message = info.message;
            }
            this.log.info("客户" + 
              privilege.getTraderID() + 
              "于" + 
              
              DateUtil.getCurDateTime() + "登录系统,IP地址" + 
              privilege.getLogonIP());
          }
        }
        else if (sessionID == -1L)
        {
          ret = -1L;
          
          message = "登录账号或者密码错误！";
        }
        else if (sessionID == -2L)
        {
          ret = -2L;
          
          message = "登录账号或者密码错误！";
        }
        else if (sessionID == -3L)
        {
          ret = -3L;
          message = "禁止登陆！";
        }
        else if (sessionID == -4L)
        {
          ret = -4L;
          message = "Key盘验证错误！";
        }
        else if (sessionID == -5L)
        {
          ret = -5L;
          message = "其它异常！";
        }
        else if (sessionID == -6L)
        {
          ret = -6L;
          message = "交易板块被禁止！";
        }
        else if (sessionID == -7L)
        {
          ret = -7L;
          message = "au没有此用户！";
        }
        else if (sessionID == -8L)
        {
          ret = -8L;
          message = "没有板块权限！";
        }
        else
        {
          ret = sessionID;
          message = "其他异常！";
        }
        this.log.info("userid=" + userID + "logon result=" + ret + 
          ",logon IP=" + logonIP + ";message=" + message);
      }
      else
      {
        ret = rr.getLongRetCode();
        message = rr.getMessage();
      }
    }
    catch (ConnectException e1)
    {
      this.log.error("logon rmi conection exception" + e1);
      ret = -204L;
      message = "RMI连接失败";
    }
    catch (RemoteException e2)
    {
      e2.printStackTrace();
      this.log.error("logon remoteExceotion:" + e2);
      errorException(e2);
      ret = -202L;
      message = "RMI调用出错!";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("logon error:" + e);
      errorException(e);
      ret = -203L;
      message = "未知异常！";
    }
    rr.setLongRetCode(ret);
    rr.setMessage(message);
    renderXML(response, ResponseXml.logon(rr, privilege));
  }
  
  protected void agency_logon(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege privilege)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'agency_logon' method");
    }
    int ret = 0;
    String message = "";
    try
    {
      this.log.info("userid=" + privilege.getTraderID() + "agency_logon!!");
      
      String agencyNO = getValueByTagName(xml, "A_N");
      if ((agencyNO != null) && (agencyNO.length() > 0) && 
        (agencyNO != null) && (agencyNO.length() > 0)) {
        this.log.info("agencyNO=" + agencyNO);
      }
      String phonePWD = StringEscapeUtils.unescapeXml(getValueByTagName(
        xml, "P_P"));
      
      long result = this.tradeRMI.checkDelegateInfo(privilege.getFirmId(), 
        agencyNO, phonePWD);
      if (result != 0L)
      {
        if (result == -1L)
        {
          ret = -202;
          message = "客户 " + agencyNO + " 不属于会员" + 
            privilege.getTraderID() + "！";
        }
        else if (result == -2L)
        {
          ret = -205;
          
          message = "客户代码或者电话密码不正确！";
        }
        else if (result == -3L)
        {
          ret = -206;
          
          message = "客户代码或者电话密码不正确！";
        }
        else
        {
          ret = -207;
          message = "其他错误！";
        }
        this.log.info("userid=" + privilege.getTraderID() + 
          "agency_logon fail,cause" + message);
      }
      else
      {
        TraderInfo info = this.tradeRMI.getTraderInfo(agencyNO);
        
        OrdersManager mgr = getOrdersManager();
        Privilege agencyPrivilege = mgr.getradePrivilege(info);
        if ((agencyPrivilege.getFirmType().equals("0")) && 
          (agencyPrivilege.getStatus().equals("F")))
        {
          ret = -1000;
          message = "客户冻结状态禁止登录！";
        }
        else if (agencyPrivilege.getStatus().equals("D"))
        {
          ret = -1001;
          message = "终止状态禁止登录！";
        }
        else
        {
          request.getSession().setAttribute("agencyPrivilege", 
            agencyPrivilege);
          
          this.log.info("会员" + 
            privilege.getTraderID() + 
            "于" + 
            
            DateUtil.getCurDateTime() + "代理客户" + agencyNO + 
            "登陆");
          
          this.log.info("AgencyPrivilege:" + agencyPrivilege);
        }
      }
    }
    catch (ConnectException e1)
    {
      this.log.error("agency_logon rmi conection exception" + e1);
      ret = -204;
      message = "RMI连接失败";
    }
    catch (RemoteException e2)
    {
      e2.printStackTrace();
      this.log.error("agency_logon remoteExceotion:" + e2);
      errorException(e2);
      ret = -202;
      message = "RMI调用出错!";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("agency_logon error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setLongRetCode(ret);
    rr.setMessage(message);
    renderXML(response, ResponseXml.responseXml(rr.getName(), ret, message));
  }
  
  protected void logoff(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege privilege, long sessionID)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'logoff' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      this.tradeRMI.logoff(privilege.getTraderID(), sessionID, privilege
        .getLogonIP(), "客户退出交易服务器");
      privilege.setLogon(false);
      request.getSession().invalidate();
      this.log.info("userid=" + privilege.getTraderID() + "logoff success!!");
    }
    catch (ConnectException ec)
    {
      this.log.error("logoff rmi conection exception" + ec);
      ret = -204;
      message = "RMI连接失败";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("logoff remoteerror:" + e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("logoff error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), ret, message));
  }
  
  protected void check_user(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'check_user' method xml=" + xml);
    }
    long ret = 0L;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + "check_user!!");
      
      long sessionID = parseLong(getValueByTagName(xml, "SESSION_ID"));
      String moduleID = getValueByTagName(xml, "MODULE_ID");
      this.log.debug("ModuleID=" + moduleID);
      String logonIP = request.getRemoteAddr();
      
      TraderInfo info = this.tradeRMI.remoteLogon(userID, "2", sessionID, 
        logonIP);
      this.log.debug("checkuser info" + info.toString());
      this.log.debug(info.auSessionId + "," + info.firmId + "," + 
        info.traderId);
      
      long ausessionID = info.auSessionId;
      if (ausessionID > 0L)
      {
        ret = 0L;
      }
      else if (ausessionID == -1L)
      {
        ret = -1L;
        message = "交易代码不存在！";
      }
      else if (ausessionID == -2L)
      {
        ret = -2L;
        message = "登录密码输入有误！";
      }
      else if (ausessionID == -3L)
      {
        ret = -3L;
        message = "禁止登陆！";
      }
      else if (ausessionID == -4L)
      {
        ret = -4L;
        message = "Key盘验证错误！";
      }
      else if (ausessionID == -5L)
      {
        ret = -5L;
        message = "其它异常！";
      }
      else if (ausessionID == -6L)
      {
        ret = -6L;
        message = "交易板块被禁止！";
      }
      else if (ausessionID == -7L)
      {
        ret = -7L;
        message = "au没有此用户,请重新登陆！";
      }
      else if (ausessionID == -8L)
      {
        ret = -8L;
        message = "没有板块权限！";
      }
      else
      {
        ret = ausessionID;
        message = "其他异常！";
      }
      this.log.info("userid=" + userID + "check_user IP=" + logonIP + 
        " result=" + ausessionID + ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("checkUser rmi conection exception" + ec);
      ret = -204L;
      message = "RMI连接失败";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("checkUser remote error:" + e1);
      ret = -205L;
      message = "远程调用方法出错！";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("checkUser error:" + e);
      errorException(e);
      ret = -203L;
      message = "未知异常！";
    }
    rr.setLongRetCode(ret);
    rr.setMessage(message);
    renderXML(response, ResponseXml.check_user(rr));
  }
  
  protected void change_password(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege privilege)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'change_password' method");
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      
      String pwdType = getValueByTagName(xml, "PWD_TYPE");
      
      this.log.info("userid=" + userID + " change_password!!");
      this.log.info("change_password PWD_TYPE=" + pwdType);
      
      String oldPassword = 
        StringEscapeUtils.unescapeXml(getValueByTagName(xml, "OLD_PASSWORD"));
      String newPassword = 
        StringEscapeUtils.unescapeXml(getValueByTagName(xml, "NEW_PASSWORD"));
      if (pwdType.equals("1")) {
        ret = this.tradeRMI.changePhonePassowrd(userID, oldPassword, 
          newPassword);
      } else {
        ret = this.tradeRMI.changePassowrd(userID, oldPassword, newPassword);
      }
      if (ret == -1) {
        message = "原密码不正确!";
      }
      this.log.info("userid=" + userID + "change_password result =" + ret + 
        ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("change_password rmi conection exception" + ec);
      ret = -204;
      message = "RMI连接失败";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("change_password remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("change_password error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), ret, message));
  }
  
  protected void firm_info(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'firm_info' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      this.log.info("userid=" + prvg.getTraderID() + " firm_info!!");
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        
        List lst = mgr.firm_info(prvg);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + prvg.getTraderID() + "firm_info result =" + 
        ret + ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("firm_info rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("firm_info remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("firm_info error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.firm_info(rr));
  }
  
  protected void order_s(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg, long sessionID)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'order_s' method xml=" + xml);
    }
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      String firmID = prvg.getFirmId();
      this.log.info("userid=" + userID + " order_s!");
      
      Short BS_Flag = new Short(getValueByTagName(xml, "BUY_SELL"));
      String commID = getValueByTagName(xml, "COMMODITY_ID");
      Double Price = new Double(getValueByTagName(xml, "PRICE"));
      Long Quantity = new Long(getValueByTagName(xml, "QTY"));
      Double DotDiff = new Double(getValueByTagName(xml, "DOT_DIFF"));
      
      Short OrderType = new Short(getValueByTagName(xml, "SETTLE_BASIS"));
      String OTHER_ID = getValueByTagName(xml, "OTHER_ID");
      

      boolean isAgency = false;
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0))
        {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
          isAgency = true;
        }
      }
      if (prvg == null)
      {
        rr.setRetCode(-201);
        rr.setMessage("身份不合法，请重新登陆！");
      }
      else if ((prvg.getStatus().equals("F")) && (!isAgency))
      {
        rr.setRetCode(-1000);
        rr.setMessage("您的账户被冻结！");
        this.log.info("userid=" + userID + " 禁止交易 ,原因" + rr.getMessage());
      }
      else
      {
        this.log.debug("Order Privilege Pass,Begin to Submit Order..");
        Short CloseMode = null;
        
        Long Holding_ID = null;
        if (OrderType.shortValue() == 2)
        {
          String CLOSEMODE = getValueByTagName(xml, "CLOSEMODE");
          
          CloseMode = Short.valueOf(CLOSEMODE.equals("") ? 1 : new Short(CLOSEMODE).shortValue());
          
          String HOLDING_ID = getValueByTagName(xml, "HOLDING_ID");
          Holding_ID = HOLDING_ID.trim().equals("") ? null : 
            new Long(HOLDING_ID);
        }
        MarketOrder ov = new MarketOrder();
        
        ov.setCommodityID(commID);
        ov.setBuyOrSell(BS_Flag);
        char oc_Flag = 'O';
        if (OrderType.shortValue() == 2) {
          oc_Flag = 'C';
        }
        ov.setOC_Flag(oc_Flag);
        ov.setPrice(Price);
        ov.setQuantity(Quantity);
        ov.setOrderPoint(DotDiff);
        ov.setCloseMode(CloseMode);
        
        ov.setSpecHoldNo(Holding_ID);
        ov.setOtherFirmID(OTHER_ID);
        
        ov.setTraderID(prvg.getTraderID());
        ov.setFirmID(prvg.getFirmId());
        if (isAgency)
        {
          ov.setConsignerID(userID);
          ov.setConsignFirmID(firmID);
        }
        rr = PrivilegeController.checkMarketOrder(prvg, ov, rr);
        if (rr.getRetCode() == 0)
        {
          long delayTime = isDelayTrade(ov, prvg);
          if (delayTime > 0L)
          {
            Thread.sleep(delayTime);
            ov.setDelayTime(Long.valueOf(delayTime));
            ov.setIsslippoint(((BigDecimal)((Map)prvg.getFirmDelayTrade().get(ov.getCommodityID())).get("isslippoint")).intValue() == 2);
          }
          if ((delayTime > 0L) && (((BigDecimal)((Map)prvg.getFirmDelayTrade().get(ov.getCommodityID())).get("isslippoint")).intValue() == 0))
          {
            rr.setRetCode(60);
            rr.setMessage("市价委托价格不能成交！");
          }
          else
          {
            rr = submitOrderS(sessionID, ov, rr, isAgency);
          }
        }
      }
      this.log.info("userid=" + userID + "order_s result =" + rr.getRetCode() + 
        ";message=" + rr.getMessage() + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("order_s rmi conection exception" + ec);
      rr.setRetCode(-204);
      rr.setMessage("RMI连接失败！");
      initRMI();
    }
    catch (RemoteException re)
    {
      this.log.error("order_s error:" + re);
      errorException(re);
      rr.setRetCode(-202);
      rr.setMessage("远程方法调用出错！");
    }
    catch (Exception e)
    {
      this.log.error("order_s error:" + e);
      
      errorException(e);
      rr.setRetCode(-203);
      rr.setMessage("未知异常！");
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), rr
      .getRetCode(), rr.getMessage()));
  }
  
  private long isDelayTrade(MarketOrder ov, Privilege prvg)
  {
    if ((prvg.getFirmDelayTrade() != null) && (prvg.getFirmDelayTrade().get(ov.getCommodityID()) != null))
    {
      int type = ((BigDecimal)((Map)prvg.getFirmDelayTrade().get(ov.getCommodityID())).get("delaytradeType")).intValue();
      long time = ((BigDecimal)((Map)prvg.getFirmDelayTrade().get(ov.getCommodityID())).get("delayTradeTime")).longValue();
      if (type == 3) {
        return time;
      }
      if (type == 2)
      {
        if (ov.getOC_Flag() == 'C') {
          return time;
        }
      }
      else if ((type == 1) && 
        (ov.getOC_Flag() == 'O')) {
        return time;
      }
    }
    return 0L;
  }
  
  protected void order_x(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg, long sessionID)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'order_x' method xml=" + xml);
    }
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      String firmID = prvg.getFirmId();
      this.log.info("userid=" + userID + " order_x!");
      
      Short BS_Flag = new Short(getValueByTagName(xml, "BUY_SELL"));
      String commID = getValueByTagName(xml, "COMMODITY_ID");
      Double Price = new Double(getValueByTagName(xml, "PRICE"));
      Long Quantity = new Long(getValueByTagName(xml, "QTY"));
      
      Double stopLossPrice = new Double(getValueByTagName(xml, 
        "STOP_LOSS"));
      Double stopProfitPrice = new Double(getValueByTagName(xml, 
        "STOP_PROFIT"));
      Short effective = new Short(getValueByTagName(xml, "VALIDITY_TYPE"));
      
      String OTHER_ID = getValueByTagName(xml, "OTHER_ID");
      

      boolean isAgency = false;
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0))
        {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
          isAgency = true;
        }
      }
      if (prvg == null)
      {
        rr.setRetCode(-201);
        rr.setMessage("身份不合法，请重新登陆！");
      }
      else if ((prvg.getStatus().equals("F")) && (!isAgency))
      {
        rr.setRetCode(-1000);
        rr.setMessage("您的账户被冻结！");
        this.log.info("userid=" + userID + " 禁止交易 ,原因" + rr.getMessage());
      }
      else
      {
        this.log.debug("Order Privilege Pass,Begin to Submit Order..");
        
        LimitOrder ov = new LimitOrder();
        ov.setCommodityID(commID);
        ov.setBuyOrSell(BS_Flag);
        ov.setPrice(Price);
        ov.setQuantity(Quantity);
        ov.setStopLossPrice(stopLossPrice);
        ov.setStopProfitPrice(stopProfitPrice);
        ov.setEffective(effective);
        ov.setOtherFirmID(OTHER_ID);
        
        ov.setTraderID(prvg.getTraderID());
        ov.setFirmID(prvg.getFirmId());
        if (isAgency)
        {
          ov.setConsignerID(userID);
          ov.setConsignFirmID(firmID);
        }
        rr = PrivilegeController.checkMarketOrder(prvg, ov, rr);
        if (rr.getRetCode() == 0) {
          rr = submitOrderX(sessionID, ov, rr, isAgency);
        }
      }
      this.log.info("userid=" + userID + "order_x result =" + rr.getRetCode() + 
        ";message=" + rr.getMessage() + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("order_x rmi conection exception" + ec);
      rr.setRetCode(-204);
      rr.setMessage("RMI连接失败！");
      initRMI();
    }
    catch (RemoteException re)
    {
      this.log.error("order_x error:" + re);
      errorException(re);
      rr.setRetCode(-202);
      rr.setMessage("远程方法调用出错！");
    }
    catch (Exception e)
    {
      this.log.error("order_x error:" + e);
      
      errorException(e);
      rr.setRetCode(-203);
      rr.setMessage("未知异常！");
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), rr
      .getRetCode(), rr.getMessage()));
  }
  
  protected void order_wd(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg, long sessionID)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'order_wd' method xml=" + xml);
    }
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      String firmID = prvg.getFirmId();
      this.log.info("userid=" + userID + " order_wd!");
      
      Long A_OrderNo_W = new Long(getValueByTagName(xml, "ORDER_NO"));
      
      this.log.info("order_wd ORDER_NO=" + A_OrderNo_W);
      

      boolean isAgency = false;
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0))
        {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
          isAgency = true;
        }
      }
      if (prvg == null)
      {
        rr.setRetCode(-201);
        rr.setMessage("身份不合法，请重新登陆！");
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        Orders orders = new Orders();
        orders.setOrderNo(A_OrderNo_W);
        
        List lst = mgr.my_order_query(orders, prvg);
        if ((lst == null) || (lst.size() == 0))
        {
          rr.setRetCode(1);
          rr.setMessage("没有委托号为" + A_OrderNo_W + "的未成交委托！");
        }
        else
        {
          Map map = (Map)lst.get(0);
          if ((userID != null) && (map != null))
          {
            WithdrawOrder ov = new WithdrawOrder();
            ov.setTraderID(prvg.getTraderID());
            ov.setFirmID(prvg.getFirmId());
            if (isAgency)
            {
              ov.setConsignerID(userID);
              ov.setConsignFirmID(firmID);
            }
            ov.setWithdrawID(A_OrderNo_W);
            

            rr = PrivilegeController.checkWithdrawOrder(prvg, ov, 
              (String)map.get("COMMODITY_ID"), rr);
            if (rr.getRetCode() == 0) {
              rr = withdrawOrder(sessionID, ov, rr, isAgency);
            }
          }
          else if (map == null)
          {
            rr.setRetCode(1);
            rr.setMessage("没有委托号为" + A_OrderNo_W + "的未成交委托！");
          }
          else
          {
            rr.setRetCode(1);
            rr.setMessage("用户身份不合法，请重新登陆！");
          }
        }
      }
      this.log.info("userid=" + userID + "order_wd result =" + rr.getRetCode() + 
        ";message=" + rr.getMessage() + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("order_wd rmi conection exception" + ec);
      rr.setRetCode(-204);
      rr.setMessage("RMI连接失败！");
      initRMI();
    }
    catch (RemoteException re)
    {
      this.log.error("order_wd error:" + re.getMessage());
      errorException(re);
      rr.setRetCode(-202);
      rr.setMessage("远程调用异常！");
    }
    catch (Exception e)
    {
      this.log.error("order_wd error:" + e.getMessage());
      errorException(e);
      rr.setRetCode(-203);
      rr.setMessage("未知异常！");
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), rr
      .getRetCode(), rr.getMessage()));
  }
  
  protected void my_order_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'my_order_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    
    long lastestUpdateTime = 0L;
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " my_order_query!");
      
      String BUY_SELL = getValueByTagName(xml, "BUY_SELL");
      Short BS_Flag = (BUY_SELL.equals("")) || (BUY_SELL.equals("0")) ? null : 
        new Short(BUY_SELL);
      String ORDER_NO = getValueByTagName(xml, "ORDER_NO");
      Long A_OrderNo = (ORDER_NO.equals("")) || (ORDER_NO.equals("0")) ? null : 
        new Long(ORDER_NO);
      
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      
      String startNum = getValueByTagName(xml, "STARTNUM");
      
      String reccnt = getValueByTagName(xml, "RECCNT");
      
      String sortfLD = getValueByTagName(xml, "SORTFLD");
      
      String isdesc = getValueByTagName(xml, "ISDESC") == "" ? "0" : "1";
      




      String updateTimeInXML = getValueByTagName(xml, "UT");
      boolean whetherNullOrEmptyString = (updateTimeInXML == null) || 
        ("0".equals(updateTimeInXML)) || ("".equals(updateTimeInXML
        .trim()));
      String updateTime = whetherNullOrEmptyString ? "queryAll" : 
        updateTimeInXML;
      

      SortCondition sc = new SortCondition();
      sc.setStartNu(startNum);
      sc.setIsdesc(Integer.parseInt(isdesc));
      sc.setReccnt(reccnt);
      sc.setSortfLD((String)this.orderKeyMap.get(sortfLD));
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        Orders orders = new Orders();
        
        orders.setBS_Flag(BS_Flag);
        orders.setOrderNo(A_OrderNo);
        orders.setTraderID(prvg.getTraderID());
        
        orders.setUpdateTime(updateTime);
        orders.setCommodityID(COMMODITY_ID);
        List lst = mgr.my_order_query(orders, prvg, sc);
        
        Timestamp dateInDB = null;
        for (int i = 0; i < lst.size(); i++)
        {
          Timestamp date = null;
          Map map = (Map)lst.get(i);
          if (map.containsKey("UPDATETIME")) {
            date = (Timestamp)map.get("UPDATETIME");
          }
          if (dateInDB == null) {
            dateInDB = date;
          } else if (dateInDB.before(date)) {
            dateInDB = date;
          }
        }
        lastestUpdateTime = dateInDB == null ? parseLong(""
          .equals(updateTimeInXML) ? "0" : updateTimeInXML) : 
          dateInDB.getTime();
        if (lst == null)
        {
          ret = -202;
          message = "服务器端数据查询异常";
        }
        else if (lst.size() == 0)
        {
          ret = 0;
          message = "没有新记录！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "my_order_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("my_order_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("my_order_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("my_order_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.my_order_query(rr, lastestUpdateTime));
  }
  
  protected void tradequery(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'tradequery' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " tradequery!");
      
      String LAST_TRADE_ID = getValueByTagName(xml, "LAST_TRADE_ID");
      Long last_trade_id = null;
      if ((LAST_TRADE_ID != null) && (!LAST_TRADE_ID.equals("")) && 
        (!LAST_TRADE_ID.equals("0"))) {
        last_trade_id = Long.valueOf(LAST_TRADE_ID);
      }
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        String firmID = prvg.getFirmId();
        if (this.taken == null)
        {
          OrdersManager mgr = getOrdersManager();
          
          this.taken = TradeService.getInstance(mgr, this);
        }
        LinkedList link = (LinkedList)this.taken.getTradeMap().get(firmID);
        if ((link == null) || (link.size() == 0))
        {
          this.log.debug("====> linkedlist has no found");
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          if (last_trade_id == null) {
            last_trade_id = Long.valueOf(0L);
          }
          this.log.debug("===>last_trade_id = " + last_trade_id);
          List lst = new ArrayList();
          for (int i = link.size() - 1; i >= 0; i--)
          {
            Trade trade = (Trade)link.get(i);
            if (last_trade_id.longValue() >= trade.getTradeNo().longValue()) {
              break;
            }
            lst.add(trade);
          }
          this.log.debug("===>clint list size =" + lst.size());
          if ((lst == null) || (lst.size() == 0))
          {
            ret = -202;
            message = "记录未找到！";
          }
          rr.setResultList(lst);
        }
        this.log.info("userid=" + userID + "tradequery result =" + ret + 
          ";message=" + message + ";");
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("tradequery rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("tradequery remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("tradequery error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    renderXML(response, ResponseXml.tradequery(rr));
  }
  
  protected void holding_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'holding_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " holding_query!");
      
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      
      String startNum = getValueByTagName(xml, "STARTNUM");
      
      String reccnt = getValueByTagName(xml, "RECCNT");
      
      String sortfLD = getValueByTagName(xml, "SORTFLD");
      

      String isdesc = getValueByTagName(xml, "ISDESC") == "" ? "0" : "1";
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        Orders orders = new Orders();
        orders.setTraderID(prvg.getTraderID());
        orders.setCommodityID(COMMODITY_ID);
        
        SortCondition sc = new SortCondition();
        sc.setStartNu(startNum);
        sc.setIsdesc(Integer.parseInt(isdesc));
        sc.setReccnt(reccnt);
        sc.setSortfLD((String)this.holdKeyMap.get(sortfLD));
        List lst = mgr.holding_query(orders, prvg, sc);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "holding_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("holding_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("holding_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("holding_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.holding_query(rr));
  }
  
  protected void holding_detail_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'holding_detail_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " holding_detail_query!");
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        Orders orders = new Orders();
        orders.setTraderID(prvg.getTraderID());
        orders.setCommodityID(COMMODITY_ID);
        
        List lst = mgr.holding_detail_query(orders, prvg, null);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "holding_detail_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("holding_detail_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("holding_detail_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("holding_detail_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.holding_detail_query(rr));
  }
  
  private ResponseCDQ commodity_data_query(HttpServletRequest request, RequestCDQ requestCDQ)
  {
    ResponseCDQ responseCDQ = new ResponseCDQ();
    int ret = 0;
    String message = "";
    try
    {
      String userID = requestCDQ.getUserID();
      long sessionID = requestCDQ.getSessionID();
      this.log.debug("userid=" + userID + " commodity_data_query!");
      
      Privilege prvg = getPrivilege(request, userID, sessionID, false);
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        if (prvg.getFirmType().equals("1"))
        {
          String agencyNO = requestCDQ.getAgencyNO();
          if ((agencyNO != null) && (agencyNO.length() > 0)) {
            this.log.info("agencyNO=" + agencyNO);
          }
          String phonePWD = requestCDQ.getPhonePWD();
          if ((agencyNO != null) && (agencyNO.length() > 0)) {
            prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
              agencyNO, phonePWD);
          }
        }
        if (prvg == null)
        {
          ret = -201;
          message = "身份不合法，请重新登陆！";
        }
        else
        {
          List<Map> lst = new ArrayList();
          Map map = mgr.getQuotationMap();
          if (map != null) {
            if ((requestCDQ.getCommodityID() != null) && 
              (requestCDQ.getCommodityID().length() > 0))
            {
              if (prvg.getNoDisplayPrivilege().containsKey(
                requestCDQ.getCommodityID())) {
                this.log.info("商品 " + requestCDQ.getCommodityID() + 
                  " 无交易权限");
              } else {
                lst.add((Map)map.get(requestCDQ
                  .getCommodityID()));
              }
            }
            else {
              for (Object o : map.keySet()) {
                if (prvg.getNoDisplayPrivilege().containsKey(
                  (String)o)) {
                  this.log.info("商品 " + (String)o + " 无交易权限");
                } else {
                  lst.add((Map)map.get(o));
                }
              }
            }
          }
          if ((lst == null) || (lst.size() == 0))
          {
            ret = -202;
            message = "记录未找到！";
          }
          else if (prvg.getQuotePoint() == null)
          {
            ret = -206;
            message = "交易员报价点差为空！";
          }
          else
          {
            for (Map element : lst) {
              if (element != null)
              {
                String commodityID = 
                  (String)element.get("CommodityID");
                
                Map<String, BigDecimal> quotePointMap = 
                  (Map)prvg.getQuotePoint().get(commodityID);
                if (quotePointMap == null)
                {
                  ret = -207;
                  message = "交易员商品 " + commodityID + 
                    "的报价点差为空！";
                }
                else
                {
                  BigDecimal quotePoint_B = 
                    (BigDecimal)quotePointMap.get("QuotePoint_B_RMB");
                  BigDecimal quotePoint_S = 
                    (BigDecimal)quotePointMap.get("QuotePoint_S_RMB");
                  if (quotePoint_B == null)
                  {
                    ret = -207;
                    message = "交易员商品 " + commodityID + 
                      "的买报价点差为空！";
                  }
                  else if (quotePoint_S == null)
                  {
                    ret = -207;
                    message = "交易员商品 " + commodityID + 
                      "的卖报价点差为空！";
                  }
                  else
                  {
                    element.put("QuotePoint_B_RMB", 
                      quotePoint_B);
                    element.put("QuotePoint_S_RMB", 
                      quotePoint_S);
                  }
                }
              }
            }
            responseCDQ.setLst(lst);
          }
        }
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("commodity_data_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("commodity_data_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (TimeOutException e)
    {
      this.log.error("commodity_data_query getPrivilege  exception " + 
        e.getMessage());
      
      ret = -2001;
      message = e.getMessage();
    }
    catch (Exception e)
    {
      this.log.error("commodity_data_query error:" + e);
      e.printStackTrace();
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    responseCDQ.setRetCode(ret);
    responseCDQ.setMessage(message);
    return responseCDQ;
  }
  
  private ResponseMCDQ m_commodity_data_query(HttpServletRequest request, RequestMCDQ requestMCDQ)
  {
    ResponseMCDQ responseMCDQ = new ResponseMCDQ();
    int ret = 0;
    String message = "";
    try
    {
      String userID = requestMCDQ.getUserID();
      long sessionID = requestMCDQ.getSessionID();
      this.log.debug("userid=" + userID + " m_commodity_data_query!");
      
      Privilege prvg = getPrivilege(request, userID, sessionID, false);
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        if (!prvg.getFirmType().equals("1"))
        {
          ret = -210;
          message = "不是会员身份不能查询会员行情！";
        }
        List<Map> lst = new ArrayList();
        Map map = mgr.getQuotationMap();
        if (map != null) {
          if ((requestMCDQ.getCommodityID() != null) && 
            (requestMCDQ.getCommodityID().length() > 0))
          {
            if (prvg.getNoDisplayPrivilege().containsKey(
              requestMCDQ.getCommodityID())) {
              this.log.info("商品 " + requestMCDQ.getCommodityID() + 
                " 无交易权限");
            } else {
              lst.add((Map)map.get(requestMCDQ
                .getCommodityID()));
            }
          }
          else {
            for (Object o : map.keySet()) {
              if (prvg.getNoDisplayPrivilege().containsKey(
                (String)o)) {
                this.log.info("商品 " + (String)o + " 无交易权限");
              } else {
                lst.add((Map)map.get(o));
              }
            }
          }
        }
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else if (prvg.getQuotePoint() == null)
        {
          ret = -206;
          message = "交易员报价点差为空！";
        }
        else if (prvg.getMyQuotePoint() == null)
        {
          ret = -207;
          message = "交易员自己报价点差为空！";
        }
        else
        {
          for (Map element : lst) {
            if (element != null)
            {
              String commodityID = 
                (String)element.get("CommodityID");
              
              Map<String, BigDecimal> quotePointMap = 
                (Map)prvg.getQuotePoint().get(commodityID);
              if (quotePointMap == null)
              {
                ret = -207;
                message = "交易员商品 " + commodityID + "的报价点差为空！";
              }
              else
              {
                BigDecimal quotePoint_B = 
                  (BigDecimal)quotePointMap.get("QuotePoint_B_RMB");
                BigDecimal quotePoint_S = 
                  (BigDecimal)quotePointMap.get("QuotePoint_S_RMB");
                if (quotePoint_B == null)
                {
                  ret = -207;
                  message = "交易员商品 " + commodityID + 
                    "的买报价点差为空！";
                }
                else if (quotePoint_S == null)
                {
                  ret = -207;
                  message = "交易员商品 " + commodityID + 
                    "的卖报价点差为空！";
                }
                else
                {
                  element.put("QuotePoint_B_RMB", 
                    quotePoint_B);
                  element.put("QuotePoint_S_RMB", 
                    quotePoint_S);
                }
              }
              Map<String, BigDecimal> mQuotePointMap = 
                (Map)prvg.getMyQuotePoint().get(commodityID);
              if (mQuotePointMap == null)
              {
                ret = -207;
                message = "交易员商品 " + commodityID + "自己的报价点差为空！";
              }
              else
              {
                BigDecimal quotePoint_B = 
                  (BigDecimal)mQuotePointMap.get("QuotePoint_B_RMB");
                BigDecimal quotePoint_S = 
                  (BigDecimal)mQuotePointMap.get("QuotePoint_S_RMB");
                if (quotePoint_B == null)
                {
                  ret = -207;
                  message = "交易员商品 " + commodityID + 
                    "自己的买报价点差为空！";
                }
                else if (quotePoint_S == null)
                {
                  ret = -207;
                  message = "交易员商品 " + commodityID + 
                    "自己的卖报价点差为空！";
                }
                else
                {
                  element.put("M_QuotePoint_B_RMB", 
                    quotePoint_B);
                  element.put("M_QuotePoint_S_RMB", 
                    quotePoint_S);
                }
              }
            }
          }
          responseMCDQ.setLst(lst);
        }
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("commodity_data_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("commodity_data_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (TimeOutException e)
    {
      this.log.error("m_commodity_data_query getPrivilege  exception" + 
        e.getMessage());
      
      ret = -2001;
      message = e.getMessage();
    }
    catch (Exception e)
    {
      this.log.error("commodity_data_query error:" + e);
      e.printStackTrace();
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    responseMCDQ.setRetCode(ret);
    responseMCDQ.setMessage(message);
    return responseMCDQ;
  }
  
  protected void commodity_data_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'commodity_data_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.debug("userid=" + userID + " commodity_data_query!");
      
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        





        List<Map> lst = new ArrayList();
        Map map = mgr.getQuotationMap();
        if (map != null) {
          if ((COMMODITY_ID != null) && (COMMODITY_ID.length() > 0))
          {
            if (prvg.getNoDisplayPrivilege().containsKey(
              COMMODITY_ID)) {
              this.log.info("商品 " + COMMODITY_ID + " 无交易权限");
            } else {
              lst.add((Map)map.get(COMMODITY_ID));
            }
          }
          else {
            for (Object o : map.keySet()) {
              if (prvg.getNoDisplayPrivilege().containsKey(
                (String)o)) {
                this.log.info("商品 " + (String)o + " 无交易权限");
              } else {
                lst.add((Map)map.get(o));
              }
            }
          }
        }
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else if (prvg.getQuotePoint() == null)
        {
          ret = -206;
          message = "交易员报价点差为空！";
        }
        else
        {
          for (Map element : lst)
          {
            String commodityID = 
              (String)element.get("CommodityID");
            
            Map<String, BigDecimal> quotePointMap = 
              (Map)prvg.getQuotePoint().get(commodityID);
            if (quotePointMap == null)
            {
              ret = -207;
              message = "交易员商品代为" + commodityID + "的报价点差为空！";
            }
            else
            {
              BigDecimal quotePoint_B = 
                (BigDecimal)quotePointMap.get("QuotePoint_B_RMB");
              BigDecimal quotePoint_S = 
                (BigDecimal)quotePointMap.get("QuotePoint_S_RMB");
              
              element.put("QuotePoint_B_RMB", quotePoint_B);
              element.put("QuotePoint_S_RMB", quotePoint_S);
            }
          }
          rr.setResultList(lst);
        }
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("commodity_data_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("commodity_data_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("commodity_data_query error:" + e);
      e.printStackTrace();
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.commodity_data_query(rr));
  }
  
  protected void commodity_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'commodity_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " commodity_query!");
      
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        Orders orders = new Orders();
        this.log.debug("-------Commodity=" + COMMODITY_ID);
        orders.setCommodityID(COMMODITY_ID);
        List lst = mgr.commodity_query(orders);
        for (int i = 0; i < lst.size(); i++)
        {
          Map map = (Map)lst.get(i);
          if (prvg.getNoDisplayPrivilege().containsKey(
            (String)map.get("CommodityID")))
          {
            lst.remove(i);
            i--;
          }
        }
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "commodity_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("commodity_query rmi conection exception" + ec);
      ret = -204;
      message = "RMI连接失败";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("commodity_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("commodity_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.commodity_query(rr, prvg));
  }
  
  protected void set_loss_profit(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'set_loss_profitelse' method");
    }
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " set_loss_profit!");
      
      Long H_ID = new Long(getValueByTagName(xml, "H_ID"));
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      
      int TY = new Integer(getValueByTagName(xml, "TY")).intValue();
      Double STOP_LOSS = new Double(getValueByTagName(xml, "STOP_LOSS"));
      Double STOP_PROFIT = new Double(getValueByTagName(xml, 
        "STOP_PROFIT"));
      

      boolean isAgency = false;
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0))
        {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
          isAgency = true;
        }
      }
      if (prvg == null)
      {
        rr.setRetCode(-201);
        rr.setMessage("身份不合法，请重新登陆！");
      }
      else
      {
        rr = PrivilegeController.checkSetLossProfit(prvg, COMMODITY_ID, 
          TY, STOP_LOSS.doubleValue(), STOP_PROFIT.doubleValue(), rr);
        if (rr.getRetCode() == 0)
        {
          String consinerID = "";
          if (isAgency) {
            consinerID = userID;
          }
          int ret = this.tradeRMI.setLossProfit(H_ID, STOP_LOSS, 
            STOP_PROFIT, consinerID);
          String message = "";
          switch (ret)
          {
          case 0: 
            break;
          case -1: 
            message = "不是交易时间!";
            break;
          case -2: 
            message = "撤销原止损单失败 !";
            break;
          case -3: 
            message = "撤销原止盈单失败!";
            break;
          case -10: 
            message = "对应的持仓不存在!";
            break;
          case -9: 
          case -8: 
          case -7: 
          case -6: 
          case -5: 
          case -4: 
          default: 
            this.log.error("-->unconfirm ret:" + ret);
            ret = -203;
            message = "返回值无法确认或交易系统异常！(ret=" + ret + ")";
          }
          rr.setRetCode(ret);
          rr.setMessage(message);
        }
      }
      this.log.info("userid=" + userID + "set_loss_profit result =" + 
        rr.getRetCode() + ";message=" + rr.getMessage() + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("set_loss_profit rmi conection exception" + ec);
      rr.setRetCode(-204);
      rr.setMessage("RMI连接失败！");
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("set_loss_profit remoteerror:" + e1);
      errorException(e1);
      rr.setRetCode(-204);
      rr.setMessage("下单服务器已关闭！");
    }
    catch (Exception e)
    {
      this.log.error("set_loss_profit error:" + e);
      errorException(e);
      rr.setRetCode(-203);
      rr.setMessage("未知异常！");
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), rr
      .getRetCode(), rr.getMessage()));
  }
  
  protected void withdraw_loss_profit(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'withdraw_loss_profit' method");
    }
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " withdraw_loss_profit!");
      
      Long H_ID = new Long(getValueByTagName(xml, "H_ID"));
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      
      Short TYPE = new Short(getValueByTagName(xml, "TYPE"));
      
      boolean isAgency = false;
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.info("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0))
        {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
          isAgency = true;
        }
      }
      if (prvg == null)
      {
        rr.setRetCode(-201);
        rr.setMessage("身份不合法，请重新登陆！");
      }
      else
      {
        rr = PrivilegeController.checkWithdrawLossProfit(prvg, 
          COMMODITY_ID, TYPE.shortValue(), rr);
        if (rr.getRetCode() == 0)
        {
          String consinerID = "";
          if (isAgency) {
            consinerID = userID;
          }
          int ret = this.tradeRMI.withdrawLossProfit(H_ID, TYPE, 
            consinerID);
          String message = "";
          switch (ret)
          {
          case 0: 
            break;
          case -1: 
            message = "撤单失败，此委托已成交或已撤单！";
            break;
          case -2: 
            message = "持仓不存在!";
            break;
          case -3: 
            message = "撤销止损止盈单失败！";
            break;
          case -4: 
            message = "撤销止盈成功撤销止损失败！";
            break;
          case -5: 
            message = "撤销止损成功撤销止盈失败！";
            break;
          case -99: 
            message = "不是交易时间，拒绝撤销止损止盈价！";
            break;
          case -100: 
            message = "执行撤单存储失败！";
            break;
          default: 
            this.log.error("-->unconfirm ret:" + ret);
            ret = -203;
            message = "返回值无法确认或交易系统异常！(ret=" + ret + ")";
          }
          rr.setRetCode(ret);
          rr.setMessage(message);
        }
      }
      this.log.info("userid=" + userID + "withdraw_loss_profit result =" + 
        rr.getRetCode() + ";message=" + rr.getMessage() + ";");
    }
    catch (ConnectException ec)
    {
      this.log.error("withdraw_loss_profit rmi conection exception" + ec);
      rr.setRetCode(-204);
      rr.setMessage("RMI连接失败！");
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("withdraw_loss_profit remoteerror:" + e1);
      errorException(e1);
      rr.setRetCode(-204);
      rr.setMessage("下单服务器已关闭！");
    }
    catch (Exception e)
    {
      this.log.error("withdraw_loss_profit error:" + e);
      errorException(e);
      rr.setRetCode(-203);
      rr.setMessage("未知异常！");
    }
    renderXML(response, ResponseXml.responseXml(rr.getName(), rr
      .getRetCode(), rr.getMessage()));
  }
  
  protected void firm_funds_info(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'firm_funds_info' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " firm_funds_info!");
      if (prvg.getFirmType().equals("0"))
      {
        ret = -1001;
        message = "客户身份不允许查综合会员资金信息！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        List lst = mgr.firm_funds_info(prvg);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "firm_funds_info result =" + ret + 
        ";message=" + message + ";");
    }
    catch (Exception e)
    {
      this.log.error("firm_funds_info error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.firm_funds_info(rr, prvg));
  }
  
  protected void customer_order_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'customer_order_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " customer_order_query!");
      
      String COMMODITY_ID = getValueByTagName(xml, "COMMODITY_ID");
      
      long sessionID = parseLong(getValueByTagName(xml, "SESSION_ID"));
      if (prvg.getFirmType().equals("0"))
      {
        ret = -1001;
        message = "客户身份不允许查客户下单情况！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        List lst = mgr.customer_order_query(prvg, COMMODITY_ID);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "customer_order_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (Exception e)
    {
      this.log.error("customer_order_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.customer_order_query(rr));
  }
  
  protected void other_firm_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'other_firm_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " other_firm_query!");
      
      String IS_D = getValueByTagName(xml, "IS_D");
      

      boolean isDefault = false;
      if ((IS_D != null) && (IS_D.equals("1"))) {
        isDefault = true;
      }
      OrdersManager mgr = getOrdersManager();
      List lst = null;
      if (isDefault) {
        lst = mgr.other_firm_query_S(prvg);
      } else {
        lst = mgr.other_firm_query(prvg);
      }
      if ((lst == null) || (lst.size() == 0))
      {
        ret = -202;
        message = "记录未找到！";
      }
      else
      {
        rr.setResultList(lst);
      }
      this.log.info("userid=" + userID + "other_firm_query result =" + ret + 
        ";message=" + message + ";");
    }
    catch (Exception e)
    {
      this.log.error("other_firm_query error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.other_firm_query(rr));
  }
  
  protected void firm_holdsum(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'firm_holdsum' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      this.log.info("userid=" + userID + " firm_holdsum!");
      if (prvg.getFirmType().equals("0"))
      {
        ret = -1001;
        message = "客户身份不允许查询会员持仓合计！";
      }
      else
      {
        OrdersManager mgr = getOrdersManager();
        List lst = mgr.firm_holdsum(prvg);
        if ((lst == null) || (lst.size() == 0))
        {
          ret = -202;
          message = "记录未找到！";
        }
        else
        {
          rr.setResultList(lst);
        }
      }
      this.log.info("userid=" + userID + "firm_holdsum result =" + ret + 
        ";message=" + message + ";");
    }
    catch (Exception e)
    {
      this.log.error("firm_holdsum error:" + e);
      errorException(e);
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.firm_holdsum(rr));
  }
  
  protected ResponseSTQ sys_time_query(HttpServletRequest request, RequestSTQ requestSTQ)
  {
    ResponseSTQ responseSTQ = new ResponseSTQ();
    int ret = 0;
    String message = "";
    boolean showTDRP = true;
    try
    {
      String userID = requestSTQ.getUserID();
      
      long last_id = requestSTQ.getLastID();
      
      long tradeNum = requestSTQ.getTradeCount();
      boolean CU_LG = requestSTQ.getCurLogon() == 1;
      showTDRP = !CU_LG;
      long sessionID = requestSTQ.getSessionID();
      
      Privilege prvg = getPrivilege(request, userID, sessionID, false);
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法，请重新登陆！";
      }
      else
      {
        if (prvg.getFirmType().equals("1"))
        {
          String agencyNO = requestSTQ.getAgencyNO();
          if ((agencyNO != null) && (agencyNO.length() > 0)) {
            this.log.debug("agencyNO=" + agencyNO);
          }
          String phonePWD = requestSTQ.getPhonePWD();
          if ((agencyNO != null) && (agencyNO.length() > 0)) {
            prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
              agencyNO, phonePWD);
          }
        }
        if (prvg == null)
        {
          ret = -201;
          message = "身份不合法！";
        }
        else
        {
          responseSTQ.setCurTime(DateUtil.Mills2Time(
            System.currentTimeMillis() + 
            TradeService.diff));
          responseSTQ.setCurDate(DateUtil.Mills2Date(
            System.currentTimeMillis() + 
            TradeService.diff));
          
          responseSTQ.setCurrentTimeMillis(System.currentTimeMillis() + 
            TradeService.diff);
          
          responseSTQ.setTradeDate(this.taken.tradeDay);
          if (this.taken == null)
          {
            OrdersManager mgr = getOrdersManager();
            this.taken = TradeService.getInstance(mgr, this);
          }
          String firmID = prvg.getFirmId();
          LinkedList link = 
            (LinkedList)this.taken.getTradeMap().get(firmID);
          byte isUpdate = 0;
          if (link != null)
          {
            if (link.size() > tradeNum)
            {
              ArrayList<Trade> list = new ArrayList();
              for (int i = (int)tradeNum; i < link.size(); i++)
              {
                Trade trade = (Trade)link.get(i);
                

                list.add(trade);
              }
              isUpdate = 1;
              responseSTQ.setLst(list);
            }
            responseSTQ.setTradeTotalCount(link.size());
          }
          if (showTDRP) {
            responseSTQ.setNewTrade(isUpdate);
          } else {
            responseSTQ.setNewTrade((byte)0);
          }
          BigDecimal maxID = 
            (BigDecimal)this.taken.getBroadcastMap().get(firmID);
          if (maxID != null) {
            responseSTQ.setLastID(maxID.longValue());
          }
          responseSTQ.setSysStatus(this.taken.getSysStatus().getStatus());
        }
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("sys_time_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("sys_time_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (TimeOutException e)
    {
      this.log.error("sys_time_query getPrivilege  exception" + 
        e.getMessage());
      
      ret = -2001;
      message = e.getMessage();
    }
    catch (Exception e)
    {
      this.log.error("sys_time_query error:" + e);
      errorException(e);
      e.printStackTrace();
      ret = -203;
      message = "未知异常！";
    }
    responseSTQ.setRetCode(ret);
    responseSTQ.setMessage(message);
    return responseSTQ;
  }
  
  protected void sys_time_query(HttpServletRequest request, HttpServletResponse response, String xml, ResponseResult rr, Privilege prvg)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'sys_time_query' method xml=" + xml);
    }
    int ret = 0;
    String message = "";
    boolean showTDRP = true;
    try
    {
      String userID = getValueByTagName(xml, "USER_ID");
      
      String LAST_ID = getValueByTagName(xml, "LAST_ID");
      long last_id = 0L;
      if ((LAST_ID != null) && (!LAST_ID.equals(""))) {
        last_id = parseLong(LAST_ID);
      }
      int tradeNum = "".equals(getValueByTagName(xml, "TD_CNT")) ? 0 : 
        Integer.parseInt(getValueByTagName(xml, "TD_CNT"));
      boolean CU_LG = "1".equals(getValueByTagName(xml, "CU_LG"));
      
      showTDRP = !"1".equals(getValueByTagName(xml, "CU_LG"));
      if (prvg.getFirmType().equals("1"))
      {
        String agencyNO = getValueByTagName(xml, "A_N");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          this.log.debug("agencyNO=" + agencyNO);
        }
        String phonePWD = getValueByTagName(xml, "P_P");
        if ((agencyNO != null) && (agencyNO.length() > 0)) {
          prvg = getAgencyPrivilege(request, prvg.getFirmId(), 
            agencyNO, phonePWD);
        }
      }
      if (prvg == null)
      {
        ret = -201;
        message = "身份不合法！";
      }
      else
      {
        List result = new ArrayList();
        Map map = new HashMap();
        


        map.put("CUR_TIME", DateUtil.Mills2Time(
          System.currentTimeMillis() + 
          TradeService.diff));
        map.put("CUR_DATE", DateUtil.Mills2Date(
          System.currentTimeMillis() + 
          TradeService.diff));
        map.put("TV_USEC", String.valueOf(System.currentTimeMillis() + 
          TradeService.diff));
        if (this.taken == null)
        {
          OrdersManager mgr = getOrdersManager();
          this.taken = TradeService.getInstance(mgr, this);
        }
        String firmID = prvg.getFirmId();
        LinkedList link = (LinkedList)this.taken.getTradeMap().get(firmID);
        int isUpdate = 0;
        if (link != null)
        {
          if (link.size() > tradeNum)
          {
            ArrayList list = new ArrayList();
            for (int i = tradeNum; i < link.size(); i++)
            {
              Trade trade = (Trade)link.get(i);
              

              list.add(trade);
            }
            isUpdate = 1;
            map.put("Trades", list);
          }
          map.put("TD_TTL", Integer.valueOf(link.size()));
        }
        map.put("NEW_T", Integer.valueOf(showTDRP ? isUpdate : 0));
        
        BigDecimal maxID = 
          (BigDecimal)this.taken.getBroadcastMap().get(firmID);
        if (maxID != null) {
          map.put("L_ID", Long.valueOf(maxID.longValue()));
        }
        if (this.taken.getSysStatus() != null) {
          map.put("SysStatus", Integer.valueOf(this.taken.getSysStatus().getStatus()));
        }
        result.add(map);
        rr.setResultList(result);
      }
    }
    catch (ConnectException ec)
    {
      this.log.error("sys_time_query rmi conection exception" + ec);
      ret = -202;
      message = "RMI连接失败！";
      initRMI();
    }
    catch (RemoteException e1)
    {
      this.log.error("sys_time_query remoteerror:" + e1);
      errorException(e1);
      ret = -204;
      message = "下单服务器已关闭！";
    }
    catch (Exception e)
    {
      this.log.error("sys_time_query error:" + e);
      errorException(e);
      e.printStackTrace();
      ret = -203;
      message = "未知异常！";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    
    renderXML(response, ResponseXml.sys_time_query(rr, this.taken.tradeDay, 
      showTDRP));
  }
  
  private ResponseResult submitOrderS(long sessionID, MarketOrder marketOrder, ResponseResult rr, boolean isAgency)
    throws Exception
  {
    int ret = 0;
    if (marketOrder.getQuantity().longValue() > 0L)
    {
      if (isAgency) {
        ret = this.tradeRMI.consignerMarketOrder(sessionID, marketOrder);
      } else {
        ret = this.tradeRMI.marketOrder(sessionID, marketOrder);
      }
    }
    else {
      ret = 51;
    }
    String message = "";
    switch (ret)
    {
    case 0: 
      break;
    case 1: 
      message = "身份不合法！";
      break;
    case 2: 
      message = "开平仓标志不能为空！";
      break;
    case 3: 
      message = "现在不是交易时间！";
      break;
    case 4: 
      message = "不是代为委托员交易时间！";
      break;
    case 5: 
      message = "交易员和代为委托员不能同时存在！";
      break;
    case 10: 
      message = "商品处于禁止交易状态！";
      break;
    case 11: 
      message = "商品不属于当前交易节！";
      break;
    case 12: 
      message = "委托价格超出涨幅上限！";
      break;
    case 13: 
      message = "委托价格低于跌幅下限！";
      break;
    case 14: 
      message = "委托价格不在此商品最小价位变动范围内！";
      break;
    case 34: 
      message = "此交易商不存在！";
      break;
    case 35: 
      message = "此交易商为禁止交易状态！";
      break;
    case 37: 
      message = "客户代码不存在！";
      break;
    case 38: 
      message = "客户不属于此会员！";
      break;
    case 40: 
      message = "计算交易保证金错误！";
      break;
    case 41: 
      message = "计算交易手续费错误！";
      break;
    case 50: 
      message = "大于单笔委托量限制！";
      break;
    case 51: 
      message = "小于单笔委托量限制！";
      break;
    case 60: 
      message = "市价委托价格不能成交！";
      break;
    case 70: 
      message = "没有收到行情数据,不能开始交易！";
      break;
    case 200: 
      message = "代码异常而失败！";
      break;
    case -1: 
      message = "资金余额不足！";
      break;
    case -2: 
      message = "超过交易商对此商品的最大订货量！";
      break;
    case -21: 
      message = "持仓不足！";
      break;
    case -22: 
      message = "指定仓不足！";
      break;
    case -31: 
      message = "客户和会员对应关系不正确";
      break;
    case -32: 
      message = "您的账户被冻结";
      break;
    case -33: 
      message = "没有可用特别会员";
      break;
    case -34: 
      message = "非客户头寸交易比例";
      break;
    case -99: 
      message = "执行存储时未找到相关数据！";
      break;
    case -100: 
      message = "执行存储失败！";
      break;
    case -204: 
      message = "交易服务器已关闭！";
      break;
    case -206: 
      message = "委托信息不能为空！";
      break;
    case -207: 
      message = "委托价不能为0！";
      break;
    default: 
      this.log.error("-->unconfirm ret:" + ret);
      ret = -203;
      message = "返回值无法确认或交易系统异常！(ret=" + ret + ")";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    return rr;
  }
  
  private ResponseResult withdrawOrder(long sessionID, WithdrawOrder withdrawOrder, ResponseResult rr, boolean isAgency)
    throws Exception
  {
    int ret = 0;
    if (isAgency) {
      ret = this.tradeRMI.consignerWithdrawOrder(sessionID, withdrawOrder);
    } else {
      ret = this.tradeRMI.withdrawOrder(sessionID, withdrawOrder);
    }
    String message = "";
    switch (ret)
    {
    case 0: 
      break;
    case 1: 
      message = "身份不合法！";
      break;
    case 3: 
      message = "不是交易时间！";
      break;
    case 4: 
      message = "不是代为委托员交易时间！";
      break;
    case 5: 
      message = "交易员和代为委托员不能同时存在！";
      break;
    case 34: 
      message = "此交易商不存在！";
      break;
    case 35: 
      message = "此交易商为禁止交易状态！";
      break;
    case 37: 
      message = "客户代码不存在！";
      break;
    case 38: 
      message = "客户不属于此会员！";
      break;
    case 42: 
      message = "此委托已成交或已撤单!";
      break;
    case 50: 
      message = "大于单笔委托量限制!";
      break;
    case 51: 
      message = "小于单笔委托量限制!";
      break;
    case 70: 
      message = "没有收到行情数据,不能开始交易！";
      break;
    case 200: 
      message = "代码异常而失败！";
      break;
    case -100: 
      message = "执行存储失败！";
      break;
    case -204: 
      message = "交易服务器已关闭！";
      break;
    case -206: 
      message = "委托信息不能为空！";
      break;
    default: 
      this.log.error("-->unconfirm ret:" + ret);
      ret = -203;
      message = "返回值无法确认或交易系统异常！(ret=" + ret + ")";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    return rr;
  }
  
  private ResponseResult submitOrderX(long sessionID, LimitOrder limitOrder, ResponseResult rr, boolean isAgency)
    throws Exception
  {
    int ret = 0;
    if (limitOrder.getQuantity().longValue() > 0L)
    {
      if (isAgency) {
        ret = this.tradeRMI.consignerLimitOrder(sessionID, limitOrder);
      } else {
        ret = this.tradeRMI.limitOrder(sessionID, limitOrder);
      }
    }
    else {
      ret = 51;
    }
    String message = "";
    switch (ret)
    {
    case 0: 
      break;
    case 1: 
      message = "身份不合法！";
      break;
    case 3: 
      message = "现在不是交易时间！";
      break;
    case 4: 
      message = "不是代为委托员交易时间！";
      break;
    case 5: 
      message = "交易员和代为委托员不能同时存在！";
      break;
    case 10: 
      message = "商品处于禁止交易状态！";
      break;
    case 11: 
      message = "商品不属于当前交易节！";
      break;
    case 12: 
      message = "委托价格超出涨幅上限！";
      break;
    case 13: 
      message = "委托价格低于跌幅下限！";
      break;
    case 14: 
      message = "委托价格不在此商品最小价位变动范围内！";
      break;
    case 15: 
      message = "不存在此商品！";
      break;
    case 34: 
      message = "此交易商不存在！";
      break;
    case 35: 
      message = "此交易商为禁止交易状态！";
      break;
    case 37: 
      message = "客户代码不存在！";
      break;
    case 38: 
      message = "客户不属于此会员！";
      break;
    case 40: 
      message = "计算交易保证金错误！";
      break;
    case 41: 
      message = "计算交易手续费错误！";
      break;
    case 50: 
      message = "大于单笔委托量限制！";
      break;
    case 51: 
      message = "小于单笔委托量限制！";
      break;
    case 70: 
      message = "没有收到行情数据,不能开始交易！";
      break;
    case 200: 
      message = "代码异常而失败！";
      break;
    case -1: 
      message = "资金余额不足！";
      break;
    case -2: 
      message = "超过交易商对此商品的最大订货量！";
      break;
    case -31: 
      message = "客户和会员对应关系不正确";
      break;
    case -32: 
      message = "您的账户被冻结";
      break;
    case -33: 
      message = "没有可用特别会员";
      break;
    case -99: 
      message = "执行存储时未找到相关数据！";
      break;
    case -100: 
      message = "执行存储失败！";
      break;
    case -204: 
      message = "交易服务器已关闭！";
      break;
    case -206: 
      message = "委托信息不能为空！";
      break;
    case -207: 
      message = "委托价不能为0！";
      break;
    default: 
      this.log.error("-->unconfirm ret:" + ret);
      ret = -203;
      message = "返回值无法确认或交易系统异常！(ret=" + ret + ")";
    }
    rr.setRetCode(ret);
    rr.setMessage(message);
    return rr;
  }
  
  private Privilege getPrivilege(HttpServletRequest request, String traderID, long sessionID, boolean isCheckLogon)
    throws Exception
  {
    ResponseResult rr = new ResponseResult();
    if (!checkRmi(rr))
    {
      String errorInfo = "chekRmi Error recode=" + rr.getLongRetCode() + 
        " Message=" + rr.getMessage();
      this.log.error(errorInfo);
      
      throw new ConnectException(errorInfo);
    }
    Privilege privilege = (Privilege)request.getSession().getAttribute(
      "privilege");
    

    Date preIsLogonTime = (Date)request.getSession().getAttribute(
      "preIsLogonTime");
    
    this.log.debug("getPrivilege");
    if ((isCheckLogon) || (privilege == null) || (preIsLogonTime == null))
    {
      int logonStatus = this.tradeRMI.getLogonStatus(traderID, sessionID);
      if (logonStatus != 0)
      {
        if (logonStatus == 2) {
          return null;
        }
        throw new TimeOutException("由于您长时间没有操作,所以您的用户状态已失效,请重新登录");
      }
      if (privilege == null)
      {
        this.log.warn("====>" + traderID + 
          "Reload privilege session ..");
        TraderInfo info = this.tradeRMI.getTraderInfo(traderID);
        
        OrdersManager mgr = getOrdersManager();
        privilege = mgr.getradePrivilege(info);
        privilege.setLogonIP(request.getRemoteAddr());
        request.getSession().setAttribute("privilege", privilege);
        this.log.warn("userID=" + privilege.getFirmId() + 
          "Reload privilege session");
        this.log.warn("PrivilegeObj:" + privilege);
      }
      if (preIsLogonTime == null)
      {
        this.log.warn("====>" + traderID + 
          "Reload preIsLogonTime session ..");
        preIsLogonTime = new Date();
        request.getSession().setAttribute("preIsLogonTime", 
          preIsLogonTime);
        this.log.warn("preIsLogonTime Time:" + 
          DateUtil.getDateTime(preIsLogonTime));
      }
      return privilege;
    }
    if (preIsLogonTime == null) {
      this.log.error("preIsLogonTime  is NULL ");
    }
    if (System.currentTimeMillis() - preIsLogonTime.getTime() > 20000L)
    {
      int logonStatus = this.tradeRMI.getLogonStatus(traderID, sessionID);
      if (logonStatus != 0)
      {
        if (logonStatus == 2) {
          return null;
        }
        throw new TimeOutException(
          "由于您长时间没有操作,所以您的用户状态已失效,请重新登录");
      }
      this.log.debug("====>Reload preIsLogonTime session ..");
      request.getSession().setAttribute("preIsLogonTime", new Date());
      this.log.debug("preIsLogonTime Time:" + 
        DateUtil.getDateTime(preIsLogonTime));
    }
    return privilege;
  }
  
  private Privilege getAgencyPrivilege(HttpServletRequest request, String firmID, String agencyNO, String phonePWD)
    throws Exception
  {
    ResponseResult rr = new ResponseResult();
    if (!checkRmi(rr))
    {
      String errorInfo = "chekRmi Error recode=" + rr.getLongRetCode() + 
        " Message=" + rr.getMessage();
      this.log.error(errorInfo);
      
      throw new ConnectException(errorInfo);
    }
    Privilege agencyPrivilege = (Privilege)request.getSession()
      .getAttribute("agencyPrivilege");
    
    this.log.debug("getAgencyPrivilege");
    
    long result = this.tradeRMI.checkDelegateInfo(firmID, agencyNO, phonePWD);
    if (result != 0L)
    {
      String message = "";
      if (result == -1L) {
        message = "客户 " + agencyNO + " 不属于会员" + firmID + "！";
      } else if (result == -2L) {
        message = "客户代码不存在！";
      } else if (result == -3L) {
        message = "电话密码不正确！";
      } else {
        message = "其他错误！";
      }
      this.log.warn("getAgencyPrivilege " + message);
      return null;
    }
    if (agencyPrivilege == null)
    {
      this.log.warn("====>Reload agencyPrivilege session ..");
      
      TraderInfo info = this.tradeRMI.getTraderInfo(agencyNO);
      
      OrdersManager mgr = getOrdersManager();
      agencyPrivilege = mgr.getradePrivilege(info);
      
      agencyPrivilege.setLogonIP(request.getRemoteAddr());
      request.getSession().setAttribute("agencyPrivilege", 
        agencyPrivilege);
      this.log.warn("agencyNO=" + agencyPrivilege.getFirmId() + 
        "Reload privilege session");
      this.log.warn("PrivilegeObj:" + agencyPrivilege);
    }
    return agencyPrivilege;
  }
  
  protected void renderXML(HttpServletResponse response, String content)
  {
    try
    {
      response.setContentType("text/xml;charset=GBK");
      response.getWriter().print(content);
    }
    catch (IOException e)
    {
      this.log.error(e.getMessage(), e);
    }
  }
  
  protected Request readBinaryFromRequestBody(HttpServletRequest request)
  {
    Request requestVO = null;
    ByteArrayOutputStream array = null;
    DataOutputStream outputArray = null;
    try
    {
      array = new ByteArrayOutputStream();
      outputArray = new DataOutputStream(array);
      ServletInputStream stream = request.getInputStream();
      byte[] tempByte = new byte[1];
      while (stream.read(tempByte) != -1) {
        outputArray.write(tempByte);
      }
      outputArray.flush();
      byte[] buf = array.toByteArray();
      outputArray.close();
      
      ByteBuffer buffer = ByteBuffer.wrap(buf);
      buffer.order(ByteOrder.nativeOrder());
      
      requestVO = Request.getRequest(buffer);
    }
    catch (Exception e)
    {
      this.log.error(e.getMessage(), e);
      if (outputArray != null) {
        try
        {
          outputArray.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.log.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.log.warn("array.close occur IOException:" + e.getMessage());
        }
      }
    }
    finally
    {
      if (outputArray != null) {
        try
        {
          outputArray.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.log.warn("outputArray.close occur IOException:" + 
            e.getMessage());
        }
      }
      if (array != null) {
        try
        {
          array.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
          this.log.warn("array.close occur IOException:" + e.getMessage());
        }
      }
    }
    return requestVO;
  }
  
  protected String readXMLFromRequestBody(HttpServletRequest request)
    throws Exception
  {
    StringBuffer xml = new StringBuffer();
    


    BufferedReader reader = request.getReader();
    
    String line = null;
    while ((line = reader.readLine()) != null) {
      xml.append(line);
    }
    return xml.toString();
  }
  
  public Object getBean(String name)
  {
    if (ctx == null) {
      ctx = 
        WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
    return ctx.getBean(name);
  }
  
  public void errorException(Exception e)
  {
    StackTraceElement[] ste = e.getStackTrace();
    this.log.error(e.getMessage());
    for (int i = 0; i < ste.length; i++) {
      this.log.error(ste[i].toString());
    }
  }
  
  public void setServerRMI(ServerRMI serverRMI)
  {
    this.serverRMI = serverRMI;
  }
  
  public synchronized ServerRMI getServerRMI()
  {
    if (this.serverRMI == null) {
      try
      {
        initRMI();
      }
      catch (Exception e)
      {
        this.log.error(e);
      }
    }
    return this.serverRMI;
  }
  
  private void initRMI()
  {
    this.serverRMI = null;
    this.strServerRMI = null;
    lookUPRMI();
  }
  
  private void createLogonManager()
  {
    System.out.println("******************启动**********************");
    LogonManager.createInstance("4", (DataSource)getBean("dataSource"));
    System.out.println("*****************启动成功******************");
  }
  
  private synchronized void lookUPRMI()
  {
    if ((this.serverRMI != null) && (this.tradeRMI != null)) {
      return;
    }
    this.log.info("=========initRMI Start=============");
    
    Map rmiMap = LogonManager.getRMIConfig("2", 
      (DataSource)getBean("dataSource"));
    StringBuffer sb = new StringBuffer();
    sb.append("rmi://").append(rmiMap.get("host")).append(":").append(
      rmiMap.get("port")).append("/TradeRMI");
    this.strTradeRMI = sb.toString();
    
    StringBuffer sb2 = new StringBuffer();
    sb2.append("rmi://").append(rmiMap.get("host")).append(":").append(
      rmiMap.get("port")).append("/ServerRMI");
    this.strServerRMI = sb2.toString();
    try
    {
      this.tradeRMI = ((TradeRMI)Naming.lookup(this.strTradeRMI));
      this.serverRMI = ((ServerRMI)Naming.lookup(this.strServerRMI));
      this.log.info("=========initRMI Success =============");
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
      this.log
        .info("=========initRMI Failure MalformedURLException=============");
      this.tradeRMI = null;
      this.serverRMI = null;
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
      this.log.info("=========initRMI Failure RemoteException=============");
      this.tradeRMI = null;
      this.serverRMI = null;
    }
    catch (NotBoundException e)
    {
      e.printStackTrace();
      this.log.info("=========initRMI Failure NotBoundException=============");
      this.tradeRMI = null;
      this.serverRMI = null;
    }
  }
  
  protected String getReqNameByXml(String xml)
  {
    String reqName = "";
    String upXml = xml.toUpperCase();
    int namePos = upXml.indexOf("NAME=");
    if (namePos < 0) {
      return reqName;
    }
    int reqEndPos = upXml.indexOf(">", namePos);
    if (reqEndPos < 0) {
      return reqName;
    }
    String name = upXml.substring(namePos + 5, reqEndPos);
    name = name.replaceAll("'", "");
    name = name.replaceAll("\"", "");
    name = name.trim();
    reqName = name.toLowerCase();
    return reqName;
  }
  
  private String getValueByTagName(String xml, String tagName)
  {
    String tagValue = "";
    String replaceXml = xml.replaceAll("</", "<");
    String[] result = replaceXml.split("<" + tagName + ">");
    if (result.length > 1) {
      tagValue = result[1];
    }
    return tagValue;
  }
  
  public void initOrderKeyMap()
  {
    this.orderKeyMap = new HashMap();
    this.orderKeyMap.put("OR_N", "A_OrderNo");
    this.orderKeyMap.put("TIME", "OrderTime");
    this.orderKeyMap.put("STA", "Status");
    this.orderKeyMap.put("TYPE", "BS_Flag");
    this.orderKeyMap.put("SE_F", "OrderType");
    this.orderKeyMap.put("TR_I", "TraderID");
    this.orderKeyMap.put("FI_I", "FirmID");
    this.orderKeyMap.put("CU_I", "CustomerID");
    this.orderKeyMap.put("CO_I", "OrderTime");
    this.orderKeyMap.put("PRI", "CommodityID");
    this.orderKeyMap.put("QTY", "Quantity");
    this.orderKeyMap.put("BAL", "notTradeQty");
    this.orderKeyMap.put("L_P", "SpecPrice");
    this.orderKeyMap.put("WD_T", "WithdrawTime");
  }
  
  public void initHoldKeyMap()
  {
    this.holdKeyMap = new HashMap();
    this.holdKeyMap.put("CO_I", "commodityid");
    this.holdKeyMap.put("CU_I", "customerid");
    this.holdKeyMap.put("BU_H", "buyQty");
    this.holdKeyMap.put("SE_H", "sellQty");
    this.holdKeyMap.put("B_V_H", "buyAvailQty");
    this.holdKeyMap.put("S_V_H", "sellAvailQty");
    this.holdKeyMap.put("BU_A", "buyEvenPrice");
    this.holdKeyMap.put("SE_A", "sellEvenPrice");
    this.holdKeyMap.put("GO_Q", "gageqty");
    this.holdKeyMap.put("FL_P", "bsPL");
    this.holdKeyMap.put("MAR", "holdmargin");
  }
  
  private long parseLong(String str)
  {
    try
    {
      return Long.parseLong(str);
    }
    catch (NumberFormatException e) {}
    return 0L;
  }
  
  public static void main(String[] args)
    throws IOException
  {
    String s = "N";
    System.out.println(ByteOrder.nativeOrder());
    int _int = 12345678;
    byte b = 49;
    ByteBuffer _nbuffer = ByteBuffer.allocate(1024);
    _nbuffer.order(ByteOrder.nativeOrder());
    
    _nbuffer.put(b);
    _nbuffer.putInt(_int);
    _nbuffer.putDouble(30.212D);
    _nbuffer.put("String字符串".getBytes("UTF-8"));
    _nbuffer.putLong(123454321L);
    _nbuffer.flip();
    
    FileOutputStream _fou = new FileOutputStream("d:\\test_dout.data");
    FileOutputStream _nfou = new FileOutputStream("d:\\test_nbuf.data");
    DataOutputStream _dou = new DataOutputStream(_fou);
    
    _dou.writeInt(_int);
    _dou.close();
    
    _nfou.write(_nbuffer.array());
    _nfou.close();
    
    String filename = "d:\\test_nbuf.data";
    FileChannel channel = new FileInputStream(filename).getChannel();
    
    ByteBuffer buffer = ByteBuffer.allocate(36);
    buffer.clear();
    int count = channel.read(buffer);
    buffer.order(ByteOrder.nativeOrder());
    buffer.flip();
    
    System.out.println(buffer.get());
    System.out.println(buffer.getInt());
    System.out.println(buffer.getDouble());
    byte[] dst = new byte[15];
    buffer.get(dst);
    System.out.println(new String(dst, "UTF-8"));
    System.out.println(buffer.getLong());
  }
}
