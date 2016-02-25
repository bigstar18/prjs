package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.CommoditySettleProp;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface CommoditySettlePropDAO
  extends DAO
{
  public abstract List commoditySettlepropList(QueryConditions paramQueryConditions);
  
  public abstract void commoditySettlepropDelete(CommoditySettleProp paramCommoditySettleProp);
  
  public abstract int commoditySettlePropAdd(String[] paramArrayOfString1, String[] paramArrayOfString2);
}
