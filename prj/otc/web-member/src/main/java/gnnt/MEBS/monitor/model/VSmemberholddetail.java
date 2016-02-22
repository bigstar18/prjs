package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VSmemberholddetail
  extends Clone
{
  private String smemberno;
  private String smembername;
  private String commodityid;
  private String commodityname;
  private String userbuyqty;
  private String usersellqty;
  private String buyprice;
  private String sellprice;
  private String buypl;
  private String sellpl;
  private String netqty;
  private String netpl;
  private String bsFlag;
  private String netholdth;
  
  public String getId()
  {
    return null;
  }
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getUserbuyqty()
  {
    return this.userbuyqty;
  }
  
  public void setUserbuyqty(String userbuyqty)
  {
    this.userbuyqty = userbuyqty;
  }
  
  public String getUsersellqty()
  {
    return this.usersellqty;
  }
  
  public void setUsersellqty(String usersellqty)
  {
    this.usersellqty = usersellqty;
  }
  
  public String getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(String netqty)
  {
    this.netqty = netqty;
  }
  
  public String getBsFlag()
  {
    return this.bsFlag;
  }
  
  public void setBsFlag(String bsFlag)
  {
    this.bsFlag = bsFlag;
  }
  
  public String getNetholdth()
  {
    return this.netholdth;
  }
  
  public void setNetholdth(String netholdth)
  {
    this.netholdth = netholdth;
  }
  
  public void setPrimary(String primary) {}
  
  public void setSmembername(String smembername)
  {
    this.smembername = smembername;
  }
  
  public String getSmembername()
  {
    return this.smembername;
  }
  
  public void setSmemberno(String smemberno)
  {
    this.smemberno = smemberno;
  }
  
  public String getSmemberno()
  {
    return this.smemberno;
  }
  
  public String getBuyprice()
  {
    return this.buyprice;
  }
  
  public void setBuyprice(String buyprice)
  {
    this.buyprice = buyprice;
  }
  
  public String getSellprice()
  {
    return this.sellprice;
  }
  
  public void setSellprice(String sellprice)
  {
    this.sellprice = sellprice;
  }
  
  public String getBuypl()
  {
    return this.buypl;
  }
  
  public void setBuypl(String buypl)
  {
    this.buypl = buypl;
  }
  
  public String getSellpl()
  {
    return this.sellpl;
  }
  
  public void setSellpl(String sellpl)
  {
    this.sellpl = sellpl;
  }
  
  public String getNetpl()
  {
    return this.netpl;
  }
  
  public void setNetpl(String netpl)
  {
    this.netpl = netpl;
  }
}
