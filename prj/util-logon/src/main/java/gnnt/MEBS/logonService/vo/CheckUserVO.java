package gnnt.MEBS.logonService.vo;

public class CheckUserVO extends BaseVO
{
  private static final long serialVersionUID = 6049725213980514919L;
  private long sessionID;
  private String userID;
  private int toModuleID;
  private String logonType;

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

  public int getToModuleID()
  {
    return this.toModuleID;
  }

  public void setToModuleID(int paramInt)
  {
    this.toModuleID = paramInt;
  }

  public String getLogonType()
  {
    return this.logonType;
  }

  public void setLogonType(String paramString)
  {
    this.logonType = paramString;
  }
}