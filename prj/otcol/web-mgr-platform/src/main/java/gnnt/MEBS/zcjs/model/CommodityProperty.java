package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class CommodityProperty
  extends Clone
{
  private long propertyId;
  private String propertyName;
  private long breedId;
  private long parentId;
  private String propertyDescription;
  private String key;
  private int status;
  
  public long getPropertyId()
  {
    return this.propertyId;
  }
  
  public void setPropertyId(long paramLong)
  {
    this.propertyId = paramLong;
  }
  
  public String getPropertyName()
  {
    return this.propertyName;
  }
  
  public void setPropertyName(String paramString)
  {
    this.propertyName = paramString;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public long getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(long paramLong)
  {
    this.parentId = paramLong;
  }
  
  public String getPropertyDescription()
  {
    return this.propertyDescription;
  }
  
  public void setPropertyDescription(String paramString)
  {
    this.propertyDescription = paramString;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
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
