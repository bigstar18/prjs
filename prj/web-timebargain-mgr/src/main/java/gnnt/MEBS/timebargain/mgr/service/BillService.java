package gnnt.MEBS.timebargain.mgr.service;

import java.util.List;
import java.util.Map;

public abstract interface BillService
{
  public abstract Map<String, List<?>> getBillList(String paramString);

  public abstract int addBill(String[] paramArrayOfString, String paramString1, String paramString2)
    throws Exception;

  public abstract List<?> getFirmList();

  public abstract Long billCancel(String paramString1, String paramString2, Long paramLong1, String paramString3, Long paramLong2, Long paramLong3)
    throws Exception;
}