package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class MemberHoldSumView
  extends Clone
{
  private String commodityname;
  private String envenpricesell;
  private String envenpricebuy;
  private String openpricesell;
  private String openpricebuy;
  private String sellqty;
  private String buyqty;
  private String sellloss;
  private String buyloss;
  private String netqty;
  private String netloss;
  
  @ClassDiscription(name="id")
  public String getId()
  {
    return this.commodityname;
  }
  
  public String getOpenpricesell()
  {
    return this.openpricesell;
  }
  
  public void setOpenpricesell(String openpricesell)
  {
    this.openpricesell = openpricesell;
  }
  
  public String getOpenpricebuy()
  {
    return this.openpricebuy;
  }
  
  public void setOpenpricebuy(String openpricebuy)
  {
    this.openpricebuy = openpricebuy;
  }
  
  public String getEnvenpricesell()
  {
    return this.envenpricesell;
  }
  
  public void setEnvenpricesell(String envenpricesell)
  {
    this.envenpricesell = envenpricesell;
  }
  
  public String getEnvenpricebuy()
  {
    return this.envenpricebuy;
  }
  
  public void setEnvenpricebuy(String envenpricebuy)
  {
    this.envenpricebuy = envenpricebuy;
  }
  
  public String getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(String sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public String getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(String buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public String getSellloss()
  {
    return this.sellloss;
  }
  
  public void setSellloss(String sellloss)
  {
    this.sellloss = sellloss;
  }
  
  public String getBuyloss()
  {
    return this.buyloss;
  }
  
  public void setBuyloss(String buyloss)
  {
    this.buyloss = buyloss;
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
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public void setPrimary(String primary)
  {
    this.commodityname = primary;
  }
}
