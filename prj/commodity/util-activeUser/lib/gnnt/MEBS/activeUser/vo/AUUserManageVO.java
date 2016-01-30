package gnnt.MEBS.activeUser.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AUUserManageVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 4463624248939431921L;
  private long sessionID;
  private String userID;
  private List<Integer> moduleIDList = new ArrayList();
  private String logonType;
  private long lastTime = 0L;
  private Date logonTime = null;
  private String logonIp;
  private String lastLogonIp;
  private String lastLogonTime;
  
  public AUUserManageVO()
  {
    this.lastTime = System.currentTimeMillis();
    this.logonTime = new Date();
  }
  
  public long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(long sessionID)
  {
    this.sessionID = sessionID;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public List<Integer> getModuleIDList()
  {
    return this.moduleIDList;
  }
  
  public void setModuleIDList(List<Integer> moduleIDList)
  {
    this.moduleIDList = moduleIDList;
  }
  
  public String getLogonType()
  {
    return this.logonType;
  }
  
  public void setLogonType(String logonType)
  {
    this.logonType = logonType;
  }
  
  public long getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(long lastTime)
  {
    this.lastTime = lastTime;
  }
  
  public Date getLogonTime()
  {
    return this.logonTime;
  }
  
  public void setLogonTime(Date logonTime)
  {
    this.logonTime = logonTime;
  }
  
  public void setLogonIp(String logonIp)
  {
    this.logonIp = logonIp;
  }
  
  public String getLogonIp()
  {
    return this.logonIp;
  }
  
  public String getLastLogonIp()
  {
    return this.lastLogonIp;
  }
  
  public void setLastLogonIp(String lastLogonIp)
  {
    this.lastLogonIp = lastLogonIp;
  }
  
  public String getLastLogonTime()
  {
    return this.lastLogonTime;
  }
  
  public void setLastLogonTime(String lastLogonTime)
  {
    this.lastLogonTime = lastLogonTime;
  }
}
