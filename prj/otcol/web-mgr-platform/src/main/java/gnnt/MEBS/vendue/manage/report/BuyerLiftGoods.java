package gnnt.MEBS.vendue.manage.report;

import java.math.BigDecimal;

public class BuyerLiftGoods
{
  private String proName = null;
  private BigDecimal tradeAmount = null;
  private BigDecimal outAmount = null;
  private BigDecimal outRadio = null;
  
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
  
  public void setOutAmount(BigDecimal paramBigDecimal)
  {
    this.outAmount = paramBigDecimal;
  }
  
  public BigDecimal getOutAmount()
  {
    return this.outAmount;
  }
  
  public void setOutRadio(BigDecimal paramBigDecimal)
  {
    this.outRadio = paramBigDecimal;
  }
  
  public BigDecimal getOutRadio()
  {
    return this.outRadio;
  }
}
