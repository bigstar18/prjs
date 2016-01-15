package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SummaryF extends StandardModel
{
  private static final long serialVersionUID = 5784146401111672026L;

  @ClassDiscription(name="摘要号", description="")
  private String summaryNo;

  @ClassDiscription(name="摘要名称", description="")
  private String summary;

  @ClassDiscription(name="归入总账项目", description="")
  private String ledgerItem;

  @ClassDiscription(name="归入总账项目对象", description="")
  private LedgerField ledgerField;

  @ClassDiscription(name=" 资金借贷方向", description="")
  private String fundDCFlag;

  @ClassDiscription(name="对方科目代码", description="")
  private String accountCodeOpp;

  @ClassDiscription(name="附加帐", description="")
  private String appendAccount;

  @ClassDiscription(name="是否初始化", description="")
  private String isInit;

  public String getSummaryNo()
  {
    return this.summaryNo;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public String getLedgerItem()
  {
    return this.ledgerItem;
  }

  public LedgerField getLedgerField()
  {
    return this.ledgerField;
  }

  public String getFundDCFlag()
  {
    return this.fundDCFlag;
  }

  public String getAccountCodeOpp()
  {
    return this.accountCodeOpp;
  }

  public String getAppendAccount()
  {
    return this.appendAccount;
  }

  public String getIsInit()
  {
    return this.isInit;
  }

  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  public void setLedgerItem(String paramString)
  {
    this.ledgerItem = paramString;
  }

  public void setLedgerField(LedgerField paramLedgerField)
  {
    this.ledgerField = paramLedgerField;
  }

  public void setFundDCFlag(String paramString)
  {
    this.fundDCFlag = paramString;
  }

  public void setAccountCodeOpp(String paramString)
  {
    this.accountCodeOpp = paramString;
  }

  public void setAppendAccount(String paramString)
  {
    this.appendAccount = paramString;
  }

  public void setIsInit(String paramString)
  {
    this.isInit = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "summaryNo", this.summaryNo);
  }
}