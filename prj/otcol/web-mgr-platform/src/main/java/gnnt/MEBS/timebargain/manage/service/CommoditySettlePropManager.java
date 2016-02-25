package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.model.CommoditySettleProp;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface CommoditySettlePropManager
{
  public abstract List commoditySettlePropList(QueryConditions paramQueryConditions);
  
  public abstract void commoditySettlePropDelete(CommoditySettleProp paramCommoditySettleProp);
  
  public abstract int commoditySettlePropAdd(String[] paramArrayOfString1, String[] paramArrayOfString2);
}
