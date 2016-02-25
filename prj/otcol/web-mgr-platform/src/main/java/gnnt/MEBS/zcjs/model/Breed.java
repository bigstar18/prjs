package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class Breed
  extends Clone
{
  private long breedId;
  private String breedName;
  private int deliveryMinDay;
  private int deliveryMaxDay;
  private String tradeUnit;
  private double unitVolume;
  private long sortId;
  private double deliveryRatio;
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public double getDeliveryRatio()
  {
    return this.deliveryRatio;
  }
  
  public void setDeliveryRatio(double paramDouble)
  {
    this.deliveryRatio = paramDouble;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getBreedName()
  {
    return this.breedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.breedName = paramString;
  }
  
  public int getDeliveryMinDay()
  {
    return this.deliveryMinDay;
  }
  
  public void setDeliveryMinDay(int paramInt)
  {
    this.deliveryMinDay = paramInt;
  }
  
  public int getDeliveryMaxDay()
  {
    return this.deliveryMaxDay;
  }
  
  public void setDeliveryMaxDay(int paramInt)
  {
    this.deliveryMaxDay = paramInt;
  }
  
  public String getTradeUnit()
  {
    return this.tradeUnit;
  }
  
  public void setTradeUnit(String paramString)
  {
    this.tradeUnit = paramString;
  }
  
  public double getUnitVolume()
  {
    return this.unitVolume;
  }
  
  public void setUnitVolume(double paramDouble)
  {
    this.unitVolume = paramDouble;
  }
  
  public long getSortId()
  {
    return this.sortId;
  }
  
  public void setSortId(long paramLong)
  {
    this.sortId = paramLong;
  }
}
