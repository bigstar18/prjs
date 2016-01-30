package gnnt.MEBS.activeUserListener.dao;

import gnnt.MEBS.activeUserListener.po.Dictionary;
import gnnt.MEBS.logonService.dao.BaseDAOJdbc;
import gnnt.MEBS.logonService.dao.ObjectRowMapper;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class SelfLogonManagerDAO
  extends BaseDAOJdbc
{
  public Dictionary getLogonConfigByID(String paramString)
  {
    String str = "select * from l_dictionary where key=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]" + arrayOfObject[i]);
    }
    List localList = getJdbcTemplate().query(str, arrayOfObject, new ObjectRowMapper(new Dictionary()));
    if ((localList != null) && (localList.size() > 0)) {
      return (Dictionary)localList.get(0);
    }
    return null;
  }
  
  public List<Map<String, Object>> getSysname()
  {
    String str = "select distinct(sysname) from l_auconfig t where t.hostip is not null";
    List localList = getJdbcTemplate().queryForList(str);
    return localList;
  }
}
