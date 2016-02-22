package gnnt.trade.bank.data.cgb.vo;

import java.io.Serializable;

public class StorageBankMoneySettlementSummary
  implements Serializable
{
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
  private String SummaryName;
  private String SummaryAccount;
  private String SummaryOpenBank;
  private String LegalPersontName;
  private String LegalPersonAccount;
  private String LegalPersonOpenBank;
  private String TradeDifference;
  private String MoneyType;
  private String CashExCode;
  private int money;
  
  public String getBankCode()
  {
    return this.BankCode;
  }
  
  public void setBankCode(String bankCode)
  {
    this.BankCode = bankCode;
  }
  
  public String getMarketCode()
  {
    return this.MarketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.MarketCode = marketCode;
  }
  
  public String getTransDateTime()
  {
    return this.TransDateTime;
  }
  
  public void setTransDateTime(String transDateTime)
  {
    this.TransDateTime = transDateTime;
  }
  
  public String getSummaryName()
  {
    return this.SummaryName;
  }
  
  public void setSummaryName(String summaryName)
  {
    this.SummaryName = summaryName;
  }
  
  public String getSummaryAccount()
  {
    return this.SummaryAccount;
  }
  
  public void setSummaryAccount(String summaryAccount)
  {
    this.SummaryAccount = summaryAccount;
  }
  
  public String getSummaryOpenBank()
  {
    return this.SummaryOpenBank;
  }
  
  public void setSummaryOpenBank(String summaryOpenBank)
  {
    this.SummaryOpenBank = summaryOpenBank;
  }
  
  public String getLegalPersontName()
  {
    return this.LegalPersontName;
  }
  
  public void setLegalPersontName(String legalPersontName)
  {
    this.LegalPersontName = legalPersontName;
  }
  
  public String getLegalPersonAccount()
  {
    return this.LegalPersonAccount;
  }
  
  public void setLegalPersonAccount(String legalPersonAccount)
  {
    this.LegalPersonAccount = legalPersonAccount;
  }
  
  public String getLegalPersonOpenBank()
  {
    return this.LegalPersonOpenBank;
  }
  
  public void setLegalPersonOpenBank(String legalPersonOpenBank)
  {
    this.LegalPersonOpenBank = legalPersonOpenBank;
  }
  
  public String getTradeDifference()
  {
    return this.TradeDifference;
  }
  
  public void setTradeDifference(String tradeDifference)
  {
    this.TradeDifference = tradeDifference;
  }
  
  public String getMoneyType()
  {
    return this.MoneyType;
  }
  
  public void setMoneyType(String moneyType)
  {
    this.MoneyType = moneyType;
  }
  
  public String getCashExCode()
  {
    return this.CashExCode;
  }
  
  public void setCashExCode(String cashExCode)
  {
    this.CashExCode = cashExCode;
  }
  
  public int getMoney()
  {
    return this.money;
  }
  
  public void setMoney(int money)
  {
    this.money = money;
  }
}
