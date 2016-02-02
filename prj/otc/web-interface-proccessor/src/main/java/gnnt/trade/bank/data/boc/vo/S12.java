package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class S12
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String Date;
  private String MoneyType;
  private String RemitMark;
  private String Money;
  
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
  
  public String getMoney()
  {
    return this.Money;
  }
  
  public void setMoney(String money)
  {
    this.Money = money;
  }
}
