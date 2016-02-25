package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AllTrade
{
  private String categoryName;
  private int categoryId;
  private String proName;
  private BigDecimal allTradeAmount;
  private BigDecimal allTradeMoney;
  private ArrayList eachProTrade;
  
  public void setCategoryName(String paramString)
  {
    this.categoryName = paramString;
  }
  
  public String getCategoryName()
  {
    return this.categoryName;
  }
  
  public void setCategoryId(int paramInt)
  {
    this.categoryId = paramInt;
  }
  
  public int getCategoryId()
  {
    return this.categoryId;
  }
  
  public void setProName(String paramString)
  {
    this.proName = paramString;
  }
  
  public String getProName()
  {
    return this.proName;
  }
  
  public void setAllTradeAmount(BigDecimal paramBigDecimal)
  {
    this.allTradeAmount = paramBigDecimal;
  }
  
  public BigDecimal getAllTradeAmount()
  {
    return this.allTradeAmount;
  }
  
  public void setAllTradeMoney(BigDecimal paramBigDecimal)
  {
    this.allTradeMoney = paramBigDecimal;
  }
  
  public BigDecimal getAllTradeMoney()
  {
    return this.allTradeMoney;
  }
  
  public void setEachProTrade(ArrayList paramArrayList)
  {
    this.eachProTrade = paramArrayList;
  }
  
  public ArrayList getEachProTrade()
  {
    return this.eachProTrade;
  }
}
