package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class FirmBankFunds
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String bankID;
  public String bankName;
  public double balance;
  public double outMoneyFrozenFunds;
  public double OutInMoney;
  public double clearOutInMoney;
  public int primaryBankFlag;
  public double RIGHTSFROZENFUNDS;
  public double RIGHTS;
  public double FIRMFEE;
  public double MARKETFEE;
  public Date b_date;

  public Date getB_date()
  {
    return this.b_date;
  }

  public void setB_date(Date bDate) {
    this.b_date = bDate;
  }

  public double getRIGHTSFROZENFUNDS() {
    return this.RIGHTSFROZENFUNDS;
  }

  public void setRIGHTSFROZENFUNDS(double rIGHTSFROZENFUNDS) {
    this.RIGHTSFROZENFUNDS = rIGHTSFROZENFUNDS;
  }

  public double getRIGHTS() {
    return this.RIGHTS;
  }

  public void setRIGHTS(double rIGHTS) {
    this.RIGHTS = rIGHTS;
  }

  public double getFIRMFEE() {
    return this.FIRMFEE;
  }

  public void setFIRMFEE(double fIRMFEE) {
    this.FIRMFEE = fIRMFEE;
  }

  public double getMARKETFEE() {
    return this.MARKETFEE;
  }

  public void setMARKETFEE(double mARKETFEE) {
    this.MARKETFEE = mARKETFEE;
  }

  public String getFirmID() {
    return this.firmID;
  }

  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }

  public String getBankID() {
    return this.bankID;
  }

  public void setBankID(String bankID) {
    this.bankID = bankID;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public double getOutMoneyFrozenFunds() {
    return this.outMoneyFrozenFunds;
  }

  public void setOutMoneyFrozenFunds(double outMoneyFrozenFunds) {
    this.outMoneyFrozenFunds = outMoneyFrozenFunds;
  }

  public double getOutInMoney() {
    return this.OutInMoney;
  }

  public void setOutInMoney(double outInMoney) {
    this.OutInMoney = outInMoney;
  }

  public double getClearOutInMoney() {
    return this.clearOutInMoney;
  }

  public void setClearOutInMoney(double clearOutInMoney) {
    this.clearOutInMoney = clearOutInMoney;
  }

  public int getPrimaryBankFlag() {
    return this.primaryBankFlag;
  }

  public void setPrimaryBankFlag(int primaryBankFlag) {
    this.primaryBankFlag = primaryBankFlag;
  }
}