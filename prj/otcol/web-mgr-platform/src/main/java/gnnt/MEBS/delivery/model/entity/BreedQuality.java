package gnnt.MEBS.delivery.model.entity;

import gnnt.MEBS.base.model.Clone;

public class BreedQuality
  extends Clone
{
  private String breedId;
  private String qualityName;
  private int no;
  
  public String getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(String paramString)
  {
    this.breedId = paramString;
  }
  
  public String getQualityName()
  {
    return this.qualityName;
  }
  
  public void setQualityName(String paramString)
  {
    this.qualityName = paramString;
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
