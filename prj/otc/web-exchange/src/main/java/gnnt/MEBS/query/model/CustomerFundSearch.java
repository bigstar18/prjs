package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerFundSearch
  extends Clone
{
  private String memberNo;
  private String memberName;
  private String organizationno;
  private String organizationname;
  private String brokerageno;
  private String brokeragename;
  private String customerNo;
  private String customerName;
  private Double beginningcaptical;
  private Double runtimefundio;
  private Double runtimeclosepl;
  private Double floatingloss;
  private Double runtimefee;
  private Double margin;
  private Double livemargin;
  private Double frozenmargin;
  private Double frozenfee;
  private Double presentcaptical;
  private Double riskRate;
  private String status;
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  public Double getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(Double margin)
  {
    this.margin = margin;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getOrganizationno()
  {
    return this.organizationno;
  }
  
  public void setOrganizationno(String organizationno)
  {
    this.organizationno = organizationno;
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public String getBrokerageno()
  {
    return this.brokerageno;
  }
  
  public void setBrokerageno(String brokerageno)
  {
    this.brokerageno = brokerageno;
  }
  
  public String getBrokeragename()
  {
    return this.brokeragename;
  }
  
  public void setBrokeragename(String brokeragename)
  {
    this.brokeragename = brokeragename;
  }
  
  public Double getRuntimefundio()
  {
    return this.runtimefundio;
  }
  
  public void setRuntimefundio(Double runtimefundio)
  {
    this.runtimefundio = runtimefundio;
  }
  
  public Double getRuntimeclosepl()
  {
    return this.runtimeclosepl;
  }
  
  public void setRuntimeclosepl(Double runtimeclosepl)
  {
    this.runtimeclosepl = runtimeclosepl;
  }
  
  public Double getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(Double floatingloss)
  {
    this.floatingloss = floatingloss;
  }
  
  public Double getRuntimefee()
  {
    return this.runtimefee;
  }
  
  public void setRuntimefee(Double runtimefee)
  {
    this.runtimefee = runtimefee;
  }
  
  public Double getLivemargin()
  {
    return this.livemargin;
  }
  
  public void setLivemargin(Double livemargin)
  {
    this.livemargin = livemargin;
  }
  
  public Double getFrozenmargin()
  {
    return this.frozenmargin;
  }
  
  public void setFrozenmargin(Double frozenmargin)
  {
    this.frozenmargin = frozenmargin;
  }
  
  public Double getFrozenfee()
  {
    return this.frozenfee;
  }
  
  public void setFrozenfee(Double frozenfee)
  {
    this.frozenfee = frozenfee;
  }
  
  public Double getBeginningcaptical()
  {
    return this.beginningcaptical;
  }
  
  public void setBeginningcaptical(Double beginningcaptical)
  {
    this.beginningcaptical = beginningcaptical;
  }
  
  public Double getPresentcaptical()
  {
    return this.presentcaptical;
  }
  
  public void setPresentcaptical(Double presentcaptical)
  {
    this.presentcaptical = presentcaptical;
  }
  
  public Double getRiskRate()
  {
    return this.riskRate;
  }
  
  public void setRiskRate(Double riskRate)
  {
    this.riskRate = riskRate;
  }
  
  public Double getRiskRate_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public String getRiskRate_log()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    String rate = "";
    if ("F".equals(this.status)) {
      rate = "--";
    } else if (this.riskRate.doubleValue() >= 2.0D) {
      rate = "安全";
    } else {
      rate = formatDecimals(b.multiply(new BigDecimal(100)), 2) + "%";
    }
    return rate;
  }
}
