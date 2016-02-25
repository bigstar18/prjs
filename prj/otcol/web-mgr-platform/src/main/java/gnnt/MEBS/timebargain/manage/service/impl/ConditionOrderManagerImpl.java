package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.ConditionOrderDao;
import gnnt.MEBS.timebargain.manage.service.ConditionOrderManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class ConditionOrderManagerImpl
  extends BaseManager
  implements ConditionOrderManager
{
  private ConditionOrderDao conditionOrderDao;
  
  public void setConditionOrderDao(ConditionOrderDao paramConditionOrderDao)
  {
    this.conditionOrderDao = paramConditionOrderDao;
  }
  
  public int getConditionOrderCount(QueryConditions paramQueryConditions)
  {
    return this.conditionOrderDao.getConditionOrderCount(paramQueryConditions);
  }
  
  public List getConditionOrder(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.conditionOrderDao.getConditionOrder(paramQueryConditions, paramInt1, paramInt2);
  }
}
