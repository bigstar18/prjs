package gnnt.MEBS.timebargain.tradeweb.model;

public class RequestMCDQ
  extends Request
{
  private String commodityID;
  
  public RequestMCDQ()
  {
    setCMD((short)3);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
}
