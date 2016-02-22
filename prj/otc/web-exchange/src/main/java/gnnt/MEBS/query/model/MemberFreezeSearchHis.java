package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class MemberFreezeSearchHis
  extends Clone
{
  private String commodity_tradeId;
  private String commodityName;
  private String memberNo;
  private String memberName;
  private Date freeze_date;
  private Date trade_date;
  private Double b_price;
  private Double s_price;
  private Date h_date;
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getCommodity_tradeId()
  {
    return this.commodity_tradeId;
  }
  
  public void setCommodity_tradeId(String commodity_tradeId)
  {
    this.commodity_tradeId = commodity_tradeId;
  }
  
  public Date getFreeze_date()
  {
    return this.freeze_date;
  }
  
  public void setFreeze_date(Date freeze_date)
  {
    this.freeze_date = freeze_date;
  }
  
  public Date getTrade_date()
  {
    return this.trade_date;
  }
  
  public void setTrade_date(Date trade_date)
  {
    this.trade_date = trade_date;
  }
  
  public Double getB_price()
  {
    return this.b_price;
  }
  
  public void setB_price(Double b_price)
  {
    this.b_price = b_price;
  }
  
  public Double getS_price()
  {
    return this.s_price;
  }
  
  public void setS_price(Double s_price)
  {
    this.s_price = s_price;
  }
  
  public Date getH_date()
  {
    return transformData(this.h_date);
  }
  
  public void setH_date(Date h_date)
  {
    this.h_date = h_date;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public void setPrimary(String primary) {}
}
