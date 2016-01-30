package gnnt.MEBS.common.front.action;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.common.front.common.ActiveUserManager;
import gnnt.MEBS.common.front.common.Global;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.service.UserService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.common.front.statictools.filetools.XMLWork;
import gnnt.MEBS.common.front.vo.CheckUserChild;
import gnnt.MEBS.common.front.vo.CheckUserXML;
import gnnt.MEBS.common.front.vo.LogonChild;
import gnnt.MEBS.common.front.vo.LogonXML;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("com_userAction")
@Scope("request")
public class UserAction
  extends StandardAction
{
  private static final long serialVersionUID = 8849362928513184976L;
  @Autowired
  @Qualifier("com_userService")
  private UserService com_userService;
  @Autowired
  @Resource(name="com_firmStatusMap")
  protected Map<String, String> com_firmStatusMap;
  @Autowired
  @Resource(name="com_firmTypeMap")
  protected Map<String, String> com_firmTypeMap;
  
  public StandardService getService()
  {
    return this.com_userService;
  }
  
  public Map<String, String> getCom_firmStatusMap()
  {
    return this.com_firmStatusMap;
  }
  
  public Map<String, String> getCom_firmTypeMap()
  {
    return this.com_firmTypeMap;
  }
  
  public String checkLogon()
  {
    String str1 = "";
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.request.getInputStream()));
      String str2 = localBufferedReader.readLine();
      StringBuffer localStringBuffer = new StringBuffer();
      while (str2 != null)
      {
        localStringBuffer.append(str2);
        str2 = localBufferedReader.readLine();
      }
      String str3 = localStringBuffer.toString();
      this.logger.info("客户端传入：" + str3);
      if (str3.indexOf("check_user") > 0) {
        str1 = checkUser(str3);
      } else if (str3.indexOf("logon") > 0) {
        str1 = checkLogon(str3);
      } else if (str3.indexOf("logoff") > 0) {
        str1 = checkLogoff(str3);
      }
      this.logger.info("回复客户端：" + str1);
    }
    catch (Exception localException)
    {
      this.logger.error("调用 checkLogon 方法异常", localException);
    }
    try
    {
      this.response.setContentType("text/html;charset=GBK");
      this.response.getWriter().print(str1);
      this.response.getWriter().close();
    }
    catch (IOException localIOException)
    {
      this.logger.error("返回登录信息时异常", localIOException);
    }
    return null;
  }
  
  private String checkLogon(String paramString)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = -1;
    LogonXML localLogonXML = (LogonXML)XMLWork.reader(LogonXML.class, paramString);
    if ((localLogonXML != null) && (localLogonXML.req != null) && ("logon".equals(localLogonXML.req.name)))
    {
      str1 = localLogonXML.req.userID;
      str2 = localLogonXML.req.password;
      str3 = localLogonXML.req.key;
      str4 = localLogonXML.req.logonType;
      i = Tools.strToInt(localLogonXML.req.selfModuleID, Global.getSelfModuleID());
    }
    String str5 = "0";
    String str6 = "";
    TraderLogonInfo localTraderLogonInfo = null;
    try
    {
      localTraderLogonInfo = ActiveUserManager.logon(str1, str2, i, str3, "", this.request.getRemoteAddr(), 0, str4);
    }
    catch (Exception localException)
    {
      this.logger.error("执行 checkLogon 登录异常", localException);
    }
    if (localTraderLogonInfo == null)
    {
      str5 = "-1";
      str6 = "登录失败，请联系管理员";
      return "<?xml version=\"1.0\" encoding=\"GB2312\"?><GNNT><REP name=\"logon\"><RESULT><RETCODE>" + str5 + "</RETCODE>" + "<MESSAGE>" + str6 + "</MESSAGE><MODULE_ID>" + i + "</MODULE_ID>" + "<LAST_TIME></LAST_TIME><LAST_IP></LAST_IP><CHG_PWD></CHG_PWD><NAME></NAME><RANDOM_KEY></RANDOM_KEY></RESULT></REP></GNNT>";
    }
    int j = Tools.strToInt(localTraderLogonInfo.getRecode(), 1);
    if ((j == -1) || (j == -2))
    {
      this.logger.debug("用户不存在或密码错误");
      str5 = "-1";
      str6 = "用户不存在或密码错误";
    }
    else if ((j == -3) || (j == -6) || (j == -7))
    {
      this.logger.debug("禁止登录");
      str5 = "-1";
      str6 = "禁止登录";
    }
    else if (j == -4)
    {
      this.logger.debug("Key盘验证错误");
      str5 = "-1";
      str6 = "Key盘验证错误";
    }
    else if (j == -5)
    {
      this.logger.debug("交易板块被禁止");
      str5 = "-1";
      str6 = "交易板块被禁止";
    }
    else if (j < 0)
    {
      this.logger.debug("核心系统返回异常编号[" + j + "]，错误信息[" + localTraderLogonInfo.getMessage() + "]");
      str5 = "-1";
      str6 = "登录失败，" + localTraderLogonInfo.getMessage();
    }
    if (j > 0)
    {
      boolean bool = ActiveUserManager.logon(localTraderLogonInfo.getTraderId(), this.request, localTraderLogonInfo.getSessionID(), str4, i);
      if (!bool)
      {
        this.logger.error("登录时，向 Session 中写入信息失败");
        str5 = "-1";
        str6 = "加载内存失败";
      }
      else
      {
        str5 = "" + localTraderLogonInfo.getSessionID();
      }
    }
    String str7 = "<?xml version=\"1.0\" encoding=\"GB2312\"?><GNNT><REP name=\"logon\"><RESULT><RETCODE>" + str5 + "</RETCODE>" + "<MESSAGE>" + str6 + "</MESSAGE><MODULE_ID>" + i + "</MODULE_ID>" + "<LAST_TIME>" + localTraderLogonInfo.getLastTime() + "</LAST_TIME>" + "<LAST_IP>" + localTraderLogonInfo.getLastIP() + "</LAST_IP>" + "<CHG_PWD>" + (localTraderLogonInfo.getForceChangePwd() == 1 ? 1 : 2) + "</CHG_PWD>" + "<NAME>" + localTraderLogonInfo.getTraderName() + "</NAME>" + "<RANDOM_KEY>" + localTraderLogonInfo.getTrustKey() + "</RANDOM_KEY></RESULT></REP></GNNT>";
    return str7;
  }
  
  private String checkUser(String paramString)
  {
    long l = 0L;
    String str1 = null;
    int i = 1;
    String str2 = null;
    String str3 = Global.getSelfLogonType();
    int j = 1;
    CheckUserXML localCheckUserXML = (CheckUserXML)XMLWork.reader(CheckUserXML.class, paramString);
    if ((localCheckUserXML != null) && (localCheckUserXML.req != null) && ("check_user".equals(localCheckUserXML.req.name)))
    {
      l = Tools.strToLong(localCheckUserXML.req.sessionID, l);
      str1 = localCheckUserXML.req.userID;
      i = Tools.strToInt(localCheckUserXML.req.moduleID);
      str2 = localCheckUserXML.req.fromLogonType;
      str3 = localCheckUserXML.req.selfLogonType;
      if ((str3 == null) || (str3.trim().length() <= 0)) {
        str3 = Global.getSelfLogonType();
      }
      j = Tools.strToInt(localCheckUserXML.req.selfModuleID, Global.getSelfModuleID());
    }
    int k = 0;
    String str4 = "";
    CheckUserResultVO localCheckUserResultVO = ActiveUserManager.checkUser(str1, l, i, str3, str2, j);
    if ((localCheckUserResultVO == null) || (localCheckUserResultVO.getResult() == -1))
    {
      this.logger.debug("通过 sessionID 到 AU 中未获取到信息");
      addReturnValue(-1, 9930101L);
      k = -1;
      str4 = "传入的 SessionID不存在";
    }
    else if (!localCheckUserResultVO.getUserManageVO().getUserID().equals(str1))
    {
      this.request.getSession().invalidate();
      this.logger.debug("通过 sessionID 到 AU 中未获取到信息");
      addReturnValue(-1, 9930103L);
      k = -1;
      str4 = "用户信息与 SessionID信息不一致";
    }
    return "<?xml version=\"1.0\" encoding=\"GBK\"?><GNNT><REP name=\"check_user\"><RESULT><RETCODE>" + k + "</RETCODE><MESSAGE>" + str4 + "</MESSAGE><MODULE_ID>" + j + "</MODULE_ID></RESULT></REP></GNNT>";
  }
  
  private String checkLogoff(String paramString)
  {
    String str1 = "0";
    String str2 = "";
    try
    {
      ActiveUserManager.logoff(this.request);
    }
    catch (Exception localException)
    {
      str1 = "-1";
      str2 = "系统处理失败";
      this.logger.error("退出用户登录 checkLogoff 异常", localException);
    }
    return "<?xml version=\"1.0\" encoding = \"GB2312\"?><GNNT><REQ name=\"logoff\"><RESULT><RETCODE>" + str1 + "</RETCODE><MESSAGE>" + str2 + "</MESSAGE></RESULT></REQ></GNNT>";
  }
  
  public String logon()
    throws Exception
  {
    User localUser = (User)this.entity;
    int i = Tools.strToInt(this.request.getParameter("userIdType"), 0);
    this.request.setAttribute("userIdType", Integer.valueOf(i));
    String str1 = (String)this.request.getSession().getAttribute("RANDOMICITYNUM");
    String str2 = null;
    String str3 = null;
    if (i == 1)
    {
      str2 = this.request.getParameter("uimgcode");
      str3 = this.request.getParameter("upassword");
    }
    else
    {
      str2 = this.request.getParameter("timgcode");
      str3 = this.request.getParameter("tpassword");
    }
    if ((str1 == null) || (str1.trim().length() < 0))
    {
      this.logger.debug("系统生成的验证码为空值");
      addReturnValue(-1, 9930103L);
      return "error";
    }
    if ((str2 == null) || (str2.trim().length() < 0))
    {
      this.logger.debug("用户传入验证码为空值");
      if (i == 1)
      {
        this.request.setAttribute("userID", localUser.getUserID());
        this.request.setAttribute("upassword", str3);
      }
      else
      {
        this.request.setAttribute("traderID", localUser.getTraderID());
        this.request.setAttribute("tpassword", str3);
      }
      addReturnValue(-1, 9930102L);
      return "error";
    }
    if (!str2.equalsIgnoreCase(str1))
    {
      this.logger.debug("用户输入验证码[" + str2 + "]与系统验生成的证码[" + str1 + "]不一致");
      if (i == 1)
      {
        this.request.setAttribute("userID", localUser.getUserID());
        this.request.setAttribute("upassword", str3);
      }
      else
      {
        this.request.setAttribute("traderID", localUser.getTraderID());
        this.request.setAttribute("tpassword", str3);
      }
      addReturnValue(-1, 9930103L);
      return "error";
    }
    TraderLogonInfo localTraderLogonInfo = null;
    String str4 = this.request.getParameter("LogonType");
    if ((str4 == null) || (str4.trim().length() <= 0)) {
      str4 = Global.getSelfLogonType();
    }
    int j = Tools.strToInt(this.request.getParameter("ModuleID"), Global.getSelfModuleID());
    if (i == 1) {
      localTraderLogonInfo = ActiveUserManager.logon(localUser.getUserID(), str3, j, localUser.getKeyCode(), localUser.getTrustKey(), this.request.getRemoteAddr(), 1, str4);
    } else {
      localTraderLogonInfo = ActiveUserManager.logon(localUser.getTraderID(), str3, j, localUser.getKeyCode(), localUser.getTrustKey(), this.request.getRemoteAddr(), 0, str4);
    }
    if (localTraderLogonInfo == null)
    {
      this.logger.debug("调用核心系统用户登录，核心返回信息异常");
      addReturnValue(-1, 9930111L);
      return "error";
    }
    int k = Tools.strToInt(localTraderLogonInfo.getRecode(), 1);
    if ((k == -1) || (k == -2))
    {
      this.logger.debug("用户不存在或密码错误");
      addReturnValue(-1, 9930118L);
    }
    else if ((k == -3) || (k == -6) || (k == -7))
    {
      this.logger.debug("禁止登录");
      addReturnValue(-1, 9930114L);
    }
    else if (k == -4)
    {
      this.logger.debug("Key盘验证错误");
      addReturnValue(-1, 9930115L);
    }
    else if (k == -5)
    {
      this.logger.debug("交易板块被禁止");
      addReturnValue(-1, 9930116L);
    }
    else if (k < 0)
    {
      this.logger.debug("核心系统返回异常编号[" + k + "]返回信息[" + localTraderLogonInfo.getMessage() + "]");
      addReturnValue(-1, 9930117L, new Object[] { localTraderLogonInfo.getMessage() });
    }
    if (k < 0) {
      return "error";
    }
    boolean bool = ActiveUserManager.logon(localTraderLogonInfo.getTraderId(), this.request, localTraderLogonInfo.getSessionID(), str4, j);
    if (!bool)
    {
      this.logger.error("登录时，向 Session 中写入信息失败");
      addReturnValue(-1, 9930111L);
      return "error";
    }
    localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    Object localObject = this.request.getParameter("preUrl");
    this.logger.debug("preUrl:    " + (String)localObject);
    if ((localObject != null) && (((String)localObject).trim().length() > 0) && (!((String)localObject).trim().equalsIgnoreCase("null")))
    {
      if (((String)localObject).indexOf("?") > 0)
      {
        if (((String)localObject).indexOf("sessionID") < 0)
        {
          localObject = (String)localObject + "&sessionID=" + localUser.getSessionId();
        }
        else
        {
          String[] arrayOfString = ((String)localObject).split("[?]|&");
          String str5 = arrayOfString[0];
          for (int m = 1; m < arrayOfString.length; m++)
          {
            if (str5.indexOf("?") > 0) {
              str5 = str5 + "&";
            } else {
              str5 = str5 + "?";
            }
            if (arrayOfString[m].startsWith("sessionID=")) {
              str5 = str5 + "sessionID=" + localUser.getSessionId();
            } else {
              str5 = str5 + arrayOfString[m];
            }
          }
          localObject = str5;
        }
      }
      else {
        localObject = (String)localObject + "?sessionID=" + localUser.getSessionId();
      }
      this.request.setAttribute("preUrl", localObject);
    }
    addReturnValue(1, 9910101L);
    return "success";
  }
  
  public String updatePasswordSelfSave()
  {
    this.logger.debug("enter passwordSelfSave");
    String str = this.request.getParameter("oldPassword");
    User localUser = (User)this.entity;
    if (localUser != null)
    {
      int i = ActiveUserManager.changePassowrd(localUser.getTraderID(), str, localUser.getPassword(), this.request.getRemoteAddr());
      if (i != 1)
      {
        addReturnValue(-1, 9930201L);
        writeOperateLog(9901, "交易商" + localUser.getTraderID() + "修改登录密码", 0, ApplicationContextInit.getErrorInfo("-1005"));
        this.request.setAttribute("user", localUser);
        this.request.setAttribute("oldPassword", str);
        return "success";
      }
      addReturnValue(1, 9910201L);
      writeOperateLog(9901, "交易商" + localUser.getTraderID() + "修改登录密码", 1, "");
    }
    this.request.setAttribute("user", localUser);
    this.request.setAttribute("oldPassword", str);
    return "success";
  }
  
  public String logout()
    throws Exception
  {
    ActiveUserManager.logoff(this.request);
    return "success";
  }
  
  public String saveShinStyle()
  {
    this.logger.debug("enter modShinStyle");
    User localUser1 = (User)this.request.getSession().getAttribute("CurrentUser");
    User localUser2 = (User)this.entity;
    localUser1.setSkin(localUser2.getSkin());
    getService().update(localUser2);
    addReturnValue(1, 9910301L);
    writeOperateLog(9901, "交易商" + localUser2.getTraderID() + "修改风格", 1, "");
    return "success";
  }
}
