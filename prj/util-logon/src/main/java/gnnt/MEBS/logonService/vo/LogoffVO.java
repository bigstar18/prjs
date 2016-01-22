package gnnt.MEBS.logonService.vo;

public class LogoffVO extends BaseVO
{
  private static final long serialVersionUID = 2924536056935552721L;
  private long sessionID;
  private String userID;
  private String logonType;
  private int moduleID;

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

  public String getLogonType()
  {
    return this.logonType;
  }

  public void setLogonType(String paramString)
  {
    this.logonType = paramString;
  }

  public int getModuleID()
  {
    return this.moduleID;
  }

  public void setModuleID(int paramInt)
  {
    this.moduleID = paramInt;
  }
}