package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class HoldPositionModel extends StandardModel
{
  private static final long serialVersionUID = 25253229016247425L;

  @ClassDiscription(name="持仓单号", description="自增，唯一标志")
  private Long holdNo;

  @ClassDiscription(name="成交号", description="委托单号")
  private Long tradeNo;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志", description="")
  private Long flag;

  @ClassDiscription(name="价格", description="")
  private Double price;

  @ClassDiscription(name="持仓数量  ", description="")
  private Long holdQty;

  @ClassDiscription(name="开仓数量", description="")
  private Long openQty;

  @ClassDiscription(name="持仓时间", description="持仓时间（精确到秒）")
  private Date holdTime;

  @ClassDiscription(name="实时保证金", description="")
  private Double holdMargin;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="抵顶数量", description="")
  private Long gageQty;

  @ClassDiscription(name="持仓担保金", description="")
  private Double holdAssure;

  @ClassDiscription(name="浮动盈亏\t", description="浮动亏损资金")
  private Double floatingLoss;

  @ClassDiscription(name="持仓类型", description="")
  private Long holdType;

  @ClassDiscription(name="订货所属结算日期", description="")
  private Date atClearDate;

  @ClassDiscription(name="到期日期", description="")
  private Date deadLine;

  @ClassDiscription(name="到期天数", description="")
  private Long remainDay;
  private MFirmModel mFirmModel;

  public Long getHoldNo()
  {
    return this.holdNo;
  }

  public void setHoldNo(Long holdNo)
  {
    this.holdNo = holdNo;
  }

  public Long getTradeNo()
  {
    return this.tradeNo;
  }

  public void setTradeNo(Long tradeNo)
  {
    this.tradeNo = tradeNo;
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

  public Double getPrice()
  {
    return this.price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public Long getHoldQty()
  {
    return this.holdQty;
  }

  public void setHoldQty(Long holdQty)
  {
    this.holdQty = holdQty;
  }

  public Long getOpenQty()
  {
    return this.openQty;
  }

  public void setOpenQty(Long openQty)
  {
    this.openQty = openQty;
  }

  public Date getHoldTime()
  {
    return this.holdTime;
  }

  public void setHoldTime(Date holdTime)
  {
    this.holdTime = holdTime;
  }

  public Double getHoldMargin()
  {
    return this.holdMargin;
  }

  public void setHoldMargin(Double holdMargin)
  {
    this.holdMargin = holdMargin;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public Long getGageQty()
  {
    return this.gageQty;
  }

  public void setGageQty(Long gageQty)
  {
    this.gageQty = gageQty;
  }

  public Double getHoldAssure()
  {
    return this.holdAssure;
  }

  public void setHoldAssure(Double holdAssure)
  {
    this.holdAssure = holdAssure;
  }

  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }

  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }

  public Long getHoldType()
  {
    return this.holdType;
  }

  public void setHoldType(Long holdType)
  {
    this.holdType = holdType;
  }

  public Date getAtClearDate()
  {
    return this.atClearDate;
  }

  public void setAtClearDate(Date atClearDate)
  {
    this.atClearDate = atClearDate;
  }

  public Date getDeadLine()
  {
    return this.deadLine;
  }

  public void setDeadLine(Date deadLine)
  {
    this.deadLine = deadLine;
  }

  public Long getRemainDay()
  {
    return this.remainDay;
  }

  public void setRemainDay(Long remainDay)
  {
    this.remainDay = remainDay;
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