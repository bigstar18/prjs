package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import java.util.Map;

public class CustomerMappingReportDao
  extends BaseDao
{
  public List<Map<String, Object>> getData(QueryConditions qc)
  {
    String sql = "select * from v_customer primary";
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "customerNo", false);
    Object[] params = (Object[])null;
    qc.removeCondition("trunc(primary.clearDate)", "<=");
    qc.removeCondition("trunc(primary.clearDate)", ">=");
    qc.removeCondition("primary.isData", "=");
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
