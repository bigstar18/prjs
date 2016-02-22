package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerFundSearchHis
  extends Clone
{
  private String memberNo;
  private String customerNo;
  private String customerName;
  private Double balance;
  private Double floatingLoss;
  private Double netBalance;
  private Double liveMargin;
  private Double margin;
  private Double frozenMargin;
  private Double riskValue;
  private Double riskRate;
  private String isDate;
  private Date atClearDate;
  
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
  
  public Double getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(Double balance)
  {
    this.balance = balance;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getNetBalance()
  {
    return this.netBalance;
  }
  
  public void setNetBalance(Double netBalance)
  {
    this.netBalance = netBalance;
  }
  
  public Double getLiveMargin()
  {
    return this.liveMargin;
  }
  
  public void setLiveMargin(Double liveMargin)
  {
    this.liveMargin = liveMargin;
  }
  
  public Double getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(Double margin)
  {
    this.margin = margin;
  }
  
  public Double getFrozenMargin()
  {
    return this.frozenMargin;
  }
  
  public void setFrozenMargin(Double frozenMargin)
  {
    this.frozenMargin = frozenMargin;
  }
  
  public Double getRiskValue()
  {
    return this.riskValue;
  }
  
  public void setRiskValue(Double riskValue)
  {
    this.riskValue = riskValue;
  }
  
  public Double getRiskRate()
  {
    return this.riskRate;
  }
  
  public void setRiskRate(Double riskRate)
  {
    this.riskRate = riskRate;
  }
  
  public String getIsDate()
  {
    return this.isDate;
  }
  
  public void setIsDate(String isDate)
  {
    this.isDate = isDate;
  }
  
  public Date getAtClearDate()
  {
    return transformData(this.atClearDate);
  }
  
  public void setAtClearDate(Date atClearDate)
  {
    this.atClearDate = atClearDate;
  }
  
  public Double getRiskRate_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public String getRiskRate_log()
  {
    BigDecimal b = BigDecimal.valueOf(this.riskRate.doubleValue());
    return formatDecimals(b.multiply(new BigDecimal(100)), 2) + "%";
  }
}
