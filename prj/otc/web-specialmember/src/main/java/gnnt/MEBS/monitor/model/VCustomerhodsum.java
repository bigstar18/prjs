package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VCustomerhodsum
  extends Clone
{
  private String commodityname;
  private String buyqty;
  private String sellqty;
  private String envenpricebuy;
  private String envenpricesell;
  private String buyloss;
  private String sellloss;
  private String netqty;
  private String netloss;
  private String openpricebuy;
  private String openpricesell;
  private String buyprice;
  private String sellprice;
  
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
  
  public String getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(String sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public String getEnvenpricebuy()
  {
    return this.envenpricebuy;
  }
  
  public void setEnvenpricebuy(String envenpricebuy)
  {
    this.envenpricebuy = envenpricebuy;
  }
  
  public String getEnvenpricesell()
  {
    return this.envenpricesell;
  }
  
  public void setEnvenpricesell(String envenpricesell)
  {
    this.envenpricesell = envenpricesell;
  }
  
  public String getBuyloss()
  {
    return this.buyloss;
  }
  
  public void setBuyloss(String buyloss)
  {
    this.buyloss = buyloss;
  }
  
  public String getSellloss()
  {
    return this.sellloss;
  }
  
  public void setSellloss(String sellloss)
  {
    this.sellloss = sellloss;
  }
  
  public String getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(String netqty)
  {
    this.netqty = netqty;
  }
  
  public String getNetloss()
  {
    return this.netloss;
  }
  
  public void setNetloss(String netloss)
  {
    this.netloss = netloss;
  }
  
  public void setPrimary(String primary) {}
  
  public void setOpenpricebuy(String openpricebuy)
  {
    this.openpricebuy = openpricebuy;
  }
  
  public String getOpenpricebuy()
  {
    return this.openpricebuy;
  }
  
  public void setOpenpricesell(String openpricesell)
  {
    this.openpricesell = openpricesell;
  }
  
  public String getOpenpricesell()
  {
    return this.openpricesell;
  }
  
  public void setBuyprice(String buyprice)
  {
    this.buyprice = buyprice;
  }
  
  public String getBuyprice()
  {
    return this.buyprice;
  }
  
  public void setSellprice(String sellprice)
  {
    this.sellprice = sellprice;
  }
  
  public String getSellprice()
  {
    return this.sellprice;
  }
}
