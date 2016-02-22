package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExchageRate
  implements Serializable
{
  private static final long serialVersionUID = 1690197650664049814L;
  private String commodityID;
  private String inCommodityID;
  private Double quoteRate;
  private Double quoteExchangeRate;
  private Double clearExchageRate;
  private Double quoteAgio;
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getInCommodityID()
  {
    return this.inCommodityID;
  }
  
  public void setInCommodityID(String inCommodityID)
  {
    this.inCommodityID = inCommodityID;
  }
  
  public Double getQuoteRate()
  {
    return this.quoteRate;
  }
  
  public void setQuoteRate(Double quoteRate)
  {
    this.quoteRate = quoteRate;
  }
  
  public Double getQuoteExchangeRate()
  {
    return this.quoteExchangeRate;
  }
  
  public void setQuoteExchangeRate(Double quoteExchangeRate)
  {
    this.quoteExchangeRate = quoteExchangeRate;
  }
  
  public Double getClearExchageRate()
  {
    return this.clearExchageRate;
  }
  
  public void setClearExchageRate(Double clearExchageRate)
  {
    this.clearExchageRate = clearExchageRate;
  }
  
  public Double getQuoteAgio()
  {
    return this.quoteAgio;
  }
  
  public void setQuoteAgio(Double quoteAgio)
  {
    this.quoteAgio = quoteAgio;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }
}
