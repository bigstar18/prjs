package gnnt.MEBS.delivery.model.entity;

import gnnt.MEBS.base.model.Clone;

public class BreedProperty
  extends Clone
{
  private String propertyKey;
  private String propertyName;
  
  public String getPropertyKey()
  {
    return this.propertyKey;
  }
  
  public void setPropertyKey(String paramString)
  {
    this.propertyKey = paramString;
  }
  
  public String getPropertyName()
  {
    return this.propertyName;
  }
  
  public void setPropertyName(String paramString)
  {
    this.propertyName = paramString;
  }
}
