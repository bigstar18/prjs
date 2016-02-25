package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.dao.SettleprivilegeDao;
import gnnt.MEBS.timebargain.manage.model.Settleprivilege;
import gnnt.MEBS.timebargain.manage.service.SettleprivilegeManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public class SettleprivilegeManageImpl
  extends BaseManager
  implements SettleprivilegeManager
{
  private SettleprivilegeDao dao;
  
  public void setSettleprivilegeDAO(SettleprivilegeDao paramSettleprivilegeDao)
  {
    this.dao = paramSettleprivilegeDao;
  }
  
  public List<Settleprivilege> listSettleprivilege(QueryConditions paramQueryConditions)
  {
    return this.dao.getSettleprivileges(paramQueryConditions);
  }
  
  public void deletePrivilege(long paramLong)
  {
    this.dao.deleteSettleprivilegeById(paramLong);
  }
  
  public void insertPrivilege(Settleprivilege paramSettleprivilege)
  {
    this.dao.insertSettleprivilege(paramSettleprivilege);
  }
  
  public List getSettlePrivilege(long paramLong)
  {
    return this.dao.getSettleprivilege(paramLong);
  }
  
  public void updateSettlePrivilege(Settleprivilege paramSettleprivilege)
  {
    this.dao.updateSettleprivilege(paramSettleprivilege);
  }
  
  public Dealer getDealerByfirmId(String paramString)
  {
    return this.dao.getDealerByfirmId(paramString);
  }
  
  public List getSettleprivilegeByFirmIdAndCommId(String paramString1, String paramString2)
  {
    return this.dao.getSettleprivilegeByFirmIdAndCommId(paramString1, paramString2);
  }
}
