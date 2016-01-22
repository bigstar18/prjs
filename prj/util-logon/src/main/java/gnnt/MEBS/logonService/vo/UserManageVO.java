package gnnt.MEBS.logonService.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManageVO extends BaseVO
{
  private static final long serialVersionUID = 4463624248939431921L;
  private long sessionID;
  private String userID;
  private List<Integer> moduleIDList = new ArrayList();
  private String logonType;
  private long lastTime = System.currentTimeMillis();
  private Date logonTime = null;
  private String logonIp;
  private String lastLogonIp;
  private String lastLogonTime;

  public long getSessionID()
  {
    return this.sessionID;
  }

  public void setSessionID(long paramLong)
  {
    this.sessionID = paramLong;
  }

  public String getUserID()
  {
    return this.userID;
  }

  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }

  public List<Integer> getModuleIDList()
  {
    return this.moduleIDList;
  }

  public void setModuleIDList(List<Integer> paramList)
  {
    this.moduleIDList = paramList;
  }

  public String getLogonType()
  {
    return this.logonType;
  }

  public void setLogonType(String paramString)
  {
    this.logonType = paramString;
  }

  public long getLastTime()
  {
    return this.lastTime;
  }

  public void setLastTime(long paramLong)
  {
    this.lastTime = paramLong;
  }

  public Date getLogonTime()
  {
    return this.logonTime;
  }

  public void setLogonTime(Date paramDate)
  {
    this.logonTime = paramDate;
  }

  public String getLogonIp()
  {
    return this.logonIp;
  }

  public void setLogonIp(String paramString)
  {
    this.logonIp = paramString;
  }

  public String getLastLogonIp()
  {
    return this.lastLogonIp;
  }

  public void setLastLogonIp(String paramString)
  {
    this.lastLogonIp = paramString;
  }

  public String getLastLogonTime()
  {
    return this.lastLogonTime;
  }

  public void setLastLogonTime(String paramString)
  {
    this.lastLogonTime = paramString;
  }
}