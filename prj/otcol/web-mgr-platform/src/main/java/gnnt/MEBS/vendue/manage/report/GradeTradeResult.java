package gnnt.MEBS.vendue.manage.report;

import gnnt.MEBS.vendue.manage.util.ManaUtil;
import java.math.BigDecimal;

public class GradeTradeResult
{
  public String grade;
  public BigDecimal planAmount;
  public BigDecimal tradeAmount;
  public BigDecimal maxPrice;
  public BigDecimal minPrice;
  public BigDecimal avgPrice;
  public BigDecimal tradeRatio;
  public BigDecimal tradeMoney;
  
  public BigDecimal getAvgPrice()
  {
    return this.avgPrice;
  }
  
  public void setAvgPrice(BigDecimal paramBigDecimal)
  {
    this.avgPrice = paramBigDecimal;
  }
  
  public BigDecimal getMaxPrice()
  {
    return this.maxPrice;
  }
  
  public void setMaxPrice(BigDecimal paramBigDecimal)
  {
    this.maxPrice = paramBigDecimal;
  }
  
  public BigDecimal getMinPrice()
  {
    return this.minPrice;
  }
  
  public void setMinPrice(BigDecimal paramBigDecimal)
  {
    this.minPrice = paramBigDecimal;
  }
  
  public BigDecimal getPlanAmount()
  {
    return this.planAmount;
  }
  
  public void setPlanAmount(BigDecimal paramBigDecimal)
  {
    this.planAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeRatio()
  {
    BigDecimal localBigDecimal1 = ManaUtil.disBD(this.planAmount);
    BigDecimal localBigDecimal2 = ManaUtil.disBD(this.tradeAmount);
    if (localBigDecimal1.compareTo(new BigDecimal(0)) <= 0) {
      localBigDecimal1 = new BigDecimal(1);
    }
    this.tradeRatio = localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100));
    return this.tradeRatio;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
}
