package gnnt.MEBS.activeUser.vo;

public class AUISLogonVO extends AUBaseVO
{
  private static final long serialVersionUID = -8519409235414142280L;
  private long sessionID;
  private String userID;
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

  public String getUserID()
  {
    return this.userID;
  }

  public void setUserID(String userID)
  {
    this.userID = userID;
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