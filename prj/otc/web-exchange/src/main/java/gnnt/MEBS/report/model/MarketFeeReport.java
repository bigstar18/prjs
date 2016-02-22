package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MarketFeeReport
  extends Clone
{
  private Date clearDate;
  private String bankCode;
  private String bankName;
  private Double lastmarketfeebalance;
  private Double marketfeenew;
  private Double marketfeeout;
  private Double marketfeebalance;
  
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
  
  public Double getLastmarketfeebalance()
  {
    return this.lastmarketfeebalance;
  }
  
  public void setLastmarketfeebalance(Double lastmarketfeebalance)
  {
    this.lastmarketfeebalance = lastmarketfeebalance;
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
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
