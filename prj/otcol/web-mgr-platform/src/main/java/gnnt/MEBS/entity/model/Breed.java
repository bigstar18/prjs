package gnnt.MEBS.entity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.entity.model.innerObejct.KeyValue;
import java.util.List;

public class Breed
  extends Clone
{
  private String breedId;
  private String breedName;
  private int status;
  private List<KeyValue> parametersList;
  private List<BreedQuality> qualityList;
  
  public List getQualityList()
  {
    return this.qualityList;
  }
  
  public void addQualityList(List<BreedQuality> paramList)
  {
    this.qualityList = paramList;
  }
  
  public List getParametersList()
  {
    return this.parametersList;
  }
  
  public void addParametersList(List<KeyValue> paramList)
  {
    this.parametersList = paramList;
  }
  
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
