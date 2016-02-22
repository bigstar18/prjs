package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import java.util.Map;

public abstract interface ReportDao
{
  public abstract List<Map<String, Object>> getData(QueryConditions paramQueryConditions);
}
