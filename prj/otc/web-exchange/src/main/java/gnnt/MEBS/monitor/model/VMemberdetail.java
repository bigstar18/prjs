package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VMemberdetail
  extends Clone
{
  private String memberno;
  private String membername;
  private String smemberno;
  private String commodityid;
  private String commodityname;
  private Integer bsFlag;
  private Long qty;
  private Double evenprice;
  private Double floatingloss;
  
  public String getId()
  {
    return this.memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getSmemberno()
  {
    return this.smemberno;
  }
  
  public void setSmemberno(String smemberno)
  {
    this.smemberno = smemberno;
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
  
  public Integer getBsFlag()
  {
    return this.bsFlag;
  }
  
  public void setBsFlag(Integer bsFlag)
  {
    this.bsFlag = bsFlag;
  }
  
  public Long getQty()
  {
    return this.qty;
  }
  
  public void setQty(Long qty)
  {
    this.qty = qty;
  }
  
  public Double getEvenprice()
  {
    return this.evenprice;
  }
  
  public void setEvenprice(Double evenprice)
  {
    this.evenprice = evenprice;
  }
  
  public Double getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(Double floatingloss)
  {
    this.floatingloss = floatingloss;
  }
  
  public String getMembername()
  {
    return this.membername;
  }
  
  public void setMembername(String membername)
  {
    this.membername = membername;
  }
  
  public void setPrimary(String primary) {}
}
