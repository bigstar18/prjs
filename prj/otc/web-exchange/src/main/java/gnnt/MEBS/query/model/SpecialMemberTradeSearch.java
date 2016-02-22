package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class SpecialMemberTradeSearch
  extends Clone
{
  private String s_memberNo;
  private String s_name;
  private String memberNo;
  private String memberName;
  private String memberSignNo;
  private String tradeNo;
  private String commodityId;
  private String commodityName;
  private String bs_flag;
  private Integer quantity;
  private Double price;
  private Double holdPrice;
  private Double close_pl;
  private Integer opentradeno;
  private Double openPrice;
  private Date holdTime;
  private Double delayFee;
  private Integer holdNo;
  private String tradeType;
  private String isSwitching;
  private Date closetime;
  private String isDelegate;
  private String s_signno;
  private String s_memberno;
  
  public String getS_memberno()
  {
    return this.s_memberno;
  }
  
  public void setS_memberno(String s_memberno)
  {
    this.s_memberno = s_memberno;
  }
  
  public Date getClosetime()
  {
    return this.closetime;
  }
  
  public void setClosetime(Date closetime)
  {
    this.closetime = closetime;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
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
  
  public String getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(String tradeNo)
  {
    this.tradeNo = tradeNo;
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
  
  public String getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(String bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public Integer getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }
  
  public Double getClose_pl()
  {
    return this.close_pl;
  }
  
  public void setClose_pl(Double close_pl)
  {
    this.close_pl = close_pl;
  }
  
  public Integer getOpentradeno()
  {
    return this.opentradeno;
  }
  
  public void setOpentradeno(Integer opentradeno)
  {
    this.opentradeno = opentradeno;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Date getHoldTime()
  {
    return this.holdTime;
  }
  
  public void setHoldTime(Date holdTime)
  {
    this.holdTime = holdTime;
  }
  
  public Double getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(Double delayFee)
  {
    this.delayFee = delayFee;
  }
  
  public Integer getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Integer holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public String getTradeType()
  {
    return this.tradeType;
  }
  
  public void setTradeType(String tradeType)
  {
    this.tradeType = tradeType;
  }
  
  public String getIsSwitching()
  {
    return this.isSwitching;
  }
  
  public void setIsSwitching(String isSwitching)
  {
    this.isSwitching = isSwitching;
  }
  
  public String getS_name()
  {
    return this.s_name;
  }
  
  public void setS_name(String s_name)
  {
    this.s_name = s_name;
  }
  
  public String getIsDelegate()
  {
    return this.isDelegate;
  }
  
  public void setIsDelegate(String isDelegate)
  {
    this.isDelegate = isDelegate;
  }
  
  public String getS_signno()
  {
    return this.s_signno;
  }
  
  public void setS_signno(String s_signno)
  {
    this.s_signno = s_signno;
  }
  
  public void setPrimary(String primary) {}
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
}
