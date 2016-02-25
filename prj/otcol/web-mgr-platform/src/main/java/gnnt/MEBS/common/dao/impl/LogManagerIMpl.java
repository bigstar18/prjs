package gnnt.MEBS.common.dao.impl;

import gnnt.MEBS.common.dao.LogManagerDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class LogManagerIMpl
  extends JdbcDaoSupport
  implements LogManagerDao
{
  private Log log = LogFactory.getLog(LogManagerIMpl.class);
  
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
}
