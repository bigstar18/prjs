package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class TCExchagerate
  extends Clone
{
  private String commodityId;
  private String inCommodityId;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getInCommodityId()
  {
    return this.inCommodityId;
  }
  
  public void setInCommodityId(String inCommodityId)
  {
    this.inCommodityId = inCommodityId;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
