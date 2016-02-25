package gnnt.MEBS.common.manage.service.impl;

import gnnt.MEBS.common.manage.dao.ApplyDAO;
import gnnt.MEBS.common.manage.model.Apply;
import gnnt.MEBS.common.manage.service.ApplyManage;
import java.util.List;

public class ApplyManageImpl
  implements ApplyManage
{
  private ApplyDAO dao;
  
  public void setApplyDAO(ApplyDAO paramApplyDAO)
  {
    this.dao = paramApplyDAO;
  }
  
  public void insertApply(Apply paramApply)
  {
    this.dao.insertApply(paramApply);
  }
  
  public Apply getApplyById(Apply paramApply, boolean paramBoolean)
  {
    paramApply = this.dao.getApplyById(paramApply, paramBoolean);
    return paramApply;
  }
  
  public List getApplys(Apply paramApply)
  {
    return this.dao.getApplys(paramApply);
  }
}
