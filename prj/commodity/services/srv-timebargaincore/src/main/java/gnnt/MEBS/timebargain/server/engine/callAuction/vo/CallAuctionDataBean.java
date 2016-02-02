package gnnt.MEBS.timebargain.server.engine.callAuction.vo;

public class CallAuctionDataBean
{
  private String commodityId;
  private double curPrice;
  private long buyQuantity;
  private long sellQuantity;
  private long quantity;
  private long buyQtyExceptPrice;
  private long sellQtyExceptPrice;
  private long quantityExceptPrice;
  private long minRemainQuantity;
  private double difPrice;
  
  public long getQuantityExceptPrice()
  {
    return this.quantityExceptPrice;
  }
  
  public void setQuantityExceptPrice(long paramLong)
  {
    this.quantityExceptPrice = paramLong;
  }
  
  public long getBuyQtyExceptPrice()
  {
    return this.buyQtyExceptPrice;
  }
  
  public void setBuyQtyExceptPrice(long paramLong)
  {
    this.buyQtyExceptPrice = paramLong;
  }
  
  public long getSellQtyExceptPrice()
  {
    return this.sellQtyExceptPrice;
  }
  
  public void setSellQtyExceptPrice(long paramLong)
  {
    this.sellQtyExceptPrice = paramLong;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public long getBuyQuantity()
  {
    return this.buyQuantity;
  }
  
  public void setBuyQuantity(long paramLong)
  {
    this.buyQuantity = paramLong;
  }
  
  public double getCurPrice()
  {
    return this.curPrice;
  }
  
  public void setCurPrice(double paramDouble)
  {
    this.curPrice = paramDouble;
  }
  
  public double getDifPrice()
  {
    return this.difPrice;
  }
  
  public void setDifPrice(double paramDouble)
  {
    this.difPrice = paramDouble;
  }
  
  public long getMinRemainQuantity()
  {
    return this.minRemainQuantity;
  }
  
  public void setMinRemainQuantity(long paramLong)
  {
    this.minRemainQuantity = paramLong;
  }
  
  public long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(long paramLong)
  {
    this.quantity = paramLong;
  }
  
  public long getSellQuantity()
  {
    return this.sellQuantity;
  }
  
  public void setSellQuantity(long paramLong)
  {
    this.sellQuantity = paramLong;
  }
  
  public String toString()
  {
    return "CallAuctionDataBean[商品代码:" + getCommodityId() + "," + "价格:" + getCurPrice() + "," + "买量:" + getBuyQuantity() + "," + "卖量:" + getSellQuantity() + "," + "成交量:" + getQuantity() + "," + "高于本价纯买量:" + getBuyQtyExceptPrice() + "," + "低于本价纯卖量:" + getSellQtyExceptPrice() + "," + "纯成交量:" + getQuantityExceptPrice() + "," + "剩余量:" + getMinRemainQuantity() + "," + "偏移价:" + getDifPrice() + "]";
  }
}
