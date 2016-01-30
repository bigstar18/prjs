package gnnt.MEBS.activeUser.vo;

public class AULogoffVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 2924536056935552721L;
  private long sessionID;
  private String userID;
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
  
  public String getLogonType()
  {
    return this.logonType;
  }
  
  public void setLogonType(String logonType)
  {
    this.logonType = logonType;
  }
}
