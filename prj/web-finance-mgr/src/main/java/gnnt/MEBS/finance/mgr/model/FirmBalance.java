package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FirmBalance extends StandardModel
{
  private static final long serialVersionUID = 487654776733350247L;

  @ClassDiscription(name="结算日期", description="")
  private Date b_Date;

  @ClassDiscription(name="交易商", description="")
  private String firmId;

  @ClassDiscription(name="上日余额", description="")
  private Double lastBalance;

  @ClassDiscription(name="当日余额", description="")
  private Double todayBalance;

  @ClassDiscription(name="上日担保金", description="")
  private Double lastWarranty;

  @ClassDiscription(name="本日担保金", description="")
  private Double todayWarranty;

  @ClassDiscription(name="上日交收保证金", description="")
  private Double lastSettleMargin;

  @ClassDiscription(name="本日交收保证金", description="")
  private Double settleMargin;

  @ClassDiscription(name="总账", description="")
  private Set<ClientLedger> clientLedger = new HashSet();

  public Set<ClientLedger> getClientLedger()
  {
    return this.clientLedger;
  }

  public void setClientLedger(Set<ClientLedger> paramSet)
  {
    this.clientLedger = paramSet;
  }

  public Date getB_Date()
  {
    return this.b_Date;
  }

  public void setB_Date(Date paramDate)
  {
    this.b_Date = paramDate;
  }

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public Double getLastBalance()
  {
    return this.lastBalance;
  }

  public void setLastBalance(Double paramDouble)
  {
    this.lastBalance = paramDouble;
  }

  public Double getTodayBalance()
  {
    return this.todayBalance;
  }

  public void setTodayBalance(Double paramDouble)
  {
    this.todayBalance = paramDouble;
  }

  public Double getLastWarranty()
  {
    return this.lastWarranty;
  }

  public void setLastWarranty(Double paramDouble)
  {
    this.lastWarranty = paramDouble;
  }

  public Double getTodayWarranty()
  {
    return this.todayWarranty;
  }

  public void setTodayWarranty(Double paramDouble)
  {
    this.todayWarranty = paramDouble;
  }

  public Double getLastSettleMargin()
  {
    return this.lastSettleMargin;
  }

  public void setLastSettleMargin(Double paramDouble)
  {
    this.lastSettleMargin = paramDouble;
  }

  public Double getSettleMargin()
  {
    return this.settleMargin;
  }

  public void setSettleMargin(Double paramDouble)
  {
    this.settleMargin = paramDouble;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}