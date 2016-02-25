package gnnt.MEBS.member.firm.unit;

public class FirmLog
{
  private String userId;
  private String firmId;
  private String action;
  
  public FirmLog(String paramString1, String paramString2, String paramString3)
  {
    this.userId = paramString1;
    this.firmId = paramString2;
    this.action = paramString3;
  }
  
  public FirmLog() {}
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
  }
}
