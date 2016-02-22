package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import java.util.Map;

public class OrganizationDemurrageReportDao
  extends BaseDao
{
  public List<Map<String, Object>> getData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "organizationNo", false);
    String sql = "select * from v_customerfundflow_search_his primary";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      qc.addCondition("primary.oprcode", "=", Integer.valueOf(220));
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
