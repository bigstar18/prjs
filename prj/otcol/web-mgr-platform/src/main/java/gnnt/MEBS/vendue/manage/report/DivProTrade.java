package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class DivProTrade
{
  private int proId;
  private String proName;
  private BigDecimal tradeAmountAgo;
  private BigDecimal tradeMoneyAgo;
  private BigDecimal tradeAmountLater;
  private BigDecimal tradeMoneyLater;
  
  public void setProId(int paramInt)
  {
    this.proId = paramInt;
  }
  
  public int getProId()
  {
    return this.proId;
  }
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setTradeAmountAgo(BigDecimal paramBigDecimal)
  {
    this.tradeAmountAgo = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmountAgo()
  {
    return this.tradeAmountAgo;
  }
  
  public void setTradeAmountLater(BigDecimal paramBigDecimal)
  {
    this.tradeAmountLater = paramBigDecimal;
  }
  
  public BigDecimal getTradeAmountLater()
  {
    return this.tradeAmountLater;
  }
  
  public void setTradeMoneyAgo(BigDecimal paramBigDecimal)
  {
    this.tradeMoneyAgo = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoneyAgo()
  {
    return this.tradeMoneyAgo;
  }
  
  public void setTradeMoneyLater(BigDecimal paramBigDecimal)
  {
    this.tradeMoneyLater = paramBigDecimal;
  }
  
  public BigDecimal getTradeMoneyLater()
  {
    return this.tradeMoneyLater;
  }
}
