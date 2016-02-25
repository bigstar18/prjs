package gnnt.MEBS.member.broker.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.member.broker.model.Broker;
import gnnt.MEBS.member.broker.services.BrokerService;
import gnnt.MEBS.member.firm.services.FirmService;
import gnnt.MEBS.member.firm.unit.FirmLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

public class BrokerController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BrokerController.class);
  
  public ModelAndView brokerList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into brokerList~~~~");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "brokerId", false);
    }
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    Object localObject = new ArrayList();
    String str = paramHttpServletRequest.getParameter("qc");
    if (str != null) {
      localObject = localBrokerService.getBrokerLists(localQueryConditions, localPageInfo);
    } else {
      localObject = localBrokerService.getBrokerList(localQueryConditions, localPageInfo);
    }
    this.logger.debug("list.size:" + ((List)localObject).size());
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerList", "resultList", localObject);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView brokerAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into brokerAdd~~~~");
    Broker localBroker = new Broker();
    ParamUtil.bindData(paramHttpServletRequest, localBroker);
    this.logger.debug("BROKER:" + localBroker.toString());
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    String str1 = "";
    int i = 0;
    try
    {
      String str2 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.brokerAdd(localBroker, str2);
      if (i == 1) {
        localBrokerService.addFirmRelated(localBroker.getBrokerid(), localBroker.getFirmId());
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "添加失败！";
    }
    this.logger.debug("result:" + i);
    ModelAndView localModelAndView = null;
    if (i == 1)
    {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "添加成功！";
      paramHttpServletRequest.setAttribute("resultMsg", str1);
      return brokerList(paramHttpServletRequest, paramHttpServletResponse);
    }
    if (i == -1)
    {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "添加失败！" + gnnt.MEBS.member.broker.util.SysData.getConfig("brokerAccount") + "错误!";
      localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerAdd", "resultMsg", str1);
    }
    else if (i == -2)
    {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerAccount") + "已被关联!";
      localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerAdd", "resultMsg", str1);
    }
    return localModelAndView;
  }
  
  public ModelAndView brokerDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into brokerDelete~~~~");
    Broker localBroker = new Broker();
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    String str1 = "";
    this.logger.debug("ck[0]:" + arrayOfString[0]);
    int i = 0;
    try
    {
      String str2 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.brokerDelete(arrayOfString, str2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "删除失败！";
    }
    if (i == 1) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "删除成功！";
    } else if (i == -1) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "里有未付清佣金，不允许删除！";
    } else if (i == -2) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "里有所辖交易商，不允许删除！请先删除其所辖交易商！";
    }
    ModelAndView localModelAndView = brokerList(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", str1);
    return localModelAndView;
  }
  
  public ModelAndView brokerMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into brokerMod~~~~");
    Broker localBroker = new Broker();
    ParamUtil.bindData(paramHttpServletRequest, localBroker);
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    this.logger.debug("name:" + localBroker.getName());
    this.logger.debug("address:" + localBroker.getAddress());
    this.logger.debug("brokerid:" + localBroker.getBrokerid());
    this.logger.debug("email:" + localBroker.getEmail());
    this.logger.debug("mobile:" + localBroker.getMobile());
    this.logger.debug("tel:" + localBroker.getTelephone());
    this.logger.debug("firmid:" + localBroker.getFirmId());
    this.logger.debug("marketManager:" + localBroker.getMarketManager());
    this.logger.debug("locationProvince:" + localBroker.getLocationProvince());
    String str1 = "";
    int i = 0;
    try
    {
      String str2 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.brokerMod(localBroker, str2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "修改失败！";
    }
    if (i == 1) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "修改成功！";
    } else if (i == -1) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "添加失败！" + gnnt.MEBS.member.broker.util.SysData.getConfig("brokerAccount") + "错误!";
    } else if (i == -2) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "添加失败！" + gnnt.MEBS.member.broker.util.SysData.getConfig("brokerAccount") + "已被关联!";
    }
    ModelAndView localModelAndView = brokerModForward(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", str1);
    return localModelAndView;
  }
  
  public ModelAndView brokerModForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into brokerModforward~~~~");
    Broker localBroker = null;
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    String str = delNull(paramHttpServletRequest.getParameter("brokerid"));
    this.logger.debug("brokerid:" + str);
    localBroker = localBrokerService.getBrokerById(str);
    ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerMod", "broker", localBroker);
    return localModelAndView;
  }
  
  public ModelAndView setBrokerPwd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into setBrokerPwd~~~~");
    Broker localBroker = new Broker();
    ParamUtil.bindData(paramHttpServletRequest, localBroker);
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    this.logger.debug("brokerid:" + localBroker.getBrokerid());
    this.logger.debug("password:" + localBroker.getPassword());
    String str1 = "";
    int i = 0;
    try
    {
      String str2 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.setBrokerPwd(localBroker.getBrokerid(), localBroker.getPassword(), str2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "密码修改失败！";
    }
    if (i == 1) {
      str1 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "密码修改成功！";
    }
    ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/setBrokerPwd");
    localModelAndView.addObject("resultMsg", str1);
    return localModelAndView;
  }
  
  public ModelAndView brokerlistFirm(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'listFirm' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    if (localQueryConditions == null) {
      localQueryConditions = new QueryConditions();
    }
    localQueryConditions.addCondition("status", "!=", "'U'");
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    String str = delNull(paramHttpServletRequest.getParameter("brokerid"));
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "firmid", false);
    }
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    List localList = localBrokerService.getFirmById(localQueryConditions, localPageInfo, str);
    this.logger.debug("brokerid:" + str);
    this.logger.debug("list.size:" + localList.size());
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerUserList", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    localModelAndView.addObject("brokerid", str);
    return localModelAndView;
  }
  
  public ModelAndView brokerFirmAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'addFirmRelated' method...");
    String str1 = delNull(paramHttpServletRequest.getParameter("brokerid"));
    String str2 = delNull(paramHttpServletRequest.getParameter("firmid"));
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    String str3 = "";
    int i = 0;
    try
    {
      String str4 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.addFirmRelated(str1, str2, str4);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str3 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "增加所属交易商" + str2 + "失败！";
    }
    if (i == 1) {
      str3 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "增加所属交易商" + str2 + "成功！";
    } else if (i == -1) {
      str3 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "所辖用户添加失败，非法交易商代码";
    } else if (i == -2) {
      str3 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "所辖用户添加失败，交易商已有所属经纪人!";
    }
    this.logger.debug("brokerid:" + str1);
    ModelAndView localModelAndView = new ModelAndView("member/broker/brokerDetail/brokerUserAdd");
    localModelAndView.addObject("resultMsg", str3);
    return localModelAndView;
  }
  
  public ModelAndView brokerFirmDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("action into delFirmRelated~~~~");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    String str1 = paramHttpServletRequest.getParameter("brokerid");
    this.logger.debug("ck:" + arrayOfString.length);
    BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
    String str2 = "";
    int i = 0;
    try
    {
      String str3 = (String)paramHttpServletRequest.getSession().getAttribute("CURRENUSERID");
      i = localBrokerService.deleteFirmRelated(arrayOfString, str1, str3);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str2 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "删除所属交易商失败！";
    }
    if (i == 1) {
      str2 = gnnt.MEBS.member.broker.util.SysData.getConfig("brokerName") + "删除所属交易商成功！";
    }
    ModelAndView localModelAndView = brokerlistFirm(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", str2);
    return localModelAndView;
  }
  
  public ModelAndView brokerAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    return new ModelAndView("member/broker/brokerDetail/brokerAdd");
  }
  
  public ModelAndView setBrokerPwdForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = paramHttpServletRequest.getParameter("brokerid");
    if (str != null) {
      paramHttpServletRequest.setAttribute("brokerid", str);
    }
    return new ModelAndView("member/broker/brokerDetail/setBrokerPwd");
  }
  
  public ModelAndView brokerUserAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = paramHttpServletRequest.getParameter("brokerid");
    if (str != null) {
      paramHttpServletRequest.setAttribute("brokerid", str);
    }
    return new ModelAndView("member/broker/brokerDetail/brokerUserAdd");
  }
  
  public void getFirmInfo(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    int i = 0;
    try
    {
      paramHttpServletRequest.setCharacterEncoding("GBK");
      paramHttpServletResponse.setContentType("text/html;charset=GBK");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      String str = paramHttpServletRequest.getParameter("firmId");
      BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
      Dealer localDealer = localBrokerService.getDealerByfirmId(str);
      if (localDealer == null) {
        i = 1;
      }
      localPrintWriter.print(i);
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void getBrokerInfo(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    int i = 0;
    try
    {
      paramHttpServletRequest.setCharacterEncoding("GBK");
      paramHttpServletResponse.setContentType("text/html;charset=GBK");
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      String str = paramHttpServletRequest.getParameter("brokerID");
      BrokerService localBrokerService = (BrokerService)gnnt.MEBS.member.broker.util.SysData.getBean("m_brokerService");
      int j = localBrokerService.getDealerByBrokerId(str);
      if (j == 0) {
        i = 1;
      }
      localPrintWriter.print(i);
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public ModelAndView verifyFirmList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'verifyFirmList' method...");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 15, "firmId", false);
    }
    if (localQueryConditions == null) {
      localQueryConditions = new QueryConditions();
    }
    localQueryConditions.addCondition("m.status", "=", "W");
    FirmService localFirmService = (FirmService)gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
    List localList = localFirmService.getFirmList(localQueryConditions, localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    this.logger.debug("resultList.size():" + localList.size());
    ModelAndView localModelAndView = new ModelAndView("member/broker/verifyFirm/verifyFirmList", "resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView verifyFirm(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'verifyFirm' method...");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str = "审核成功";
    FirmService localFirmService = (FirmService)gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
    try
    {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        FirmLog localFirmLog = new FirmLog();
        localFirmLog.setUserId(AclCtrl.getLogonID(paramHttpServletRequest));
        localFirmLog.setFirmId(arrayOfString[i]);
        localFirmLog.setAction("交易商审核");
        int j = localFirmService.verifyFirm(arrayOfString[i], "N", localFirmLog);
        if (j == -2) {
          str = "该交易商已审核成功";
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "审核失败";
    }
    ModelAndView localModelAndView = verifyFirmList(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", str);
    return localModelAndView;
  }
  
  public ModelAndView brokerRefuseFirm(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("entering 'brokerRefuseFirm' method...");
    String str1 = paramHttpServletRequest.getParameter("firmIds");
    ModelAndView localModelAndView = new ModelAndView("member/broker/verifyFirm/refuseFirm");
    localModelAndView.addObject("firmIds", str1);
    String str2 = paramHttpServletRequest.getParameter("note");
    if (str2 != null)
    {
      FirmLog localFirmLog = new FirmLog();
      localFirmLog.setUserId(AclCtrl.getLogonID(paramHttpServletRequest));
      localFirmLog.setAction("交易商驳回");
      FirmService localFirmService = (FirmService)gnnt.MEBS.member.firm.util.SysData.getBean("m_firmService");
      String[] arrayOfString = str1.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
      {
        localFirmLog.setFirmId(arrayOfString[i]);
        localFirmService.rejectFirm(arrayOfString[i], str2, localFirmLog);
      }
      localModelAndView.addObject("resultMsg", "操作成功!");
    }
    return localModelAndView;
  }
}
