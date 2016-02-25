package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface ApplyGageManager
{
  public abstract ApplyGage getApplyGage(long paramLong);
  
  public abstract List listApplyGage(QueryConditions paramQueryConditions);
  
  public abstract int saveApplyGage(ApplyGage paramApplyGage);
  
  public abstract int gageProcessorAudit(ApplyGage paramApplyGage, String paramString);
  
  public abstract int gageProcessorAuditPass(ApplyGage paramApplyGage);
  
  public abstract int updateApplyGage(ApplyGage paramApplyGage, int paramInt);
}
