package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface ConditionOrderManager
{
  public abstract int getConditionOrderCount(QueryConditions paramQueryConditions);
  
  public abstract List getConditionOrder(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
}
