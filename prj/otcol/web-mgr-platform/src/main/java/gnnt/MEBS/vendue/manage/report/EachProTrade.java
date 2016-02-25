package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EachProTrade
{
  private int proId;
  private String proName;
  private BigDecimal tradeAmount;
  private BigDecimal tradeMoney;
  private ArrayList divProTrade;
  
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
  
  public void setDivProTrade(ArrayList paramArrayList)
  {
    this.divProTrade = paramArrayList;
  }
  
  public ArrayList getDivProTrade()
  {
    return this.divProTrade;
  }
}
