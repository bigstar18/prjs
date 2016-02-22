package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;

public class VCustomercloseTip
  extends Clone
{
  private static final long serialVersionUID = -4107830215068903504L;
  private Long tradeno;
  private Long opentradeno;
  private String open_bs_flag;
  private Double openprice;
  private Double holdprice;
  private String customername;
  private String memberno;
  private String membername;
  private String organizationname;
  private String managerno;
  
  public Long getTradeno()
  {
    return this.tradeno;
  }
  
  public void setTradeno(Long tradeno)
  {
    this.tradeno = tradeno;
  }
  
  public Long getOpentradeno()
  {
    return this.opentradeno;
  }
  
  public void setOpentradeno(Long opentradeno)
  {
    this.opentradeno = opentradeno;
  }
  
  public String getOpen_bs_flag()
  {
    return this.open_bs_flag;
  }
  
  public void setOpen_bs_flag(String openBsFlag)
  {
    this.open_bs_flag = openBsFlag;
  }
  
  public Double getOpenprice()
  {
    return this.openprice;
  }
  
  public void setOpenprice(Double openprice)
  {
    this.openprice = openprice;
  }
  
  public Double getHoldprice()
  {
    return this.holdprice;
  }
  
  public void setHoldprice(Double holdprice)
  {
    this.holdprice = holdprice;
  }
  
  public String getCustomername()
  {
    return this.customername;
  }
  
  public void setCustomername(String customername)
  {
    this.customername = customername;
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
  
  public Long getId()
  {
    return getTradeno();
  }
  
  public void setPrimary(String primary) {}
}
