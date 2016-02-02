package gnnt.MEBS.timebargain.plugin.condition.model;

import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Quotation
{
  private Date updateTime;
  private String commodityID;
  private Double yesterBalancePrice = Double.valueOf(0.0D);
  private Double closePrice = Double.valueOf(0.0D);
  private Double openPrice = Double.valueOf(0.0D);
  private Double highPrice = Double.valueOf(0.0D);
  private Double lowPrice = Double.valueOf(0.0D);
  private Double curPrice = Double.valueOf(0.0D);
  private Long curAmount = Long.valueOf(0L);
  private Long openAmount = Long.valueOf(0L);
  private Long buyOpenAmount = Long.valueOf(0L);
  private Long sellOpenAmount = Long.valueOf(0L);
  private Long closeAmount = Long.valueOf(0L);
  private Long buyCloseAmount = Long.valueOf(0L);
  private Long sellCloseAmount = Long.valueOf(0L);
  private Long reserveCount = Long.valueOf(0L);
  private Double price = Double.valueOf(0.0D);
  private Double totalMoney = Double.valueOf(0.0D);
  private Long totalAmount = Long.valueOf(0L);
  private Long outAmount = Long.valueOf(0L);
  private Long inAmount = Long.valueOf(0L);
  private Timestamp createTime = null;
  private double buy1;
  private double sell1;
  private long buyqty1;
  private long sellqty1;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Date getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(Date paramDate)
  {
    this.updateTime = paramDate;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public Double getYesterBalancePrice()
  {
    return this.yesterBalancePrice;
  }
  
  public void setYesterBalancePrice(Double paramDouble)
  {
    this.yesterBalancePrice = paramDouble;
  }
  
  public Double getClosePrice()
  {
    return this.closePrice;
  }
  
  public void setClosePrice(Double paramDouble)
  {
    this.closePrice = paramDouble;
  }
  
  public Double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(Double paramDouble)
  {
    this.openPrice = paramDouble;
  }
  
  public Double getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setHighPrice(Double paramDouble)
  {
    this.highPrice = paramDouble;
  }
  
  public Double getLowPrice()
  {
    return this.lowPrice;
  }
  
  public void setLowPrice(Double paramDouble)
  {
    this.lowPrice = paramDouble;
  }
  
  public Double getCurPrice()
  {
    return this.curPrice;
  }
  
  public void setCurPrice(Double paramDouble)
  {
    this.curPrice = paramDouble;
  }
  
  public Long getCurAmount()
  {
    return this.curAmount;
  }
  
  public void setCurAmount(Long paramLong)
  {
    this.curAmount = paramLong;
  }
  
  public Long getOpenAmount()
  {
    return this.openAmount;
  }
  
  public void setOpenAmount(Long paramLong)
  {
    this.openAmount = paramLong;
  }
  
  public Long getBuyOpenAmount()
  {
    return this.buyOpenAmount;
  }
  
  public void setBuyOpenAmount(Long paramLong)
  {
    this.buyOpenAmount = paramLong;
  }
  
  public Long getSellOpenAmount()
  {
    return this.sellOpenAmount;
  }
  
  public void setSellOpenAmount(Long paramLong)
  {
    this.sellOpenAmount = paramLong;
  }
  
  public Long getCloseAmount()
  {
    return this.closeAmount;
  }
  
  public void setCloseAmount(Long paramLong)
  {
    this.closeAmount = paramLong;
  }
  
  public Long getBuyCloseAmount()
  {
    return this.buyCloseAmount;
  }
  
  public void setBuyCloseAmount(Long paramLong)
  {
    this.buyCloseAmount = paramLong;
  }
  
  public Long getSellCloseAmount()
  {
    return this.sellCloseAmount;
  }
  
  public void setSellCloseAmount(Long paramLong)
  {
    this.sellCloseAmount = paramLong;
  }
  
  public Long getReserveCount()
  {
    return this.reserveCount;
  }
  
  public void setReserveCount(Long paramLong)
  {
    this.reserveCount = paramLong;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public Double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(Double paramDouble)
  {
    this.totalMoney = paramDouble;
  }
  
  public Long getTotalAmount()
  {
    return this.totalAmount;
  }
  
  public void setTotalAmount(Long paramLong)
  {
    this.totalAmount = paramLong;
  }
  
  public Long getOutAmount()
  {
    return this.outAmount;
  }
  
  public void setOutAmount(Long paramLong)
  {
    this.outAmount = paramLong;
  }
  
  public Long getInAmount()
  {
    return this.inAmount;
  }
  
  public void setInAmount(Long paramLong)
  {
    this.inAmount = paramLong;
  }
  
  public Timestamp getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Timestamp paramTimestamp)
  {
    this.createTime = paramTimestamp;
  }
  
  public double getBuy1()
  {
    return this.buy1;
  }
  
  public void setBuy1(double paramDouble)
  {
    this.buy1 = paramDouble;
  }
  
  public double getSell1()
  {
    return this.sell1;
  }
  
  public void setSell1(double paramDouble)
  {
    this.sell1 = paramDouble;
  }
  
  public long getBuyqty1()
  {
    return this.buyqty1;
  }
  
  public void setBuyqty1(long paramLong)
  {
    this.buyqty1 = paramLong;
  }
  
  public long getSellqty1()
  {
    return this.sellqty1;
  }
  
  public void setSellqty1(long paramLong)
  {
    this.sellqty1 = paramLong;
  }
  
  public int hashCode()
  {
    int i = 1;
    long l = Double.doubleToLongBits(this.buy1);
    i = 31 * i + (int)(l ^ l >>> 32);
    i = 31 * i + (this.price == null ? 0 : this.price.hashCode());
    l = Double.doubleToLongBits(this.sell1);
    i = 31 * i + (int)(l ^ l >>> 32);
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    Quotation localQuotation = (Quotation)paramObject;
    if (Double.doubleToLongBits(this.buy1) != Double.doubleToLongBits(localQuotation.buy1)) {
      return false;
    }
    if (this.price == null)
    {
      if (localQuotation.price != null) {
        return false;
      }
    }
    else if (!this.price.equals(localQuotation.price)) {
      return false;
    }
    return Double.doubleToLongBits(this.sell1) == Double.doubleToLongBits(localQuotation.sell1);
  }
}
