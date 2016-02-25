package gnnt.MEBS.timebargain.manage.dao.xtgl;

import gnnt.MEBS.timebargain.manage.dao.DAO;
import java.util.List;

public abstract interface UserDAO
  extends DAO
{
  public abstract void addSysLog(String paramString1, String paramString2, String paramString3);
  
  public abstract List getSysLogs(String paramString)
    throws Exception;
}
