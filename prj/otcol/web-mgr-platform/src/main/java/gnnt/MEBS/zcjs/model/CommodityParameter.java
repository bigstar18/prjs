package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class CommodityParameter
  extends Clone
{
  private long parameterId;
  private long breedId;
  private long commodityPropertyId;
  private String parameterName;
  private String parameterDescription;
  private int parameterStatus;
  
  public long getParameterId()
  {
    return this.parameterId;
  }
  
  public void setParameterId(long paramLong)
  {
    this.parameterId = paramLong;
  }
  
  public long getCommodityPropertyId()
  {
    return this.commodityPropertyId;
  }
  
  public void setCommodityPropertyId(long paramLong)
  {
    this.commodityPropertyId = paramLong;
  }
  
  public String getParameterName()
  {
    return this.parameterName;
  }
  
  public void setParameterName(String paramString)
  {
    this.parameterName = paramString;
  }
  
  public String getParameterDescription()
  {
    return this.parameterDescription;
  }
  
  public void setParameterDescription(String paramString)
  {
    this.parameterDescription = paramString;
  }
  
  public int getParameterStatus()
  {
    return this.parameterStatus;
  }
  
  public void setParameterStatus(int paramInt)
  {
    this.parameterStatus = paramInt;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
}
