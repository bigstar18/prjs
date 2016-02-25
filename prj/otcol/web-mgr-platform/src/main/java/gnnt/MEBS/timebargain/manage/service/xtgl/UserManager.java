package gnnt.MEBS.timebargain.manage.service.xtgl;

import gnnt.MEBS.timebargain.manage.dao.xtgl.UserDAO;
import java.util.List;

public abstract interface UserManager
{
  public abstract void setUserDAO(UserDAO paramUserDAO);
  
  public abstract void addSysLog(String paramString1, String paramString2, String paramString3);
  
  public abstract List getSysLogs(String paramString)
    throws Exception;
}
