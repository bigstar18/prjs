package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface BreedDAO
  extends DAO
{
  public abstract Breed getBreedById(String paramString);
  
  public abstract List getBreeds(QueryConditions paramQueryConditions);
  
  public abstract void insertBreed(Breed paramBreed);
  
  public abstract void updateBreed(Breed paramBreed);
  
  public abstract void deleteBreedById(String paramString);
  
  public abstract void insertBreed1(Breed paramBreed);
  
  public abstract void updateBreed1(Breed paramBreed);
  
  public abstract String getIsSettleFlagByModuleID(String paramString);
  
  public abstract void insertBreedAndSettle(Breed paramBreed);
  
  public abstract void updateBreedAndSettle(Breed paramBreed);
}
