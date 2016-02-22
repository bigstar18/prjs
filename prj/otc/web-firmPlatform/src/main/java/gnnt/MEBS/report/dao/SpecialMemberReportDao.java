package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import java.util.Map;

public class SpecialMemberReportDao
  extends BaseDao
{
  public List<Map<String, Object>> getData(QueryConditions qc)
  {
    String sql = "select * from (select memberNo as s_memberNo ,name from m_s_memberInfo) primary";
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "s_memberNo", false);
    Object[] params = (Object[])null;
    qc.removeCondition("primary.ClearDate", "<=");
    qc.removeCondition("primary.ClearDate", ">=");
    qc.removeCondition("primary.membersignno", "=");
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
