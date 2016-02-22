package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VCustomerclosesumSimple
  extends Clone
{
  private static final long serialVersionUID = 6087031283439691924L;
  private String commodityid;
  private String commodityname;
  private Double buyqty;
  private Double buycloseprice;
  private Double buyclosepl;
  private Double sellqty;
  private Double sellcloseprice;
  private Double sellclosepl;
  private Double tradefee;
  
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
  
  public Double getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(Double buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public Double getBuycloseprice()
  {
    return this.buycloseprice;
  }
  
  public void setBuycloseprice(Double buycloseprice)
  {
    this.buycloseprice = buycloseprice;
  }
  
  public Double getBuyclosepl()
  {
    return this.buyclosepl;
  }
  
  public void setBuyclosepl(Double buyclosepl)
  {
    this.buyclosepl = buyclosepl;
  }
  
  public Double getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(Double sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public Double getSellcloseprice()
  {
    return this.sellcloseprice;
  }
  
  public void setSellcloseprice(Double sellcloseprice)
  {
    this.sellcloseprice = sellcloseprice;
  }
  
  public Double getSellclosepl()
  {
    return this.sellclosepl;
  }
  
  public void setSellclosepl(Double sellclosepl)
  {
    this.sellclosepl = sellclosepl;
  }
  
  public Double getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(Double tradefee)
  {
    this.tradefee = tradefee;
  }
  
  public Double getNetqty()
  {
    if ((getBuyqty() == null) && (getSellqty() == null)) {
      return null;
    }
    Double result = new Double(0.0D);
    if (getBuyqty() != null) {
      result = Double.valueOf(result.doubleValue() + getBuyqty().doubleValue());
    }
    if (getSellqty() != null) {
      result = Double.valueOf(result.doubleValue() - getSellqty().doubleValue());
    }
    return result;
  }
  
  public Double getAllpl()
  {
    if ((getBuyclosepl() == null) && (getSellclosepl() == null)) {
      return null;
    }
    Double result = new Double(0.0D);
    if (getBuyclosepl() != null) {
      result = Double.valueOf(result.doubleValue() + getBuyclosepl().doubleValue());
    }
    if (getSellclosepl() != null) {
      result = Double.valueOf(result.doubleValue() + getSellclosepl().doubleValue());
    }
    return result;
  }
  
  public Double getNetpl()
  {
    if ((getBuyclosepl() == null) && (getSellclosepl() == null) && (getTradefee() == null)) {
      return null;
    }
    Double result = new Double(0.0D);
    if (getBuyclosepl() != null) {
      result = Double.valueOf(result.doubleValue() + getBuyclosepl().doubleValue());
    }
    if (getSellclosepl() != null) {
      result = Double.valueOf(result.doubleValue() + getSellclosepl().doubleValue());
    }
    if (getTradefee() != null) {
      result = Double.valueOf(result.doubleValue() + getTradefee().doubleValue());
    }
    return result;
  }
  
  public String getId()
  {
    return getCommodityid();
  }
  
  public void setPrimary(String primary) {}
}
