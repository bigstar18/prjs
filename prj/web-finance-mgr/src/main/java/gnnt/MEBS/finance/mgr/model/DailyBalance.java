package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class DailyBalance extends StandardModel
{
  private static final long serialVersionUID = 321817798263451147L;

  @ClassDiscription(name="结算日期", description="")
  private Date bdate;

  @ClassDiscription(name="科目代码", description="")
  private String code;

  @ClassDiscription(name="上日余额", description="")
  private Double lastDayBalance;

  @ClassDiscription(name="借方发生额", description="")
  private Double debitAmount;

  @ClassDiscription(name=" 货方发生额", description="")
  private Double creditAmount;

  @ClassDiscription(name="本日余额", description="")
  private Double todayBalance;

  @ClassDiscription(name="引用科目实体对象", description="")
  private Account account;

  public Date getBdate()
  {
    return this.bdate;
  }

  public void setBdate(Date paramDate)
  {
    this.bdate = paramDate;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public Double getLastDayBalance()
  {
    return this.lastDayBalance;
  }

  public void setLastDayBalance(Double paramDouble)
  {
    this.lastDayBalance = paramDouble;
  }

  public Double getDebitAmount()
  {
    return this.debitAmount;
  }

  public void setDebitAmount(Double paramDouble)
  {
    this.debitAmount = paramDouble;
  }

  public Double getCreditAmount()
  {
    return this.creditAmount;
  }

  public void setCreditAmount(Double paramDouble)
  {
    this.creditAmount = paramDouble;
  }

  public Double getTodayBalance()
  {
    return this.todayBalance;
  }

  public void setTodayBalance(Double paramDouble)
  {
    this.todayBalance = paramDouble;
  }

  public Account getAccount()
  {
    return this.account;
  }

  public void setAccount(Account paramAccount)
  {
    this.account = paramAccount;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}