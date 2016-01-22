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
  public double canOutBalance;
  public double lastBalance;
  public double floatingloss;
  public String firmBankId;
  public String firmBank;
  public String bankAccount;
  public double bankBalance;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("firmId:" + this.firmId + sep);
    sb.append("marketBalance:" + this.marketBalance + sep);
    sb.append("canOutBalance:" + this.canOutBalance + sep);
    sb.append("lastBalance:" + this.lastBalance + sep);
    sb.append("floatingloss:" + this.floatingloss + sep);
    sb.append("frozenBalance:" + this.frozenBalance + sep);
    sb.append("avilableBalance:" + this.avilableBalance + sep);
    sb.append("firmBankId:" + this.firmBankId + sep);
    sb.append("firmBank:" + this.firmBank + sep);
    sb.append("bankAccount:" + this.bankAccount + sep);
    sb.append("bankBalance:" + this.bankBalance + sep);
    sb.append(sep);
    return sb.toString();
  }

  public String getBankAccount()
  {
    return this.bankAccount;
  }

  public double getCanOutBalance() {
    return this.canOutBalance;
  }

  public void setCanOutBalance(double canOutBalance) {
    this.canOutBalance = canOutBalance;
  }

  public void setBankAccount(String bankAccount) {
    this.bankAccount = bankAccount;
  }

  public double getBankBalance() {
    return this.bankBalance;
  }

  public void setBankBalance(double bankBalance) {
    this.bankBalance = bankBalance;
  }

  public String getFirmBank() {
    return this.firmBank;
  }

  public void setFirmBank(String firmBank) {
    this.firmBank = firmBank;
  }

  public String getFirmId() {
    return this.firmId;
  }

  public void setFirmId(String firmId) {
    this.firmId = firmId;
  }

  public double getMarketBalance() {
    return this.marketBalance;
  }

  public void setMarketBalance(double marketBalance) {
    this.marketBalance = marketBalance;
  }

  public String getFirmBankId() {
    return this.firmBankId;
  }

  public void setFirmBankId(String firmBankId) {
    this.firmBankId = firmBankId;
  }

  public double getAvilableBalance() {
    return this.avilableBalance;
  }

  public void setAvilableBalance(double avilableBalance) {
    this.avilableBalance = avilableBalance;
  }

  public double getFrozenBalance() {
    return this.frozenBalance;
  }

  public void setFrozenBalance(double frozenBalance) {
    this.frozenBalance = frozenBalance;
  }

  public double getLastBalance() {
    return this.lastBalance;
  }

  public void setLastBalance(double lastBalance) {
    this.lastBalance = lastBalance;
  }
}