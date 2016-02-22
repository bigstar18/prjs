package gnnt.trade.bank.data.boc.vo;

import java.io.Serializable;

public class S13
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String BankNo;
  private String StrandsNo;
  private String Date;
  private String MoneyType;
  private String RemitMark;
  private double FundAccAmount;
  private double AccountAmount;
  private double PrepareMoney;
  private double BusinessMark;
  private double AgencyDeliveryMoney;
  
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
  
  public double getFundAccAmount()
  {
    return this.FundAccAmount;
  }
  
  public void setFundAccAmount(double fundAccAmount)
  {
    this.FundAccAmount = fundAccAmount;
  }
  
  public double getAccountAmount()
  {
    return this.AccountAmount;
  }
  
  public void setAccountAmount(double accountAmount)
  {
    this.AccountAmount = accountAmount;
  }
  
  public double getPrepareMoney()
  {
    return this.PrepareMoney;
  }
  
  public void setPrepareMoney(double prepareMoney)
  {
    this.PrepareMoney = prepareMoney;
  }
  
  public double getBusinessMark()
  {
    return this.BusinessMark;
  }
  
  public void setBusinessMark(double businessMark)
  {
    this.BusinessMark = businessMark;
  }
  
  public double getAgencyDeliveryMoney()
  {
    return this.AgencyDeliveryMoney;
  }
  
  public void setAgencyDeliveryMoney(double agencyDeliveryMoney)
  {
    this.AgencyDeliveryMoney = agencyDeliveryMoney;
  }
}
