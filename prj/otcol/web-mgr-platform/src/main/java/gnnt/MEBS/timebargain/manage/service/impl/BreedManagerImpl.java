package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.BreedDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.service.BreedManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class BreedManagerImpl
  extends BaseManager
  implements BreedManager
{
  private BreedDAO dao;
  
  public void setBreedDAO(BreedDAO paramBreedDAO)
  {
    this.dao = paramBreedDAO;
  }
  
  public Breed getBreedById(String paramString)
  {
    return this.dao.getBreedById(paramString);
  }
  
  public List getBreeds(QueryConditions paramQueryConditions)
  {
    return this.dao.getBreeds(paramQueryConditions);
  }
  
  public void insertBreed(Breed paramBreed)
  {
    this.dao.insertBreed1(paramBreed);
  }
  
  public void updateBreed(Breed paramBreed)
  {
    this.dao.updateBreed1(paramBreed);
  }
  
  public void deleteBreedById(String paramString)
  {
    this.dao.deleteBreedById(paramString);
  }
  
  public String getIsSettleFlagByModuleID(String paramString)
  {
    return this.dao.getIsSettleFlagByModuleID(paramString);
  }
  
  public void insertBreedAndSettle(Breed paramBreed)
  {
    this.dao.insertBreedAndSettle(paramBreed);
  }
  
  public void updateBreedAndSettle(Breed paramBreed)
  {
    this.dao.updateBreedAndSettle(paramBreed);
  }
}
