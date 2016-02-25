package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VariTradeResult
{
  public BigDecimal vPlanAmount;
  public BigDecimal vTradeAmount;
  public BigDecimal vTradeRatio;
  public BigDecimal vTradeMoney;
  public BigDecimal vAvgPrice;
  public String vari;
  public String grade;
  public ArrayList gradeArray;
  
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
  
  public void setVPlanAmount(BigDecimal paramBigDecimal)
  {
    this.vPlanAmount = paramBigDecimal;
  }
  
  public BigDecimal getVPlanAmount()
  {
    return this.vPlanAmount;
  }
  
  public void setVTradeAmount(BigDecimal paramBigDecimal)
  {
    this.vTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getVTradeAmount()
  {
    return this.vTradeAmount;
  }
  
  public void setVTradeRatio(BigDecimal paramBigDecimal)
  {
    this.vTradeRatio = paramBigDecimal;
  }
  
  public BigDecimal getVTradeRatio()
  {
    return this.vTradeRatio;
  }
  
  public void setVTradeMoney(BigDecimal paramBigDecimal)
  {
    this.vTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getVTradeMoney()
  {
    return this.vTradeMoney;
  }
  
  public void setVAvgPrice(BigDecimal paramBigDecimal)
  {
    this.vAvgPrice = paramBigDecimal;
  }
  
  public BigDecimal getVAvgPrice()
  {
    return this.vAvgPrice;
  }
  
  public ArrayList getGradeArray()
  {
    return this.gradeArray;
  }
  
  public void setGradeArray(ArrayList paramArrayList)
  {
    this.gradeArray = paramArrayList;
  }
}
