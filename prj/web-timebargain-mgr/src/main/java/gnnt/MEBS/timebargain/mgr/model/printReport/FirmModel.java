package gnnt.MEBS.timebargain.mgr.model.printReport;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.math.BigDecimal;
import java.util.Date;

public class FirmModel extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;

  @ClassDiscription(name="交易商编号", description="")
  private String firmId;

  @ClassDiscription(name="交易商状态", description="")
  private Integer status;

  @ClassDiscription(name="最大持仓量", description="")
  private BigDecimal maxHoldQty;

  @ClassDiscription(name="最大持仓量", description="")
  private BigDecimal minClearDeposit;

  @ClassDiscription(name="最大持仓量", description="")
  private BigDecimal maxOverdraft;

  @ClassDiscription(name="最大持仓量", description="")
  private BigDecimal virtualFunds;

  @ClassDiscription(name="最大持仓量", description="")
  private BigDecimal runtimeFL;

  @ClassDiscription(name="结算浮亏", description="")
  private BigDecimal clearFL;

  @ClassDiscription(name="临时交易保证金", description="")
  private BigDecimal runtimeMargin;

  @ClassDiscription(name="结算交易保证金", description="")
  private BigDecimal clearMargin;

  @ClassDiscription(name="结算交易保证金", description="")
  private BigDecimal runtimeAssure;

  @ClassDiscription(name="结算交易保证金", description="")
  private BigDecimal clearAssure;

  @ClassDiscription(name="当日交收保证金", description="")
  private BigDecimal runtimeSettleMargin;

  @ClassDiscription(name="上日交收保证金", description="")
  private BigDecimal clearSettleMargin;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public BigDecimal getMaxHoldQty()
  {
    return this.maxHoldQty;
  }

  public void setMaxHoldQty(BigDecimal maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }

  public BigDecimal getMinClearDeposit()
  {
    return this.minClearDeposit;
  }

  public void setMinClearDeposit(BigDecimal minClearDeposit)
  {
    this.minClearDeposit = minClearDeposit;
  }

  public BigDecimal getMaxOverdraft()
  {
    return this.maxOverdraft;
  }

  public void setMaxOverdraft(BigDecimal maxOverdraft)
  {
    this.maxOverdraft = maxOverdraft;
  }

  public BigDecimal getVirtualFunds()
  {
    return this.virtualFunds;
  }

  public void setVirtualFunds(BigDecimal virtualFunds)
  {
    this.virtualFunds = virtualFunds;
  }

  public BigDecimal getRuntimeFL()
  {
    return this.runtimeFL;
  }

  public void setRuntimeFL(BigDecimal runtimeFL)
  {
    this.runtimeFL = runtimeFL;
  }

  public BigDecimal getClearFL()
  {
    return this.clearFL;
  }

  public void setClearFL(BigDecimal clearFL)
  {
    this.clearFL = clearFL;
  }

  public BigDecimal getRuntimeMargin()
  {
    return this.runtimeMargin;
  }

  public void setRuntimeMargin(BigDecimal runtimeMargin)
  {
    this.runtimeMargin = runtimeMargin;
  }

  public BigDecimal getClearMargin()
  {
    return this.clearMargin;
  }

  public void setClearMargin(BigDecimal clearMargin)
  {
    this.clearMargin = clearMargin;
  }

  public BigDecimal getRuntimeAssure()
  {
    return this.runtimeAssure;
  }

  public void setRuntimeAssure(BigDecimal runtimeAssure)
  {
    this.runtimeAssure = runtimeAssure;
  }

  public BigDecimal getClearAssure()
  {
    return this.clearAssure;
  }

  public void setClearAssure(BigDecimal clearAssure)
  {
    this.clearAssure = clearAssure;
  }

  public BigDecimal getRuntimeSettleMargin()
  {
    return this.runtimeSettleMargin;
  }

  public void setRuntimeSettleMargin(BigDecimal runtimeSettleMargin)
  {
    this.runtimeSettleMargin = runtimeSettleMargin;
  }

  public BigDecimal getClearSettleMargin()
  {
    return this.clearSettleMargin;
  }

  public void setClearSettleMargin(BigDecimal clearSettleMargin)
  {
    this.clearSettleMargin = clearSettleMargin;
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
    return new StandardModel.PrimaryInfo( "firmId", this.firmId);
  }
}