package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.GageWarehouseDAO;
import gnnt.MEBS.timebargain.manage.service.GageWarehouseManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class GageWarehouseManagerImpl
  extends BaseManager
  implements GageWarehouseManager
{
  private GageWarehouseDAO gageWarehouseDAO;
  
  public void setGageWarehouseDAO(GageWarehouseDAO paramGageWarehouseDAO)
  {
    this.gageWarehouseDAO = paramGageWarehouseDAO;
  }
  
  public List gageWarehouseList(QueryConditions paramQueryConditions)
  {
    return this.gageWarehouseDAO.gageWarehouseList(paramQueryConditions);
  }
  
  public long revocation(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str = this.gageWarehouseDAO.getRegStockWeight(paramString1);
    long l1 = 0L;
    long l2 = Long.parseLong(paramString4);
    long l3 = this.gageWarehouseDAO.getUseNum(paramString2, paramString3);
    if (l3 >= l2)
    {
      l1 = this.gageWarehouseDAO.FreezeAndThaw(paramString1, str, "0", paramString5);
      if (l1 == 1L)
      {
        this.gageWarehouseDAO.deleteGageBill(paramString1);
        this.gageWarehouseDAO.updateValidGageBill(paramString2, paramString3, paramString4);
      }
    }
    else
    {
      l1 = -1L;
    }
    return l1;
  }
  
  public long revocationNew(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    long l1 = 0L;
    long l2 = Long.parseLong(paramString4);
    long l3 = this.gageWarehouseDAO.getUseNum(paramString2, paramString3);
    if (l3 >= l2)
    {
      String str1 = this.gageWarehouseDAO.getIsSettleFlagByModuleID();
      if (str1.equals("Y"))
      {
        String str2 = this.gageWarehouseDAO.getRegStockWeight(paramString1);
        l1 = this.gageWarehouseDAO.FreezeAndThaw(paramString1, str2, "0", paramString5);
      }
      else if (str1.equals("N"))
      {
        l1 = 1L;
      }
      if (l1 == 1L)
      {
        this.gageWarehouseDAO.deleteGageBill(paramString1);
        this.gageWarehouseDAO.updateValidGageBill(paramString2, paramString3, paramString4);
      }
    }
    else
    {
      l1 = -1L;
    }
    return l1;
  }
  
  public long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    return this.gageWarehouseDAO.addGageWarehouse(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
  }
  
  public long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    return this.gageWarehouseDAO.addGageWarehouse(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7);
  }
  
  public List validGageBillList(QueryConditions paramQueryConditions)
  {
    return this.gageWarehouseDAO.validGageBillList(paramQueryConditions);
  }
  
  public List validateFirmList(QueryConditions paramQueryConditions)
  {
    return this.gageWarehouseDAO.validateFirmList(paramQueryConditions);
  }
}
