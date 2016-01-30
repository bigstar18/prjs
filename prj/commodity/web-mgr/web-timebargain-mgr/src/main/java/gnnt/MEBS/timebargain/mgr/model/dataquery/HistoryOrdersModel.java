package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Timestamp;
import java.util.Date;

public class HistoryOrdersModel extends StandardModel
{
  private static final long serialVersionUID = 2743936666449722454L;

  @ClassDiscription(name="结算日期", description="")
  private Date clearDate;

  @ClassDiscription(name="主键", description="")
  private Long orderNo;

  @ClassDiscription(name="委托单号", description="")
  private Long orderNo1;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerId;

  @ClassDiscription(name="交易员ID", description="")
  private String traderId;

  @ClassDiscription(name="买卖标志", description="1:买 buy，2:卖 sell")
  private Long flag;

  @ClassDiscription(name="委托类型", description="")
  private Long orderType;

  @ClassDiscription(name="状态", description="")
  private Long status1;

  @ClassDiscription(name="撤单类型", description="")
  private Long withdrawType;

  @ClassDiscription(name="委托失败代码", description="")
  private Long failCode;

  @ClassDiscription(name="数量", description="委托数量")
  private Long quantity;

  @ClassDiscription(name="委托价格", description="委托价格")
  private Double price;

  @ClassDiscription(name="平仓方式", description="指定时间，指定价位")
  private Long closeMode;

  @ClassDiscription(name="价格", description="指定要平仓的持仓价格")
  private Double specPrice;

  @ClassDiscription(name="指定时间标志", description="1 本日仓 2 历史仓")
  private Integer timeFlag;

  @ClassDiscription(name="已成交数量", description="部分成交的数量")
  private Long tradeQty;

  @ClassDiscription(name="冻结资金", description="对应金额的持仓保证金和交易费用")
  private Double frozenFunds;

  @ClassDiscription(name="解冻资金数量", description="")
  private Double unfrozenFunds;

  @ClassDiscription(name="委托时间", description="委托提交的时间")
  private Date orderTime;

  @ClassDiscription(name="撤单时间", description="")
  private Date withdrawTime;

  @ClassDiscription(name="委托者IP", description="")
  private String ordererIp;

  @ClassDiscription(name="防抵赖码", description="")
  private String signature;

  @ClassDiscription(name="平仓标志", description="")
  private Long closeFlag;

  @ClassDiscription(name="易商ID", description="")
  private String firmId;

  @ClassDiscription(name="委托员ID", description="")
  private String consignerId;

  @ClassDiscription(name="撤单员ID", description="")
  private String withdrawerId;

  @ClassDiscription(name="更新时间", description="")
  private Timestamp updateTime;

  @ClassDiscription(name="仓单交易类型", description="0：正常（默认），1：卖仓单，2：抵顶转让")
  private Long billTradeType;

  @ClassDiscription(name="撮合类型", description="")
  private Long specialorderflag;
  private MFirmModel mFirmModel;

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public Long getOrderNo()
  {
    return this.orderNo;
  }

  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }

  public Long getOrderNo1()
  {
    return this.orderNo1;
  }

  public void setOrderNo1(Long orderNo1)
  {
    this.orderNo1 = orderNo1;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public String getTraderId()
  {
    return this.traderId;
  }

  public void setTraderId(String traderId)
  {
    this.traderId = traderId;
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

  public Long getStatus1()
  {
    return this.status1;
  }

  public void setStatus1(Long status1)
  {
    this.status1 = status1;
  }

  public Long getWithdrawType()
  {
    return this.withdrawType;
  }

  public void setWithdrawType(Long withdrawType)
  {
    this.withdrawType = withdrawType;
  }

  public Long getFailCode()
  {
    return this.failCode;
  }

  public void setFailCode(Long failCode)
  {
    this.failCode = failCode;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Long quantity)
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

  public Long getCloseMode()
  {
    return this.closeMode;
  }

  public void setCloseMode(Long closeMode)
  {
    this.closeMode = closeMode;
  }

  public Double getSpecPrice()
  {
    return this.specPrice;
  }

  public void setSpecPrice(Double specPrice)
  {
    this.specPrice = specPrice;
  }

  public Integer getTimeFlag()
  {
    return this.timeFlag;
  }

  public void setTimeFlag(Integer timeFlag)
  {
    this.timeFlag = timeFlag;
  }

  public Long getTradeQty()
  {
    return this.tradeQty;
  }

  public void setTradeQty(Long tradeQty)
  {
    this.tradeQty = tradeQty;
  }

  public Double getFrozenFunds()
  {
    return this.frozenFunds;
  }

  public void setFrozenFunds(Double frozenFunds)
  {
    this.frozenFunds = frozenFunds;
  }

  public Double getUnfrozenFunds()
  {
    return this.unfrozenFunds;
  }

  public void setUnfrozenFunds(Double unfrozenFunds)
  {
    this.unfrozenFunds = unfrozenFunds;
  }

  public Date getOrderTime()
  {
    return this.orderTime;
  }

  public void setOrderTime(Date orderTime)
  {
    this.orderTime = orderTime;
  }

  public Date getWithdrawTime()
  {
    return this.withdrawTime;
  }

  public void setWithdrawTime(Date withdrawTime)
  {
    this.withdrawTime = withdrawTime;
  }

  public String getOrdererIp()
  {
    return this.ordererIp;
  }

  public void setOrdererIp(String ordererIp)
  {
    this.ordererIp = ordererIp;
  }

  public String getSignature()
  {
    return this.signature;
  }

  public void setSignature(String signature)
  {
    this.signature = signature;
  }

  public Long getCloseFlag()
  {
    return this.closeFlag;
  }

  public void setCloseFlag(Long closeFlag)
  {
    this.closeFlag = closeFlag;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getConsignerId()
  {
    return this.consignerId;
  }

  public void setConsignerId(String consignerId)
  {
    this.consignerId = consignerId;
  }

  public String getWithdrawerId()
  {
    return this.withdrawerId;
  }

  public void setWithdrawerId(String withdrawerId)
  {
    this.withdrawerId = withdrawerId;
  }

  public Timestamp getUpdateTime()
  {
    return this.updateTime;
  }

  public void setUpdateTime(Timestamp updateTime)
  {
    this.updateTime = updateTime;
  }

  public Long getBillTradeType()
  {
    return this.billTradeType;
  }

  public void setBillTradeType(Long billTradeType)
  {
    this.billTradeType = billTradeType;
  }

  public Long getSpecialorderflag()
  {
    return this.specialorderflag;
  }

  public void setSpecialorderflag(Long specialorderflag)
  {
    this.specialorderflag = specialorderflag;
  }

  public MFirmModel getmFirmModel()
  {
    return this.mFirmModel;
  }

  public void setmFirmModel(MFirmModel mFirmModel)
  {
    this.mFirmModel = mFirmModel;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}