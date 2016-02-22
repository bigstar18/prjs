package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCommoditytry
  extends Clone
{
  private String commodityid;
  private String commodityname;
  private String curprice;
  private String price;
  
  public String getId()
  {
    return getCommodityid();
  }
  
  public void setPrimary(String primary) {}
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCurprice(String curprice)
  {
    this.curprice = new DecimalFormat(",##0.00").format(Double.parseDouble(curprice));
  }
  
  public String getCurprice()
  {
    return this.curprice;
  }
  
  public void setPrice(String price)
  {
    this.price = new DecimalFormat("##0.00").format(Double.parseDouble(price));
  }
  
  public String getPrice()
  {
    return this.price;
  }
}
