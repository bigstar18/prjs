package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.settle.ApplyAheadSettle;
import java.util.List;

public abstract interface AheadSettleService
{
  public abstract List<?> getCustomerList();

  public abstract int addApplyAheadSettle(String[] paramArrayOfString, ApplyAheadSettle paramApplyAheadSettle, String paramString1, String paramString2)
    throws Exception;

  public abstract int auditApplyAheadSettlePass(ApplyAheadSettle paramApplyAheadSettle)
    throws Exception;

  public abstract int auditApplyAheadSettleFail(ApplyAheadSettle paramApplyAheadSettle)
    throws Exception;
}