package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.math.BigDecimal;
import java.util.Date;

public class Quotation
  extends Clone
{
  private String commodityId;
  private BigDecimal minPriceMove;
  private BigDecimal lastPrice;
  private Integer quotepointalgr;
  private double curPrice_B;
  private double curPrice_S;
  private String commodityName;
  private Double yesterBalancePrice;
  private Double closePrice;
  private Double openPrice;
  private Double highPrice;
  private Double lowPrice;
  private Double curPrice;
  private Double curAmount;
  private Double openAmount;
  private Double buyOpenAmount;
  private Double sellOpenAmount;
  private Double closeAmount;
  private Double buyCloseAmount;
  private Double sellCloseAmount;
  private Double reserveCount;
  private Double reserveChange;
  private Double price;
  private Double totalMoney;
  private Double totalAmount;
  private Double spread;
  private Date createTime;
  
  public Quotation(String commodityId, BigDecimal minPriceMove, String commodityName, BigDecimal lastPrice, double curPrice_B, double curPrice_S, Double yesterBalancePrice, Double closePrice, Double openPrice, Double highPrice, Double lowPrice, Double curPrice, Double curAmount, Double openAmount, Double buyOpenAmount, Double sellOpenAmount, Double closeAmount, Double buyCloseAmount, Double sellCloseAmount, Double reserveCount, Double reserveChange, Double price, Double totalMoney, Double totalAmount, Double spread, Date createTime)
  {
    this.commodityId = commodityId;
    this.minPriceMove = minPriceMove;
    this.commodityName = commodityName;
    this.lastPrice = lastPrice;
    this.curPrice_B = curPrice_B;
    this.curPrice_S = curPrice_S;
    this.yesterBalancePrice = yesterBalancePrice;
    this.closePrice = closePrice;
    this.openPrice = openPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.curPrice = curPrice;
    this.curAmount = curAmount;
    this.openAmount = openAmount;
    this.buyOpenAmount = buyOpenAmount;
    this.sellOpenAmount = sellOpenAmount;
    this.closeAmount = closeAmount;
    this.buyCloseAmount = buyCloseAmount;
    this.sellCloseAmount = sellCloseAmount;
    this.reserveCount = reserveCount;
    this.reserveChange = reserveChange;
    this.price = price;
    this.totalMoney = totalMoney;
    this.totalAmount = totalAmount;
    this.spread = spread;
    this.createTime = createTime;
  }
  
  public Quotation() {}
  
  public BigDecimal getMinPriceMove()
  {
    return this.minPriceMove;
  }
  
  public void setMinPriceMove(BigDecimal minPriceMove)
  {
    this.minPriceMove = minPriceMove;
  }
  
  public Double getCurPrice()
  {
    BigDecimal bigDecimal = new BigDecimal(getCurPrice_B() + getCurPrice_S());
    
    Double quotepoint = Double.valueOf(Math.round(bigDecimal.doubleValue() / 2.0D * 100.0D) / 100.0D);
    
    return quotepoint;
  }
  
  public Double getBasePrice()
  {
    return this.curPrice;
  }
  
  public double getCurPrice_B()
  {
    return this.curPrice_B;
  }
  
  public void setCurPrice_B(double curPrice_B)
  {
    this.curPrice_B = curPrice_B;
  }
  
  public double getCurPrice_S()
  {
    return this.curPrice_S;
  }
  
  public void setCurPrice_S(double curPrice_S)
  {
    this.curPrice_S = curPrice_S;
  }
  
  public void setCurPrice(Double curPrice)
  {
    this.curPrice = curPrice;
  }
  
  public BigDecimal getLastPrice()
  {
    return this.lastPrice;
  }
  
  public void setLastPrice(BigDecimal lastPrice)
  {
    this.lastPrice = lastPrice;
  }
  
  public Integer getQuotepointalgr()
  {
    return this.quotepointalgr;
  }
  
  public void setQuotepointalgr(Integer quotepointalgr)
  {
    this.quotepointalgr = quotepointalgr;
  }
  
  @ClassDiscription(name="商品代码", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public Double getYesterBalancePrice()
  {
    return this.yesterBalancePrice;
  }
  
  public void setYesterBalancePrice(Double yesterBalancePrice)
  {
    this.yesterBalancePrice = yesterBalancePrice;
  }
  
  public Double getClosePrice()
  {
    return this.closePrice;
  }
  
  public void setClosePrice(Double closePrice)
  {
    this.closePrice = closePrice;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public Double getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setHighPrice(Double highPrice)
  {
    this.highPrice = highPrice;
  }
  
  public Double getLowPrice()
  {
    return this.lowPrice;
  }
  
  public void setLowPrice(Double lowPrice)
  {
    this.lowPrice = lowPrice;
  }
  
  public Double getCurAmount()
  {
    return this.curAmount;
  }
  
  public void setCurAmount(Double curAmount)
  {
    this.curAmount = curAmount;
  }
  
  public Double getOpenAmount()
  {
    return this.openAmount;
  }
  
  public void setOpenAmount(Double openAmount)
  {
    this.openAmount = openAmount;
  }
  
  public Double getBuyOpenAmount()
  {
    return this.buyOpenAmount;
  }
  
  public void setBuyOpenAmount(Double buyOpenAmount)
  {
    this.buyOpenAmount = buyOpenAmount;
  }
  
  public Double getSellOpenAmount()
  {
    return this.sellOpenAmount;
  }
  
  public void setSellOpenAmount(Double sellOpenAmount)
  {
    this.sellOpenAmount = sellOpenAmount;
  }
  
  public Double getCloseAmount()
  {
    return this.closeAmount;
  }
  
  public void setCloseAmount(Double closeAmount)
  {
    this.closeAmount = closeAmount;
  }
  
  public Double getBuyCloseAmount()
  {
    return this.buyCloseAmount;
  }
  
  public void setBuyCloseAmount(Double buyCloseAmount)
  {
    this.buyCloseAmount = buyCloseAmount;
  }
  
  public Double getSellCloseAmount()
  {
    return this.sellCloseAmount;
  }
  
  public void setSellCloseAmount(Double sellCloseAmount)
  {
    this.sellCloseAmount = sellCloseAmount;
  }
  
  public Double getReserveCount()
  {
    return this.reserveCount;
  }
  
  public void setReserveCount(Double reserveCount)
  {
    this.reserveCount = reserveCount;
  }
  
  public Double getReserveChange()
  {
    return this.reserveChange;
  }
  
  public void setReserveChange(Double reserveChange)
  {
    this.reserveChange = reserveChange;
  }
  
  @ClassDiscription(name="结算价")
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(Double totalMoney)
  {
    this.totalMoney = totalMoney;
  }
  
  public Double getTotalAmount()
  {
    return this.totalAmount;
  }
  
  public void setTotalAmount(Double totalAmount)
  {
    this.totalAmount = totalAmount;
  }
  
  public Double getSpread()
  {
    return this.spread;
  }
  
  public void setSpread(Double spread)
  {
    this.spread = spread;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String getId()
  {
    return this.commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public void setPrimary(String primary) {}
}
