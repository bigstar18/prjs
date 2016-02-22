package gnnt.MEBS.trade.model;

public class OrdersForm
{
  private String traderID;
  private Short BS_Flag;
  private Short OrderType;
  private Long Quantity;
  private Double Price;
  private String commodityID;
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }
  
  public void setBS_Flag(Short bS_Flag)
  {
    this.BS_Flag = bS_Flag;
  }
  
  public Short getOrderType()
  {
    return this.OrderType;
  }
  
  public void setOrderType(Short orderType)
  {
    this.OrderType = orderType;
  }
  
  public Long getQuantity()
  {
    return this.Quantity;
  }
  
  public void setQuantity(Long quantity)
  {
    this.Quantity = quantity;
  }
  
  public Double getPrice()
  {
    return this.Price;
  }
  
  public void setPrice(Double price)
  {
    this.Price = price;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String toString()
  {
    return "traderID:" + this.traderID + "  BS_Flag:" + this.BS_Flag + "  OrderType:" + this.OrderType + "  Quantity:" + this.Quantity + "  Price:" + this.Price + "  commodityID:" + this.commodityID;
  }
}
