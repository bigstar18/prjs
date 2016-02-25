package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface ApplyGageDAO
  extends DAO
{
  public abstract ApplyGage getApplyGage(long paramLong);
  
  public abstract long getApplyGageId();
  
  public abstract List listApplyGage(QueryConditions paramQueryConditions);
  
  public abstract void updateApplyGage(ApplyGage paramApplyGage)
    throws Exception;
  
  public abstract void saveApplyGage(ApplyGage paramApplyGage)
    throws Exception;
  
  public abstract int auditApplyGage(ApplyGage paramApplyGage);
  
  public abstract int auditCancleGage(ApplyGage paramApplyGage);
  
  public abstract List getResultListBySQL(ApplyGage paramApplyGage);
}
