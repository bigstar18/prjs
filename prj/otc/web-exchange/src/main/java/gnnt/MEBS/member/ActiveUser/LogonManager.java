package gnnt.MEBS.member.ActiveUser;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
  private Connection conn;
  private int localRMIPort;
  private int space = 30;
  private int expireTime = 30;
  private int MULTI_MODE = 2;
  private int allowLogonError = 30;
  private static ActiveUserManager au;
  
  private LogonManager(int expireTime)
  {
    this.expireTime = expireTime;
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
  
  public static LogonManager createInstance(String moduleId, DataSource ds, int expireTime)
  {
    if (um == null) {
      synchronized (LogonManager.class)
      {
        if (um == null)
        {
          um = new LogonManager(expireTime);
          um.init(moduleId, ds);
          um.initActiveUserManager();
        }
      }
    }
    return um;
  }
  
  public static LogonManager createInstance(String moduleId, DataSource ds)
  {
    if (um == null) {
      synchronized (LogonManager.class)
      {
        if (um == null)
        {
          um = new LogonManager();
          um.init(moduleId, ds);
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
  
  private void init(String moduleId, DataSource ds)
  {
    this.auConfigs = new HashMap();
    this.moduleId = moduleId;
    this.ds = ds;
    try
    {
      this.conn = ds.getConnection();
      String sql = "select moduleid, hostip, rmi_port,mutex from m_trademodule where hostip is not null and rmi_port is not null";
      PreparedStatement stat = this.conn.prepareStatement(sql);
      ResultSet rs = stat.executeQuery();
      while (rs.next())
      {
        String moduleid = rs.getString("MODULEID");
        String ip = rs.getString("HOSTIP");
        Long port = Long.valueOf(rs.getLong("RMI_PORT"));
        String mutex = rs.getString("MUTEX");
        if (moduleid.equals(this.moduleId))
        {
          this.localRMIPort = port.intValue();
          try
          {
            Registry r = LocateRegistry.getRegistry(this.localRMIPort);
            if (r != null) {
              r = LocateRegistry.createRegistry(this.localRMIPort);
            }
          }
          catch (Exception localException1) {}
          ActiveUserRMI auRMI = new ActiveUserRMIImpl();
          String name = "//" + ip + ":" + this.localRMIPort + "/ActiveUserService";
          this.logger.info("UserManager rmi bind:" + name);
          Naming.rebind(name, auRMI);
          this.logger.debug("==============>" + this.localRMIPort + " RMI服务启动成功。");
        }
        Map<String, Object> conf = new HashMap();
        
        conf.put("HOSTIP", ip);
        this.logger.debug("==============>ip:" + ip);
        conf.put("RMI_PORT", port);
        this.logger.debug("==============>port:" + port);
        if ("Y".equals(mutex)) {
          this.MULTI_MODE = 1;
        }
        this.auConfigs.put(moduleid, conf);
      }
      rs.close();
      stat.close();
      

      sql = "select value from M_Configuration where key='allowLogonError'";
      stat = this.conn.prepareStatement(sql);
      rs = stat.executeQuery();
      if (rs.next())
      {
        String value = rs.getString("value");
        this.allowLogonError = Integer.parseInt(value);
      }
      rs.close();
      stat.close();
      
      this.conn.close();
      this.conn = null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.logger.error("UserManager init error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
  }
  
  public String getUserID(long auSessionId)
  {
    return au.getUserID(auSessionId);
  }
  
  public String[] getAllUsers()
  {
    return au.getAllUsers();
  }
  
  public String[] getAllUsersWithIP()
  {
    return au.getAllUsersWithIP();
  }
  
  public TraderInfo logon(String userId, String password)
  {
    return logon(userId, password, null, null);
  }
  
  public TraderInfo logon(String userId, String password, String key, String logonIP)
  {
    this.logger.debug("Entering 'logon' method");
    TraderInfo trader = new TraderInfo();
    LogonInfo logoninfo = new LogonInfo();
    long auSessionId = -1L;
    try
    {
      this.conn = this.ds.getConnection();
      

      String delete_sql = "delete from m_errorloginlog e where trunc(e.logindate)<trunc(sysdate) and e.TraderID=?";
      PreparedStatement delete_stat = this.conn.prepareStatement(delete_sql);
      delete_stat.setString(1, userId);
      delete_stat.executeQuery();
      delete_stat.close();
      delete_stat = null;
      
      String err_sql = "select count(*) from M_ErrorLoginLog e where e.TraderID=? and trunc(e.loginDate)=trunc(sysdate)";
      PreparedStatement err_stat = this.conn.prepareStatement(err_sql);
      err_stat.setString(1, userId);
      ResultSet err_rs = err_stat.executeQuery();
      int sum = 0;
      if (err_rs.next()) {
        sum = err_rs.getInt(1);
      }
      err_rs.close();
      err_rs = null;
      err_stat.close();
      err_stat = null;
      
      String sql = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      

      PreparedStatement stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, this.moduleId);
      
      ResultSet rs = stat.executeQuery();
      if (rs.next())
      {
        String status = rs.getString("status");
        String keyCode = rs.getString("KeyCode");
        String enableKey = rs.getString("EnableKey");
        String Enabled = rs.getString("Enabled");
        String pwd = rs.getString("password");
        
        logoninfo.setAuSessionId(auSessionId);
        logoninfo.setCkey(key);
        logoninfo.setEnableKey(enableKey);
        logoninfo.setErrSum(sum);
        logoninfo.setLogonIP(logonIP);
        logoninfo.setPassword(password);
        logoninfo.setPwd(pwd);
        logoninfo.setSkey(keyCode);
        logoninfo.setUserId(userId);
        
        boolean flag = checkErrorLogin(logoninfo);
        auSessionId = logoninfo.getAuSessionId();
        if (flag) {
          if (("N".equals(status)) && 
            (("N".equals(enableKey)) || (("Y".equals(enableKey)) && 
            (key.equals(keyCode)))) && ("Y".equals(Enabled)) && 
            (pwd.equals(MD5.getMD5(userId, password))))
          {
            long sid = au.logon(userId, logonIP);
            if (sid == -1L) {
              auSessionId = -5L;
            } else {
              auSessionId = sid;
            }
          }
          else if ("D".equals(status))
          {
            auSessionId = -3L;
          }
          else if (("Y".equals(enableKey)) && (!key.equals(keyCode)))
          {
            auSessionId = -4L;
          }
          else if ("N".equals(Enabled))
          {
            auSessionId = -6L;
          }
        }
      }
      else
      {
        auSessionId = -1L;
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
      if (auSessionId > 0L)
      {
        sql = "select f.firmId,f.name firmName,traderId,m.name traderName,m.type,m.forceChangePwd,m.KeyCode from m_firm f,m_trader m where f.firmId=m.firmId and m.traderId=?";
        stat = this.conn.prepareStatement(sql);
        stat.setString(1, userId);
        rs = stat.executeQuery();
        if (rs.next())
        {
          trader.firmId = rs.getString(1);
          trader.firmName = rs.getString(2);
          trader.traderId = rs.getString(3);
          trader.traderName = rs.getString(4);
          trader.type = rs.getString(5);
          trader.forceChangePwd = rs.getInt(6);
          
          trader.keyCode = rs.getString(7);
          trader.message = logoninfo.getMsg();
        }
        rs.close();
        rs = null;
        stat.close();
        stat = null;
        
        sql = "select ip,max(OccurTime) maxTime from M_TraderLog where TraderID=? and ModuleID=? and OprType=1 group by ip order by maxTime desc";
        stat = this.conn.prepareStatement(sql);
        stat.setString(1, userId);
        stat.setString(2, this.moduleId);
        rs = stat.executeQuery();
        if (rs.next())
        {
          trader.lastIP = rs.getString(1);
          
          trader.lastTime = rs.getString(2);
        }
        rs.close();
        rs = null;
        stat.close();
        stat = null;
        
        sql = "insert into M_TraderLog values(?,1,?,?,sysdate)";
        stat = this.conn.prepareStatement(sql);
        stat.setString(1, userId);
        stat.setString(2, this.moduleId);
        stat.setString(3, logonIP);
        stat.executeUpdate();
        stat.close();
        stat = null;
      }
      trader.auSessionId = auSessionId;
    }
    catch (Exception e)
    {
      this.logger.error("User logon error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    return trader;
  }
  
  public int checkUser(String userId, String password)
  {
    int result = -1;
    try
    {
      String sql = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      this.conn = this.ds.getConnection();
      PreparedStatement stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, this.moduleId);
      ResultSet rs = stat.executeQuery();
      if (rs.next())
      {
        String status = rs.getString("status");
        String pwd = rs.getString("password");
        String Enabled = rs.getString("Enabled");
        if (("N".equals(status)) && (pwd.equals(MD5.getMD5(userId, password))) && ("Y".equals(Enabled))) {
          result = 0;
        } else if (!pwd.equals(MD5.getMD5(userId, password))) {
          result = -2;
        } else if ("D".equals(status)) {
          result = -3;
        } else if ("N".equals(Enabled)) {
          result = -6;
        }
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
    }
    catch (Exception e)
    {
      this.logger.error("User logon error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    return result;
  }
  
  public int changePassowrd(String userId, String passwordOld, String password)
  {
    int result = -1;
    try
    {
      String sql = "select * from M_Trader t where t.traderid=? and t.password=?";
      this.conn = this.ds.getConnection();
      PreparedStatement stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, MD5.getMD5(userId, passwordOld));
      ResultSet rs = stat.executeQuery();
      if (rs.next()) {
        result = 0;
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
      if (result == 0)
      {
        sql = "update M_trader set password=?,forceChangePwd=0 where traderId=? ";
        stat = this.conn.prepareStatement(sql);
        stat.setString(1, MD5.getMD5(userId, password));
        stat.setString(2, userId);
        stat.executeUpdate();
        stat.close();
        stat = null;
      }
    }
    catch (Exception e)
    {
      result = -2;
      this.logger.error("Change password error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    return result;
  }
  
  public void logoff(String userId)
  {
    au.logoffUser(userId);
  }
  
  public void logoff(long sessionID)
  {
    String userId = getUserID(sessionID);
    
    logoff(userId, sessionID);
  }
  
  public void logoff(String userId, long sessionID)
  {
    au.logoff(sessionID);
    try
    {
      this.conn = this.ds.getConnection();
      String sql = "insert into M_TraderLog values(?,2,?,null,sysdate)";
      PreparedStatement stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, this.moduleId);
      stat.executeUpdate();
      stat.close();
      stat = null;
    }
    catch (Exception e)
    {
      this.logger.error("insert M_TraderLog error", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
  }
  
  public boolean isLogon(String userId, long auSessionId)
  {
    return isLogon(userId, null, auSessionId, null);
  }
  
  public TraderInfo getTraderInfo(String userId)
  {
    TraderInfo trader = new TraderInfo();
    try
    {
      String sql = "";
      this.conn = this.ds.getConnection();
      PreparedStatement stat = null;
      
      sql = "select f.firmId,f.name firmName,traderId,m.name traderName,m.type,m.forceChangePwd from m_firm f,m_trader m where f.firmId=m.firmId and m.traderId=?";
      stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      ResultSet rs = stat.executeQuery();
      if (rs.next())
      {
        trader.firmId = rs.getString(1);
        trader.firmName = rs.getString(2);
        trader.traderId = rs.getString(3);
        trader.traderName = rs.getString(4);
        trader.type = rs.getString(5);
        trader.forceChangePwd = rs.getInt(6);
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
      
      sql = "select ip,max(OccurTime) occourTime from M_TraderLog where TraderID=? and ModuleID=? and OprType=1 group by ip order by occourTime";
      stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, this.moduleId);
      rs = stat.executeQuery();
      if (rs.next())
      {
        trader.lastIP = rs.getString(1);
        
        trader.lastTime = rs.getString(2);
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
    }
    catch (Exception e)
    {
      this.logger.error("getTraderInfo error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    return trader;
  }
  
  public TraderInfo remoteLogon(String userId, String moduleId, long auSessionId, String logonIP)
  {
    TraderInfo trader = null;
    this.logger.debug("logon:" + isLogon(userId, moduleId, auSessionId, logonIP));
    if (checkModule(userId))
    {
      if (isLogon(userId, moduleId, auSessionId, logonIP))
      {
        trader = getTraderInfo(userId);
        trader.auSessionId = auSessionId;
      }
      else
      {
        trader = new TraderInfo();
        trader.auSessionId = -7L;
      }
    }
    else
    {
      trader = new TraderInfo();
      trader.auSessionId = -8L;
    }
    return trader;
  }
  
  public boolean checkModule(String userId)
  {
    boolean sign = false;
    try
    {
      String sql = "select * from M_Trader t,M_TraderModule m where t.traderid=m.traderid and t.traderid=? and m.moduleid=?";
      this.conn = this.ds.getConnection();
      
      PreparedStatement stat = this.conn.prepareStatement(sql);
      stat.setString(1, userId);
      stat.setString(2, this.moduleId);
      
      ResultSet rs = stat.executeQuery();
      if (rs.next())
      {
        String status = rs.getString("status");
        

        String Enabled = rs.getString("Enabled");
        if (("N".equals(status)) && ("Y".equals(Enabled))) {
          sign = true;
        }
      }
      rs.close();
      rs = null;
      stat.close();
      stat = null;
    }
    catch (Exception e)
    {
      this.logger.error("User logon error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    return sign;
  }
  
  public static Map<String, Object> getRMIConfig(String moduleId, DataSource ds)
  {
    Map<String, Object> map = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    Connection conn = null;
    Log logger = LogFactory.getLog(LogonManager.class);
    try
    {
      String sql = "";
      conn = ds.getConnection();
      sql = "select hostip,rmi_port from m_trademodule where moduleId='" + moduleId + "'";
      logger.debug(sql);
      state = conn.prepareStatement(sql);
      
      rs = state.executeQuery();
      if (rs.next())
      {
        map = new HashMap();
        map.put("host", rs.getString(1));
        logger.debug("ip:" + map.get("host"));
        map.put("port", Integer.valueOf(rs.getInt(2)));
        logger.debug("port:" + map.get("port"));
      }
      rs.close();
      rs = null;
      state.close();
      state = null;
    }
    catch (Exception e)
    {
      logger.error("getRMIConfig error!", e);
      try
      {
        if (conn != null) {
          conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (conn != null) {
          conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        logger.error(ex);
      }
    }
    return map;
  }
  
  private boolean isLogon(String userId, String moduleId, long auSessionId, String logonIP)
  {
    Boolean ok = Boolean.valueOf(false);
    String uid = au.getUserID(auSessionId);
    this.logger.debug(Boolean.valueOf(uid == null));
    if (uid == null)
    {
      if (moduleId != null)
      {
        Map<String, Object> conf = (Map)this.auConfigs.get(moduleId);
        if (conf != null) {
          if ((conf.get("HOSTIP") != null) && (conf.get("RMI_PORT") != null))
          {
            this.logger.debug(conf.get("HOSTIP"));
            this.logger.debug(conf.get("RMI_PORT"));
            try
            {
              ActiveUserRMI aur = (ActiveUserRMI)Naming.lookup("rmi://" + conf.get("HOSTIP") + ":" + conf.get("RMI_PORT") + "/ActiveUserService");
              uid = aur.getUserId(Long.valueOf(auSessionId));
              this.logger.debug("uid" + uid);
              this.logger.debug("userId+" + userId);
              if ((userId != null) && (userId.equals(uid)))
              {
                au.logon(userId, logonIP, Long.valueOf(auSessionId));
                ok = Boolean.valueOf(true);
              }
              else
              {
                ok = Boolean.valueOf(false);
              }
            }
            catch (Exception e)
            {
              this.logger.error("UserManager rmi validate user session error!", e);
            }
          }
        }
      }
    }
    else if (uid.equals(userId)) {
      ok = Boolean.valueOf(true);
    }
    return ok.booleanValue();
  }
  
  private boolean checkErrorLogin(LogonInfo logoninfo)
  {
    this.logger.debug("Entering 'checkErrorLogin' method");
    if ("N".equals(logoninfo.getEnableKey())) {
      if ((logoninfo.getSkey() == null) || ("".equals(logoninfo.getSkey().trim())))
      {
        if (logoninfo.getErrSum() >= this.allowLogonError)
        {
          logoninfo.setAuSessionId(-3L);
          return false;
        }
        if (!checkPwd(logoninfo.getPwd(), logoninfo.getPassword(), logoninfo.getUserId(), logoninfo.getLogonIP()))
        {
          logoninfo.setAuSessionId(-2L);
          return false;
        }
      }
      else if ((logoninfo.getCkey() != null) && (!"".equals(logoninfo.getCkey().trim())))
      {
        if (logoninfo.getCkey().equals(logoninfo.getSkey()))
        {
          if (!logoninfo.getPwd().equals(MD5.getMD5(logoninfo.getUserId(), logoninfo.getPassword())))
          {
            logoninfo.setAuSessionId(-2L);
            return false;
          }
        }
        else
        {
          if (logoninfo.getErrSum() >= this.allowLogonError)
          {
            logoninfo.setAuSessionId(-3L);
            return false;
          }
          if (checkPwd(logoninfo.getPwd(), logoninfo.getPassword(), logoninfo.getUserId(), logoninfo.getLogonIP()))
          {
            logoninfo.setMsg("您本次登录地点和上次不一致,如果这不是您自己的主动行为,建议您及时修改密码!");
            return true;
          }
          logoninfo.setAuSessionId(-2L);
          return false;
        }
      }
      else
      {
        if (logoninfo.getErrSum() >= this.allowLogonError)
        {
          logoninfo.setAuSessionId(-3L);
          return false;
        }
        if (!checkPwd(logoninfo.getPwd(), logoninfo.getPassword(), logoninfo.getUserId(), logoninfo.getLogonIP()))
        {
          logoninfo.setAuSessionId(-2L);
          return false;
        }
      }
    }
    return true;
  }
  
  private boolean checkPwd(String pwd, String password, String userId, String logonIP)
  {
    this.logger.debug("Entering 'checkPwd' method");
    if (pwd.equals(MD5.getMD5(userId, password)))
    {
      createRandomKeyCode(userId);
      return true;
    }
    addErrLog(userId, logonIP, "登录密码错误！");
    return false;
  }
  
  private void addErrLog(String userId, String logonIP, String msg)
  {
    this.logger.debug("Entering 'addErrLog' method");
    try
    {
      String errsql = "insert into M_ErrorLoginLog values(?,sysdate,?,?)";
      PreparedStatement error = this.conn.prepareStatement(errsql);
      error.setString(1, userId);
      error.setString(2, this.moduleId);
      error.setString(3, logonIP);
      error.executeUpdate();
      error.close();
      error = null;
      
      String errLogsql = "insert into M_FirmLog values(sysdate,?,?,?)";
      PreparedStatement errLog = this.conn.prepareStatement(errLogsql);
      errLog.setString(1, userId);
      errLog.setString(2, userId);
      errLog.setString(3, msg);
      errLog.executeUpdate();
      errLog.close();
      errLog = null;
    }
    catch (Exception e)
    {
      this.logger.error("User logon error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
  }
  
  private void createRandomKeyCode(String userId)
  {
    this.logger.debug("Entering 'createRandomKeyCode' method");
    
    SimpleDateFormat spdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
    String date = spdf.format(new Date());
    String randomKey = date + userId + String.valueOf((Math.random() * 100000.0D));
    try
    {
      String sql = "update m_trader set keycode = ? where traderid = ?";
      PreparedStatement prs = this.conn.prepareStatement(sql);
      prs.setString(1, randomKey);
      prs.setString(2, userId);
      prs.execute();
      prs.close();
      prs = null;
    }
    catch (Exception e)
    {
      this.logger.error("User logon error!", e);
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
    finally
    {
      try
      {
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (SQLException ex)
      {
        ex.printStackTrace();
        this.logger.error(ex);
      }
    }
  }
}
