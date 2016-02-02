package gnnt.MEBS.timebargain.server.model.quotation;

import java.util.Date;

public class DayDataVO
{
  private String code;
  private Date tradeDate;
  private Date time = new Date();
  private float highPrice;
  private float lowPrice;
  private float openPrice;
  private float closePrice;
  private long totalAmount;
  private double totalMoney;
  private float balancePrice;
  private long reserveCount;
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date paramDate)
  {
    this.tradeDate = paramDate;
  }
  
  public Date getTime()
  {
    return this.time;
  }
  
  public void setTime(Date paramDate)
  {
    this.time = paramDate;
  }
  
  public float getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setHighPrice(float paramFloat)
  {
    this.highPrice = paramFloat;
  }
  
  public float getLowPrice()
  {
    return this.lowPrice;
  }
  
  public void setLowPrice(float paramFloat)
  {
    this.lowPrice = paramFloat;
  }
  
  public float getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(float paramFloat)
  {
    this.openPrice = paramFloat;
  }
  
  public float getClosePrice()
  {
    return this.closePrice;
  }
  
  public void setClosePrice(float paramFloat)
  {
    this.closePrice = paramFloat;
  }
  
  public long getTotalAmount()
  {
    return this.totalAmount;
  }
  
  public void setTotalAmount(long paramLong)
  {
    this.totalAmount = paramLong;
  }
  
  public double getTotalMoney()
  {
    return this.totalMoney;
  }
  
  public void setTotalMoney(double paramDouble)
  {
    this.totalMoney = paramDouble;
  }
  
  public float getBalancePrice()
  {
    return this.balancePrice;
  }
  
  public void setBalancePrice(float paramFloat)
  {
    this.balancePrice = paramFloat;
  }
  
  public long getReserveCount()
  {
    return this.reserveCount;
  }
  
  public void setReserveCount(long paramLong)
  {
    this.reserveCount = paramLong;
  }
  
  public void setDefult(float paramFloat, long paramLong)
  {
    this.balancePrice = paramFloat;
    this.openPrice = paramFloat;
    this.highPrice = paramFloat;
    this.lowPrice = paramFloat;
    this.closePrice = paramFloat;
    this.reserveCount = paramLong;
    this.totalAmount = 0L;
    this.totalMoney = 0.0D;
  }
  
  public String toString()
  {
    return "Code:" + this.code + "," + "tradeDate:" + this.tradeDate + "," + "highPrice:" + this.highPrice + "," + "lowPrice:" + this.lowPrice + "," + "openPrice:" + this.openPrice + "," + "closePrice:" + this.closePrice + "," + "totalamount:" + this.totalAmount + "," + "totalamount:" + this.totalAmount + "," + "totalmoney:" + this.totalMoney + "," + "reservecount:" + this.reserveCount;
  }
}
