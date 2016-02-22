package gnnt.MEBS.query.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import gnnt.MEBS.base.query.jdbc.QueryConditions;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("tradeOrderHisSearchDao")
public class TradeOrderHisSearchDao
  extends DaoHelper
{
  public List getList(QueryConditions qc)
  {
    String sql = "select primary.ORDERNO,primary.COMMODITYID,primary.FIRMID,primary.TRADERID,primary.BS_FLAG,primary.OC_FLAG,primary.ORDERTYPE,primary.primary.TATUS,primary.QUANTITY,primary.ORDERTIME,primary.PRICE,primary.TRADEPRICE,primary.ORDERPOINT,primary.STOPLOSSPRICE,primary.STOPPROFITPRICE,primary.FROZENMARGIN,primary.FROZENFEE,primary.CLOSEMODE,primary.HOLDNO,primary.WITHDRAWTIME,primary.WITHDRAWTYPE,primary.CONSIGNERID,primary.WITHDRAWERID,primary.UPDATETIME,primary.O_FIRMID from t_orders primary union select primary2.ORDERNO,primary2.COMMODITYID,primary2.FIRMID,primary2.TRADERID,primary2.BS_FLAG,primary2.OC_FLAG,primary2.ORDERTYPE,primary2.primary2.TATUS,primary2.QUANTITY,primary2.ORDERTIME,primary2.PRICE,primary2.TRADEPRICE,primary2.ORDERPOINT,primary2.STOPLOSSPRICE,primary2.STOPPROFITPRICE,primary2.FROZENMARGIN,primary2.FROZENFEE,primary2.CLOSEMODE,primary2.HOLDNO,primary2.WITHDRAWTIME,primary2.WITHDRAWTYPE,primary2.CONSIGNERID,primary2.WITHDRAWERID,primary2.UPDATETIME,primary2.O_FIRMID from t_orders primary2 ";
    Object[] params = (Object[])null;
    if ((qc != null) && (qc.getFieldsSqlClause() != null))
    {
      params = qc.getValueArray();
      sql = sql + " where " + qc.getFieldsSqlClause();
    }
    return queryBySQL(sql, params, null);
  }
}
