package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class XJTradeResult
{
  public BigDecimal xjPlanAmount;
  public BigDecimal xjTradeAmount;
  public BigDecimal xjTradeRatio;
  public BigDecimal xjTradeMoney;
  public ArrayList tradeResult;
  public String proName;
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setXJPlanAmount(BigDecimal paramBigDecimal)
  {
    this.xjPlanAmount = paramBigDecimal;
  }
  
  public BigDecimal getXJPlanAmount()
  {
    return this.xjPlanAmount;
  }
  
  public void setXJTradeAmount(BigDecimal paramBigDecimal)
  {
    this.xjTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getXJTradeAmount()
  {
    return this.xjTradeAmount;
  }
  
  public void setXJTradeRatio(BigDecimal paramBigDecimal)
  {
    this.xjTradeRatio = paramBigDecimal;
  }
  
  public BigDecimal getXJTradeRatio()
  {
    return this.xjTradeRatio;
  }
  
  public void setXJTradeMoney(BigDecimal paramBigDecimal)
  {
    this.xjTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getXJTradeMoney()
  {
    return this.xjTradeMoney;
  }
  
  public void setTradeResult(ArrayList paramArrayList)
  {
    this.tradeResult = paramArrayList;
  }
  
  public ArrayList getTradeResult()
  {
    return this.tradeResult;
  }
}
