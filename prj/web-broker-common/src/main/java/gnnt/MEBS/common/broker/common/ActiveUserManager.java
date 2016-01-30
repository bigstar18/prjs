package gnnt.MEBS.common.broker.common;

import gnnt.MEBS.checkLogon.check.broker.BrokerAgeCheck;
import gnnt.MEBS.checkLogon.check.broker.BrokerCheck;
import gnnt.MEBS.checkLogon.vo.broker.BrokerAgeLogonResultVO;
import gnnt.MEBS.checkLogon.vo.broker.BrokerLogonResultVO;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.BrokerAge;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.BrokerAgeService;
import gnnt.MEBS.common.broker.service.BrokerService;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActiveUserManager
{
  private static final transient Log logger = LogFactory.getLog(ActiveUserManager.class);

  public static GetUserResultVO getUser(long paramLong, String paramString)
  {
    GetUserResultVO localGetUserResultVO = null;
    GetUserVO localGetUserVO = new GetUserVO();
    localGetUserVO.setLogonType(paramString);
    localGetUserVO.setModuleID(Integer.valueOf(Global.getSelfModuleID()));
    localGetUserVO.setSessionID(paramLong);
    try
    {
      localGetUserResultVO = LogonActualize.getInstance().getUserBySessionID(localGetUserVO);
    }
    catch (Exception localException)
    {
      logger.error("验证SessionID在AU中是否已经登录异常", localException);
    }
    return localGetUserResultVO;
  }

  public static BrokerLogonResultVO brokerLogon(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
    throws Exception
  {
    return BrokerCheck.getInstance().logon(paramString1, paramString2, paramString3, paramInt, paramString4);
  }

  public static BrokerAgeLogonResultVO brokerAgeLogon(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
    throws Exception
  {
    return BrokerAgeCheck.getInstance().logon(paramString1, paramString2, paramString3, paramInt, paramString4);
  }

  public static boolean brokerLogonSuccess(String paramString1, HttpServletRequest paramHttpServletRequest, long paramLong, String paramString2)
  {
    boolean bool = false;
    BrokerService localBrokerService = (BrokerService)ApplicationContextInit.getBean("com_brokerService");
    Broker localBroker = (Broker)localBrokerService.getBrokerByID(paramString1).clone();
    if (localBroker == null)
      return bool;
    String str1 = "select firmId from br_firmandbroker where brokerId = '" + paramString1 + "'";
    localBroker.setSessionId(paramLong);
    localBroker.setIpAddress(paramHttpServletRequest.getRemoteAddr());
    User localUser = new User();
    localUser.setUserId(paramString1);
    localUser.setLogonType(paramString2);
    localUser.setBroker(localBroker);
    localUser.setSql(str1);
    localUser.setType("0");
    localUser.setSessionId(paramLong);
    paramHttpServletRequest.getSession().invalidate();
    paramHttpServletRequest.getSession().setAttribute("CurrentUserType", Integer.valueOf(0));
    paramHttpServletRequest.getSession().setAttribute("CurrentUser", localUser);
    paramHttpServletRequest.getSession().setAttribute("sessionID", Long.valueOf(paramLong));
    paramHttpServletRequest.getSession().setAttribute("LogonType", paramString2);
    paramHttpServletRequest.getSession().setAttribute("LOGONIP", localUser.getIpAddress());
    String str2 = ApplicationContextInit.getConfig("SessionExpireTime");
    paramHttpServletRequest.getSession().setMaxInactiveInterval(Tools.strToInt(str2, 120) * 60);
    bool = true;
    return bool;
  }

  public static boolean brokerAgeLogonSuccess(String paramString1, HttpServletRequest paramHttpServletRequest, long paramLong, String paramString2)
  {
    boolean bool = false;
    BrokerAgeService localBrokerAgeService = (BrokerAgeService)ApplicationContextInit.getBean("com_brokerAgeService");
    BrokerAge localBrokerAge = (BrokerAge)localBrokerAgeService.getBrokerAgeByID(paramString1).clone();
    if (localBrokerAge == null)
      return bool;
    String str1 = "select firmId from br_brokerageandfirm  where brokerageid = '" + paramString1 + "'";
    localBrokerAge.setSessionId(paramLong);
    localBrokerAge.setIpAddress(paramHttpServletRequest.getRemoteAddr());
    User localUser = new User();
    localUser.setUserId(paramString1);
    localUser.setLogonType(paramString2);
    localUser.setBrokerAge(localBrokerAge);
    localUser.setSql(str1);
    localUser.setType("4");
    localUser.setSessionId(paramLong);
    paramHttpServletRequest.getSession().invalidate();
    paramHttpServletRequest.getSession().setAttribute("CurrentUserType", Integer.valueOf(0));
    paramHttpServletRequest.getSession().setAttribute("CurrentUser", localUser);
    paramHttpServletRequest.getSession().setAttribute("sessionID", Long.valueOf(paramLong));
    paramHttpServletRequest.getSession().setAttribute("LogonType", paramString2);
    paramHttpServletRequest.getSession().setAttribute("LOGONIP", localUser.getIpAddress());
    String str2 = ApplicationContextInit.getConfig("SessionExpireTime");
    paramHttpServletRequest.getSession().setMaxInactiveInterval(Tools.strToInt(str2, 120) * 60);
    bool = true;
    return bool;
  }

  public static int BrokerAgeChangePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return BrokerAgeCheck.getInstance().changePassword(paramString1, paramString2, paramString3, paramString4);
  }

  public static int BrokerChangePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return BrokerCheck.getInstance().changePassowrd(paramString1, paramString2, paramString3, paramString4);
  }

  public static void logoff(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    User localUser = (User)paramHttpServletRequest.getSession().getAttribute("CurrentUser");
    paramHttpServletRequest.getSession().invalidate();
    if (localUser != null)
    {
      LogoffVO localLogoffVO = new LogoffVO();
      localLogoffVO.setUserID(localUser.getUserId());
      localLogoffVO.setSessionID(localUser.getSessionId());
      localLogoffVO.setLogonType(localUser.getLogonType());
      LogonActualize.getInstance().logoff(localLogoffVO);
    }
  }

  public static ISLogonResultVO isLogon(String paramString1, long paramLong, int paramInt, String paramString2)
    throws Exception
  {
    ISLogonVO localISLogonVO = new ISLogonVO();
    localISLogonVO.setUserID(paramString1);
    localISLogonVO.setModuleID(Integer.valueOf(paramInt));
    localISLogonVO.setSessionID(paramLong);
    localISLogonVO.setLogonType(paramString2);
    return LogonActualize.getInstance().isLogon(localISLogonVO);
  }

  public static CheckUserResultVO checkUser(CheckUserVO paramCheckUserVO, int paramInt, String paramString)
  {
    return LogonActualize.getInstance().checkUser(paramCheckUserVO, paramInt, paramString);
  }
}