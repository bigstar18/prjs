package gnnt.MEBS.timebargain.server.util;

import gnnt.MEBS.checkLogon.check.front.FrontCheck;
import gnnt.MEBS.checkLogon.dao.front.FrontCheckDAO;
import gnnt.MEBS.checkLogon.po.front.TraderPO;
import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FrontCheckLogon
{
  Log log = LogFactory.getLog(FrontCheckLogon.class);
  private static volatile FrontCheckLogon instance;
  private FrontCheckDAO frontCheckDAO = new FrontCheckDAO();
  
  public FrontCheckLogon(DataSource paramDataSource)
  {
    this.frontCheckDAO.setDataSource(paramDataSource);
  }
  
  public static FrontCheckLogon createInstance(DataSource paramDataSource)
  {
    if (instance == null) {
      synchronized (FrontCheckLogon.class)
      {
        if (instance == null) {
          instance = new FrontCheckLogon(paramDataSource);
        }
      }
    }
    return instance;
  }
  
  public static FrontCheckLogon getInstance()
  {
    return instance;
  }
  
  public TraderLogonInfo logon(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, int paramInt2, String paramString6)
    throws Exception
  {
    this.log.error("用户登录; 用户名[" + paramString1 + "]模块[" + paramInt1 + "]登录IP[" + paramString5 + "]用户类型[" + paramInt2 + "]登录方式[" + paramString6 + "]");
    return FrontCheck.getInstance().logon(paramString1, paramString2, paramInt1, paramString3, paramString4, paramString5, paramInt2, paramString6);
  }
  
  public void logoff(long paramLong, int paramInt, String paramString)
  {
    this.log.debug("用户注销; sessionID: " + paramLong + ", moduleID: " + paramInt + ", logonType: " + paramString);
    UserManageVO localUserManageVO = getUserBySessionID(paramLong, paramInt, paramString);
    if (localUserManageVO != null)
    {
      LogoffVO localLogoffVO = new LogoffVO();
      localLogoffVO.setSessionID(paramLong);
      localLogoffVO.setUserID(localUserManageVO.getUserID());
      localLogoffVO.setLogonType(paramString);
      localLogoffVO.setModuleID(paramInt);
      LogonActualize.getInstance().logoff(localLogoffVO);
    }
  }
  
  public UserManageVO getUserBySessionID(long paramLong, int paramInt, String paramString)
  {
    this.log.debug("getUserBySessionID ; sessionID: " + paramLong + ", moduleID: " + paramInt + ", logonType: " + paramString);
    UserManageVO localUserManageVO = null;
    GetUserVO localGetUserVO = new GetUserVO();
    localGetUserVO.setLogonType(paramString);
    localGetUserVO.setModuleID(Integer.valueOf(paramInt));
    localGetUserVO.setSessionID(paramLong);
    GetUserResultVO localGetUserResultVO = LogonActualize.getInstance().getUserBySessionID(localGetUserVO);
    localUserManageVO = localGetUserResultVO.getUserManageVO();
    return localUserManageVO;
  }
  
  public boolean isLogon(String paramString1, long paramLong, int paramInt, String paramString2)
  {
    this.log.debug("isLogon ; traderID: " + paramString1 + ", sessionID: " + paramLong + ", moduleID: " + paramInt + ", logonType: " + paramString2);
    boolean bool = false;
    bool = FrontCheck.getInstance().checkModuleRight(paramString1, paramInt);
    ISLogonVO localISLogonVO = new ISLogonVO();
    localISLogonVO.setUserID(paramString1);
    localISLogonVO.setModuleID(Integer.valueOf(paramInt));
    localISLogonVO.setSessionID(paramLong);
    localISLogonVO.setLogonType(paramString2);
    ISLogonResultVO localISLogonResultVO = null;
    localISLogonResultVO = LogonActualize.getInstance().isLogon(localISLogonVO);
    if (localISLogonResultVO.getResult() == 1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public CheckUserResultVO checkUser(String paramString1, long paramLong, String paramString2, String paramString3, int paramInt1, String paramString4, int paramInt2)
  {
    this.log.debug("CheckUser ; traderID: " + paramString1 + ", logonIP: " + paramString2 + ", fromLogonType: " + paramString3 + ", fromModuleID: " + paramInt1 + ", toLogonType: " + paramString4 + ", toModuleID: " + paramInt2);
    CheckUserVO localCheckUserVO = new CheckUserVO();
    localCheckUserVO.setLogonType(paramString4);
    localCheckUserVO.setSessionID(paramLong);
    localCheckUserVO.setToModuleID(paramInt2);
    localCheckUserVO.setUserID(paramString1);
    CheckUserResultVO localCheckUserResultVO = LogonActualize.getInstance().checkUser(localCheckUserVO, paramInt1, paramString3);
    return localCheckUserResultVO;
  }
  
  public TraderLogonInfo getTraderInfo(String paramString)
  {
    TraderLogonInfo localTraderLogonInfo = new TraderLogonInfo();
    TraderPO localTraderPO = this.frontCheckDAO.getTraderByID(paramString);
    localTraderLogonInfo.setLastIP(localTraderPO.getLastIP());
    localTraderLogonInfo.setLastTime(localTraderPO.getLastTime());
    localTraderLogonInfo.setFirmId(localTraderPO.getFirmID());
    localTraderLogonInfo.setFirmName(localTraderPO.getFirmName());
    localTraderLogonInfo.setTraderId(localTraderPO.getTraderID());
    localTraderLogonInfo.setTraderName(localTraderPO.getName());
    localTraderLogonInfo.setType(localTraderPO.getType());
    localTraderLogonInfo.setForceChangePwd(localTraderPO.getForceChangePwd().intValue());
    localTraderLogonInfo.setTrustKey(localTraderPO.getTrustKey());
    return localTraderLogonInfo;
  }
  
  public int changePassowrd(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return FrontCheck.getInstance().changePassowrd(paramString1, paramString2, paramString3, paramString4);
  }
}
