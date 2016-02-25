package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import java.util.List;

public abstract interface DirectFirmBreedDAO
  extends DAO
{
  public abstract List directFirmBreedList();
  
  public abstract DirectFirmBreed directFirmView(DirectFirmBreed paramDirectFirmBreed);
  
  public abstract List getFirmList(QueryConditions paramQueryConditions);
  
  public abstract List directFirmBreedGet(QueryConditions paramQueryConditions);
  
  public abstract void directFirmBreedSave(DirectFirmBreed paramDirectFirmBreed);
  
  public abstract void directFirmBreedMod(DirectFirmBreed paramDirectFirmBreed1, DirectFirmBreed paramDirectFirmBreed2);
  
  public abstract void directFirmBreedDelete(DirectFirmBreed paramDirectFirmBreed);
}
