package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class CapitalValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long iD;
  public long actionID;
  public String firmID;
  public String contact;
  public String funID;
  public String bankID;
  public String debitID;
  public String creditID;
  public int type;
  public double money;
  public String oprcode;
  public Timestamp createtime;
  public Timestamp bankTime;
  public int status;
  public int express;
  public String note;
  public String bankName;
  public String firmName;
  public String account;
  public String sysFirmID;
  public String systemID;
  public String tradeSource;
  public double feeMoney;

  public long getiD()
  {
    return this.iD;
  }
  public void setiD(long iD) {
    this.iD = iD;
  }
  public long getActionID() {
    return this.actionID;
  }
  public void setActionID(long actionID) {
    this.actionID = actionID;
  }
  public String getFirmID() {
    return this.firmID;
  }
  public void setFirmID(String firmID) {
    this.firmID = firmID;
  }
  public String getContact() {
    return this.contact;
  }
  public void setContact(String contact) {
    this.contact = contact;
  }
  public String getFunID() {
    return this.funID;
  }
  public void setFunID(String funID) {
    this.funID = funID;
  }
  public String getBankID() {
    return this.bankID;
  }
  public void setBankID(String bankID) {
    this.bankID = bankID;
  }
  public String getDebitID() {
    return this.debitID;
  }
  public void setDebitID(String debitID) {
    this.debitID = debitID;
  }
  public String getCreditID() {
    return this.creditID;
  }
  public void setCreditID(String creditID) {
    this.creditID = creditID;
  }
  public int getType() {
    return this.type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public double getMoney() {
    return this.money;
  }
  public void setMoney(double money) {
    this.money = money;
  }
  public String getOprcode() {
    return this.oprcode;
  }
  public void setOprcode(String oprcode) {
    this.oprcode = oprcode;
  }
  public Timestamp getCreatetime() {
    return this.createtime;
  }
  public void setCreatetime(Timestamp createtime) {
    this.createtime = createtime;
  }
  public Timestamp getBankTime() {
    return this.bankTime;
  }
  public void setBankTime(Timestamp bankTime) {
    this.bankTime = bankTime;
  }
  public int getStatus() {
    return this.status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public int getExpress() {
    return this.express;
  }
  public void setExpress(int express) {
    this.express = express;
  }
  public String getNote() {
    return this.note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public String getBankName() {
    return this.bankName;
  }
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
  public String getFirmName() {
    return this.firmName;
  }
  public void setFirmName(String firmName) {
    this.firmName = firmName;
  }
  public String getAccount() {
    return this.account;
  }
  public void setAccount(String account) {
    this.account = account;
  }
  public String getSysFirmID() {
    return this.sysFirmID;
  }
  public void setSysFirmID(String sysFirmID) {
    this.sysFirmID = sysFirmID;
  }
  public String getSystemID() {
    return this.systemID;
  }
  public void setSystemID(String systemID) {
    this.systemID = systemID;
  }
  public String getTradeSource() {
    return this.tradeSource;
  }
  public void setTradeSource(String tradeSource) {
    this.tradeSource = tradeSource;
  }
  public double getFeeMoney() {
    return this.feeMoney;
  }
  public void setFeeMoney(double feeMoney) {
    this.feeMoney = feeMoney;
  }

  public String toString()
  {
    return "CapitalValue [account=" + this.account + ", actionID=" + 
      this.actionID + ", bankID=" + this.bankID + ", bankName=" + 
      this.bankName + ", bankTime=" + this.bankTime + ", contact=" + 
      this.contact + ", createtime=" + this.createtime + ", creditID=" + 
      this.creditID + ", debitID=" + this.debitID + ", express=" + 
      this.express + ", feeMoney=" + this.feeMoney + ", firmID=" + this.firmID + 
      ", firmName=" + this.firmName + ", funID=" + this.funID + ", iD=" + 
      this.iD + ", money=" + this.money + ", note=" + this.note + ", oprcode=" + 
      this.oprcode + ", status=" + this.status + ", sysFirmID=" + 
      this.sysFirmID + ", systemID=" + this.systemID + ", tradeSource=" + 
      this.tradeSource + ", type=" + this.type + "]";
  }
}