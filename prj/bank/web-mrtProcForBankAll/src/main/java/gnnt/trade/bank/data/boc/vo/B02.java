package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class B02
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String TransAddressCode;
  private String TransDate;
  private String BankAccount;
  private String TransFundAcc;
  private String ClientName;
  private String MoneyType;
  private String RemitMark;
  private String SaveState;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("BankNo:" + getBankNo() + sep);
    sb.append("StrandsNo:" + getStrandsNo() + sep);
    sb.append("TransAddressCode:" + getTransAddressCode() + sep);
    sb.append("TransDate:" + getTransDate() + sep);
    sb.append("BankAccount:" + getBankAccount() + sep);
    sb.append("TransFundAcc:" + getTransFundAcc() + sep);
    sb.append("ClientName:" + getClientName() + sep);
    sb.append("MoneyType:" + getMoneyType() + sep);
    sb.append("RemitMark:" + getRemitMark() + sep);
    sb.append("SaveState:" + getSaveState() + sep);
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
  
  public String getSaveState()
  {
    return this.SaveState;
  }
  
  public void setSaveState(String saveState)
  {
    this.SaveState = saveState;
  }
}
