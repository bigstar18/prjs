package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VCustomerfundTip
  extends Clone
{
  private static final long serialVersionUID = 1998657579950200087L;
  private String customerno;
  private String customername;
  private Double lastbalance;
  private Double netbalance;
  private Double floatingloss;
  private Double frozenmargin;
  private Double margin;
  private Double livemargin;
  private Double riskvalue;
  private String memberno;
  private String membername;
  private String organizationname;
  private String managerno;
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
  }
  
  public String getCustomername()
  {
    return this.customername;
  }
  
  public void setCustomername(String customername)
  {
    this.customername = customername;
  }
  
  public Double getLastbalance()
  {
    return this.lastbalance;
  }
  
  public void setLastbalance(Double lastbalance)
  {
    this.lastbalance = lastbalance;
  }
  
  public Double getNetbalance()
  {
    return this.netbalance;
  }
  
  public void setNetbalance(Double netbalance)
  {
    this.netbalance = netbalance;
  }
  
  public Double getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(Double floatingloss)
  {
    this.floatingloss = floatingloss;
  }
  
  public Double getFrozenmargin()
  {
    return this.frozenmargin;
  }
  
  public void setFrozenmargin(Double frozenmargin)
  {
    this.frozenmargin = frozenmargin;
  }
  
  public Double getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(Double margin)
  {
    this.margin = margin;
  }
  
  public Double getLivemargin()
  {
    return this.livemargin;
  }
  
  public void setLivemargin(Double livemargin)
  {
    this.livemargin = livemargin;
  }
  
  public Double getRiskvalue()
  {
    return this.riskvalue;
  }
  
  public void setRiskvalue(Double riskvalue)
  {
    this.riskvalue = riskvalue;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getMembername()
  {
    return this.membername;
  }
  
  public void setMembername(String membername)
  {
    this.membername = membername;
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public String getManagerno()
  {
    return this.managerno;
  }
  
  public void setManagerno(String managerno)
  {
    this.managerno = managerno;
  }
  
  public String getId()
  {
    return getCustomerno();
  }
  
  public void setPrimary(String primary) {}
}
