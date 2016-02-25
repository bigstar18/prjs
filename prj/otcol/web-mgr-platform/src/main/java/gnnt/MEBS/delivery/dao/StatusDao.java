package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.delivery.model.Status;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class StatusDao
  extends DaoHelperImpl
{
  public Status getStatus(String paramString, int paramInt)
  {
    String str = "select * from w_status where kind=? and value=?";
    this.logger.debug("sql: " + str);
    Object[] arrayOfObject = { paramString, Integer.valueOf(paramInt) };
    this.logger.debug("value:" + paramInt);
    Object localObject = getJdbcTemplate().queryForObject(str, arrayOfObject, new CommonRowMapper(new Status()));
    return (Status)localObject;
  }
  
  public List getStatusMap(String paramString)
  {
    String str = "select * from w_status where kind='" + paramString + "'";
    List localList = queryBySQL(str);
    return localList;
  }
}
