package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class Quality
  extends Clone
{
  private long qualityId;
  private long breedId;
  private String qualityName;
  private int status;
  
  public long getQualityId()
  {
    return this.qualityId;
  }
  
  public void setQualityId(long paramLong)
  {
    this.qualityId = paramLong;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getQualityName()
  {
    return this.qualityName;
  }
  
  public void setQualityName(String paramString)
  {
    this.qualityName = paramString;
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
