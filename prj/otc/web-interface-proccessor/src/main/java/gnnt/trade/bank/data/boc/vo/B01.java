package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class B01
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String TransAddressCode;
  private String TransDate;
  private String TransTime;
  private String BankSerialNo;
  private String StartSerialNo;
  private String BankAccount;
  private String TransFundAcc;
  private String ClientName;
  private String StartName;
  private String TransferDirection;
  private String MoneyType;
  private String RemitMark;
  private double TransferMoney;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("BankNo:" + getBankNo() + sep);
    sb.append("StrandsNo:" + getStrandsNo() + sep);
    sb.append("TransAddressCode:" + getTransAddressCode() + sep);
    sb.append("TransDate:" + getTransDate() + sep);
    sb.append("TransTime:" + getTransTime() + sep);
    sb.append("BankSerialNo:" + getBankSerialNo() + sep);
    sb.append("StartSerialNo:" + getStartSerialNo() + sep);
    sb.append("BankAccount:" + getBankAccount() + sep);
    sb.append("TransFundAcc:" + getTransFundAcc() + sep);
    sb.append("ClientName:" + getClientName() + sep);
    sb.append("StartName:" + getStartName() + sep);
    sb.append("TransferDirection:" + getTransferDirection() + sep);
    sb.append("MoneyType:" + getMoneyType() + sep);
    sb.append("RemitMark:" + getRemitMark() + sep);
    sb.append("TransferMoney:" + getTransferMoney() + sep);
    sb.append(sep);
    return sb.toString();
  }
  
  public String getBankNo()
  {
    return this.BankNo;
  }
  
  public void setBankNo(String bankNo)
  {
    this.BankNo = bankNo;
  }
  
  public String getStrandsNo()
  {
    return this.StrandsNo;
  }
  
  public void setStrandsNo(String strandsNo)
  {
    this.StrandsNo = strandsNo;
  }
  
  public String getTransAddressCode()
  {
    return this.TransAddressCode;
  }
  
  public void setTransAddressCode(String transAddressCode)
  {
    this.TransAddressCode = transAddressCode;
  }
  
  public String getTransDate()
  {
    return this.TransDate;
  }
  
  public void setTransDate(String transDate)
  {
    this.TransDate = transDate;
  }
  
  public String getTransTime()
  {
    return this.TransTime;
  }
  
  public void setTransTime(String transTime)
  {
    this.TransTime = transTime;
  }
  
  public String getBankSerialNo()
  {
    return this.BankSerialNo;
  }
  
  public void setBankSerialNo(String bankSerialNo)
  {
    this.BankSerialNo = bankSerialNo;
  }
  
  public String getStartSerialNo()
  {
    return this.StartSerialNo;
  }
  
  public void setStartSerialNo(String startSerialNo)
  {
    this.StartSerialNo = startSerialNo;
  }
  
  public String getBankAccount()
  {
    return this.BankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.BankAccount = bankAccount;
  }
  
  public String getTransFundAcc()
  {
    return this.TransFundAcc;
  }
  
  public void setTransFundAcc(String transFundAcc)
  {
    this.TransFundAcc = transFundAcc;
  }
  
  public String getClientName()
  {
    return this.ClientName;
  }
  
  public void setClientName(String clientName)
  {
    this.ClientName = clientName;
  }
  
  public String getStartName()
  {
    return this.StartName;
  }
  
  public void setStartName(String startName)
  {
    this.StartName = startName;
  }
  
  public String getTransferDirection()
  {
    return this.TransferDirection;
  }
  
  public void setTransferDirection(String transferDirection)
  {
    this.TransferDirection = transferDirection;
  }
  
  public String getMoneyType()
  {
    return this.MoneyType;
  }
  
  public void setMoneyType(String moneyType)
  {
    this.MoneyType = moneyType;
  }
  
  public String getRemitMark()
  {
    return this.RemitMark;
  }
  
  public void setRemitMark(String remitMark)
  {
    this.RemitMark = remitMark;
  }
  
  public double getTransferMoney()
  {
    return this.TransferMoney;
  }
  
  public void setTransferMoney(double transferMoney)
  {
    this.TransferMoney = transferMoney;
  }
}
