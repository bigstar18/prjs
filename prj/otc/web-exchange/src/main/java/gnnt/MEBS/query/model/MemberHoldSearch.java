package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.sql.Timestamp;

public class MemberHoldSearch
  extends Clone
{
  private Integer holdNo;
  private String traderId;
  private String memberNo;
  private String memberName;
  private String commodityId;
  private String commodityName;
  private int holdQty;
  private int bs_flag;
  private Timestamp holdTime;
  private String s_memberNo;
  private String s_memberName;
  private Double openPrice;
  private Double holdPrice;
  private Double curprice;
  private Double floatingLoss;
  private Double delayFee;
  private Double mktdelayFee;
  private Double memberdelayFee;
  
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
  
  @ClassDiscription(name="持仓单号")
  public Integer getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Integer holdNo)
  {
    this.holdNo = holdNo;
  }
  
  @ClassDiscription(name="操作员")
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }
  
  @ClassDiscription(name="商品代码")
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称")
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="数量")
  public int getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(int holdQty)
  {
    this.holdQty = holdQty;
  }
  
  @ClassDiscription(name="买卖标志", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public int getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(int bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  @ClassDiscription(name="下单时间")
  public Timestamp getHoldTime()
  {
    return this.holdTime;
  }
  
  public void setHoldTime(Timestamp holdTime)
  {
    this.holdTime = holdTime;
  }
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String no)
  {
    this.s_memberNo = no;
  }
  
  @ClassDiscription(name="建仓价")
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  @ClassDiscription(name="持仓价")
  public Double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }
  
  @ClassDiscription(name="盈亏")
  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  @ClassDiscription(name=" 延期费")
  public Double getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(Double delayFee)
  {
    this.delayFee = delayFee;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getS_memberName()
  {
    return this.s_memberName;
  }
  
  public void setS_memberName(String s_memberName)
  {
    this.s_memberName = s_memberName;
  }
  
  public Double getCurprice()
  {
    return this.curprice;
  }
  
  public void setCurprice(Double curprice)
  {
    this.curprice = curprice;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public void setPrimary(String primary) {}
}
