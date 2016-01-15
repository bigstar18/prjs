package gnnt.MEBS.integrated.broker.model.fundsQuery;

import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.StandardModel.PrimaryInfo;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FirmBalance extends StandardModel
{
  private static final long serialVersionUID = 487654776733350247L;
  private Date b_Date;
  private String firmId;
  private Double lastBalance;
  private Double todayBalance;
  private Double lastWarranty;
  private Double todayWarranty;
  private Double lastSettleMargin;
  private Double settleMargin;
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