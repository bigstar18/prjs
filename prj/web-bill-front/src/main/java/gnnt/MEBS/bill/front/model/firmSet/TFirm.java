package gnnt.MEBS.bill.front.model.firmSet;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class TFirm
  extends StandardModel
{
  private static final long serialVersionUID = 2371235574819358302L;
  @ClassDiscription(name="交易商ID", description="")
  private String firmID;
  @ClassDiscription(name="状态", description="")
  private Integer status;
  @ClassDiscription(name="最大持仓量", description="")
  private Integer maxHoldQty;
  @ClassDiscription(name="最低结算准备金", description="")
  private Double minClearDeposit;
  @ClassDiscription(name="最大透支额度", description="")
  private Double maxOverdraft;
  @ClassDiscription(name="虚拟资金", description="")
  private Double virtualFunds;
  @ClassDiscription(name="临时浮亏", description="")
  private Double runtimeFL;
  @ClassDiscription(name="结算浮亏", description="")
  private Double clearFL;
  @ClassDiscription(name="临时交易保证金", description="")
  private Double runtimeMargin;
  @ClassDiscription(name="结算交易保证金", description="")
  private Double clearMargin;
  @ClassDiscription(name="临时担保金", description="")
  private Double runtimeAssure;
  @ClassDiscription(name="结算担保金", description="")
  private Double clearAssure;
  @ClassDiscription(name="当日交收保证金", description="")
  private Double runtimeSettleMargin;
  @ClassDiscription(name="当日交收保证金", description="")
  private Double clearSettleMargin;
  @ClassDiscription(name="套餐ID", description="")
  private String tariffID;
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
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public Integer getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Integer maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }
  
  public Double getMinClearDeposit()
  {
    return this.minClearDeposit;
  }
  
  public void setMinClearDeposit(Double minClearDeposit)
  {
    this.minClearDeposit = minClearDeposit;
  }
  
  public Double getMaxOverdraft()
  {
    return this.maxOverdraft;
  }
  
  public void setMaxOverdraft(Double maxOverdraft)
  {
    this.maxOverdraft = maxOverdraft;
  }
  
  public Double getVirtualFunds()
  {
    return this.virtualFunds;
  }
  
  public void setVirtualFunds(Double virtualFunds)
  {
    this.virtualFunds = virtualFunds;
  }
  
  public Double getRuntimeFL()
  {
    return this.runtimeFL;
  }
  
  public void setRuntimeFL(Double runtimeFL)
  {
    this.runtimeFL = runtimeFL;
  }
  
  public Double getClearFL()
  {
    return this.clearFL;
  }
  
  public void setClearFL(Double clearFL)
  {
    this.clearFL = clearFL;
  }
  
  public Double getRuntimeMargin()
  {
    return this.runtimeMargin;
  }
  
  public void setRuntimeMargin(Double runtimeMargin)
  {
    this.runtimeMargin = runtimeMargin;
  }
  
  public Double getClearMargin()
  {
    return this.clearMargin;
  }
  
  public void setClearMargin(Double clearMargin)
  {
    this.clearMargin = clearMargin;
  }
  
  public Double getRuntimeAssure()
  {
    return this.runtimeAssure;
  }
  
  public void setRuntimeAssure(Double runtimeAssure)
  {
    this.runtimeAssure = runtimeAssure;
  }
  
  public Double getClearAssure()
  {
    return this.clearAssure;
  }
  
  public void setClearAssure(Double clearAssure)
  {
    this.clearAssure = clearAssure;
  }
  
  public Double getRuntimeSettleMargin()
  {
    return this.runtimeSettleMargin;
  }
  
  public void setRuntimeSettleMargin(Double runtimeSettleMargin)
  {
    this.runtimeSettleMargin = runtimeSettleMargin;
  }
  
  public Double getClearSettleMargin()
  {
    return this.clearSettleMargin;
  }
  
  public void setClearSettleMargin(Double clearSettleMargin)
  {
    this.clearSettleMargin = clearSettleMargin;
  }
  
  public String getTariffID()
  {
    return this.tariffID;
  }
  
  public void setTariffID(String tariffID)
  {
    this.tariffID = tariffID;
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
    return new StandardModel.PrimaryInfo("firmID", this.firmID);
  }
}
