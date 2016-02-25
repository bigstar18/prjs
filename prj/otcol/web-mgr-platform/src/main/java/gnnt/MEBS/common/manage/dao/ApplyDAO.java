package gnnt.MEBS.common.manage.dao;

import gnnt.MEBS.common.manage.model.Apply;
import java.util.List;

public abstract interface ApplyDAO
{
  public abstract void insertApply(Apply paramApply);
  
  public abstract Apply getApplyById(Apply paramApply, boolean paramBoolean);
  
  public abstract List getApplys(Apply paramApply);
  
  public abstract void updateApply(Apply paramApply);
}
