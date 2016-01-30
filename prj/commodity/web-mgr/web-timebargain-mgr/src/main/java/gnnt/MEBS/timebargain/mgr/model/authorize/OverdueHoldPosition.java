package gnnt.MEBS.timebargain.mgr.model.authorize;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class OverdueHoldPosition extends StandardModel
{
  private static final long serialVersionUID = 6333313007070490141L;
  private String firmID;
  private String customerID;
  private String commodityID;
  private Long BSFlag;
  private Long holdQty;
  private Long openQty;
  private Long gageQty;
  private Long maycloseQty;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public String getCustomerID() {
    return this.customerID;
  }

  public void setCustomerID(String customerID) {
    this.customerID = customerID;
  }

  public String getCommodityID() {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID) {
    this.commodityID = commodityID;
  }

  public Long getBSFlag() {
    return this.BSFlag;
  }

  public void setBSFlag(Long bSFlag) {
    this.BSFlag = bSFlag;
  }

  public Long getHoldQty() {
    return this.holdQty;
  }

  public void setHoldQty(Long holdQty) {
    this.holdQty = holdQty;
  }

  public Long getOpenQty() {
    return this.openQty;
  }

  public void setOpenQty(Long openQty) {
    this.openQty = openQty;
  }

  public Long getGageQty() {
    return this.gageQty;
  }

  public void setGageQty(Long gageQty) {
    this.gageQty = gageQty;
  }

  public Long getMaycloseQty() {
    return this.maycloseQty;
  }

  public void setMaycloseQty(Long maycloseQty) {
    this.maycloseQty = maycloseQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}