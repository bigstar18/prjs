package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MartketFundsReport
  extends Clone
{
  private Date clearDate;
  private String bankCode;
  private String bankName;
  private Double lastbankbalance;
  private Integer outcount;
  private Double outfund;
  private Integer incount;
  private Double infund;
  private Double totaltradefee;
  private Double marketfeenew;
  private Double marketdelayfeenew;
  private Double marketfeeout;
  private Double marketfeebalance;
  private Double totaldelayfee;
  private Double totalpl;
  private Double netbalance;
  private Double bankbalance;
  
  public Double getMarketdelayfeenew()
  {
    return this.marketdelayfeenew;
  }
  
  public void setMarketdelayfeenew(Double marketdelayfeenew)
  {
    this.marketdelayfeenew = marketdelayfeenew;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getBankCode()
  {
    return this.bankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.bankCode = bankCode;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
  
  public Double getLastbankbalance()
  {
    return this.lastbankbalance;
  }
  
  public void setLastbankbalance(Double lastbankbalance)
  {
    this.lastbankbalance = lastbankbalance;
  }
  
  public Integer getOutcount()
  {
    return this.outcount;
  }
  
  public void setOutcount(Integer outcount)
  {
    this.outcount = outcount;
  }
  
  public Double getOutfund()
  {
    return this.outfund;
  }
  
  public void setOutfund(Double outfund)
  {
    this.outfund = outfund;
  }
  
  public Integer getIncount()
  {
    return this.incount;
  }
  
  public void setIncount(Integer incount)
  {
    this.incount = incount;
  }
  
  public Double getInfund()
  {
    return this.infund;
  }
  
  public void setInfund(Double infund)
  {
    this.infund = infund;
  }
  
  public Double getTotaltradefee()
  {
    return this.totaltradefee;
  }
  
  public void setTotaltradefee(Double totaltradefee)
  {
    this.totaltradefee = totaltradefee;
  }
  
  public Double getMarketfeenew()
  {
    return this.marketfeenew;
  }
  
  public void setMarketfeenew(Double marketfeenew)
  {
    this.marketfeenew = marketfeenew;
  }
  
  public Double getMarketfeeout()
  {
    return this.marketfeeout;
  }
  
  public void setMarketfeeout(Double marketfeeout)
  {
    this.marketfeeout = marketfeeout;
  }
  
  public Double getMarketfeebalance()
  {
    return this.marketfeebalance;
  }
  
  public void setMarketfeebalance(Double marketfeebalance)
  {
    this.marketfeebalance = marketfeebalance;
  }
  
  public Double getTotaldelayfee()
  {
    return this.totaldelayfee;
  }
  
  public void setTotaldelayfee(Double totaldelayfee)
  {
    this.totaldelayfee = totaldelayfee;
  }
  
  public Double getTotalpl()
  {
    return this.totalpl;
  }
  
  public void setTotalpl(Double totalpl)
  {
    this.totalpl = totalpl;
  }
  
  public Double getNetbalance()
  {
    return this.netbalance;
  }
  
  public void setNetbalance(Double netbalance)
  {
    this.netbalance = netbalance;
  }
  
  public Double getBankbalance()
  {
    return this.bankbalance;
  }
  
  public void setBankbalance(Double bankbalance)
  {
    this.bankbalance = bankbalance;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
