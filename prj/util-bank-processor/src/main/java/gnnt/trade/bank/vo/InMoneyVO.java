package gnnt.trade.bank.vo;

import java.io.Serializable;

public class InMoneyVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String bankname;
  private String bankID;
  private double money;
  private String firmID;
  private String contact;
  private TransferInfoValue payInfo;
  private String payPwd;
  private TransferInfoValue receiveInfo;
  private String operator;
  private long actionID;
  private String inOutStart;
  private String personName;
  private String amoutDate;
  private String bankName;
  private String outAccount;
  private String payChannel;

  public InMoneyVO(String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, String payPwd, TransferInfoValue receiveInfo, String operator, long actionID, String inOutStart, String personName, String amoutDate, String bankName, String outAccount)
  {
    setActionID(actionID);
    setBankID(bankID);
    setBankname(bankname);
    setMoney(money);
    setFirmID(firmID);
    setPayInfo(payInfo);
    setPayPwd(payPwd);
    setReceiveInfo(receiveInfo);
    setOperator(operator);
    setInOutStart(inOutStart);
    setPersonName(personName);
    setAmoutDate(amoutDate);
    setBankName(bankName);
    setOutAccount(outAccount);
  }

  public InMoneyVO(String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, String payPwd, TransferInfoValue receiveInfo, String operator, long actionID)
  {
    setActionID(actionID);
    setBankID(bankID);
    setBankname(bankname);
    setMoney(money);
    setFirmID(firmID);
    setPayInfo(payInfo);
    setPayPwd(payPwd);
    setReceiveInfo(receiveInfo);
    setOperator(operator);
  }

  public InMoneyVO(String bankID, String bankname, double money, String firmID, String contact, TransferInfoValue payInfo, String payPwd, TransferInfoValue receiveInfo, String operator, long actionID, String payChannel)
  {
    setActionID(actionID);
    setBankID(bankID);
    setBankname(bankname);
    setMoney(money);
    setFirmID(firmID);
    setContact(contact);
    setPayInfo(payInfo);
    setPayPwd(payPwd);
    setReceiveInfo(receiveInfo);
    setOperator(operator);
    setPayChannel(payChannel);
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("bankname:" + this.bankname + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("money:" + this.money + str);
    sb.append("firmID:" + this.firmID + str);
    sb.append("payInfo:" + this.payInfo + str);
    sb.append("receiveInfo:" + this.receiveInfo + str);
    sb.append("operator:" + this.operator + str);
    sb.append("actionID:" + this.actionID + str);
    sb.append("InOutStart:" + this.inOutStart + str);
    sb.append("personName:" + this.personName + str);
    sb.append("amoutDate:" + this.amoutDate + str);
    sb.append("bankName:" + this.bankName + str);
    sb.append("outAccount:" + this.outAccount + str);
    return sb.toString();
  }

  public String getContact()
  {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getInOutStart() {
    return this.inOutStart;
  }

  public void setInOutStart(String inOutStart) {
    this.inOutStart = inOutStart;
  }

  public String getPersonName() {
    return this.personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public String getAmoutDate() {
    return this.amoutDate;
  }

  public void setAmoutDate(String amoutDate) {
    this.amoutDate = amoutDate;
  }

  public String getBankName() {
    return this.bankName;
  }

  public String getPayChannel() {
    return this.payChannel;
  }

  public void setPayChannel(String payChannel) {
    this.payChannel = payChannel;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getOutAccount() {
    return this.outAccount;
  }

  public void setOutAccount(String outAccount) {
    this.outAccount = outAccount;
  }

  public long getActionID()
  {
    return this.actionID;
  }

  public void setActionID(long actionID)
  {
    this.actionID = actionID;
  }

  public String getBankID()
  {
    return this.bankID;
  }

  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public double getMoney()
  {
    return this.money;
  }

  public void setMoney(double money)
  {
    this.money = money;
  }

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String operator)
  {
    this.operator = operator;
  }

  public TransferInfoValue getPayInfo()
  {
    return this.payInfo;
  }

  public void setPayInfo(TransferInfoValue payInfo)
  {
    this.payInfo = payInfo;
  }

  public String getPayPwd()
  {
    return this.payPwd;
  }

  public void setPayPwd(String payPwd)
  {
    this.payPwd = payPwd;
  }

  public TransferInfoValue getReceiveInfo()
  {
    return this.receiveInfo;
  }

  public void setReceiveInfo(TransferInfoValue receiveInfo)
  {
    this.receiveInfo = receiveInfo;
  }

  public String getBankname() {
    return this.bankname;
  }

  public void setBankname(String bankname) {
    this.bankname = bankname;
  }
}