package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Date;

public class HistoryCustomerHoldSumModel extends StandardModel
{
  private static final long serialVersionUID = 6249271738505172932L;

  @ClassDiscription(name="结算日期", description="")
  private Date clearDate;

  @ClassDiscription(name="交易客户ID", description="")
  private String customerID;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志", description="1:买 buy，2:卖 sell")
  private Long flag;

  @ClassDiscription(name="持仓数量", description="")
  private Long holdQty;

  @ClassDiscription(name="持仓金额", description="")
  private Double holdFunds;

  @ClassDiscription(name="浮动盈亏", description="")
  private Double floatingLoss;

  @ClassDiscription(name="持仓均价", description="")
  private Double evenPrice;

  @ClassDiscription(name="冻结数量", description="")
  private Long frozenQty;

  @ClassDiscription(name="实时保证金", description="")
  private Double holdMargin;

  @ClassDiscription(name="抵顶数量", description="")
  private Long gageQty;

  @ClassDiscription(name="持仓担保金", description="")
  private Double holdAssure;

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="抵顶冻结数量", description="")
  private Long gageFrozenQty;
  private Long holdQtyGageQty;
  private MFirmModel mFirmModel;

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public String getCustomerID()
  {
    return this.customerID;
  }

  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
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

  public Long getHoldQty()
  {
    return this.holdQty;
  }

  public void setHoldQty(Long holdQty)
  {
    this.holdQty = holdQty;
  }

  public Double getHoldFunds()
  {
    return this.holdFunds;
  }

  public void setHoldFunds(Double holdFunds)
  {
    this.holdFunds = holdFunds;
  }

  public Double getFloatingLoss()
  {
    return this.floatingLoss;
  }

  public void setFloatingLoss(Double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }

  public Double getEvenPrice()
  {
    return this.evenPrice;
  }

  public void setEvenPrice(Double evenPrice)
  {
    this.evenPrice = evenPrice;
  }

  public Long getFrozenQty()
  {
    return this.frozenQty;
  }

  public void setFrozenQty(Long frozenQty)
  {
    this.frozenQty = frozenQty;
  }

  public Double getHoldMargin()
  {
    return this.holdMargin;
  }

  public void setHoldMargin(Double holdMargin)
  {
    this.holdMargin = holdMargin;
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

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public Long getGageFrozenQty()
  {
    return this.gageFrozenQty;
  }

  public void setGageFrozenQty(Long gageFrozenQty)
  {
    this.gageFrozenQty = gageFrozenQty;
  }

  public MFirmModel getmFirmModel()
  {
    return this.mFirmModel;
  }

  public void setmFirmModel(MFirmModel mFirmModel)
  {
    this.mFirmModel = mFirmModel;
  }

  public Long getHoldQtyGageQty()
  {
    return Long.valueOf(this.holdQty.longValue() + this.gageQty.longValue());
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}