package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class CustomerFundsModel extends StandardModel
{
  private static final long serialVersionUID = 7024424999128593514L;
  private String firmId;
  private String firmName;
  private Double nowLeaveBalance;
  private Double runTimeFl;
  private Double runTimeMargin;
  private Double runTimeAssure;
  private Double lastBalance;
  private Double clearFl;
  private Double clearMargin;
  private Double clearAssure;
  private Double tradeFee;
  private Double maxOverdraft;
  private Double close;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }

  public String getFirmName()
  {
    return this.firmName;
  }

  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }

  public Double getNowLeaveBalance()
  {
    return this.nowLeaveBalance;
  }

  public void setNowLeaveBalance(Double nowLeaveBalance)
  {
    this.nowLeaveBalance = nowLeaveBalance;
  }

  public Double getRunTimeFl()
  {
    return this.runTimeFl;
  }

  public void setRunTimeFl(Double runTimeFl)
  {
    this.runTimeFl = runTimeFl;
  }

  public Double getRunTimeMargin()
  {
    return this.runTimeMargin;
  }

  public void setRunTimeMargin(Double runTimeMargin)
  {
    this.runTimeMargin = runTimeMargin;
  }

  public Double getRunTimeAssure()
  {
    return this.runTimeAssure;
  }

  public void setRunTimeAssure(Double runTimeAssure)
  {
    this.runTimeAssure = runTimeAssure;
  }

  public Double getLastBalance()
  {
    return this.lastBalance;
  }

  public void setLastBalance(Double lastBalance)
  {
    this.lastBalance = lastBalance;
  }

  public Double getClearFl()
  {
    return this.clearFl;
  }

  public void setClearFl(Double clearFl)
  {
    this.clearFl = clearFl;
  }

  public Double getClearMargin()
  {
    return this.clearMargin;
  }

  public void setClearMargin(Double clearMargin)
  {
    this.clearMargin = clearMargin;
  }

  public Double getClearAssure()
  {
    return this.clearAssure;
  }

  public void setClearAssure(Double clearAssure)
  {
    this.clearAssure = clearAssure;
  }

  public Double getTradeFee()
  {
    return this.tradeFee;
  }

  public void setTradeFee(Double tradeFee)
  {
    this.tradeFee = tradeFee;
  }

  public Double getMaxOverdraft()
  {
    return this.maxOverdraft;
  }

  public void setMaxOverdraft(Double maxOverdraft)
  {
    this.maxOverdraft = maxOverdraft;
  }

  public Double getClose()
  {
    return this.close;
  }

  public void setClose(Double close)
  {
    this.close = close;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}