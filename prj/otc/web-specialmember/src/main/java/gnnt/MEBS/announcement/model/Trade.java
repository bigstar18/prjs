package gnnt.MEBS.announcement.model;

import gnnt.MEBS.base.model.Clone;

public class Trade
  extends Clone
{
  private Long tradeNo;
  private Long orderNo;
  private Long holdNo;
  private int bs_flag;
  private String firmId;
  private String commodityId;
  private String o_firmId;
  private String commodityName;
  private String firmName;
  private Integer o_bs_flag;
  private String oc_flag;
  private Long quantity;
  
  public Trade() {}
  
  public Trade(Long tradeNo, Long orderNo, Long holdNo, int bs_flag, String firmId, String commodityId, String o_firmId, String commodityName, String firmName, Integer o_bs_flag, String oc_flag, Long quantity)
  {
    this.tradeNo = tradeNo;
    this.orderNo = orderNo;
    this.holdNo = holdNo;
    this.bs_flag = bs_flag;
    this.firmId = firmId;
    this.commodityId = commodityId;
    this.o_firmId = o_firmId;
    this.commodityName = commodityName;
    this.firmName = firmName;
    this.o_bs_flag = o_bs_flag;
    this.oc_flag = oc_flag;
    this.quantity = quantity;
  }
  
  public Integer getO_bs_flag()
  {
    return this.o_bs_flag;
  }
  
  public void setO_bs_flag(Integer o_bs_flag)
  {
    this.o_bs_flag = o_bs_flag;
  }
  
  public String getOc_flag()
  {
    return this.oc_flag;
  }
  
  public void setOc_flag(String oc_flag)
  {
    this.oc_flag = oc_flag;
  }
  
  public Long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Long getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public Long getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Long holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public int getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(int bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getO_firmId()
  {
    return this.o_firmId;
  }
  
  public void setO_firmId(String o_firmId)
  {
    this.o_firmId = o_firmId;
  }
  
  public Long getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(Long tradeNo)
  {
    this.tradeNo = tradeNo;
  }
  
  public Long getId()
  {
    return this.tradeNo;
  }
  
  public void setPrimary(String primary)
  {
    this.tradeNo = Long.valueOf(Long.parseLong(primary));
  }
}
