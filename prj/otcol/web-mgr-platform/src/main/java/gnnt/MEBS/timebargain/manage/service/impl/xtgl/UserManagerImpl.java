package gnnt.MEBS.timebargain.manage.service.impl.xtgl;

import gnnt.MEBS.timebargain.manage.dao.xtgl.UserDAO;
import gnnt.MEBS.timebargain.manage.service.impl.BaseManager;
import gnnt.MEBS.timebargain.manage.service.xtgl.UserManager;
import java.util.List;

public class UserManagerImpl
  extends BaseManager
  implements UserManager
{
  private UserDAO dao;
  
  public void setUserDAO(UserDAO paramUserDAO)
  {
    this.dao = paramUserDAO;
  }
  
  public void addSysLog(String paramString1, String paramString2, String paramString3)
  {
    this.dao.addSysLog(paramString1, paramString2, paramString3);
  }
  
  public List getSysLogs(String paramString)
    throws Exception
  {
    return this.dao.getSysLogs(paramString);
  }
}
