package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.BreedDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface BreedManager
{
  public abstract void setBreedDAO(BreedDAO paramBreedDAO);
  
  public abstract Breed getBreedById(String paramString);
  
  public abstract List getBreeds(QueryConditions paramQueryConditions);
  
  public abstract void insertBreed(Breed paramBreed);
  
  public abstract void updateBreed(Breed paramBreed);
  
  public abstract void deleteBreedById(String paramString);
  
  public abstract String getIsSettleFlagByModuleID(String paramString);
  
  public abstract void insertBreedAndSettle(Breed paramBreed);
  
  public abstract void updateBreedAndSettle(Breed paramBreed);
}
