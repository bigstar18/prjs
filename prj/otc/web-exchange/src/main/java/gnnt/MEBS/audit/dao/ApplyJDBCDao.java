package gnnt.MEBS.audit.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;

public class ApplyJDBCDao
  extends DaoHelper
{
  public List getApplyList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from(select primary.id,primary.applytype,primary.statusdiscribe,primary.status from c_apply primary)";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
