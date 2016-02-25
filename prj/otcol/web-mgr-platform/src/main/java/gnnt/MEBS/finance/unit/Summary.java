package gnnt.MEBS.finance.unit;

public class Summary
{
  private String summaryNo;
  private String summary;
  private String ledgerItem;
  private String fundDCFlag;
  private String accountCodeOpp;
  private String appendAccount;
  
  public String getSummaryNo()
  {
    return this.summaryNo;
  }
  
  public void setSummaryNo(String paramString)
  {
    this.summaryNo = paramString;
  }
  
  public String getAccountCodeOpp()
  {
    return this.accountCodeOpp;
  }
  
  public void setAccountCodeOpp(String paramString)
  {
    this.accountCodeOpp = paramString;
  }
  
  public String getFundDCFlag()
  {
    return this.fundDCFlag;
  }
  
  public void setFundDCFlag(String paramString)
  {
    this.fundDCFlag = paramString;
  }
  
  public String getLedgerItem()
  {
    return this.ledgerItem;
  }
  
  public void setLedgerItem(String paramString)
  {
    this.ledgerItem = paramString;
  }
  
  public String getSummary()
  {
    return this.summary;
  }
  
  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }
  
  public String getAppendAccount()
  {
    return this.appendAccount;
  }
  
  public void setAppendAccount(String paramString)
  {
    this.appendAccount = paramString;
  }
}
