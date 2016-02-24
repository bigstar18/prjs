package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class S11
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String DeliveryDate;
  private String BankShortName;
  private String CorpDeliveryAccName;
  private String CorpDeliveryAccNo;
  private String CorpDeliveryAccBank;
  private String SavaBankAccAmountName;
  private String SaveBankAccAmountNo;
  private String SaveBankAccAmountOpenName;
  private String BusinessMark;
  private String MoneyType;
  private String RemitMark;
  private double DeliveryMoney;
  
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
  
  public String getBankShortName()
  {
    return this.BankShortName;
  }
  
  public void setBankShortName(String bankShortName)
  {
    this.BankShortName = bankShortName;
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
  
  public String getSavaBankAccAmountName()
  {
    return this.SavaBankAccAmountName;
  }
  
  public void setSavaBankAccAmountName(String savaBankAccAmountName)
  {
    this.SavaBankAccAmountName = savaBankAccAmountName;
  }
  
  public String getSaveBankAccAmountNo()
  {
    return this.SaveBankAccAmountNo;
  }
  
  public void setSaveBankAccAmountNo(String saveBankAccAmountNo)
  {
    this.SaveBankAccAmountNo = saveBankAccAmountNo;
  }
  
  public String getSaveBankAccAmountOpenName()
  {
    return this.SaveBankAccAmountOpenName;
  }
  
  public void setSaveBankAccAmountOpenName(String saveBankAccAmountOpenName)
  {
    this.SaveBankAccAmountOpenName = saveBankAccAmountOpenName;
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
  
  public double getDeliveryMoney()
  {
    return this.DeliveryMoney;
  }
  
  public void setDeliveryMoney(double deliveryMoney)
  {
    this.DeliveryMoney = deliveryMoney;
  }
}
