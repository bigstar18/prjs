package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import java.util.Date;
import java.util.List;

public abstract interface DirectFirmBreedManager
{
  public abstract List<DirectFirmBreed> directFirmBreedList(String paramString);
  
  public abstract List directFirmBreedGet(QueryConditions paramQueryConditions, String paramString);
  
  public abstract List getFirmList(QueryConditions paramQueryConditions);
  
  public abstract void directFirmBreedAdd(DirectFirmBreed paramDirectFirmBreed);
  
  public abstract void directFirmBreedMod(DirectFirmBreed paramDirectFirmBreed1, DirectFirmBreed paramDirectFirmBreed2);
  
  public abstract void directFirmBreedDelete(DirectFirmBreed paramDirectFirmBreed);
  
  public abstract List directFirmBreedHisList(QueryConditions paramQueryConditions);
  
  public abstract void directFirmBreedHisDelete(DirectFirmBreed paramDirectFirmBreed, Date paramDate);
}
