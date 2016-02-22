package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VMemberclosesum
  extends Clone
{
  private String commodityname;
  private String buyqty;
  private String buyopenprice;
  private String buyholdprice;
  private String buycloseprice;
  private String buyclosepl;
  private String sellqty;
  private String sellopenprice;
  private String sellholdprice;
  private String sellcloseprice;
  private String sellclosepl;
  private String tradefee;
  private String netqty;
  private String allpl;
  private String netpl;
  
  public String getId()
  {
    return getCommodityname();
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(String buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public String getBuyopenprice()
  {
    return this.buyopenprice;
  }
  
  public void setBuyopenprice(String buyopenprice)
  {
    this.buyopenprice = buyopenprice;
  }
  
  public String getBuycloseprice()
  {
    return this.buycloseprice;
  }
  
  public void setBuycloseprice(String buycloseprice)
  {
    this.buycloseprice = buycloseprice;
  }
  
  public String getBuyclosepl()
  {
    return this.buyclosepl;
  }
  
  public void setBuyclosepl(String buyclosepl)
  {
    this.buyclosepl = buyclosepl;
  }
  
  public String getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(String sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public String getSellopenprice()
  {
    return this.sellopenprice;
  }
  
  public void setSellopenprice(String sellopenprice)
  {
    this.sellopenprice = sellopenprice;
  }
  
  public String getSellcloseprice()
  {
    return this.sellcloseprice;
  }
  
  public void setSellcloseprice(String sellcloseprice)
  {
    this.sellcloseprice = sellcloseprice;
  }
  
  public String getSellclosepl()
  {
    return this.sellclosepl;
  }
  
  public void setSellclosepl(String sellclosepl)
  {
    this.sellclosepl = sellclosepl;
  }
  
  public String getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(String tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public String getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(String netqty)
  {
    this.netqty = netqty;
  }
  
  public String getAllpl()
  {
    return this.allpl;
  }
  
  public void setAllpl(String allpl)
  {
    this.allpl = allpl;
  }
  
  public String getNetpl()
  {
    return this.netpl;
  }
  
  public void setNetpl(String netpl)
  {
    this.netpl = netpl;
  }
  
  public void setPrimary(String primary) {}
  
  public void setBuyholdprice(String buyholdprice)
  {
    this.buyholdprice = buyholdprice;
  }
  
  public String getBuyholdprice()
  {
    return this.buyholdprice;
  }
  
  public void setSellholdprice(String sellholdprice)
  {
    this.sellholdprice = sellholdprice;
  }
  
  public String getSellholdprice()
  {
    return this.sellholdprice;
  }
}
