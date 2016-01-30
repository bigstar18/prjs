package gnnt.MEBS.broker.mgr.model.configparam;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class RewardParam extends StandardModel
{
  private String autoPay;
  private Short payPeriod;
  private Integer payPeriodDate;

  public String getAutoPay()
  {
    return this.autoPay;
  }

  public void setAutoPay(String paramString)
  {
    this.autoPay = paramString;
  }

  public Short getPayPeriod()
  {
    return this.payPeriod;
  }

  public void setPayPeriod(Short paramShort)
  {
    this.payPeriod = paramShort;
  }

  public Integer getPayPeriodDate()
  {
    return this.payPeriodDate;
  }

  public void setPayPeriodDate(Integer paramInteger)
  {
    this.payPeriodDate = paramInteger;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}