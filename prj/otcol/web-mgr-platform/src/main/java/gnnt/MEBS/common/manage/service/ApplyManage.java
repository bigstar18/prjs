package gnnt.MEBS.common.manage.service;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.model.Apply;
import java.util.List;

public abstract interface ApplyManage
{
  public abstract void setApplyDAO(ApplyDAO paramApplyDAO);
  
  public abstract void insertApply(Apply paramApply);
  
  public abstract Apply getApplyById(Apply paramApply, boolean paramBoolean);
  
  public abstract List getApplys(Apply paramApply);
}
