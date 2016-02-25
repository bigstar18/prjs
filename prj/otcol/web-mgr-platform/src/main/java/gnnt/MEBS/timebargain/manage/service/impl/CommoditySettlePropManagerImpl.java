package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.CommoditySettlePropDAO;
import gnnt.MEBS.timebargain.manage.model.CommoditySettleProp;
import gnnt.MEBS.timebargain.manage.service.CommoditySettlePropManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class CommoditySettlePropManagerImpl
  extends BaseManager
  implements CommoditySettlePropManager
{
  private CommoditySettlePropDAO commoditySettlePropDAO;
  
  public CommoditySettlePropDAO getCommoditySettlePropDAO()
  {
    return this.commoditySettlePropDAO;
  }
  
  public void setCommoditySettlePropDAO(CommoditySettlePropDAO paramCommoditySettlePropDAO)
  {
    this.commoditySettlePropDAO = paramCommoditySettlePropDAO;
  }
  
  public List commoditySettlePropList(QueryConditions paramQueryConditions)
  {
    return this.commoditySettlePropDAO.commoditySettlepropList(paramQueryConditions);
  }
  
  public void commoditySettlePropDelete(CommoditySettleProp paramCommoditySettleProp)
  {
    this.commoditySettlePropDAO.commoditySettlepropDelete(paramCommoditySettleProp);
  }
  
  public int commoditySettlePropAdd(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    return this.commoditySettlePropDAO.commoditySettlePropAdd(paramArrayOfString1, paramArrayOfString2);
  }
}
