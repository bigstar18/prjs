package gnnt.MEBS.common.front.common;

import gnnt.MEBS.checkLogon.check.front.FrontCheck;
import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.common.front.callbak.LogonCallbak;
import gnnt.MEBS.common.front.exception.CallbakErrorException;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.RightService;
import gnnt.MEBS.common.front.service.RoleService;
import gnnt.MEBS.common.front.service.UserService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActiveUserManager
{
  private static final transient Log logger = LogFactory.getLog(ActiveUserManager.class);
  
  public static GetUserResultVO getUser(long paramLong, String paramString, int paramInt)
  {
    GetUserResultVO localGetUserResultVO = null;
    try
    {
      GetUserVO localGetUserVO = new GetUserVO();
      localGetUserVO.setSessionID(paramLong);
      localGetUserVO.setLogonType(paramString);
      localGetUserVO.setModuleID(Integer.valueOf(paramInt));
      localGetUserResultVO = LogonActualize.getInstance().getUserBySessionID(localGetUserVO);
    }
    catch (Exception localException)
    {
      logger.error("验证SessionID在AU中是否已经登录异常", localException);
    }
    return localGetUserResultVO;
  }
  
  public static TraderLogonInfo logon(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, int paramInt2, String paramString6)
    throws Exception
  {
    return FrontCheck.getInstance().logon(paramString1, paramString2, paramInt1, paramString3, paramString4, paramString5, paramInt2, paramString6);
  }
  
  public static boolean logon(String paramString1, HttpServletRequest paramHttpServletRequest, long paramLong, String paramString2, int paramInt)
  {
    boolean bool1 = true;
    UserService localUserService = (UserService)ApplicationContextInit.getBean("com_userService");
    User localUser1 = localUserService.getTraderByID(paramString1);
    User localUser2 = (User)localUser1.clone();
    localUser2.setSessionId(Long.valueOf(paramLong));
    localUser2.setIpAddress(paramHttpServletRequest.getRemoteAddr());
    localUser2.setLogonType(paramString2);
    localUser2.setModuleID(Integer.valueOf(paramInt));
    boolean bool2 = false;
    if ("A".equals(localUser2.getType())) {
      bool2 = true;
    }
    RoleService localRoleService = (RoleService)ApplicationContextInit.getBean("com_roleService");
    localUser2.setRoleSet(localRoleService.getAllRole());
    RightService localRightService = (RightService)ApplicationContextInit.getBean("com_rightService");
    localUser2.setRightSet(localRightService.getAllRight());
    paramHttpServletRequest.getSession().invalidate();
    paramHttpServletRequest.getSession().setAttribute("IsSuperAdmin", Boolean.valueOf(bool2));
    paramHttpServletRequest.getSession().setAttribute("CurrentUser", localUser2);
    paramHttpServletRequest.getSession().setAttribute("sessionID", Long.valueOf(paramLong));
    paramHttpServletRequest.getSession().setAttribute("LogonType", paramString2);
    String str = ApplicationContextInit.getConfig("SessionExpireTime");
    paramHttpServletRequest.getSession().setMaxInactiveInterval(Tools.strToInt(str, 120) * 60);
    try
    {
      LogonCallbak localLogonCallbak = null;
      try
      {
        localLogonCallbak = (LogonCallbak)ApplicationContextInit.getBean("logonCallbak");
      }
      catch (Exception localException2)
      {
        System.out.println("系统中没有配置登录回调函数。");
      }
      if (localLogonCallbak != null) {
        localLogonCallbak.logonSuccessCallbak(localUser2, paramHttpServletRequest);
      }
    }
    catch (CallbakErrorException localCallbakErrorException)
    {
      paramHttpServletRequest.getSession().invalidate();
      bool1 = false;
      logger.error("Action logon 方法进行登录后，执行登录回调，抛出自定义异常：", localCallbakErrorException);
    }
    catch (Exception localException1)
    {
      paramHttpServletRequest.getSession().invalidate();
      bool1 = false;
      logger.error("Action logon 方法进行登录后，执行登录回调，异常：", localException1);
    }
    return bool1;
  }
  
  public static int changePassowrd(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return FrontCheck.getInstance().changePassowrd(paramString1, paramString2, paramString3, paramString4);
  }
  
  public static void logoff(HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    User localUser = (User)paramHttpServletRequest.getSession().getAttribute("CurrentUser");
    paramHttpServletRequest.getSession().invalidate();
    if (localUser != null)
    {
      LogoffVO localLogoffVO = new LogoffVO();
      localLogoffVO.setSessionID(localUser.getSessionId().longValue());
      localLogoffVO.setUserID(localUser.getTraderID());
      localLogoffVO.setLogonType(localUser.getLogonType());
      localLogoffVO.setModuleID(localUser.getModuleID().intValue());
      LogonActualize.getInstance().logoff(localLogoffVO);
    }
  }
  
  public static ISLogonResultVO isLogon(String paramString1, long paramLong, String paramString2, int paramInt)
    throws Exception
  {
    ISLogonVO localISLogonVO = new ISLogonVO();
    localISLogonVO.setUserID(paramString1);
    localISLogonVO.setModuleID(Integer.valueOf(paramInt));
    localISLogonVO.setSessionID(paramLong);
    localISLogonVO.setLogonType(paramString2);
    ISLogonResultVO localISLogonResultVO = LogonActualize.getInstance().isLogon(localISLogonVO);
    return localISLogonResultVO;
  }
  
  public static boolean checkModuleRight(String paramString)
  {
    return FrontCheck.getInstance().checkModuleRight(paramString, Global.getSelfModuleID());
  }
  
  public static CheckUserResultVO checkUser(String paramString1, long paramLong, int paramInt1, String paramString2, String paramString3, int paramInt2)
  {
    CheckUserResultVO localCheckUserResultVO = new CheckUserResultVO();
    CheckUserVO localCheckUserVO = new CheckUserVO();
    localCheckUserVO.setUserID(paramString1);
    localCheckUserVO.setSessionID(paramLong);
    localCheckUserVO.setToModuleID(paramInt2);
    localCheckUserVO.setLogonType(paramString2);
    localCheckUserResultVO = LogonActualize.getInstance().checkUser(localCheckUserVO, paramInt1, paramString3);
    return localCheckUserResultVO;
  }
}
