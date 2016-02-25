package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface GageWarehouseDAO
  extends DAO
{
  public abstract List gageWarehouseList(QueryConditions paramQueryConditions);
  
  public abstract long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  
  public abstract long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);
  
  public abstract List validGageBillList(QueryConditions paramQueryConditions);
  
  public abstract String getRegStockWeight(String paramString);
  
  public abstract long FreezeAndThaw(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void deleteGageBill(String paramString);
  
  public abstract long getUseNum(String paramString1, String paramString2);
  
  public abstract void updateValidGageBill(String paramString1, String paramString2, String paramString3);
  
  public abstract List validateFirmList(QueryConditions paramQueryConditions);
  
  public abstract String getIsSettleFlagByModuleID();
}
