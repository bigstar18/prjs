package gnnt.MEBS.delivery.model.entity;

import gnnt.MEBS.base.model.Clone;

public class BreedParameters
  extends Clone
{
  private String breedId;
  private String propertyKey;
  private String name;
  private int no;
  
  public String getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(String paramString)
  {
    this.breedId = paramString;
  }
  
  public String getPropertyKey()
  {
    return this.propertyKey;
  }
  
  public void setPropertyKey(String paramString)
  {
    this.propertyKey = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public int getNo()
  {
    return this.no;
  }
  
  public void setNo(int paramInt)
  {
    this.no = paramInt;
  }
}
