package gnnt.MEBS.report.dao;

import gnnt.MEBS.base.query.jdbc.PageInfo;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.config.constant.ActionConstant;
import java.util.List;

public class TradeHoldPositingReportDao
  extends BaseDao
{
  public List getCustomerHoldData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "memberid", false);
    String sql = "select sum(buyqty) buyqty,sum(sellqty) sellqty,sum(buyloss) buyloss,sum(sellloss) sellloss,sum(netloss) netloss,memberid,commodityname from v_customerholdsum primary ";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    sql = sql + " group by memberid,commodityname";
    List list = queryBySQL(sql, params, pageInfo);
    return list;
  }
  
  public List getMemberHoldData(QueryConditions qc)
  {
    PageInfo pageInfo = new PageInfo(ActionConstant.PAGEINFONO, ActionConstant.PAGEINFOSIZE, "memberid", false);
    String sql = "select sum(buyqty) buyqty,sum(sellqty) sellqty,sum(buyloss) buyloss,sum(sellloss) sellloss,sum(netloss) netloss,memberid,commodityname,s_memberid from v_memberholdsum primary ";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    sql = sql + " group by commodityname,memberid,s_memberid";
    List list = queryBySQL(sql, params, pageInfo);
    return list;
  }
}
