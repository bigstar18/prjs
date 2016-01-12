package gnnt.MEBS.timebargain.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Orders extends StandardModel
{
  private static final long serialVersionUID = 3690197650654049816L;

  @ClassDiscription(name="代理系统委托单号", description="")
  private Long A_OrderNo;

  @ClassDiscription(name="交易市场委托单号", description="")
  private Long M_OrderNo;

  @ClassDiscription(name="被撤委托单号", description="")
  private Long A_OrderNo_W;

  @ClassDiscription(name="商品统一代码", description="")
  private String Uni_Cmdty_Code;

  @ClassDiscription(name="交易用户ID", description="")
  private String customerId;

  @ClassDiscription(name="交易员ID", description="")
  private String traderId;

  @ClassDiscription(name="买卖标志", description="")
  private Short BS_Flag;

  @ClassDiscription(name="委托类型", description="")
  private Short orderType;

  @ClassDiscription(name="状态 ", description="")
  private Short status;

  @ClassDiscription(name="委托失败代码", description="")
  private Short failCode;

  @ClassDiscription(name="委托数量", description="")
  private Long quantity;

  @ClassDiscription(name="委托价格", description="")
  private Double price;

  @ClassDiscription(name="平仓方式", description="")
  private Short closeMode;

  @ClassDiscription(name="指定价格", description="")
  private Double specPrice;

  @ClassDiscription(name="指定时间标志", description="")
  private Short timeFlag;

  @ClassDiscription(name="已成交数量", description="")
  private Long tradeQty;

  @ClassDiscription(name="冻结资金", description="")
  private Double frozenFunds;

  @ClassDiscription(name=" 解冻资金数量  ", description="")
  private Double unfrozenFunds;

  @ClassDiscription(name="委托时间", description="")
  private Date orderTime;

  @ClassDiscription(name="撤单时间  ", description="")
  private Date withdrawTime;

  @ClassDiscription(name="委托者IP", description="")
  private String ordererIp;

  @ClassDiscription(name="防抵赖码", description="")
  private String signature;

  @ClassDiscription(name="平仓标志", description="1:交易商委托交易员平仓，2:强平")
  private Short closeFlag;

  @ClassDiscription(name="市场代码", description="")
  private String marketCode;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="提交方式", description="1：立即提交 ；2：入预备单")
  private String submitStyle;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="委托类型", description="")
  private String type;

  @ClassDiscription(name="强平类型", description="")
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

  public void setA_OrderNo(Long aOrderNo)
  {
    this.A_OrderNo = aOrderNo;
  }

  public void setM_OrderNo(Long mOrderNo)
  {
    this.M_OrderNo = mOrderNo;
  }

  public void setA_OrderNo_W(Long aOrderNoW)
  {
    this.A_OrderNo_W = aOrderNoW;
  }

  public void setUni_Cmdty_Code(String uniCmdtyCode)
  {
    this.Uni_Cmdty_Code = uniCmdtyCode;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
  }

  public void setBS_Flag(Short bSFlag)
  {
    this.BS_Flag = bSFlag;
  }

  public void setOrderType(Short orderType)
  {
    this.orderType = orderType;
  }

  public void setStatus(Short status)
  {
    this.status = status;
  }

  public void setFailCode(Short failCode)
  {
    this.failCode = failCode;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public void setCloseMode(Short closeMode)
  {
    this.closeMode = closeMode;
  }

  public void setSpecPrice(Double specPrice)
  {
    this.specPrice = specPrice;
  }

  public void setTimeFlag(Short timeFlag)
  {
    this.timeFlag = timeFlag;
  }

  public void setTradeQty(Long tradeQty)
  {
    this.tradeQty = tradeQty;
  }

  public void setFrozenFunds(Double frozenFunds)
  {
    this.frozenFunds = frozenFunds;
  }

  public void setUnfrozenFunds(Double unfrozenFunds)
  {
    this.unfrozenFunds = unfrozenFunds;
  }

  public void setOrderTime(Date orderTime)
  {
    this.orderTime = orderTime;
  }

  public void setWithdrawTime(Date withdrawTime)
  {
    this.withdrawTime = withdrawTime;
  }

  public void setOrdererIp(String ordererIp)
  {
    this.ordererIp = ordererIp;
  }

  public void setSignature(String signature)
  {
    this.signature = signature;
  }

  public void setCloseFlag(Short closeFlag)
  {
    this.closeFlag = closeFlag;
  }

  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public void setSubmitStyle(String submitStyle)
  {
    this.submitStyle = submitStyle;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public void setForceCloseType(String forceCloseType)
  {
    this.forceCloseType = forceCloseType;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "A_OrderNo", this.A_OrderNo);
  }
}