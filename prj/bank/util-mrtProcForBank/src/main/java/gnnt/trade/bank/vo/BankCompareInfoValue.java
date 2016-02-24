package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankCompareInfoValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public String funid;
  public String firmid;
  public String bankid;
  public long account;
  public int type;
  public double money;
  public String compareDate;
  public String note;
  public String createTime;
  public int status;

  public long getAccount()
  {
    return this.account;
  }

  public void setAccount(long account) {
    this.account = account;
  }

  public String getBankid() {
    return this.bankid;
  }

  public void setBankid(String bankid) {
    this.bankid = bankid;
  }

  public String getCompareDate() {
    return this.compareDate;
  }

  public void setCompareDate(String compareDate) {
    this.compareDate = compareDate;
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getFirmid() {
    return this.firmid;
  }

  public void setFirmid(String firmid) {
    this.firmid = firmid;
  }

  public String getFunid() {
    return this.funid;
  }

  public void setFunid(String funid) {
    this.funid = funid;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getMoney() {
    return this.money;
  }

  public void setMoney(double money) {
    this.money = money;
  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("id:" + this.id + sep);
    sb.append("funid:" + this.funid + sep);
    sb.append("firmid:" + this.firmid + sep);
    sb.append("bankid:" + this.bankid + sep);
    sb.append("account:" + this.account + sep);
    sb.append("type:" + this.type + sep);
    sb.append("money:" + this.money + sep);
    sb.append("compareDate:" + this.compareDate + sep);
    sb.append("note:" + this.note + sep);
    sb.append("createTime:" + this.createTime + sep);
    sb.append("status:" + this.status + sep);
    sb.append(sep);
    return sb.toString();
  }
}