package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.LookupUseBackDsDAO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class LookupUseBackDsDAOJdbc
  extends BaseDAOJdbc
  implements LookupUseBackDsDAO
{
  public List getBmkTjDistinctCommodityID(String paramString1, String paramString2)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("retrieving all bmk with tj ...");
    }
    String str = "select distinct commodityID from " + paramString1 + (paramString2 == null ? "" : paramString2);
    this.log.debug("sqlStr: " + str);
    return getJdbcTemplate().queryForList(str);
  }
}
