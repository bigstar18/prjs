package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmBalanceValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmId;
  public double marketBalance;
  public double frozenBalance;
  public double avilableBalance;
  public double inOutMoney;
  public double canOutMoney;
  public double lastBalance;
  public String firmBankId;
  public String bankAccount;
  public double bankBalance;
  public double inTransitMoney;
  public String bankName;
  public double floatingloss;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("firmId:" + this.firmId + sep);
    sb.append("marketBalance:" + this.marketBalance + sep);
    sb.append("lastBalance:" + this.lastBalance + sep);
    sb.append("frozenBalance:" + this.frozenBalance + sep);
    sb.append("avilableBalance:" + this.avilableBalance + sep);
    sb.append("inOutMoney:" + this.inOutMoney + sep);
    sb.append("canOutMoney:" + this.canOutMoney + sep);
    sb.append("firmBankId:" + this.firmBankId + sep);
    sb.append("bankAccount:" + this.bankAccount + sep);
    sb.append("bankBalance:" + this.bankBalance + sep);
    sb.append("inTransitMoney:" + this.inTransitMoney + sep);
    sb.append("bankName:" + this.bankName + sep);
    sb.append(sep);
    return sb.toString();
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.bankAccount = bankAccount;
  }
  
  public double getBankBalance()
  {
    return this.bankBalance;
  }
  
  public void setBankBalance(double bankBalance)
  {
    this.bankBalance = bankBalance;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public double getMarketBalance()
  {
    return this.marketBalance;
  }
  
  public void setMarketBalance(double marketBalance)
  {
    this.marketBalance = marketBalance;
  }
  
  public String getFirmBankId()
  {
    return this.firmBankId;
  }
  
  public void setFirmBankId(String firmBankId)
  {
    this.firmBankId = firmBankId;
  }
  
  public double getAvilableBalance()
  {
    return this.avilableBalance;
  }
  
  public void setAvilableBalance(double avilableBalance)
  {
    this.avilableBalance = avilableBalance;
  }
  
  public double getInOutMoney()
  {
    return this.inOutMoney;
  }
  
  public void setInOutMoney(double inOutMoney)
  {
    this.inOutMoney = inOutMoney;
  }
  
  public double getCanOutMoney()
  {
    return this.canOutMoney;
  }
  
  public void setCanOutMoney(double canOutMoney)
  {
    this.canOutMoney = canOutMoney;
  }
  
  public double getFrozenBalance()
  {
    return this.frozenBalance;
  }
  
  public void setFrozenBalance(double frozenBalance)
  {
    this.frozenBalance = frozenBalance;
  }
  
  public double getLastBalance()
  {
    return this.lastBalance;
  }
  
  public void setLastBalance(double lastBalance)
  {
    this.lastBalance = lastBalance;
  }
  
  public double getInTransitMoney()
  {
    return this.inTransitMoney;
  }
  
  public void setInTransitMoney(double inTransitMoney)
  {
    this.inTransitMoney = inTransitMoney;
  }
}
