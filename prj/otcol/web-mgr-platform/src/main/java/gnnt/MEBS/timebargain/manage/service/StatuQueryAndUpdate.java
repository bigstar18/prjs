package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.StatuQueryAndUpdateDAO;

public abstract interface StatuQueryAndUpdate
{
  public abstract void setStatuQueryAndUpdateDAO(StatuQueryAndUpdateDAO paramStatuQueryAndUpdateDAO);
  
  public abstract boolean getStatus();
  
  public abstract void updateStatus(String paramString)
    throws Exception;
}
