package gnnt.MEBS.timebargain.mgr.model.systemMana;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class CommodityModel extends StandardModel
{

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="商品名称", description="")
  private String name;

  @ClassDiscription(name="商品分类ID", description="")
  private Long sortID;

  @ClassDiscription(name="状态", description="")
  private Short status;

  @ClassDiscription(name="手续费算法", description="")
  private Short feeAlgr;

  @ClassDiscription(name="手续费系数", description="")
  private Double feeRate_B;

  @ClassDiscription(name="手续费系数", description="")
  private Double feeRate_S;

  @ClassDiscription(name="保证金算法", description="")
  private Short marginAlgr;

  @ClassDiscription(name="交易保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name="交易保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="买担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  @ClassDiscription(name="结算价", description="")
  private Double lastPrice;

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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getSortID() {
    return this.sortID;
  }

  public void setSortID(Long sortID) {
    this.sortID = sortID;
  }

  public Short getStatus() {
    return this.status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Short getFeeAlgr() {
    return this.feeAlgr;
  }

  public void setFeeAlgr(Short feeAlgr) {
    this.feeAlgr = feeAlgr;
  }

  public Double getFeeRate_B() {
    return this.feeRate_B;
  }

  public void setFeeRate_B(Double feeRateB) {
    this.feeRate_B = feeRateB;
  }

  public Double getFeeRate_S() {
    return this.feeRate_S;
  }

  public void setFeeRate_S(Double feeRateS) {
    this.feeRate_S = feeRateS;
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

  public Double getLastPrice() {
    return this.lastPrice;
  }

  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }
}