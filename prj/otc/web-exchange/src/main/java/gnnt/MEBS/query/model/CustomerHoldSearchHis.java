package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class CustomerHoldSearchHis
  extends Clone
{
  private Date clearDate;
  private String customer_TradeId;
  private String customerNo;
  private String memberNo;
  private String memberName;
  private String organizationNo;
  private String organizationName;
  private String brokerageNo;
  private String brokerageName;
  private String traderName;
  private String conTact;
  private int holdNo;
  private String commodityId;
  private String commodityName;
  private int holdQty;
  private int bs_flag;
  private Timestamp holdTime;
  private Double openPrice;
  private Double holdPrice;
  private Double price;
  private Double floatingLoss;
  private Double holdMargin;
  private Double tradeFee;
  private Double delayFee;
  private Double mktdelayFee;
  private Double memberdelayFee;
  private String oparateName;
  
  public Double getMktdelayFee()
  {
    return this.mktdelayFee;
  }
  
  public void setMktdelayFee(Double mktdelayFee)
  {
    this.mktdelayFee = mktdelayFee;
  }
  
  public Double getMemberdelayFee()
  {
    return this.memberdelayFee;
  }
  
  public void setMemberdelayFee(Double memberdelayFee)
  {
    this.memberdelayFee = memberdelayFee;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getCustomer_TradeId()
  {
    return this.customer_TradeId;
  }
  
  public void setCustomer_TradeId(String customer_TradeId)
  {
    this.customer_TradeId = customer_TradeId;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getOrganizationNo()
  {
    return this.organizationNo;
  }
  
  public void setOrganizationNo(String organizationNo)
  {
    this.organizationNo = organizationNo;
  }
  
  public String getOrganizationName()
  {
    return this.organizationName;
  }
  
  public void setOrganizationName(String organizationName)
  {
    this.organizationName = organizationName;
  }
  
  public String getBrokerageNo()
  {
    return this.brokerageNo;
  }
  
  public void setBrokerageNo(String brokerageNo)
  {
    this.brokerageNo = brokerageNo;
  }
  
  public String getBrokerageName()
  {
    return this.brokerageName;
  }
  
  public void setBrokerageName(String brokerageName)
  {
    this.brokerageName = brokerageName;
  }
  
  public String getTraderName()
  {
    return this.traderName;
  }
  
  public void setTraderName(String traderName)
  {
    this.traderName = traderName;
  }
  
  public String getConTact()
  {
    return this.conTact;
  }
  
  public void setConTact(String conTact)
  {
    this.conTact = conTact;
  }
  
  public int getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(int holdNo)
  {
    this.holdNo = holdNo;
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
  
  public int getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(int holdQty)
  {
    this.holdQty = holdQty;
  }
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public int getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(int bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public Timestamp getHoldTime()
  {
    return this.holdTime;
  }
  
  public void setHoldTime(Timestamp holdTime)
  {
    this.holdTime = holdTime;
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
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getHoldMargin()
  {
    return this.holdMargin;
  }
  
  public void setHoldMargin(Double holdMargin)
  {
    this.holdMargin = holdMargin;
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
  
  public String getOparateName()
  {
    return this.oparateName;
  }
  
  public void setOparateName(String oparateName)
  {
    this.oparateName = oparateName;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
