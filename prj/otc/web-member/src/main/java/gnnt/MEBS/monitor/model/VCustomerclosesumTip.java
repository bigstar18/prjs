package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VCustomerclosesumTip
  extends Clone
{
  private static final long serialVersionUID = -5781950165541829958L;
  private String commodityid;
  private Double buyopenprice;
  private Double buyholdprice;
  private Double sellopenprice;
  private Double sellholdprice;
  private Double buyqty;
  private Double sellqty;
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public Double getBuyopenprice()
  {
    return this.buyopenprice;
  }
  
  public void setBuyopenprice(Double buyopenprice)
  {
    this.buyopenprice = buyopenprice;
  }
  
  public Double getBuyholdprice()
  {
    return this.buyholdprice;
  }
  
  public void setBuyholdprice(Double buyholdprice)
  {
    this.buyholdprice = buyholdprice;
  }
  
  public Double getSellopenprice()
  {
    return this.sellopenprice;
  }
  
  public void setSellopenprice(Double sellopenprice)
  {
    this.sellopenprice = sellopenprice;
  }
  
  public Double getSellholdprice()
  {
    return this.sellholdprice;
  }
  
  public void setSellholdprice(Double sellholdprice)
  {
    this.sellholdprice = sellholdprice;
  }
  
  public Double getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(Double buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public Double getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(Double sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public String getId()
  {
    return getCommodityid();
  }
  
  public void setPrimary(String primary) {}
}
