package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class Delivery
  extends Clone
{
  private long deliveryId;
  private long tradeNo;
  private long breedId;
  private double quantity;
  private double price;
  private String firmId_s;
  private double sellMargin;
  private String firmId_b;
  private double buyMargin;
  private int type;
  private String regStockId;
  private int status;
  
  public long getDeliveryId()
  {
    return this.deliveryId;
  }
  
  public void setDeliveryId(long paramLong)
  {
    this.deliveryId = paramLong;
  }
  
  public long getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(long paramLong)
  {
    this.tradeNo = paramLong;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public String getFirmId_s()
  {
    return this.firmId_s;
  }
  
  public void setFirmId_s(String paramString)
  {
    this.firmId_s = paramString;
  }
  
  public double getSellMargin()
  {
    return this.sellMargin;
  }
  
  public void setSellMargin(double paramDouble)
  {
    this.sellMargin = paramDouble;
  }
  
  public String getFirmId_b()
  {
    return this.firmId_b;
  }
  
  public void setFirmId_b(String paramString)
  {
    this.firmId_b = paramString;
  }
  
  public double getBuyMargin()
  {
    return this.buyMargin;
  }
  
  public void setBuyMargin(double paramDouble)
  {
    this.buyMargin = paramDouble;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}
