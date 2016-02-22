package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class ArticleParitiesAgio
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private String inCommodityId;
  private BigDecimal quoteRate;
  private BigDecimal quoteExchangeRate;
  private BigDecimal clearExchageRate;
  private BigDecimal quoteAgio;
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="报价系数:")
  public BigDecimal getQuoteRateAndQuoteExchangeRate()
  {
    return formatDecimals(this.quoteRate.multiply(this.quoteExchangeRate), NumberDigits.QUOTERATE);
  }
  
  @ClassDiscription(name="单位换算:")
  public BigDecimal getQuoteRate()
  {
    return formatDecimals(this.quoteRate, NumberDigits.QUOTERATE);
  }
  
  public void setQuoteRate(BigDecimal quoteRate)
  {
    this.quoteRate = quoteRate;
  }
  
  @ClassDiscription(name="报价汇率:")
  public BigDecimal getQuoteExchangeRate()
  {
    return formatDecimals(this.quoteExchangeRate, NumberDigits.QUOTEEXCHANGERATE);
  }
  
  public void setQuoteExchangeRate(BigDecimal quoteExchangeRate)
  {
    this.quoteExchangeRate = quoteExchangeRate;
  }
  
  @ClassDiscription(name="结算汇率:")
  public BigDecimal getClearExchageRate()
  {
    return formatDecimals(this.clearExchageRate, NumberDigits.CLEAREXCHAGERATE - 4);
  }
  
  public void setClearExchageRate(BigDecimal clearExchageRate)
  {
    this.clearExchageRate = clearExchageRate;
  }
  
  @ClassDiscription(name="报价贴水:")
  public BigDecimal getQuoteAgio()
  {
    return formatDecimals(this.quoteAgio, NumberDigits.QUOTEAGIO);
  }
  
  public void setQuoteAgio(BigDecimal quoteAgio)
  {
    this.quoteAgio = quoteAgio;
  }
  
  public ArticleParitiesAgio(String commodityId, String commodityName, BigDecimal quoteRate, BigDecimal quoteExchangeRate, BigDecimal clearExchageRate, BigDecimal quoteAgio, String inCommodityId)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.quoteRate = quoteRate;
    this.quoteExchangeRate = quoteExchangeRate;
    this.clearExchageRate = clearExchageRate;
    this.quoteAgio = quoteAgio;
    this.inCommodityId = inCommodityId;
  }
  
  public ArticleParitiesAgio() {}
  
  public ArticleParitiesAgio(String commodityId, String commodityName, BigDecimal quoteRate)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.quoteRate = quoteRate;
  }
  
  public String getId()
  {
    return this.commodityId;
  }
  
  public void setPrimary(String primary)
  {
    this.commodityId = primary;
  }
  
  public String getInCommodityId()
  {
    return this.inCommodityId;
  }
  
  public void setInCommodityId(String inCommodityId)
  {
    this.inCommodityId = inCommodityId;
  }
}
