package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.applyGage.ApplyGage;

public abstract interface ApplyGageService
{
  public abstract int addApplyGage(ApplyGage paramApplyGage)
    throws Exception;

  public abstract int failApplyGage(ApplyGage paramApplyGage)
    throws Exception;

  public abstract int auditApplyGage(ApplyGage paramApplyGage)
    throws Exception;

  public abstract int cancelApplyGage(ApplyGage paramApplyGage)
    throws Exception;
}