package gnnt.MEBS.timebargain.mgr.model.systemMana;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class FirmMargin extends StandardModel
{

  @ClassDiscription(name="交易商ID", description="")
  private String firmId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="保证金算法", description="")
  private Short marginAlgr;

  @ClassDiscription(name="买交易保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name="卖交易保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="买担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "commodityId", this.commodityId);
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId) {
    this.commodityId = commodityId;
  }

  public Short getMarginAlgr() {
    return this.marginAlgr;
  }

  public void setMarginAlgr(Short marginAlgr) {
    this.marginAlgr = marginAlgr;
  }

  public Double getMarginRate_B() {
    return this.marginRate_B;
  }

  public void setMarginRate_B(Double marginRateB) {
    this.marginRate_B = marginRateB;
  }

  public Double getMarginRate_S() {
    return this.marginRate_S;
  }

  public void setMarginRate_S(Double marginRateS) {
    this.marginRate_S = marginRateS;
  }

  public Double getMarginAssure_B() {
    return this.marginAssure_B;
  }

  public void setMarginAssure_B(Double marginAssureB) {
    this.marginAssure_B = marginAssureB;
  }

  public Double getMarginAssure_S() {
    return this.marginAssure_S;
  }

  public void setMarginAssure_S(Double marginAssureS) {
    this.marginAssure_S = marginAssureS;
  }

  public String getFirmId() {
    return this.firmId;
  }

  public void setFirmId(String firmId) {
    this.firmId = firmId;
  }
}