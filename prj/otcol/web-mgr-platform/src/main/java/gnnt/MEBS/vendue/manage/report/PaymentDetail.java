package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class PaymentDetail
{
  private String contractId;
  private String kuDian;
  private String buyerName;
  private String paymentDate;
  private String vari;
  private BigDecimal price;
  private BigDecimal amount;
  private BigDecimal tradeMoney;
  private BigDecimal exauAmount;
  private BigDecimal exauMoney;
  private BigDecimal payKuDianMoney;
  private BigDecimal poundage;
  private BigDecimal payupMoney;
  private BigDecimal procurePrice;
  private String grade;
  private String code;
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
  
  public BigDecimal getProcurePrice()
  {
    return this.procurePrice;
  }
  
  public void setProcurePrice(BigDecimal paramBigDecimal)
  {
    this.procurePrice = paramBigDecimal;
  }
  
  public void setContractId(String paramString)
  {
    this.contractId = paramString;
  }
  
  public String getContractId()
  {
    return this.contractId;
  }
  
  public void setKuDian(String paramString)
  {
    this.kuDian = paramString;
  }
  
  public String getKuDian()
  {
    return this.kuDian;
  }
  
  public void setPaymentDate(String paramString)
  {
    this.paymentDate = paramString;
  }
  
  public String getPaymentDate()
  {
    return this.paymentDate;
  }
  
  public void setBuyerName(String paramString)
  {
    this.buyerName = paramString;
  }
  
  public String getBuyerName()
  {
    return this.buyerName;
  }
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setPrice(BigDecimal paramBigDecimal)
  {
    this.price = paramBigDecimal;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setExauAmount(BigDecimal paramBigDecimal)
  {
    this.exauAmount = paramBigDecimal;
  }
  
  public BigDecimal getExauAmount()
  {
    return this.exauAmount;
  }
  
  public void setExauMoney(BigDecimal paramBigDecimal)
  {
    this.exauMoney = paramBigDecimal;
  }
  
  public BigDecimal getExauMoney()
  {
    return this.exauMoney;
  }
  
  public void setPayKuDianMoney(BigDecimal paramBigDecimal)
  {
    this.payKuDianMoney = paramBigDecimal;
  }
  
  public BigDecimal getPayKuDianMoney()
  {
    return this.payKuDianMoney;
  }
  
  public void setPoundage(BigDecimal paramBigDecimal)
  {
    this.poundage = paramBigDecimal;
  }
  
  public BigDecimal getPoundage()
  {
    return this.poundage;
  }
  
  public void setPayupMoney(BigDecimal paramBigDecimal)
  {
    this.payupMoney = paramBigDecimal;
  }
  
  public BigDecimal getPayupMoney()
  {
    return this.payupMoney;
  }
}
