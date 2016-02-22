package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.sql.Date;

public class ClientState
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String TransAddressCode;
  private Date TransDate;
  private String BankAccount;
  private String TransFundAcc;
  private String ClientName;
  private String MoneyType;
  private String RemitMark;
  private String SaveState;

  public String getBankNo()
  {
    return this.BankNo;
  }
  public void setBankNo(String bankNo) {
    this.BankNo = bankNo;
  }
  public String getStrandsNo() {
    return this.StrandsNo;
  }
  public void setStrandsNo(String strandsNo) {
    this.StrandsNo = strandsNo;
  }
  public String getTransAddressCode() {
    return this.TransAddressCode;
  }
  public void setTransAddressCode(String transAddressCode) {
    this.TransAddressCode = transAddressCode;
  }

  public Date getTransDate() {
    return this.TransDate;
  }
  public void setTransDate(Date transDate) {
    this.TransDate = transDate;
  }
  public String getBankAccount() {
    return this.BankAccount;
  }
  public void setBankAccount(String bankAccount) {
    this.BankAccount = bankAccount;
  }
  public String getTransFundAcc() {
    return this.TransFundAcc;
  }
  public void setTransFundAcc(String transFundAcc) {
    this.TransFundAcc = transFundAcc;
  }
  public String getClientName() {
    return this.ClientName;
  }
  public void setClientName(String clientName) {
    this.ClientName = clientName;
  }
  public String getMoneyType() {
    return this.MoneyType;
  }
  public void setMoneyType(String moneyType) {
    this.MoneyType = moneyType;
  }
  public String getRemitMark() {
    return this.RemitMark;
  }
  public void setRemitMark(String remitMark) {
    this.RemitMark = remitMark;
  }
  public String getSaveState() {
    return this.SaveState;
  }
  public void setSaveState(String saveState) {
    this.SaveState = saveState;
  }
}