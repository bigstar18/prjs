package gnnt.MEBS.logonService.vo;

public class ISLogonVO extends BaseVO
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

  public Integer getModuleID()
  {
    return this.moduleID;
  }

  public void setModuleID(Integer paramInteger)
  {
    this.moduleID = paramInteger;
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