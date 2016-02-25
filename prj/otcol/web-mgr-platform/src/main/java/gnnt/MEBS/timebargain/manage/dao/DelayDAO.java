package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Delay;
import gnnt.MEBS.timebargain.manage.model.DelayStatusLocal;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface DelayDAO
  extends DAO
{
  public abstract Delay getDelayById(Long paramLong);
  
  public abstract Delay getDelayByType(short paramShort);
  
  public abstract void insertDelaySection(Delay paramDelay);
  
  public abstract void updateDelaySection(Delay paramDelay);
  
  public abstract void deleteDelaySectionById(String paramString);
  
  public abstract DelayStatusLocal getDelayStatus();
  
  public abstract List getDelayOrdersList(QueryConditions paramQueryConditions);
  
  public abstract List getDelayTradeList(QueryConditions paramQueryConditions);
  
  public abstract List getDelayQuotationList(QueryConditions paramQueryConditions);
}
