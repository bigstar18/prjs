package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class BaseDao
  extends DaoHelper
  implements ReportDao
{
  @Resource(name="dataSourceForQuery")
  public void setSuperDataSource(DataSource dataSource)
  {
    super.setDataSource(dataSource);
  }
  
  public List<Map<String, Object>> getData(QueryConditions qc)
  {
    return null;
  }
}
