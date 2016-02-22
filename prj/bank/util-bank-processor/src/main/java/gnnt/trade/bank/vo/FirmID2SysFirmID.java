package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmID2SysFirmID
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String sysFirmID;
  public String systemID;
  public String bankID;
  public String defaultSystem;
  public String note;
  public String account;
  public String accountName;
  public String cardType;
  public String card;

  public String getNote()
  {
    return this.note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public String getAccount() {
    return this.account;
  }
  public void setAccount(String account) {
    this.account = account;
  }
  public String getAccountName() {
    return this.accountName;
  }
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }
  public String getCardType() {
    return this.cardType;
  }
  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
  public String getCard() {
    return this.card;
  }
  public void setCard(String card) {
    this.card = card;
  }
  public String getFirmID() {
    return this.firmID;
  }
  public void setFirmID(String firmID) {
    this.firmID = firmID;
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
  public String getBankID() {
    return this.bankID;
  }
  public void setBankID(String bankID) {
    this.bankID = bankID;
  }
  public String getDefaultSystem() {
    return this.defaultSystem;
  }
  public void setDefaultSystem(String defaultSystem) {
    this.defaultSystem = defaultSystem;
  }

  public String toString() {
    return "FirmID2SysFirmID [bankID=" + this.bankID + ", defaultSystem=" + 
      this.defaultSystem + ", firmID=" + this.firmID + ", sysFirmID=" + 
      this.sysFirmID + ", systemID=" + this.systemID + "]";
  }
}