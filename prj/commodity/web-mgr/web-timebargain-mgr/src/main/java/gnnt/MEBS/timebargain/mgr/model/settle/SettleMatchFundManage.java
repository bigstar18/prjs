package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class SettleMatchFundManage extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="操作编号", description="")
  private Integer id;

  @ClassDiscription(name="操作编号", description="")
  private String matchID;

  @ClassDiscription(name="交易商代码", description="")
  private String firmID;

  @ClassDiscription(name="摘要号", description="")
  private String summaryNo;

  @ClassDiscription(name="发生金额", description="")
  private double amount;

  @ClassDiscription(name="操作时间", description="")
  private Date operateDate;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  public SettleMatchFundManage()
  {
  }

  public SettleMatchFundManage(String matchID, String firmID, String summaryNo, double amount, Date operateDate, String commodityId)
  {
    this.matchID = matchID;
    this.firmID = firmID;
    this.summaryNo = summaryNo;
    this.amount = amount;
    this.operateDate = operateDate;
    this.commodityId = commodityId;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getMatchID()
  {
    return this.matchID;
  }

  public void setMatchID(String matchID)
  {
    this.matchID = matchID;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public String getSummaryNo() {
    return this.summaryNo;
  }

  public void setSummaryNo(String summaryNo) {
    this.summaryNo = summaryNo;
  }

  public double getAmount() {
    return this.amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Date getOperateDate() {
    return this.operateDate;
  }

  public void setOperateDate(Date operateDate) {
    this.operateDate = operateDate;
  }

  public String getCommodityId() {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId) {
    this.commodityId = commodityId;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}