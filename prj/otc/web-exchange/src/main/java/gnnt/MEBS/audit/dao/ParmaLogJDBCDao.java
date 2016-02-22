package gnnt.MEBS.audit.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("parmaLogJDBCDao")
public class ParmaLogJDBCDao
  extends ApplyJDBCDao
{
  private final transient Log logger = LogFactory.getLog(ParmaLogJDBCDao.class);
  
  public List getApplyList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from  C_PARAMLOG primary ";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause() + "  order by primary.operateTime  ";
    }
    return queryBySQL(sql, params, pageInfo);
  }
  
  public List getSystemStatusDate(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from  T_SystemStatus primary ";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
  
  public void delete(String mySql)
  {
    String sql = "delete from  C_PARAMLOG primary ";
    if (mySql != null) {
      sql = sql + " where " + mySql;
    }
    updateBySQL(sql);
  }
  
  public List getComodityStatus(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from  T_Commodity primary ";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
