package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("memberCustomerAcountDao")
public class MemberCustomerAcountDao
  extends BaseDao
{
  public List getDistinctData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "cleardate", false);
    String sql = "select distinct primary.cleardate,primary.memberno,primary.membername,primary.contact,primary.createcount,primary.totalcreatecount,primary.demisecount,primary.totaldemisecount,primary.dealcount,primary.totaldealcount from v_member_customercount_stat primary";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
  
  public List getAllData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "memberNo", false);
    String sql = "select * from v_member_customercount_stat primary";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, pageInfo);
  }
}
