package gnnt.MEBS.timebargain.server.model;

public class DelayPrice
{
  private Double price;
  private long priceTime;
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPriceTime(long priceTime)
  {
    this.priceTime = priceTime;
  }
  
  public long getPriceTime()
  {
    return this.priceTime;
  }
}
