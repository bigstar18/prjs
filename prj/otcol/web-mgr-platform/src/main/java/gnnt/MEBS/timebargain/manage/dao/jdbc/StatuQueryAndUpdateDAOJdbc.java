package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.StatuQueryAndUpdateDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatuQueryAndUpdateDAOJdbc
  extends BaseDAOJdbc
  implements StatuQueryAndUpdateDAO
{
  private Log log = LogFactory.getLog(StatuQueryAndUpdateDAOJdbc.class);
  
  public List getStatus()
  {
    String str = "select * from T_E_SETTING";
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public void updateStatus(String paramString)
  {
    String str = "update T_E_SETTING set STATUS = ? ,SETTINGDATE = sysdate";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("修改失败！");
    }
  }
}
