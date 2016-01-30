package gnnt.MEBS.timebargain.mgr.model.applyGage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CustomerHoldSumA extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="交易客户代码", description="")
  private String customerId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="买卖标志", description="")
  private Integer bs_Flag;

  @ClassDiscription(name="抵顶数量", description="")
  private Long gageQty;

  @ClassDiscription(name="交易商代码", description="")
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

  public Long getGageQty()
  {
    return this.gageQty;
  }

  public void setGageQty(Long gageQty) {
    this.gageQty = gageQty;
  }

  public String getFirmId()
  {
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