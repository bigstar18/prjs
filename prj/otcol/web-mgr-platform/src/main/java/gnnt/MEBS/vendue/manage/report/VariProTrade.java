package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class VariProTrade
{
  private String vari;
  private BigDecimal avgPrice;
  
  public void setVari(String paramString)
  {
    this.vari = paramString;
  }
  
  public String getVari()
  {
    return this.vari;
  }
  
  public void setAvgPrice(BigDecimal paramBigDecimal)
  {
    this.avgPrice = paramBigDecimal;
  }
  
  public BigDecimal getAvgPrice()
  {
    return this.avgPrice;
  }
}
