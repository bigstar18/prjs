package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.model.Settleprivilege;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface SettleprivilegeDao
  extends DAO
{
  public abstract List getSettleprivilege(long paramLong);
  
  public abstract List getSettleprivileges(QueryConditions paramQueryConditions);
  
  public abstract void insertSettleprivilege(Settleprivilege paramSettleprivilege);
  
  public abstract void updateSettleprivilege(Settleprivilege paramSettleprivilege);
  
  public abstract void deleteSettleprivilegeById(long paramLong);
  
  public abstract long getId();
  
  public abstract Dealer getDealerByfirmId(String paramString);
  
  public abstract List getSettleprivilegeByFirmIdAndCommId(String paramString1, String paramString2);
}
