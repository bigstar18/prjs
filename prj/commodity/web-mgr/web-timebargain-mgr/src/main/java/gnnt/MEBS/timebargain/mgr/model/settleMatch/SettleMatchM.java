package gnnt.MEBS.timebargain.mgr.model.settleMatch;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class SettleMatchM extends StandardModel
{

  @ClassDiscription(name="配对编号", description="")
  private String matchId;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="交收数量", description="")
  private Long quantity;

  @ClassDiscription(name="状态", description="0 未处理 1 处理中 2 处理完成 3 作废")
  private Integer status;

  @ClassDiscription(name="履约状态", description="1 履约 2 买方违约 3 卖方违约 4 双方违约")
  private Integer result;

  @ClassDiscription(name="交收类型", description="1：到期交收 2：提前交收 3：延期交收")
  private Integer settleType;

  @ClassDiscription(name="买方交易商ID", description="")
  private String firmID_B;

  @ClassDiscription(name="卖方交易商ID", description="")
  private String firmID_S;

  @ClassDiscription(name="", description="")
  private Date settleDate;

  @ClassDiscription(name="", description="")
  private Date createTime;

  public Date getSettleDate()
  {
    return this.settleDate;
  }

  public void setSettleDate(Date settleDate)
  {
    this.settleDate = settleDate;
  }

  public String getMatchId()
  {
    return this.matchId;
  }

  public void setMatchId(String matchId)
  {
    this.matchId = matchId;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public Long getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getResult()
  {
    return this.result;
  }

  public void setResult(Integer result)
  {
    this.result = result;
  }

  public Integer getSettleType()
  {
    return this.settleType;
  }

  public void setSettleType(Integer settleType)
  {
    this.settleType = settleType;
  }

  public String getFirmID_B()
  {
    return this.firmID_B;
  }

  public void setFirmID_B(String firmIDB)
  {
    this.firmID_B = firmIDB;
  }

  public String getFirmID_S()
  {
    return this.firmID_S;
  }

  public void setFirmID_S(String firmIDS)
  {
    this.firmID_S = firmIDS;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "matchId", this.matchId);
  }
}