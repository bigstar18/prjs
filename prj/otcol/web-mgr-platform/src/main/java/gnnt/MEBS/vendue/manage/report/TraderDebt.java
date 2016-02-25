package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class TraderDebt
{
  public String trader;
  public String name;
  public BigDecimal tradeAmount;
  public BigDecimal tradeMoney;
  public BigDecimal arrivedPayment;
  public BigDecimal outAmont;
  public double debt;
  public String mgTel;
  
  public void setTrader(String paramString)
  {
    this.trader = paramString;
  }
  
  public String getTrader()
  {
    return this.trader;
  }
  
  public void setDebt(double paramDouble)
  {
    this.debt = paramDouble;
  }
  
  public double getDebt()
  {
    return this.debt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setArrivedPayment(BigDecimal paramBigDecimal)
  {
    this.arrivedPayment = paramBigDecimal;
  }
  
  public BigDecimal getArrivedPayment()
  {
    return this.arrivedPayment;
  }
  
  public void setOutAmount(BigDecimal paramBigDecimal)
  {
    this.outAmont = paramBigDecimal;
  }
  
  public BigDecimal getOutAmount()
  {
    return this.outAmont;
  }
  
  public void setMGTel(String paramString)
  {
    this.mgTel = paramString;
  }
  
  public String getMGTel()
  {
    return this.mgTel;
  }
}
