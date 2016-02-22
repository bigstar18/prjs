package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VSmembersum
  extends Clone
{
  private String commodityname;
  private String smemberno;
  private Integer buyqty;
  private Integer sellqty;
  private Double envenpricebuy;
  private Double envenpricesell;
  private Double buyloss;
  private Double sellloss;
  private Integer netqty;
  private Double netloss;
  
  public String getId()
  {
    return this.commodityname;
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getSmemberno()
  {
    return this.smemberno;
  }
  
  public void setSmemberno(String smemberno)
  {
    this.smemberno = smemberno;
  }
  
  public Integer getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(Integer buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public Integer getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(Integer sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public Double getEnvenpricebuy()
  {
    return this.envenpricebuy;
  }
  
  public void setEnvenpricebuy(Double envenpricebuy)
  {
    this.envenpricebuy = envenpricebuy;
  }
  
  public Double getEnvenpricesell()
  {
    return this.envenpricesell;
  }
  
  public void setEnvenpricesell(Double envenpricesell)
  {
    this.envenpricesell = envenpricesell;
  }
  
  public Double getBuyloss()
  {
    return this.buyloss;
  }
  
  public void setBuyloss(Double buyloss)
  {
    this.buyloss = buyloss;
  }
  
  public Double getSellloss()
  {
    return this.sellloss;
  }
  
  public void setSellloss(Double sellloss)
  {
    this.sellloss = sellloss;
  }
  
  public Integer getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(Integer netqty)
  {
    this.netqty = netqty;
  }
  
  public Double getNetloss()
  {
    return this.netloss;
  }
  
  public void setNetloss(Double netloss)
  {
    this.netloss = netloss;
  }
  
  public void setPrimary(String primary) {}
}
