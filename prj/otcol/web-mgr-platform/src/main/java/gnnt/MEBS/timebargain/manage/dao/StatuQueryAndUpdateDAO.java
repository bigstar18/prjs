package gnnt.MEBS.timebargain.manage.dao;

import java.util.List;

public abstract interface StatuQueryAndUpdateDAO
  extends DAO
{
  public abstract List getStatus();
  
  public abstract void updateStatus(String paramString);
}
