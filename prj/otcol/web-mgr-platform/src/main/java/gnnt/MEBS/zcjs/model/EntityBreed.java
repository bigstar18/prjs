package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class EntityBreed
  extends Clone
{
  private String breedId;
  private String breedName;
  private int status;
  
  public String getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(String paramString)
  {
    this.breedId = paramString;
  }
  
  public String getBreedName()
  {
    return this.breedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.breedName = paramString;
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
