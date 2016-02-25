package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class HJTradeResult
{
  public BigDecimal hjPlanAmount;
  public BigDecimal hjTradeAmount;
  public BigDecimal hjTradeRatio;
  public BigDecimal hjTradeMoney;
  
  public void setHJPlanAmount(BigDecimal paramBigDecimal)
  {
    this.hjPlanAmount = paramBigDecimal;
  }
  
  public BigDecimal getHJPlanAmount()
  {
    return this.hjPlanAmount;
  }
  
  public void setHJTradeAmount(BigDecimal paramBigDecimal)
  {
    this.hjTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getHJTradeAmount()
  {
    return this.hjTradeAmount;
  }
  
  public void setHJTradeRatio(BigDecimal paramBigDecimal)
  {
    this.hjTradeRatio = paramBigDecimal;
  }
  
  public BigDecimal getHJTradeRatio()
  {
    return this.hjTradeRatio;
  }
  
  public void setHJTradeMoney(BigDecimal paramBigDecimal)
  {
    this.hjTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getHJTradeMoney()
  {
    return this.hjTradeMoney;
  }
}
