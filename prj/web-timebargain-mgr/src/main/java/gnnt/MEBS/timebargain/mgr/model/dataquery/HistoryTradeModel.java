package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class HistoryTradeModel extends StandardModel
{
  private static final long serialVersionUID = -6819975734302520411L;

  @ClassDiscription(name="成交号", description="")
  private Long tradeNo;

  @ClassDiscription(name="结算时间", description="")
  private Date clearDate;

  @ClassDiscription(name="委托单号", description="")
  private Long orderNo;

  @ClassDiscription(name="成交时间", description="")
  private Date tradeTime;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志 ", description="1:买 buy 2:卖 sell")
  private Long flag;

  @ClassDiscription(name="委托类型", description="")
  private Long orderType;

  @ClassDiscription(name="价格", description="")
  private Double price;

  @ClassDiscription(name=" 数量", description="")
  private Long quantity;

  @ClassDiscription(name="平仓盈亏", description="")
  private Double close;

  @ClassDiscription(name="易手续费", description="")
  private Double tradeFee;

  @ClassDiscription(name="成交类型", description="")
  private Long tradeType;

  @ClassDiscription(name="订货价格", description="")
  private Double holdPrice;

  @ClassDiscription(name="订货时间", description="")
  private Date holdTime;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="平仓增值税", description="")
  private Double closeAddedTax;

  @ClassDiscription(name="对方撮合成交号", description="")
  private String firmName;

  @ClassDiscription(name="订货所属结算日期", description="")
  private String oppCustomerId;

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public Long getTradeNo()
  {
    return this.tradeNo;
  }

  public void setTradeNo(Long tradeNo)
  {
    this.tradeNo = tradeNo;
  }

  public Long getOrderNo()
  {
    return this.orderNo;
  }

  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }

  public Date getTradeTime()
  {
    return this.tradeTime;
  }

  public void setTradeTime(Date tradeTime)
  {
    this.tradeTime = tradeTime;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public Long getFlag()
  {
    return this.flag;
  }

  public void setFlag(Long flag)
  {
    this.flag = flag;
  }

  public Long getOrderType()
  {
    return this.orderType;
  }

  public void setOrderType(Long orderType)
  {
    this.orderType = orderType;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public Double getClose()
  {
    return this.close;
  }

  public void setClose(Double close)
  {
    this.close = close;
  }

  public Double getTradeFee()
  {
    return this.tradeFee;
  }

  public void setTradeFee(Double tradeFee)
  {
    this.tradeFee = tradeFee;
  }

  public Long getTradeType()
  {
    return this.tradeType;
  }

  public void setTradeType(Long tradeType)
  {
    this.tradeType = tradeType;
  }

  public Double getHoldPrice()
  {
    return this.holdPrice;
  }

  public void setHoldPrice(Double holdPrice)
  {
    this.holdPrice = holdPrice;
  }

  public Date getHoldTime()
  {
    return this.holdTime;
  }

  public void setHoldTime(Date holdTime)
  {
    this.holdTime = holdTime;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public Double getCloseAddedTax()
  {
    return this.closeAddedTax;
  }

  public void setCloseAddedTax(Double closeAddedTax)
  {
    this.closeAddedTax = closeAddedTax;
  }

  public String getFirmName()
  {
    return this.firmName;
  }

  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }

  public String getOppCustomerId()
  {
    return this.oppCustomerId;
  }

  public void setOppCustomerId(String oppCustomerId)
  {
    this.oppCustomerId = oppCustomerId;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}