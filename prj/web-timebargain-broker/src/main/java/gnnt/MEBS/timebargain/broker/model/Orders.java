package gnnt.MEBS.timebargain.broker.model;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class Orders extends StandardModel
{
  private static final long serialVersionUID = 3690197650654049816L;
  private Long A_OrderNo;
  private Long M_OrderNo;
  private Long A_OrderNo_W;
  private String Uni_Cmdty_Code;
  private String customerId;
  private String traderId;
  private Short BS_Flag;
  private Short orderType;
  private Short status;
  private Short failCode;
  private Long quantity;
  private Double price;
  private Short closeMode;
  private Double specPrice;
  private Short timeFlag;
  private Long tradeQty;
  private Double frozenFunds;
  private Double unfrozenFunds;
  private Date orderTime;
  private Date withdrawTime;
  private String ordererIp;
  private String signature;
  private Short closeFlag;
  private String marketCode;
  private String commodityId;
  private String submitStyle;
  private String firmId;
  private String type;
  private String forceCloseType;

  public Long getA_OrderNo()
  {
    return this.A_OrderNo;
  }

  public Long getM_OrderNo()
  {
    return this.M_OrderNo;
  }

  public Long getA_OrderNo_W()
  {
    return this.A_OrderNo_W;
  }

  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public String getTraderId()
  {
    return this.traderId;
  }

  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }

  public Short getOrderType()
  {
    return this.orderType;
  }

  public Short getStatus()
  {
    return this.status;
  }

  public Short getFailCode()
  {
    return this.failCode;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public Short getCloseMode()
  {
    return this.closeMode;
  }

  public Double getSpecPrice()
  {
    return this.specPrice;
  }

  public Short getTimeFlag()
  {
    return this.timeFlag;
  }

  public Long getTradeQty()
  {
    return this.tradeQty;
  }

  public Double getFrozenFunds()
  {
    return this.frozenFunds;
  }

  public Double getUnfrozenFunds()
  {
    return this.unfrozenFunds;
  }

  public Date getOrderTime()
  {
    return this.orderTime;
  }

  public Date getWithdrawTime()
  {
    return this.withdrawTime;
  }

  public String getOrdererIp()
  {
    return this.ordererIp;
  }

  public String getSignature()
  {
    return this.signature;
  }

  public Short getCloseFlag()
  {
    return this.closeFlag;
  }

  public String getMarketCode()
  {
    return this.marketCode;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public String getSubmitStyle()
  {
    return this.submitStyle;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public String getType()
  {
    return this.type;
  }

  public String getForceCloseType()
  {
    return this.forceCloseType;
  }

  public void setA_OrderNo(Long paramLong)
  {
    this.A_OrderNo = paramLong;
  }

  public void setM_OrderNo(Long paramLong)
  {
    this.M_OrderNo = paramLong;
  }

  public void setA_OrderNo_W(Long paramLong)
  {
    this.A_OrderNo_W = paramLong;
  }

  public void setUni_Cmdty_Code(String paramString)
  {
    this.Uni_Cmdty_Code = paramString;
  }

  public void setCustomerId(String paramString)
  {
    this.customerId = paramString;
  }

  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }

  public void setBS_Flag(Short paramShort)
  {
    this.BS_Flag = paramShort;
  }

  public void setOrderType(Short paramShort)
  {
    this.orderType = paramShort;
  }

  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }

  public void setFailCode(Short paramShort)
  {
    this.failCode = paramShort;
  }

  public void setQuantity(Long paramLong)
  {
    this.quantity = paramLong;
  }

  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }

  public void setCloseMode(Short paramShort)
  {
    this.closeMode = paramShort;
  }

  public void setSpecPrice(Double paramDouble)
  {
    this.specPrice = paramDouble;
  }

  public void setTimeFlag(Short paramShort)
  {
    this.timeFlag = paramShort;
  }

  public void setTradeQty(Long paramLong)
  {
    this.tradeQty = paramLong;
  }

  public void setFrozenFunds(Double paramDouble)
  {
    this.frozenFunds = paramDouble;
  }

  public void setUnfrozenFunds(Double paramDouble)
  {
    this.unfrozenFunds = paramDouble;
  }

  public void setOrderTime(Date paramDate)
  {
    this.orderTime = paramDate;
  }

  public void setWithdrawTime(Date paramDate)
  {
    this.withdrawTime = paramDate;
  }

  public void setOrdererIp(String paramString)
  {
    this.ordererIp = paramString;
  }

  public void setSignature(String paramString)
  {
    this.signature = paramString;
  }

  public void setCloseFlag(Short paramShort)
  {
    this.closeFlag = paramShort;
  }

  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }

  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }

  public void setSubmitStyle(String paramString)
  {
    this.submitStyle = paramString;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setForceCloseType(String paramString)
  {
    this.forceCloseType = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("A_OrderNo", this.A_OrderNo);
  }
}