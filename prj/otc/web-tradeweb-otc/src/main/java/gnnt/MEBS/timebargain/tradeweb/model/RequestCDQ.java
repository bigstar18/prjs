package gnnt.MEBS.timebargain.tradeweb.model;

public class RequestCDQ
  extends Request
{
  private String commodityID;
  private String agencyNO;
  private String phonePWD;
  
  public RequestCDQ()
  {
    setCMD((short)1);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getAgencyNO()
  {
    return this.agencyNO;
  }
  
  public void setAgencyNO(String agencyNO)
  {
    this.agencyNO = agencyNO;
  }
  
  public String getPhonePWD()
  {
    return this.phonePWD;
  }
  
  public void setPhonePWD(String phonePWD)
  {
    this.phonePWD = phonePWD;
  }
}
