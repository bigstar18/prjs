package gnnt.MEBS.timebargain.mgr.model.deduct;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CustomerHoldSum extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志", description="1:买 buy，2:卖 sell")
  private Integer bs_Flag;

  @ClassDiscription(name="持仓数量", description="")
  private Long holdQty;

  @ClassDiscription(name="持仓金额", description="")
  private Double holdFunds;

  @ClassDiscription(name="浮动盈亏", description="")
  private Double floatingLoss;

  @ClassDiscription(name="持仓均价", description="")
  private Double evenPrice;

  @ClassDiscription(name="持仓均价", description="")
  private Long frozenQty;

  @ClassDiscription(name="持仓均价", description="")
  private Double holdMargin;

  @ClassDiscription(name="持仓均价", description="")
  private Long gageQty;

  @ClassDiscription(name="持仓担保金", description="")
  private Double holdAssure;

  @ClassDiscription(name="持仓担保金", description="")
  private String firmId;

  @ClassDiscription(name="抵顶冻结数量", description="")
  private Long gageFrozenQty;

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCommodityId() {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId) {
    this.commodityId = commodityId;
  }

  public Integer getBs_Flag() {
    return this.bs_Flag;
  }

  public void setBs_Flag(Integer bsFlag) {
    this.bs_Flag = bsFlag;
  }

  public Long getHoldQty() {
    return this.holdQty;
  }

  public void setHoldQty(Long holdQty) {
    this.holdQty = holdQty;
  }

  public Double getHoldFunds() {
    return this.holdFunds;
  }

  public void setHoldFunds(Double holdFunds) {
    this.holdFunds = holdFunds;
  }

  public Double getFloatingLoss() {
    return this.floatingLoss;
  }

  public void setFloatingLoss(Double floatingLoss) {
    this.floatingLoss = floatingLoss;
  }

  public Double getEvenPrice() {
    return this.evenPrice;
  }

  public void setEvenPrice(Double evenPrice) {
    this.evenPrice = evenPrice;
  }

  public Long getFrozenQty() {
    return this.frozenQty;
  }

  public void setFrozenQty(Long frozenQty) {
    this.frozenQty = frozenQty;
  }

  public Double getHoldMargin() {
    return this.holdMargin;
  }

  public void setHoldMargin(Double holdMargin) {
    this.holdMargin = holdMargin;
  }

  public Long getGageQty() {
    return this.gageQty;
  }

  public void setGageQty(Long gageQty) {
    this.gageQty = gageQty;
  }

  public Double getHoldAssure() {
    return this.holdAssure;
  }

  public void setHoldAssure(Double holdAssure) {
    this.holdAssure = holdAssure;
  }

  public String getFirmId() {
    return this.firmId;
  }

  public void setFirmId(String firmId) {
    this.firmId = firmId;
  }

  public Long getGageFrozenQty() {
    return this.gageFrozenQty;
  }

  public void setGageFrozenQty(Long gageFrozenQty) {
    this.gageFrozenQty = gageFrozenQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}