package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;

public class DirectFirmBreed
  implements Serializable
{
  private String firmId;
  private int breedId;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public int getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(int paramInt)
  {
    this.breedId = paramInt;
  }
}
