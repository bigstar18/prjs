package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeOrderSearch
  extends Clone
{
  private Long orderno;
  private String firmId;
  private String tradeId;
  private String commodityId;
  private String commodityName;
  private String bs_flag;
  private String oc_flag;
  private String orderType;
  private String status;
  private Integer quantity;
  private Date orderTime;
  private BigDecimal price;
  private BigDecimal tradePrice;
  private BigDecimal stopLossPrice;
  private BigDecimal stopProfitPrice;
  private BigDecimal freezenMagin;
  private BigDecimal freezenFee;
  private String closeMode;
  private Long holdNo;
  private Date withDrawTime;
  private String withDrawType;
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public Long getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Long orderno)
  {
    this.orderno = orderno;
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
  
  public String getOc_flag()
  {
    return this.oc_flag;
  }
  
  public void setOc_flag(String oc_flag)
  {
    this.oc_flag = oc_flag;
  }
  
  public String getOrderType()
  {
    return this.orderType;
  }
  
  public void setOrderType(String orderType)
  {
    this.orderType = orderType;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public Integer getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }
  
  public Date getOrderTime()
  {
    return this.orderTime;
  }
  
  public void setOrderTime(Date orderTime)
  {
    this.orderTime = orderTime;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }
  
  public BigDecimal getTradePrice()
  {
    return this.tradePrice;
  }
  
  public void setTradePrice(BigDecimal tradePrice)
  {
    this.tradePrice = tradePrice;
  }
  
  public BigDecimal getStopLossPrice()
  {
    return this.stopLossPrice;
  }
  
  public void setStopLossPrice(BigDecimal stopLossPrice)
  {
    this.stopLossPrice = stopLossPrice;
  }
  
  public BigDecimal getStopProfitPrice()
  {
    return this.stopProfitPrice;
  }
  
  public void setStopProfitPrice(BigDecimal stopProfitPrice)
  {
    this.stopProfitPrice = stopProfitPrice;
  }
  
  public BigDecimal getFreezenMagin()
  {
    return this.freezenMagin;
  }
  
  public void setFreezenMagin(BigDecimal freezenMagin)
  {
    this.freezenMagin = freezenMagin;
  }
  
  public BigDecimal getFreezenFee()
  {
    return this.freezenFee;
  }
  
  public void setFreezenFee(BigDecimal freezenFee)
  {
    this.freezenFee = freezenFee;
  }
  
  public String getCloseMode()
  {
    return this.closeMode;
  }
  
  public void setCloseMode(String closeMode)
  {
    this.closeMode = closeMode;
  }
  
  public Long getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Long holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public Date getWithDrawTime()
  {
    return this.withDrawTime;
  }
  
  public void setWithDrawTime(Date withDrawTime)
  {
    this.withDrawTime = withDrawTime;
  }
  
  public String getWithDrawType()
  {
    return this.withDrawType;
  }
  
  public void setWithDrawType(String withDrawType)
  {
    this.withDrawType = withDrawType;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getTradeId()
  {
    return this.tradeId;
  }
  
  public void setTradeId(String tradeId)
  {
    this.tradeId = tradeId;
  }
  
  public TradeOrderSearch(String commodityId, String commodityName, String bs_flag, String oc_flag, String orderType, String status, Integer quantity, Date orderTime, BigDecimal price, BigDecimal tradePrice, BigDecimal stopLossPrice, BigDecimal stopProfitPrice, BigDecimal freezenMagin, BigDecimal freezenFee, String closeMode, Long holdNo, Date withDrawTime, String withDrawType)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.bs_flag = bs_flag;
    this.oc_flag = oc_flag;
    this.orderType = orderType;
    this.status = status;
    this.quantity = quantity;
    this.orderTime = orderTime;
    this.price = price;
    this.tradePrice = tradePrice;
    this.stopLossPrice = stopLossPrice;
    this.stopProfitPrice = stopProfitPrice;
    this.freezenMagin = freezenMagin;
    this.freezenFee = freezenFee;
    this.closeMode = closeMode;
    this.holdNo = holdNo;
    this.withDrawTime = withDrawTime;
    this.withDrawType = withDrawType;
  }
  
  public TradeOrderSearch() {}
}
