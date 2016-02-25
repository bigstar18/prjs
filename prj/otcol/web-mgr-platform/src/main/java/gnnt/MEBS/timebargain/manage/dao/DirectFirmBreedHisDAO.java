package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import java.util.Date;
import java.util.List;

public abstract interface DirectFirmBreedHisDAO
  extends DAO
{
  public abstract List directFirmBreedHisList(QueryConditions paramQueryConditions);
  
  public abstract void directFirmBreedHisInsert(DirectFirmBreed paramDirectFirmBreed);
  
  public abstract void dirctFirmBreedHisDelete(DirectFirmBreed paramDirectFirmBreed, Date paramDate);
}
