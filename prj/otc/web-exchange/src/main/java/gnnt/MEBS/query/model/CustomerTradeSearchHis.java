package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class CustomerTradeSearchHis
  extends Clone
{
  private String customerId;
  private String customerName;
  private String builderId;
  private String de_builderId;
  private String customer_tradeId;
  private Integer buildTradeType;
  private String commodityId;
  private String commodityName;
  private Integer holdQty;
  private String buildNo;
  private Integer build_flag;
  private Double openPrice;
  private Double holdPrice;
  private Date buildDate;
  private String de_buildNo;
  private Integer de_build_flag;
  private Double de_buildPrice;
  private Date de_buildDate;
  private Double floatingLoss;
  private Double netFloatingLoss;
  private Double tradeFee;
  private Double delayFee;
  private Date atClearDate;
  private String memberNo;
  private String tradeType;
  
  public String getTradeType()
  {
    return this.tradeType;
  }
  
  public void setTradeType(String tradeType)
  {
    this.tradeType = tradeType;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public Date getAtClearDate()
  {
    return transformData(this.atClearDate);
  }
  
  public void setAtClearDate(Date atClearDate)
  {
    this.atClearDate = atClearDate;
  }
  
  public String getCustomerId()
  {
    return this.customerId;
  }
  
  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  public String getBuilderId()
  {
    return this.builderId;
  }
  
  public void setBuilderId(String builderId)
  {
    this.builderId = builderId;
  }
  
  public String getDe_builderId()
  {
    return this.de_builderId;
  }
  
  public void setDe_builderId(String de_builderId)
  {
    this.de_builderId = de_builderId;
  }
  
  public String getCustomer_tradeId()
  {
    return this.customer_tradeId;
  }
  
  public void setCustomer_tradeId(String customer_tradeId)
  {
    this.customer_tradeId = customer_tradeId;
  }
  
  public Integer getBuildTradeType()
  {
    return this.buildTradeType;
  }
  
  public void setBuildTradeType(Integer buildTradeType)
  {
    this.buildTradeType = buildTradeType;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Integer getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Integer holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public String getBuildNo()
  {
    return this.buildNo;
  }
  
  public void setBuildNo(String buildNo)
  {
    this.buildNo = buildNo;
  }
  
  public Integer getBuild_flag()
  {
    return this.build_flag;
  }
  
  public void setBuild_flag(Integer build_flag)
  {
    this.build_flag = build_flag;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }
  
  public Date getBuildDate()
  {
    return this.buildDate;
  }
  
  public void setBuildDate(Date buildDate)
  {
    this.buildDate = buildDate;
  }
  
  public String getDe_buildNo()
  {
    return this.de_buildNo;
  }
  
  public void setDe_buildNo(String de_buildNo)
  {
    this.de_buildNo = de_buildNo;
  }
  
  public Integer getDe_build_flag()
  {
    return this.de_build_flag;
  }
  
  public void setDe_build_flag(Integer de_build_flag)
  {
    this.de_build_flag = de_build_flag;
  }
  
  public Double getDe_buildPrice()
  {
    return this.de_buildPrice;
  }
  
  public void setDe_buildPrice(Double de_buildPrice)
  {
    this.de_buildPrice = de_buildPrice;
  }
  
  public Date getDe_buildDate()
  {
    return this.de_buildDate;
  }
  
  public void setDe_buildDate(Date de_buildDate)
  {
    this.de_buildDate = de_buildDate;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getNetFloatingLoss()
  {
    return this.netFloatingLoss;
  }
  
  public void setNetFloatingLoss(Double netFloatingLoss)
  {
    this.netFloatingLoss = netFloatingLoss;
  }
  
  public Double getTradeFee()
  {
    return this.tradeFee;
  }
  
  public void setTradeFee(Double tradeFee)
  {
    this.tradeFee = tradeFee;
  }
  
  public Double getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(Double delayFee)
  {
    this.delayFee = delayFee;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
