package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class StorageBankCashReserveBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankCode;
  private String MarketCode;
  private String TransDateTime;
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
