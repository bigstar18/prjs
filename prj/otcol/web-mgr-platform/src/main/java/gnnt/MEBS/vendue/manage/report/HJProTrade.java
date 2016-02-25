package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HJProTrade
{
  private BigDecimal hjTradeAmount;
  private BigDecimal hjTradeMoney;
  private ArrayList tradePro;
  
  public void setHJTradeAmount(BigDecimal paramBigDecimal)
  {
    this.hjTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getHJTradeAmount()
  {
    return this.hjTradeAmount;
  }
  
  public void setHJTradeMoney(BigDecimal paramBigDecimal)
  {
    this.hjTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getHJTradeMoney()
  {
    return this.hjTradeMoney;
  }
  
  public void setTradePro(ArrayList paramArrayList)
  {
    this.tradePro = paramArrayList;
  }
  
  public ArrayList getTradePro()
  {
    return this.tradePro;
  }
}
