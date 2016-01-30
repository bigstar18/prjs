package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.timebargain.tradeweb.dao.LookupDAO;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class LookupDAOJdbc
  extends BaseDAOJdbc
  implements LookupDAO
{
  public List getBmk(String paramString)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk ...");
    }
    String str = "select bm,mc from " + paramString + " order by bm";
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBmkTj(String paramString1, String paramString2)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk with tj ...");
    }
    String str = "select * from " + paramString1 + (paramString2 == null ? "" : paramString2);
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving mc with tj ...");
    }
    String str = "select " + paramString2 + " from " + paramString1 + (paramString3 == null ? "" : paramString3);
    this.log.debug("sqlStr: " + str);
    List localList = getJdbcTemplate().queryForList(str);
    if (localList.size() <= 0) {
      return "";
    }
    Map localMap = (Map)localList.get(0);
    return (String)localMap.get(paramString2);
  }
  
  public List getTable(String paramString)
    throws Exception
  {
    this.log.debug("strSql: " + paramString);
    return getJdbcTemplate().queryForList(paramString);
  }
}
