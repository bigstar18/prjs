package gnnt.trade.bank.vo;

import java.io.Serializable;

public class InMoneyVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String payChannel;
  private String bankName;
  private String bankID;
  private double money;
  private String firmID;
  private TransferInfoValue payInfo;
  private String payPwd;
  private TransferInfoValue receiveInfo;
  private String operator;
  private long actionID;
  private String inOutStart;
  private String personName;
  private String amoutDate;
  private String outAccount;
  private String outBankName;
  private String bankFlag;
  
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
  
  public InMoneyVO(String amoutDate, String outBankName, String outAccount, String personName, String inOutStart, String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, String payPwd, TransferInfoValue receiveInfo, String operator, long actionID)
  {
    setAmoutDate(amoutDate);
    setOutBankName(outBankName);
    setOutAccount(outAccount);
    setPersonName(personName);
    setInOutStart(inOutStart);
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
  
  public InMoneyVO(String amoutDate, String outBankName, String outAccount, String personName, String inOutStart, String bankID, String bankname, double money, String firmID, TransferInfoValue payInfo, String payPwd, TransferInfoValue receiveInfo, String operator, long actionID, String payChannel)
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
    setPayChannel(payChannel);
  }
  
  public String getPayChannel()
  {
    return this.payChannel;
  }
  
  public void setPayChannel(String payChannel)
  {
    this.payChannel = payChannel;
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
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankname(String bankName)
  {
    this.bankName = bankName;
  }
  
  public String getInOutStart()
  {
    return this.inOutStart;
  }
  
  public void setInOutStart(String inOutStart)
  {
    this.inOutStart = inOutStart;
  }
  
  public String getPersonName()
  {
    return this.personName;
  }
  
  public void setPersonName(String personName)
  {
    this.personName = personName;
  }
  
  public String getAmoutDate()
  {
    return this.amoutDate;
  }
  
  public void setAmoutDate(String amoutDate)
  {
    this.amoutDate = amoutDate;
  }
  
  public String getOutAccount()
  {
    return this.outAccount;
  }
  
  public void setOutAccount(String outAccount)
  {
    this.outAccount = outAccount;
  }
  
  public String getBankFlag()
  {
    return this.bankFlag;
  }
  
  public void setBankFlag(String bankFlag)
  {
    this.bankFlag = bankFlag;
  }
  
  public String getOutBankName()
  {
    return this.outBankName;
  }
  
  public void setOutBankName(String outBankName)
  {
    this.outBankName = outBankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
  }
}
