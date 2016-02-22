package gnnt.trade.bank.data.cgb.vo;

import java.io.Serializable;

public class StorageBankMoneySupervision
  implements Serializable
{
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
  private String MoneyType;
  private String CashExCode;
  private int CapitalAccountSummaryMoney;
  private int SummaryAccountMoney;
  private int ReservePreparedToPay;
  private String TradeDifference;
  private int GenerationMoney;
  
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
  
  public int getCapitalAccountSummaryMoney()
  {
    return this.CapitalAccountSummaryMoney;
  }
  
  public void setCapitalAccountSummaryMoney(int capitalAccountSummaryMoney)
  {
    this.CapitalAccountSummaryMoney = capitalAccountSummaryMoney;
  }
  
  public int getSummaryAccountMoney()
  {
    return this.SummaryAccountMoney;
  }
  
  public void setSummaryAccountMoney(int summaryAccountMoney)
  {
    this.SummaryAccountMoney = summaryAccountMoney;
  }
  
  public int getReservePreparedToPay()
  {
    return this.ReservePreparedToPay;
  }
  
  public void setReservePreparedToPay(int reservePreparedToPay)
  {
    this.ReservePreparedToPay = reservePreparedToPay;
  }
  
  public String getTradeDifference()
  {
    return this.TradeDifference;
  }
  
  public void setTradeDifference(String tradeDifference)
  {
    this.TradeDifference = tradeDifference;
  }
  
  public int getGenerationMoney()
  {
    return this.GenerationMoney;
  }
  
  public void setGenerationMoney(int generationMoney)
  {
    this.GenerationMoney = generationMoney;
  }
}
