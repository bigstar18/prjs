package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;

public class TradeClosePositingReportDao
  extends BaseDao
{
  public List getCustomerCloseData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "memberno", false);
    String sql = "select sum(buyqty) buyqty,sum(sellqty) sellqty,sum(buyclosepl) buyclosepl,sum(sellclosepl) sellclosepl,sum(allpl) allpl,memberno,commodityname from v_customerclosesum primary ";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    sql = sql + " group by memberno,commodityname";
    List list = queryBySQL(sql, params, pageInfo);
    return list;
  }
  
  public List getMemberCloseData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "memberno", false);
    String sql = "select commodityname,memberno,smemberno,sum(buyqty) buyqty,sum(buyclosepl) buyclosepl,sum(sellqty) sellqty,sum(sellclosepl) sellclosepl,sum(allpl) allpl from v_memberclosesum primary";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    sql = sql + " group by commodityname,memberno,smemberno";
    List list = queryBySQL(sql, params, pageInfo);
    return list;
  }
}
