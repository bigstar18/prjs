package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.sql.Date;

public class HistoryFirmHoldSumModel extends StandardModel
{
  private static final long serialVersionUID = -1005013766818277426L;

  @ClassDiscription(name="结算日期", description="")
  private Date clearDate;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志", description="")
  private Long flag;

  @ClassDiscription(name="持仓数量", description="")
  private Long holdQty;

  @ClassDiscription(name="持仓金额", description="")
  private Double holdFunds;

  @ClassDiscription(name="浮动盈亏", description="")
  private Double floatingLoss;

  @ClassDiscription(name="持仓均价", description="")
  private Double evenPrice;

  @ClassDiscription(name="实时保证金", description="")
  private Double holdMargin;

  @ClassDiscription(name="抵顶数量", description="")
  private Long gageQty;

  @ClassDiscription(name="持仓担保金", description="")
  private Double holdAssure;
  private MFirmModel mFirmModel;
  private Long holdQtyGageQty;

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
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