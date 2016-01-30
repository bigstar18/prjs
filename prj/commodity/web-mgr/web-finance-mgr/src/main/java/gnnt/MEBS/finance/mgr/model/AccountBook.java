package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class AccountBook extends StandardModel
{
  private static final long serialVersionUID = 6798401873474641828L;

  @ClassDiscription(name="结算日期", description="")
  private Date bdate;

  @ClassDiscription(name="凭证摘要号", description="")
  private String summaryNo;

  @ClassDiscription(name="凭证摘要", description="")
  private String summary;

  @ClassDiscription(name="凭证号", description="")
  private Integer voucherNo;

  @ClassDiscription(name="合同号", description="")
  private String contractNo;

  @ClassDiscription(name="借方科目", description="")
  private String debitNo;

  @ClassDiscription(name="贷方科目", description="")
  private String creditNo;

  @ClassDiscription(name="发生额", description="")
  private Double amount;

  @ClassDiscription(name="借方科目对象", description="")
  private Account debitAccount;

  @ClassDiscription(name="贷方科目对象", description="")
  private Account creditAccount;

  public Date getBdate()
  {
    return this.bdate;
  }

  public void setBdate(Date paramDate)
  {
    this.bdate = paramDate;
  }

  public String getSummaryNo()
  {
    return this.summaryNo;
  }

  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  public Integer getVoucherNo()
  {
    return this.voucherNo;
  }

  public void setVoucherNo(Integer paramInteger)
  {
    this.voucherNo = paramInteger;
  }

  public String getContractNo()
  {
    return this.contractNo;
  }

  public void setContractNo(String paramString)
  {
    this.contractNo = paramString;
  }

  public String getDebitNo()
  {
    return this.debitNo;
  }

  public void setDebitNo(String paramString)
  {
    this.debitNo = paramString;
  }

  public String getCreditNo()
  {
    return this.creditNo;
  }

  public void setCreditNo(String paramString)
  {
    this.creditNo = paramString;
  }

  public Double getAmount()
  {
    return this.amount;
  }

  public void setAmount(Double paramDouble)
  {
    this.amount = paramDouble;
  }

  public Account getDebitAccount()
  {
    return this.debitAccount;
  }

  public void setDebitAccount(Account paramAccount)
  {
    this.debitAccount = paramAccount;
  }

  public Account getCreditAccount()
  {
    return this.creditAccount;
  }

  public void setCreditAccount(Account paramAccount)
  {
    this.creditAccount = paramAccount;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}