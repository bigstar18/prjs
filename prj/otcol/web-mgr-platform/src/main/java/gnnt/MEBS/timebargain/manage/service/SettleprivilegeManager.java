package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.model.Settleprivilege;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface SettleprivilegeManager
{
  public abstract List<Settleprivilege> listSettleprivilege(QueryConditions paramQueryConditions);
  
  public abstract void deletePrivilege(long paramLong);
  
  public abstract void insertPrivilege(Settleprivilege paramSettleprivilege);
  
  public abstract List getSettlePrivilege(long paramLong);
  
  public abstract void updateSettlePrivilege(Settleprivilege paramSettleprivilege);
  
  public abstract Dealer getDealerByfirmId(String paramString);
  
  public abstract List getSettleprivilegeByFirmIdAndCommId(String paramString1, String paramString2);
}
