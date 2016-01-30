package gnnt.MEBS.timebargain.tradeweb.service;

import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.tradeweb.dao.ConditionDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.util.List;
import java.util.Map;

public abstract interface ConditionOrderManager
{
  public abstract void setConditionDAO(ConditionDAO paramConditionDAO);
  
  public abstract List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  
  public abstract List<?> conditionOrderPageQuery(Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap);
  
  public abstract List<?> comty_code_query(String paramString);
  
  public abstract int getFirmType(String paramString);
  
  public abstract ConditionOrder singl_order_query(String paramString);
  
  public abstract List getTradeDate();
  
  public abstract int selectConditionOrdeCount(String paramString1, String paramString2);
  
  public abstract List<?> selectCondittion();
  
  public abstract int selectConditionOrderCountFromCache(String paramString1, String paramString2);
  
  public abstract void conditionOrderUpdateTime_query();
  
  public abstract List<?> getRmiConf(int paramInt);
}
