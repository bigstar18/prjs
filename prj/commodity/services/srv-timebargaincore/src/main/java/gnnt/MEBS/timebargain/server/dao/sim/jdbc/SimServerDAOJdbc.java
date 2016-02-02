package gnnt.MEBS.timebargain.server.dao.sim.jdbc;

import gnnt.MEBS.timebargain.server.dao.jdbc.BaseDAOJdbc;
import gnnt.MEBS.timebargain.server.dao.sim.SimServerDAO;
import gnnt.MEBS.timebargain.server.model.Order;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

public class SimServerDAOJdbc
  extends BaseDAOJdbc
  implements SimServerDAO
{
  public long getMinOrderNo()
  {
    return getJdbcTemplate().queryForLong("select nvl(min(t.a_orderno),0) from t_orders t");
  }
  
  public void insertOrder(Order paramOrder)
  {
    String str = "insert into t_orders ( A_ORDERNO, COMMODITYID, CUSTOMERID, TRADERID, BS_FLAG, ORDERTYPE, STATUS,  QUANTITY, PRICE,  TRADEQTY, FROZENFUNDS, UNFROZENFUNDS, ORDERTIME,  FIRMID) values (" + paramOrder.getOrderNo() + ", '" + paramOrder.getCommodityID() + "', '" + paramOrder.getCustomerID() + "', '" + paramOrder.getTraderID() + "'," + paramOrder.getBuyOrSell() + "," + paramOrder.getOrderType() + ", " + paramOrder.getStatus() + "," + paramOrder.getQuantity() + "," + paramOrder.getPrice() + "," + paramOrder.getTradeQty() + "," + " 0, 0, sysdate,'" + paramOrder.getFirmID() + "')";
    getJdbcTemplate().execute(str);
  }
  
  public void deleteOrder(Order paramOrder)
  {
    String str = "delete from t_orders where A_ORDERNO=" + paramOrder.getOrderNo();
    getJdbcTemplate().execute(str);
  }
  
  public Date getOrderTime(Order paramOrder)
  {
    return (Date)getJdbcTemplate().queryForObject("select ordertime from t_orders t where a_orderno=" + paramOrder.getOrderNo(), Timestamp.class);
  }
}
