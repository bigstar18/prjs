package gnnt.MEBS.transformhq.server.model;

public class TopPrice
{
  private Double highPrice;
  private Double lowPrice;
  
  public TopPrice(Double price)
  {
    this.highPrice = price;
    this.lowPrice = price;
  }
  
  public void setHighPrice(Double highPrice)
  {
    this.highPrice = highPrice;
  }
  
  public Double getHighPrice()
  {
    return this.highPrice;
  }
  
  public void setLowPrice(Double lowPrice)
  {
    this.lowPrice = lowPrice;
  }
  
  public Double getLowPrice()
  {
    return this.lowPrice;
  }
}
