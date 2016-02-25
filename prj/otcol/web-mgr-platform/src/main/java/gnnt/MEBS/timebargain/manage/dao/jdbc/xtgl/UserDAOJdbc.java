package gnnt.MEBS.timebargain.manage.dao.jdbc.xtgl;

import gnnt.MEBS.timebargain.manage.dao.jdbc.BaseDAOJdbc;
import gnnt.MEBS.timebargain.manage.dao.xtgl.UserDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDAOJdbc
  extends BaseDAOJdbc
  implements UserDAO
{
  private Log log = LogFactory.getLog(UserDAOJdbc.class);
  
  public void addSysLog(String paramString1, String paramString2, String paramString3)
  {
    String str = "insert into T_SYSLOG(ID,userID,action,note,CreateTime) values(SEQ_T_SYSLOG.Nextval,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public List getSysLogs(String paramString)
    throws Exception
  {
    String str = "select * from T_SYSLOG where 1=1 ";
    if (paramString != null) {
      str = str + " and action='" + paramString + "'";
    }
    this.log.debug("sql:" + str);
    return getJdbcTemplate().queryForList(str);
  }
}
