package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmSettleMargin extends StandardModel
{
  private static final long serialVersionUID = 1337406235385335337L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="商品ID", description="")
  private String commodityID;

  @ClassDiscription(name="买交收保证金算法", description="")
  private Integer settleMarginAlgr_B;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Double settleMarginRate_B;

  @ClassDiscription(name="卖交收保证金算法", description="")
  private Integer settleMarginAlgr_S;

  @ClassDiscription(name="交收保证金卖系数", description="")
  private Double settleMarginRate_S;

  @ClassDiscription(name="交收货款算法", description="")
  private Integer payoutAlgr;

  @ClassDiscription(name="交收货款系数", description="")
  private Double payoutRate;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Integer getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }

  public void setSettleMarginAlgr_B(Integer settleMarginAlgrB)
  {
    this.settleMarginAlgr_B = settleMarginAlgrB;
  }

  public Double getSettleMarginRate_B()
  {
    return this.settleMarginRate_B;
  }

  public void setSettleMarginRate_B(Double settleMarginRateB)
  {
    this.settleMarginRate_B = settleMarginRateB;
  }

  public Integer getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }

  public void setSettleMarginAlgr_S(Integer settleMarginAlgrS)
  {
    this.settleMarginAlgr_S = settleMarginAlgrS;
  }

  public Double getSettleMarginRate_S()
  {
    return this.settleMarginRate_S;
  }

  public void setSettleMarginRate_S(Double settleMarginRateS)
  {
    this.settleMarginRate_S = settleMarginRateS;
  }

  public Integer getPayoutAlgr()
  {
    return this.payoutAlgr;
  }

  public void setPayoutAlgr(Integer payoutAlgr)
  {
    this.payoutAlgr = payoutAlgr;
  }

  public Double getPayoutRate()
  {
    return this.payoutRate;
  }

  public void setPayoutRate(Double payoutRate)
  {
    this.payoutRate = payoutRate;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}