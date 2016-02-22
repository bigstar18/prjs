package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class CommonReportDao
  extends BaseDao
{
  private String sqlTemplete;
  private String pageInfoStr;
  
  public void setSql(String sqlTemplete)
  {
    this.sqlTemplete = sqlTemplete;
  }
  
  public void setPageInfoStr(String pageInfoStr)
  {
    this.pageInfoStr = pageInfoStr;
  }
  
  public List<Map<String, Object>> getData(QueryConditions qc)
  {
    String sql = "";
    
    PageInfo pageInfo = new PageInfo();
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = this.sqlTemplete + " where " + qc.getFieldsSqlClause();
    }
    else
    {
      sql = this.sqlTemplete;
    }
    pageInfo.setOrderString(this.pageInfoStr);
    return queryBySQL(sql, params, pageInfo);
  }
}
