package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class RZQS
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date date;
  public String systemID;
  public String firmID;
  public double balance;
  public double lastBalance;
  public double rights;
  public double lastRights;
  public double fee;
  public double fundIO;
  public Date createDate;
  public int doFlag;

  public Date getDate()
  {
    return this.date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public String getSystemID() {
    return this.systemID;
  }
  public void setSystemID(String systemID) {
    this.systemID = systemID;
  }
  public String getFirmID() {
    return this.firmID;
  }
  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }
  public double getBalance() {
    return this.balance;
  }
  public void setBalance(double balance) {
    this.balance = balance;
  }
  public double getLastBalance() {
    return this.lastBalance;
  }
  public void setLastBalance(double lastBalance) {
    this.lastBalance = lastBalance;
  }
  public double getRights() {
    return this.rights;
  }
  public void setRights(double rights) {
    this.rights = rights;
  }
  public double getLastRights() {
    return this.lastRights;
  }
  public void setLastRights(double lastRights) {
    this.lastRights = lastRights;
  }
  public double getFee() {
    return this.fee;
  }
  public void setFee(double fee) {
    this.fee = fee;
  }
  public double getFundIO() {
    return this.fundIO;
  }
  public void setFundIO(double fundIO) {
    this.fundIO = fundIO;
  }
  public Date getCreateDate() {
    return this.createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public int getDoFlag() {
    return this.doFlag;
  }
  public void setDoFlag(int doFlag) {
    this.doFlag = doFlag;
  }
}