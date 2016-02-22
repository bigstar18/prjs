package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SpecialMemberHoldHisSearch
  extends Clone
{
  private String s_memberNo;
  private String s_name;
  private String memberNo;
  private String memberName;
  private String memberSignNo;
  private String s_signNo;
  private Integer openTradeNo;
  private String commodityId;
  private String commodityName;
  private String bs_flag;
  private Integer holdQty;
  private Double holdPrice;
  private Double openPrice;
  private Double price;
  private Double yesterBalancePrice;
  private Double holdPl;
  private Double floatingLoss;
  private Double delayFee;
  private String status;
  private Timestamp holdTime;
  private Date clearDate;
  private String traderId;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public String getS_name()
  {
    return this.s_name;
  }
  
  public void setS_name(String s_name)
  {
    this.s_name = s_name;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getMemberSignNo()
  {
    return this.memberSignNo;
  }
  
  public void setMemberSignNo(String memberSignNo)
  {
    this.memberSignNo = memberSignNo;
  }
  
  public String getS_signNo()
  {
    return this.s_signNo;
  }
  
  public void setS_signNo(String s_signNo)
  {
    this.s_signNo = s_signNo;
  }
  
  public Integer getOpenTradeNo()
  {
    return this.openTradeNo;
  }
  
  public void setOpenTradeNo(Integer openTradeNo)
  {
    this.openTradeNo = openTradeNo;
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
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public String getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(String bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public Integer getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Integer holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public Double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Double getYesterBalancePrice()
  {
    return this.yesterBalancePrice;
  }
  
  public void setYesterBalancePrice(Double yesterBalancePrice)
  {
    this.yesterBalancePrice = yesterBalancePrice;
  }
  
  public Double getHoldPl()
  {
    return this.holdPl;
  }
  
  public void setHoldPl(Double holdPl)
  {
    this.holdPl = holdPl;
  }
  
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public Double getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(Double delayFee)
  {
    this.delayFee = delayFee;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Timestamp getHoldTime()
  {
    return this.holdTime;
  }
  
  public void setHoldTime(Timestamp holdTime)
  {
    this.holdTime = holdTime;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
