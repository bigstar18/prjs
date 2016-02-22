package gnnt.MEBS.audit.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;

public class CommodityFeeJDBCDao
  extends ApplyJDBCDao
{
  public List getApplyList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from (select primary.id as id,primary.applyType,primary.statusdiscribe as statusDiscribe,extractValue(xmlType(primary.content), '/root/firmName') as firmName,extractValue(xmlType(primary.content), '/root/commodityId') as commodityId,extractValue(xmlType(primary.content), '/root/commodityName') as commodityName from c_apply primary)";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
