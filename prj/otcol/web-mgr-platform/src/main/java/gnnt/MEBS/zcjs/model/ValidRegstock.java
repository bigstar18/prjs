package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class ValidRegstock
  extends Clone
{
  private String regstockId;
  private String firmId;
  private long breedId;
  private String commodityProperties;
  private String quality;
  private double quantity;
  private int status;
  private int type;
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getRegstockId()
  {
    return this.regstockId;
  }
  
  public void setRegstockId(String paramString)
  {
    this.regstockId = paramString;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getCommodityProperties()
  {
    return this.commodityProperties;
  }
  
  public void setCommodityProperties(String paramString)
  {
    this.commodityProperties = paramString;
  }
  
  public String getQuality()
  {
    return this.quality;
  }
  
  public void setQuality(String paramString)
  {
    this.quality = paramString;
  }
  
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
}
