package gnnt.MEBS.logonService.vo;

public class GetUserVO extends BaseVO
{
  private static final long serialVersionUID = -5163763200646529238L;
  private long sessionID;
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