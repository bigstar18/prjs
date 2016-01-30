package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.timebargain.mgr.model.settle.ApplyAheadSettle;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.HoldFrozen;
import java.util.List;
import java.util.Map;

public abstract interface AheadSettleDao
{
  public abstract List<Map<Object, Object>> queryBySql(String paramString);

  public abstract List<?> getCustomerList();

  public abstract void addApplyAheadSettle(ApplyAheadSettle paramApplyAheadSettle);

  public abstract ApplyAheadSettle getApplyAheadSettle(ApplyAheadSettle paramApplyAheadSettle);

  public abstract void updateApplyAheadSettle(ApplyAheadSettle paramApplyAheadSettle);

  public abstract void addBillFrozen(BillFrozen paramBillFrozen);

  public abstract void addHoldFrozen(HoldFrozen paramHoldFrozen);

  public abstract Object executeProcedure(String paramString, Object[] paramArrayOfObject)
    throws Exception;

  public abstract void executeUpdateBySql(String paramString);
}