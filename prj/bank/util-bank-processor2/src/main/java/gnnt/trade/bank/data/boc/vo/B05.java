package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class B05
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String Date;
  private String MoneyType;
  private String RemitMark;
  private String MachineAccount;
  private String TransFundAcc;
  private String ClientName;
  private double MachineAccountMoney;
  private double TransFundAccMoney;
  
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
  
  public String getDate()
  {
    return this.Date;
  }
  
  public void setDate(String date)
  {
    this.Date = date;
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
  
  public double getMachineAccountMoney()
  {
    return this.MachineAccountMoney;
  }
  
  public void setMachineAccountMoney(double machineAccountMoney)
  {
    this.MachineAccountMoney = machineAccountMoney;
  }
  
  public double getTransFundAccMoney()
  {
    return this.TransFundAccMoney;
  }
  
  public void setTransFundAccMoney(double transFundAccMoney)
  {
    this.TransFundAccMoney = transFundAccMoney;
  }
}
