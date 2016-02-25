package gnnt.MEBS.vendue.server.beans.dtobeans;

import java.sql.Timestamp;

public class Commodity
{
  private Long ID;
  private String code;
  private Timestamp firstTime;
  private Timestamp createTime;
  private Long status;
  private Long splitID;
  private Double beginPrice;
  private Double stepPrice;
  private Long amount;
  private Double tradeUnit;
  private Double alertPrice;
  private Double security;
  private Double fee;
  private Double minAmount;
  
  public Double getAlertPrice()
  {
    return this.alertPrice;
  }
  
  public void setAlertPrice(Double paramDouble)
  {
    this.alertPrice = paramDouble;
  }
  
  public Long getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Long paramLong)
  {
    this.amount = paramLong;
  }
  
  public Double getBeginPrice()
  {
    return this.beginPrice;
  }
  
  public void setBeginPrice(Double paramDouble)
  {
    this.beginPrice = paramDouble;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public Double getFee()
  {
    return this.fee;
  }
  
  public void setFee(Double paramDouble)
  {
    this.fee = paramDouble;
  }
  
  public Long getID()
  {
    return this.ID;
  }
  
  public void setID(Long paramLong)
  {
    this.ID = paramLong;
  }
  
  public Double getMinAmount()
  {
    return this.minAmount;
  }
  
  public void setMinAmount(Double paramDouble)
  {
    this.minAmount = paramDouble;
  }
  
  public Double getSecurity()
  {
    return this.security;
  }
  
  public void setSecurity(Double paramDouble)
  {
    this.security = paramDouble;
  }
  
  public Long getSplitID()
  {
    return this.splitID;
  }
  
  public void setSplitID(Long paramLong)
  {
    this.splitID = paramLong;
  }
  
  public Long getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Long paramLong)
  {
    this.status = paramLong;
  }
  
  public Double getStepPrice()
  {
    return this.stepPrice;
  }
  
  public void setStepPrice(Double paramDouble)
  {
    this.stepPrice = paramDouble;
  }
  
  public Double getTradeUnit()
  {
    return this.tradeUnit;
  }
  
  public void setTradeUnit(Double paramDouble)
  {
    this.tradeUnit = paramDouble;
  }
  
  public Timestamp getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp paramTimestamp)
  {
    this.createTime = paramTimestamp;
  }
  
  public Timestamp getFirstTime()
  {
    return this.firstTime;
  }
  
  public void setFirstTime(Timestamp paramTimestamp)
  {
    this.firstTime = paramTimestamp;
  }
}
