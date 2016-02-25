package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class TransferAccountsTransactionDetailed
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankCode;
  private String MarketCode;
  private String TransAddressCode;
  private String TransDateTime;
  private String TransTime;
  private String BankSerialNumber;
  private String LaunchSerialNumber;
  private String BankAccount;
  private String bondAcc;
  private String certificationName;
  private String Launch;
  private String TransferDirection;
  private String MoneyType;
  private String CashExCode;
  private double money;
  
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
  
  public String getTransTime()
  {
    return this.TransTime;
  }
  
  public void setTransTime(String transTime)
  {
    this.TransTime = transTime;
  }
  
  public String getBankSerialNumber()
  {
    return this.BankSerialNumber;
  }
  
  public void setBankSerialNumber(String bankSerialNumber)
  {
    this.BankSerialNumber = bankSerialNumber;
  }
  
  public String getLaunchSerialNumber()
  {
    return this.LaunchSerialNumber;
  }
  
  public void setLaunchSerialNumber(String launchSerialNumber)
  {
    this.LaunchSerialNumber = launchSerialNumber;
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
  
  public String getLaunch()
  {
    return this.Launch;
  }
  
  public void setLaunch(String launch)
  {
    this.Launch = launch;
  }
  
  public String getTransferDirection()
  {
    return this.TransferDirection;
  }
  
  public void setTransferDirection(String transferDirection)
  {
    this.TransferDirection = transferDirection;
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
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
}
