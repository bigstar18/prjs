package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface GageWarehouseManager
{
  public abstract List gageWarehouseList(QueryConditions paramQueryConditions);
  
  public abstract long revocation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract long revocationNew(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  
  public abstract long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);
  
  public abstract List validGageBillList(QueryConditions paramQueryConditions);
  
  public abstract List validateFirmList(QueryConditions paramQueryConditions);
}
