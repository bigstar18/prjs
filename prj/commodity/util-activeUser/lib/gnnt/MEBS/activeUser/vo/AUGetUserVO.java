package gnnt.MEBS.activeUser.vo;

public class AUGetUserVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 4203707902289457424L;
  private long sessionID;
  private Integer moduleID;
  private String logonType;
  
  public long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(long sessionID)
  {
    this.sessionID = sessionID;
  }
  
  public Integer getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(Integer moduleID)
  {
    this.moduleID = moduleID;
  }
  
  public String getLogonType()
  {
    return this.logonType;
  }
  
  public void setLogonType(String logonType)
  {
    this.logonType = logonType;
  }
}
