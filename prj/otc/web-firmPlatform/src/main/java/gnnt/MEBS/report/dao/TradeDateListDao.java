package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("tradeDateListDao")
public class TradeDateListDao
  extends BaseDao
{
  public List getList(QueryConditions qc)
  {
    String sql = "select max(tradeDate) from t_systemstatus_h primary";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, null);
  }
}
