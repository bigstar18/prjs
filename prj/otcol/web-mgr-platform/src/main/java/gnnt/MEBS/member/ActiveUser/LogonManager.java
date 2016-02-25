package gnnt.MEBS.member.ActiveUser;

import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogonManager
{
  private final transient Log logger = LogFactory.getLog(LogonManager.class);
  private String moduleId;
  private Map<String, Map<String, Object>> auConfigs;
  private static volatile LogonManager um;
  private DataSource ds;
  private int localRMIPort;
  private int space = 30;
  private int expireTime = 30;
  private int MULTI_MODE = 2;
  private int allowLogonError = 30;
  private static ActiveUserManager au;
  
  private LogonManager(int paramInt)
  {
    this.expireTime = paramInt;
  }
  
  private LogonManager() {}
  
  private void initActiveUserManager()
  {
    au = new ActiveUserManager(this.space, this.expireTime, this.MULTI_MODE);
  }
  
  public static LogonManager getInstance()
  {
    return um;
  }
  
  public static LogonManager createInstance(String paramString, DataSource paramDataSource, int paramInt)
  {
    if (um == null) {
      synchronized (LogonManager.class)
      {
        if (um == null)
        {
          um = new LogonManager(paramInt);
          um.init(paramString, paramDataSource);
          um.initActiveUserManager();
        }
      }
    }
    return um;
  }
  
  public static LogonManager createInstance(String paramString, DataSource paramDataSource)
  {
    if (um == null) {
      synchronized (LogonManager.class)
      {
        if (um == null)
        {
          um = new LogonManager();
          um.init(paramString, paramDataSource);
          um.initActiveUserManager();
        }
      }
    }
    return um;
  }
  
  public static ActiveUserManager getActiveUserManager()
  {
    if (au == null) {
      au = new ActiveUserManager();
    }
    return au;
  }
  
  private void init(String paramString, DataSource paramDataSource)
  {
    this.auConfigs = new HashMap();
    this.moduleId = paramString;
    this.ds = paramDataSource;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = paramDataSource.getConnection();
      String str1 = "select moduleid, hostip, rmi_port, rmi_dataport,mutex from m_trademodule where hostip is not null and rmi_port is not null";
      localPreparedStatement = localConnection.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      String str2;
      while (localResultSet.next())
      {
        str2 = localResultSet.getString("MODULEID");
        String str3 = localResultSet.getString("HOSTIP");
        Long localLong1 = Long.valueOf(localResultSet.getLong("RMI_PORT"));
        Long localLong2 = Long.valueOf(localResultSet.getLong("RMI_DataPort"));
        String str4 = localResultSet.getString("MUTEX");
        if (str2.equals(this.moduleId))
        {
          this.localRMIPort = localLong1.intValue();
          try
          {
            try
            {
              RMISocketFactory.setSocketFactory(new RmiDataSocket(localLong2.intValue()));
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
              System.out.println("绑定数据端口失败" + localException2.toString());
            }
            Registry localRegistry = LocateRegistry.getRegistry(this.localRMIPort);
            if (localRegistry != null) {
              localRegistry = LocateRegistry.createRegistry(this.localRMIPort);
            }
          }
          catch (Exception localException3) {}
          localObject1 = new ActiveUserRMIImpl();
          String str5 = "//" + str3 + ":" + this.localRMIPort + "/ActiveUserService";
          this.logger.info("UserManager rmi bind:" + str5);
          Naming.rebind(str5, (Remote)localObject1);
          this.logger.debug("==============>" + this.localRMIPort + " RMI服务启动成功。");
          if ("Y".equals(str4)) {
            this.MULTI_MODE = 1;
          }
        }
        Object localObject1 = new HashMap();
        ((Map)localObject1).put("HOSTIP", str3);
        this.logger.debug("==============>ip:" + str3);
        ((Map)localObject1).put("RMI_PORT", localLong1);
        this.logger.debug("==============>port:" + localLong1);
        this.auConfigs.put(str2, localObject1);
      }
      localResultSet.close();
      localPreparedStatement.close();
      str1 = "select value from M_Configuration where key='allowLogonError'";
      localPreparedStatement = localConnection.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str2 = localResultSet.getString("value");
        this.allowLogonError = Integer.parseInt(str2);
      }
      localResultSet.close();
      localPreparedStatement.close();
      localConnection.close();
      localConnection = null;
      return;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      this.logger.error("UserManager init error!", localException1);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public String getUserID(long paramLong)
  {
    return au.getUserID(paramLong);
  }
  
  public String[] getAllUsers()
  {
    return au.getAllUsers();
  }
  
  public String[] getAllUsersWithIP()
  {
    return au.getAllUsersWithIP();
  }
  
  public TraderInfo logon(String paramString1, String paramString2)
  {
    return logon(paramString1, paramString2, null, null);
  }
  
  public TraderInfo logon(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.logger.debug("Entering 'logon' method");
    localTraderInfo = new TraderInfo();
    LogonInfo localLogonInfo = new LogonInfo();
    long l1 = -1L;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    PreparedStatement localPreparedStatement3 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localConnection = this.ds.getConnection();
      String str1 = "delete from m_errorloginlog e where trunc(e.logindate)<trunc(sysdate) and e.TraderID=?";
      localPreparedStatement1 = localConnection.prepareStatement(str1);
      localPreparedStatement1.setString(1, paramString1);
      localPreparedStatement1.executeQuery();
      localPreparedStatement1.close();
      localPreparedStatement1 = null;
      String str2 = "select count(*) from M_ErrorLoginLog e where e.TraderID=? and trunc(e.loginDate)=trunc(sysdate)";
      localPreparedStatement2 = localConnection.prepareStatement(str2);
      localPreparedStatement2.setString(1, paramString1);
      localResultSet1 = localPreparedStatement2.executeQuery();
      int i = 0;
      if (localResultSet1.next()) {
        i = localResultSet1.getInt(1);
      }
      localResultSet1.close();
      localResultSet1 = null;
      localPreparedStatement2.close();
      localPreparedStatement2 = null;
      String str3 = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      localPreparedStatement3 = localConnection.prepareStatement(str3);
      localPreparedStatement3.setString(1, paramString1);
      localPreparedStatement3.setString(2, this.moduleId);
      localResultSet2 = localPreparedStatement3.executeQuery();
      if (localResultSet2.next())
      {
        String str4 = localResultSet2.getString("status");
        String str5 = localResultSet2.getString("KeyCode");
        String str6 = localResultSet2.getString("EnableKey");
        String str7 = localResultSet2.getString("Enabled");
        String str8 = localResultSet2.getString("password");
        localLogonInfo.setAuSessionId(l1);
        localLogonInfo.setCkey(paramString3);
        localLogonInfo.setEnableKey(str6);
        localLogonInfo.setErrSum(i);
        localLogonInfo.setLogonIP(paramString4);
        localLogonInfo.setPassword(paramString2);
        localLogonInfo.setPwd(str8);
        localLogonInfo.setSkey(str5);
        localLogonInfo.setUserId(paramString1);
        boolean bool = checkErrorLogin(localLogonInfo);
        l1 = localLogonInfo.getAuSessionId();
        if (bool) {
          if (("N".equals(str4)) && (("N".equals(str6)) || (("Y".equals(str6)) && (paramString3.equals(str5)))) && ("Y".equals(str7)))
          {
            if (str8.equals(MD5.getMD5(paramString1, paramString2)))
            {
              long l2 = au.logon(paramString1, paramString4);
              if (l2 == -1L) {
                l1 = -5L;
              } else {
                l1 = l2;
              }
            }
            else
            {
              l1 = -2L;
            }
          }
          else if ("D".equals(str4)) {
            l1 = -3L;
          } else if (("Y".equals(str6)) && (!paramString3.equals(str5))) {
            l1 = -4L;
          } else if ("N".equals(str7)) {
            l1 = -6L;
          }
        }
      }
      else
      {
        l1 = -1L;
      }
      localResultSet2.close();
      localResultSet2 = null;
      localPreparedStatement3.close();
      localPreparedStatement3 = null;
      if (l1 > 0L)
      {
        str3 = "select f.firmId,f.name firmName,traderId,m.name traderName,m.type,m.forceChangePwd,m.KeyCode from m_firm f,m_trader m where f.firmId=m.firmId and m.traderId=?";
        localPreparedStatement3 = localConnection.prepareStatement(str3);
        localPreparedStatement3.setString(1, paramString1);
        localResultSet2 = localPreparedStatement3.executeQuery();
        if (localResultSet2.next())
        {
          localTraderInfo.firmId = localResultSet2.getString(1);
          localTraderInfo.firmName = localResultSet2.getString(2);
          localTraderInfo.traderId = localResultSet2.getString(3);
          localTraderInfo.traderName = localResultSet2.getString(4);
          localTraderInfo.type = localResultSet2.getString(5);
          localTraderInfo.forceChangePwd = localResultSet2.getInt(6);
          localTraderInfo.keyCode = localResultSet2.getString(7);
          localTraderInfo.message = localLogonInfo.getMsg();
        }
        localResultSet2.close();
        localResultSet2 = null;
        localPreparedStatement3.close();
        localPreparedStatement3 = null;
        str3 = "select ip, maxtime from (select ip, occurtime maxtime from m_traderlog where traderid = ? and moduleid = ? and oprtype = 1 order by maxtime desc) where rownum < 2";
        localPreparedStatement3 = localConnection.prepareStatement(str3);
        localPreparedStatement3.setString(1, paramString1);
        localPreparedStatement3.setString(2, this.moduleId);
        localResultSet2 = localPreparedStatement3.executeQuery();
        if (localResultSet2.next())
        {
          localTraderInfo.lastIP = localResultSet2.getString(1);
          localTraderInfo.lastTime = localResultSet2.getString(2);
        }
        localResultSet2.close();
        localResultSet2 = null;
        localPreparedStatement3.close();
        localPreparedStatement3 = null;
        str3 = "insert into M_TraderLog values(?,1,?,?,sysdate)";
        localPreparedStatement3 = localConnection.prepareStatement(str3);
        localPreparedStatement3.setString(1, paramString1);
        localPreparedStatement3.setString(2, this.moduleId);
        localPreparedStatement3.setString(3, paramString4);
        localPreparedStatement3.executeUpdate();
        localPreparedStatement3.close();
        localPreparedStatement3 = null;
      }
      localTraderInfo.auSessionId = l1;
      return localTraderInfo;
    }
    catch (Exception localException)
    {
      this.logger.error("User logon error!", localException);
    }
    finally
    {
      try
      {
        if (localPreparedStatement1 != null) {
          localPreparedStatement1.close();
        }
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
        if (localPreparedStatement2 != null) {
          localPreparedStatement2.close();
        }
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
        if (localPreparedStatement3 != null) {
          localPreparedStatement3.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public int checkUser(String paramString1, String paramString2)
  {
    i = -1;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str1 = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      localConnection = this.ds.getConnection();
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, this.moduleId);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        String str2 = localResultSet.getString("status");
        String str3 = localResultSet.getString("password");
        String str4 = localResultSet.getString("Enabled");
        if (("N".equals(str2)) && (str3.equals(MD5.getMD5(paramString1, paramString2))) && ("Y".equals(str4))) {
          i = 0;
        } else if (!str3.equals(MD5.getMD5(paramString1, paramString2))) {
          i = -2;
        } else if ("D".equals(str2)) {
          i = -3;
        } else if ("N".equals(str4)) {
          i = -6;
        }
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      return i;
    }
    catch (Exception localException)
    {
      this.logger.error("User logon error!", localException);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public int changePassowrd(String paramString1, String paramString2, String paramString3)
  {
    i = -1;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = "select * from M_Trader t where t.traderid=? and t.password=?";
      localConnection = this.ds.getConnection();
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, MD5.getMD5(paramString1, paramString2));
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next()) {
        i = 0;
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if (i == 0)
      {
        str = "update M_trader set password=?,forceChangePwd=0 where traderId=? ";
        localPreparedStatement = localConnection.prepareStatement(str);
        localPreparedStatement.setString(1, MD5.getMD5(paramString1, paramString3));
        localPreparedStatement.setString(2, paramString1);
        localPreparedStatement.executeUpdate();
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      return i;
    }
    catch (Exception localException)
    {
      i = -2;
      this.logger.error("Change password error!", localException);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public void logoff(String paramString)
  {
    au.logoffUser(paramString);
  }
  
  public void logoff(long paramLong)
  {
    String str = getUserID(paramLong);
    logoff(str, paramLong);
  }
  
  public void logoff(String paramString, long paramLong)
  {
    au.logoff(paramLong);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = this.ds.getConnection();
      String str = "insert into M_TraderLog values(?,2,?,null,sysdate)";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setString(2, this.moduleId);
      localPreparedStatement.executeUpdate();
      localPreparedStatement.close();
      localPreparedStatement = null;
      return;
    }
    catch (Exception localException)
    {
      this.logger.error("insert M_TraderLog error", localException);
    }
    finally
    {
      try
      {
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public boolean isLogon(String paramString, long paramLong)
  {
    return isLogon(paramString, null, paramLong, null);
  }
  
  public TraderInfo getTraderInfo(String paramString)
  {
    localTraderInfo = new TraderInfo();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = "";
      localConnection = this.ds.getConnection();
      str = "select f.firmId,f.name firmName,traderId,m.name traderName,m.type,m.forceChangePwd from m_firm f,m_trader m where f.firmId=m.firmId and m.traderId=?";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localTraderInfo.firmId = localResultSet.getString(1);
        localTraderInfo.firmName = localResultSet.getString(2);
        localTraderInfo.traderId = localResultSet.getString(3);
        localTraderInfo.traderName = localResultSet.getString(4);
        localTraderInfo.type = localResultSet.getString(5);
        localTraderInfo.forceChangePwd = localResultSet.getInt(6);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      str = "select ip, maxtime from (select ip, occurtime maxtime from m_traderlog where traderid = ? and moduleid = ? and oprtype = 1 order by maxtime desc) where rownum < 2";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setString(2, this.moduleId);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localTraderInfo.lastIP = localResultSet.getString(1);
        localTraderInfo.lastTime = localResultSet.getString(2);
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      return localTraderInfo;
    }
    catch (Exception localException)
    {
      this.logger.error("getTraderInfo error!", localException);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public TraderInfo remoteLogon(String paramString1, String paramString2, long paramLong, String paramString3)
  {
    TraderInfo localTraderInfo = null;
    this.logger.debug("logon:" + isLogon(paramString1, paramString2, paramLong, paramString3));
    if (checkModule(paramString1))
    {
      if (isLogon(paramString1, paramString2, paramLong, paramString3))
      {
        localTraderInfo = getTraderInfo(paramString1);
        localTraderInfo.auSessionId = paramLong;
      }
      else
      {
        localTraderInfo = new TraderInfo();
        localTraderInfo.auSessionId = -7L;
      }
    }
    else
    {
      localTraderInfo = new TraderInfo();
      localTraderInfo.auSessionId = -8L;
    }
    return localTraderInfo;
  }
  
  public boolean checkModule(String paramString)
  {
    bool = false;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str1 = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      localConnection = this.ds.getConnection();
      localPreparedStatement = localConnection.prepareStatement(str1);
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setString(2, this.moduleId);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        String str2 = localResultSet.getString("status");
        String str3 = localResultSet.getString("Enabled");
        if (("N".equals(str2)) && ("Y".equals(str3))) {
          bool = true;
        }
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      return bool;
    }
    catch (Exception localException)
    {
      this.logger.error("User logon error!", localException);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  public static Map<String, Object> getRMIConfig(String paramString, DataSource paramDataSource)
  {
    localHashMap = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    Log localLog = LogFactory.getLog(LogonManager.class);
    try
    {
      String str = "";
      localConnection = paramDataSource.getConnection();
      str = "select hostip,rmi_port from m_trademodule where moduleId='" + paramString + "'";
      localLog.debug(str);
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localHashMap = new HashMap();
        localHashMap.put("host", localResultSet.getString(1));
        localLog.debug("ip:" + localHashMap.get("host"));
        localHashMap.put("port", Integer.valueOf(localResultSet.getInt(2)));
        localLog.debug("port:" + localHashMap.get("port"));
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      return localHashMap;
    }
    catch (Exception localException)
    {
      localLog.error("getRMIConfig error!", localException);
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        localLog.error(localSQLException3);
      }
    }
  }
  
  private boolean isLogon(String paramString1, String paramString2, long paramLong, String paramString3)
  {
    Boolean localBoolean = Boolean.valueOf(false);
    String str = au.getUserID(paramLong);
    this.logger.debug(Boolean.valueOf(str == null));
    if (str == null)
    {
      if (paramString2 != null)
      {
        Map localMap = (Map)this.auConfigs.get(paramString2);
        if ((localMap != null) && (localMap.get("HOSTIP") != null) && (localMap.get("RMI_PORT") != null))
        {
          this.logger.debug(localMap.get("HOSTIP"));
          this.logger.debug(localMap.get("RMI_PORT"));
          try
          {
            ActiveUserRMI localActiveUserRMI = (ActiveUserRMI)Naming.lookup("rmi://" + localMap.get("HOSTIP") + ":" + localMap.get("RMI_PORT") + "/ActiveUserService");
            str = localActiveUserRMI.getUserId(Long.valueOf(paramLong));
            this.logger.debug("uid" + str);
            this.logger.debug("userId+" + paramString1);
            if ((paramString1 != null) && (paramString1.equals(str)))
            {
              au.logon(paramString1, paramString3, Long.valueOf(paramLong));
              localBoolean = Boolean.valueOf(true);
            }
            else
            {
              localBoolean = Boolean.valueOf(false);
            }
          }
          catch (Exception localException)
          {
            this.logger.error("UserManager rmi validate user session error!", localException);
          }
        }
      }
    }
    else if (str.equals(paramString1)) {
      localBoolean = Boolean.valueOf(true);
    }
    return localBoolean.booleanValue();
  }
  
  private boolean checkErrorLogin(LogonInfo paramLogonInfo)
  {
    this.logger.debug("Entering 'checkErrorLogin' method");
    if ("N".equals(paramLogonInfo.getEnableKey())) {
      if ((paramLogonInfo.getSkey() == null) || ("".equals(paramLogonInfo.getSkey().trim())))
      {
        if (paramLogonInfo.getErrSum() >= this.allowLogonError)
        {
          paramLogonInfo.setAuSessionId(-3L);
          return false;
        }
        if (!checkPwd(paramLogonInfo.getPwd(), paramLogonInfo.getPassword(), paramLogonInfo.getUserId(), paramLogonInfo.getLogonIP()))
        {
          paramLogonInfo.setAuSessionId(-2L);
          return false;
        }
      }
      else if ((paramLogonInfo.getCkey() != null) && (!"".equals(paramLogonInfo.getCkey().trim())))
      {
        if (paramLogonInfo.getCkey().equals(paramLogonInfo.getSkey()))
        {
          if (!paramLogonInfo.getPwd().equals(MD5.getMD5(paramLogonInfo.getUserId(), paramLogonInfo.getPassword())))
          {
            paramLogonInfo.setAuSessionId(-2L);
            return false;
          }
        }
        else
        {
          if (paramLogonInfo.getErrSum() >= this.allowLogonError)
          {
            paramLogonInfo.setAuSessionId(-3L);
            return false;
          }
          if (checkPwd(paramLogonInfo.getPwd(), paramLogonInfo.getPassword(), paramLogonInfo.getUserId(), paramLogonInfo.getLogonIP()))
          {
            paramLogonInfo.setMsg("您本次登录地点和上次不一致,如果这不是您自己的主动行为,建议您及时修改密码!");
            return true;
          }
          paramLogonInfo.setAuSessionId(-2L);
          return false;
        }
      }
      else
      {
        if (paramLogonInfo.getErrSum() >= this.allowLogonError)
        {
          paramLogonInfo.setAuSessionId(-3L);
          return false;
        }
        if (!checkPwd(paramLogonInfo.getPwd(), paramLogonInfo.getPassword(), paramLogonInfo.getUserId(), paramLogonInfo.getLogonIP()))
        {
          paramLogonInfo.setAuSessionId(-2L);
          return false;
        }
      }
    }
    return true;
  }
  
  private boolean checkPwd(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.logger.debug("Entering 'checkPwd' method");
    if (paramString1.equals(MD5.getMD5(paramString3, paramString2)))
    {
      createRandomKeyCode(paramString3);
      return true;
    }
    addErrLog(paramString3, paramString4, "登录密码错误！");
    return false;
  }
  
  private void addErrLog(String paramString1, String paramString2, String paramString3)
  {
    this.logger.debug("Entering 'addErrLog' method");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection = this.ds.getConnection();
      String str1 = "insert into M_ErrorLoginLog values(?,sysdate,?,?)";
      localPreparedStatement1 = localConnection.prepareStatement(str1);
      localPreparedStatement1.setString(1, paramString1);
      localPreparedStatement1.setString(2, this.moduleId);
      localPreparedStatement1.setString(3, paramString2);
      localPreparedStatement1.executeUpdate();
      localPreparedStatement1.close();
      localPreparedStatement1 = null;
      String str2 = "insert into M_FirmLog values(sysdate,?,?,?)";
      localPreparedStatement2 = localConnection.prepareStatement(str2);
      localPreparedStatement2.setString(1, paramString1);
      localPreparedStatement2.setString(2, paramString1);
      localPreparedStatement2.setString(3, paramString3);
      localPreparedStatement2.executeUpdate();
      localPreparedStatement2.close();
      localPreparedStatement2 = null;
      return;
    }
    catch (Exception localException)
    {
      this.logger.error("User logon error! addErrlog  error", localException);
    }
    finally
    {
      try
      {
        if (localPreparedStatement1 != null) {
          localPreparedStatement1.close();
        }
        if (localPreparedStatement2 != null) {
          localPreparedStatement2.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
  
  private void createRandomKeyCode(String paramString)
  {
    this.logger.debug("Entering 'createRandomKeyCode' method");
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = str1 + paramString + String.valueOf((Math.random() * 100000.0D));
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = this.ds.getConnection();
      String str3 = "update m_trader set keycode = ? where traderid = ?";
      localPreparedStatement = localConnection.prepareStatement(str3);
      localPreparedStatement.setString(1, str2);
      localPreparedStatement.setString(2, paramString);
      localPreparedStatement.execute();
      localPreparedStatement.close();
      localPreparedStatement = null;
      return;
    }
    catch (Exception localException)
    {
      this.logger.error("User logon error! Generate  random error", localException);
    }
    finally
    {
      try
      {
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        if (localConnection != null) {
          localConnection.close();
        }
      }
      catch (SQLException localSQLException3)
      {
        localSQLException3.printStackTrace();
        this.logger.error(localSQLException3);
      }
    }
  }
}
