package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class S08
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String DeliveryDate;
  private String BankAccAmountName;
  private String BankAccAmountNo;
  private String BankAccAmoutnOpenName;
  private String CorpDeliveryAccName;
  private String CorpDeliveryAccNo;
  private String CorpDeliveryAccBank;
  private String BusinessMark;
  private String MoneyType;
  private String RemitMark;
  private String DeliveryMoney;
  
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
  
  public String getDeliveryDate()
  {
    return this.DeliveryDate;
  }
  
  public void setDeliveryDate(String deliveryDate)
  {
    this.DeliveryDate = deliveryDate;
  }
  
  public String getBankAccAmountName()
  {
    return this.BankAccAmountName;
  }
  
  public void setBankAccAmountName(String bankAccAmountName)
  {
    this.BankAccAmountName = bankAccAmountName;
  }
  
  public String getBankAccAmountNo()
  {
    return this.BankAccAmountNo;
  }
  
  public void setBankAccAmountNo(String bankAccAmountNo)
  {
    this.BankAccAmountNo = bankAccAmountNo;
  }
  
  public String getBankAccAmoutnOpenName()
  {
    return this.BankAccAmoutnOpenName;
  }
  
  public void setBankAccAmoutnOpenName(String bankAccAmoutnOpenName)
  {
    this.BankAccAmoutnOpenName = bankAccAmoutnOpenName;
  }
  
  public String getCorpDeliveryAccName()
  {
    return this.CorpDeliveryAccName;
  }
  
  public void setCorpDeliveryAccName(String corpDeliveryAccName)
  {
    this.CorpDeliveryAccName = corpDeliveryAccName;
  }
  
  public String getCorpDeliveryAccNo()
  {
    return this.CorpDeliveryAccNo;
  }
  
  public void setCorpDeliveryAccNo(String corpDeliveryAccNo)
  {
    this.CorpDeliveryAccNo = corpDeliveryAccNo;
  }
  
  public String getCorpDeliveryAccBank()
  {
    return this.CorpDeliveryAccBank;
  }
  
  public void setCorpDeliveryAccBank(String corpDeliveryAccBank)
  {
    this.CorpDeliveryAccBank = corpDeliveryAccBank;
  }
  
  public String getBusinessMark()
  {
    return this.BusinessMark;
  }
  
  public void setBusinessMark(String businessMark)
  {
    this.BusinessMark = businessMark;
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
  
  public String getDeliveryMoney()
  {
    return this.DeliveryMoney;
  }
  
  public void setDeliveryMoney(String deliveryMoney)
  {
    this.DeliveryMoney = deliveryMoney;
  }
}
