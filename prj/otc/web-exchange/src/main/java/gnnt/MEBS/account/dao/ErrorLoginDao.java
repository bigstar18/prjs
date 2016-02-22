package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.ErrorLogin;
import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("errorLoginDao")
public class ErrorLoginDao
  extends DaoHelper
{
  private final transient Log logger = LogFactory.getLog(ErrorLoginDao.class);
  
  public Class getEntityClass()
  {
    return new ErrorLogin().getClass();
  }
  
  public List<Map<String, Object>> getExList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from(select traderid,count(*) counts,trunc(loginDate) loginDate from m_errorloginlog group by traderid,trunc(loginDate))";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
  
  public List<Map<String, Object>> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String sql = "select * from(select traderid,loginDate,ip from m_errorloginlog)";
    Object[] params = (Object[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sql = sql + " where " + conditions.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
  
  public void delete(String id)
  {
    String sql = "delete from M_ErrorLoginLog ";
    Object[] params = { id };
    sql = sql + " where " + "traderID=?";
    updateBySQL(sql, params);
  }
  
  public Date getSysDate()
  {
    String sqlString = "select sysdate  CRTDATE from dual";
    return (Date)queryForObject(sqlString, Date.class);
  }
  
  public int getCounts()
  {
    String sql = "select value from m_configuration";
    return queryForInt(sql);
  }
}
