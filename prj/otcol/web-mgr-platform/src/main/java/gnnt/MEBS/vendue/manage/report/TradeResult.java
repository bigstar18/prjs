package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TradeResult
{
  public String vari;
  public BigDecimal planAmount;
  public BigDecimal tradeAmount;
  public BigDecimal maxPrice;
  public BigDecimal minPrice;
  public BigDecimal avgPrice;
  public BigDecimal tradeRatio;
  public BigDecimal tradeMoney;
  public String grade;
  public ArrayList gradeDetail;
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setPlanAmount(BigDecimal paramBigDecimal)
  {
    this.planAmount = paramBigDecimal;
  }
  
  public BigDecimal getPlanAmount()
  {
    return this.planAmount;
  }
  
  public void setTradeAmount(BigDecimal paramBigDecimal)
  {
    this.tradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmount()
  {
    return this.tradeAmount;
  }
  
  public void setMaxPrice(BigDecimal paramBigDecimal)
  {
    this.maxPrice = paramBigDecimal;
  }
  
  public BigDecimal getMaxPrice()
  {
    return this.maxPrice;
  }
  
  public void setMinPrice(BigDecimal paramBigDecimal)
  {
    this.minPrice = paramBigDecimal;
  }
  
  public BigDecimal getMinPrice()
  {
    return this.minPrice;
  }
  
  public void setAvgPrice(BigDecimal paramBigDecimal)
  {
    this.avgPrice = paramBigDecimal;
  }
  
  public BigDecimal getAvgPrice()
  {
    return this.avgPrice;
  }
  
  public void setTradeRatio(BigDecimal paramBigDecimal)
  {
    this.tradeRatio = paramBigDecimal;
  }
  
  public BigDecimal getTradeRatio()
  {
    return this.tradeRatio;
  }
  
  public void setTradeMoney(BigDecimal paramBigDecimal)
  {
    this.tradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoney()
  {
    return this.tradeMoney;
  }
  
  public ArrayList getGradeDetail()
  {
    return this.gradeDetail;
  }
  
  public void setGradeDetail(ArrayList paramArrayList)
  {
    this.gradeDetail = paramArrayList;
  }
}
