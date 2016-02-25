package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class PlanDetail
{
  private String contractId;
  private String code;
  private String kudian;
  private String str3;
  private BigDecimal price;
  private String name;
  private String vari;
  private BigDecimal amount;
  private int proId;
  private String proName;
  private BigDecimal actualAmount;
  private BigDecimal liftedAmount;
  private BigDecimal fellBackAmount;
  private String userCode;
  private String tradeDate;
  private String commodityId;
  private BigDecimal tradeMoney;
  private String proYear;
  
  public String getProYear()
  {
    return this.proYear;
  }
  
  public void setProYear(String paramString)
  {
    this.proYear = paramString;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public BigDecimal getActualAmount()
  {
    return this.actualAmount;
  }
  
  public void setActualAmount(BigDecimal paramBigDecimal)
  {
    this.actualAmount = paramBigDecimal;
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public String getContractId()
  {
    return this.contractId;
  }
  
  public void setContractId(String paramString)
  {
    this.contractId = paramString;
  }
  
  public BigDecimal getFellBackAmount()
  {
    return this.fellBackAmount;
  }
  
  public void setFellBackAmount(BigDecimal paramBigDecimal)
  {
    this.fellBackAmount = paramBigDecimal;
  }
  
  public String getKudian()
  {
    return this.kudian;
  }
  
  public void setKudian(String paramString)
  {
    this.kudian = paramString;
  }
  
  public BigDecimal getLiftedAmount()
  {
    return this.liftedAmount;
  }
  
  public void setLiftedAmount(BigDecimal paramBigDecimal)
  {
    this.liftedAmount = paramBigDecimal;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
  
  public void setPrice(BigDecimal paramBigDecimal)
  {
    this.price = paramBigDecimal;
  }
  
  public int getProId()
  {
    return this.proId;
  }
  
  public void setProId(int paramInt)
  {
    this.proId = paramInt;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
  
  public String getStr3()
  {
    return this.str3;
  }
  
  public void setStr3(String paramString)
  {
    this.str3 = paramString;
  }
  
  public String getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(String paramString)
  {
    this.tradeDate = paramString;
  }
  
  public String getUserCode()
  {
    return this.userCode;
  }
  
  public void setUserCode(String paramString)
  {
    this.userCode = paramString;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
}
