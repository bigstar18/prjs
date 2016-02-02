package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class S05
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String TransAddressCode;
  private String TransDate;
  private String TransSerialNo;
  private String MachineAccount;
  private String TransFundAcc;
  private String ClientName;
  private String AccrualType;
  private String MoneyType;
  private String RemitMark;
  private double Balance;
  
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
  
  public String getTransSerialNo()
  {
    return this.TransSerialNo;
  }
  
  public void setTransSerialNo(String transSerialNo)
  {
    this.TransSerialNo = transSerialNo;
  }
  
  public String getMachineAccount()
  {
    return this.MachineAccount;
  }
  
  public void setMachineAccount(String machineAccount)
  {
    this.MachineAccount = machineAccount;
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
  
  public String getAccrualType()
  {
    return this.AccrualType;
  }
  
  public void setAccrualType(String accrualType)
  {
    this.AccrualType = accrualType;
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
  
  public double getBalance()
  {
    return this.Balance;
  }
  
  public void setBalance(double balance)
  {
    this.Balance = balance;
  }
}
