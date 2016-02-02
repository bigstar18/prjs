package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class AccountStatusReconciliation
  implements Serializable
{
  private String BankCode;
  private String MarketCode;
  private String TransAddressCode;
  private String TransDateTime;
  private String BankAccount;
  private String bondAcc;
  private String certificationName;
  private String MoneyType;
  private String CashExCode;
  private String status;
  
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
  
  public String getTransAddressCode()
  {
    return this.TransAddressCode;
  }
  
  public void setTransAddressCode(String transAddressCode)
  {
    this.TransAddressCode = transAddressCode;
  }
  
  public String getTransDateTime()
  {
    return this.TransDateTime;
  }
  
  public void setTransDateTime(String transDateTime)
  {
    this.TransDateTime = transDateTime;
  }
  
  public String getBankAccount()
  {
    return this.BankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.BankAccount = bankAccount;
  }
  
  public String getBondAcc()
  {
    return this.bondAcc;
  }
  
  public void setBondAcc(String bondAcc)
  {
    this.bondAcc = bondAcc;
  }
  
  public String getCertificationName()
  {
    return this.certificationName;
  }
  
  public void setCertificationName(String certificationName)
  {
    this.certificationName = certificationName;
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
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
}
