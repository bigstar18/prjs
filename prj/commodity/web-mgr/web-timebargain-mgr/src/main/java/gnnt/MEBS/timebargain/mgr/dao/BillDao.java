package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.GageBill;
import java.util.List;
import java.util.Map;

public abstract interface BillDao
{
  public abstract List<Map<Object, Object>> queryBySql(String paramString);

  public abstract String addGageBill(GageBill paramGageBill);

  public abstract int existenceOfValidGageBill(String paramString1, String paramString2);

  public abstract void executeUpdateBySql(String paramString);

  public abstract void addBillFrozen(BillFrozen paramBillFrozen);

  public abstract List<?> getFirmList();

  public abstract void deleteBillFrozen(BillFrozen paramBillFrozen);

  public abstract GageBill getGageBill(Long paramLong);

  public abstract void deleteGageBill(GageBill paramGageBill);

  public abstract void updateGageBill(GageBill paramGageBill);

  public abstract void addHisGageBill(GageBill paramGageBill);
}