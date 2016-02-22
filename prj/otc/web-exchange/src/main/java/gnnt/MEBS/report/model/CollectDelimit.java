package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CollectDelimit
  extends Clone
{
  private Date clearDate;
  private String bankCode;
  private String bankName;
  private String ReceivablesorPayables;
  private Double funds;
  private Double marketfeebalance;
  private Double marketdelayfeebalance;
  private Double offsetbalance;
  
  public Double getMarketdelayfeebalance()
  {
    return this.marketdelayfeebalance;
  }
  
  public void setMarketdelayfeebalance(Double marketdelayfeebalance)
  {
    this.marketdelayfeebalance = marketdelayfeebalance;
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
  
  public String getReceivablesorPayables()
  {
    return this.ReceivablesorPayables;
  }
  
  public void setReceivablesorPayables(String receivablesorPayables)
  {
    this.ReceivablesorPayables = receivablesorPayables;
  }
  
  public Double getFunds()
  {
    return this.funds;
  }
  
  public void setFunds(Double funds)
  {
    this.funds = funds;
  }
  
  public Double getMarketfeebalance()
  {
    return this.marketfeebalance;
  }
  
  public void setMarketfeebalance(Double marketfeebalance)
  {
    this.marketfeebalance = marketfeebalance;
  }
  
  public Double getOffsetbalance()
  {
    return this.offsetbalance;
  }
  
  public void setOffsetbalance(Double offsetbalance)
  {
    this.offsetbalance = offsetbalance;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
