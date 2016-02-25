package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.DelayDAO;
import gnnt.MEBS.timebargain.manage.model.Delay;
import gnnt.MEBS.timebargain.manage.model.DelayStatusLocal;
import gnnt.MEBS.timebargain.manage.service.DelayManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class DelayManagerImpl
  implements DelayManager
{
  public DelayDAO delayDAO;
  
  public void setDelayDAO(DelayDAO paramDelayDAO)
  {
    this.delayDAO = paramDelayDAO;
  }
  
  public Delay getDelayById(Long paramLong)
  {
    return this.delayDAO.getDelayById(paramLong);
  }
  
  public Delay getDelayByType(short paramShort)
  {
    return this.delayDAO.getDelayByType(paramShort);
  }
  
  public void insertDelaySection(Delay paramDelay)
  {
    this.delayDAO.insertDelaySection(paramDelay);
  }
  
  public void updateDelaySection(Delay paramDelay)
  {
    this.delayDAO.updateDelaySection(paramDelay);
  }
  
  public void deleteDelaySectionById(String paramString)
  {
    this.delayDAO.deleteDelaySectionById(paramString);
  }
  
  public DelayStatusLocal getDelayStatus()
  {
    return this.delayDAO.getDelayStatus();
  }
  
  public List getDelayOrdersList(QueryConditions paramQueryConditions)
  {
    return this.delayDAO.getDelayOrdersList(paramQueryConditions);
  }
  
  public List getDelayTradeList(QueryConditions paramQueryConditions)
  {
    return this.delayDAO.getDelayTradeList(paramQueryConditions);
  }
  
  public List getDelayQuotationList(QueryConditions paramQueryConditions)
  {
    return this.delayDAO.getDelayQuotationList(paramQueryConditions);
  }
}
