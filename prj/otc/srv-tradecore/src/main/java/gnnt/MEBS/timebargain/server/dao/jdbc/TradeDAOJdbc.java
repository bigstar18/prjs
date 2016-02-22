package gnnt.MEBS.timebargain.server.dao.jdbc;

import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.HoldPosition;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Trade;
import gnnt.MEBS.timebargain.server.util.StringUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

public class TradeDAOJdbc
  extends BaseDAOJdbc
  implements TradeDAO
{
  private Log log = LogFactory.getLog(getClass());
  
  public List<Order> getNotTradeOrders()
  {
    List<Order> orderList = new ArrayList();
    String sql = "select * from T_Orders where Status=1 order by OrderNo";
    this.log.debug("sql: " + sql);
    orderList = getJdbcTemplate().query(sql, new OrderRowMapper(null));
    return orderList;
  }
  
  private class OrderRowMapper
    implements RowMapper
  {
    private OrderRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      Order order = new Order();
      order.setOrderNo(Long.valueOf(rs.getLong("OrderNo")));
      order.setStatus(Short.valueOf(rs.getShort("Status")));
      order.setStopLossPrice(Double.valueOf(rs.getDouble("StopLossPrice")));
      order.setStopProfitPrice(Double.valueOf(rs.getDouble("StopProfitPrice")));
      order.setBuyOrSell(Short.valueOf(rs.getShort("BS_Flag")));
      order.setOC_Flag(rs.getString("OC_Flag").charAt(0));
      order.setCommodityID(rs.getString("CommodityID"));
      order.setConsignerID(rs.getString("ConsignerID"));
      order.setWithdrawerID(rs.getString("WithdrawerID"));
      order.setFirmID(rs.getString("FirmID"));
      order.setTraderID(rs.getString("TraderID"));
      order.setTradePrice(Double.valueOf(rs.getDouble("TradePrice")));
      order.setOrderPoint(Double.valueOf(rs.getDouble("OrderPoint")));
      order.setOrderType(Short.valueOf(rs.getShort("OrderType")));
      order.setPrice(Double.valueOf(rs.getDouble("Price")));
      order.setQuantity(Long.valueOf(rs.getLong("Quantity")));
      order.setHoldNo(Long.valueOf(rs.getLong("HoldNo")));
      order.setWithdrawType(Short.valueOf(rs.getShort("WithdrawType")));
      order.setOrderTime(rs.getTimestamp("OrderTime"));
      order.setOtherFirmID(rs.getString("O_FirmID"));
      order.setCloseMode(Short.valueOf(rs.getShort("CloseMode")));
      order.setMargin(Double.valueOf(rs.getDouble("FrozenMargin")));
      order.setFee(Double.valueOf(rs.getDouble("FrozenFee")));
      order.setStopPLFlag(rs.getShort("StopPL_Flag"));
      return order;
    }
  }
  
  public List<HoldPosition> getHoldPosition(Long holdNO)
  {
    List<HoldPosition> holdPositionList = new ArrayList();
    StringBuffer sb = new StringBuffer(
      "select * from t_holdposition t where 1=1 ");
    if (holdNO.longValue() > 0L) {
      sb.append(" and t.HoldNO=").append(holdNO).append(" ");
    }
    this.log.debug("sql: " + sb.toString());
    holdPositionList = getJdbcTemplate().query(sb.toString(), 
      new HoldPositionRowMapper(null));
    return holdPositionList;
  }
  
  private class HoldPositionRowMapper
    implements RowMapper
  {
    private HoldPositionRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      HoldPosition holdPosition = new HoldPosition();
      
      holdPosition.setHoldNO(Long.valueOf(rs.getLong("HOLDNO")));
      holdPosition.setFirmID(rs.getString("FIRMID"));
      holdPosition.setTraderID(rs.getString("TRADERID"));
      holdPosition.setConsignerID(rs.getString("CONSIGNERID"));
      holdPosition.setTradeNO(Long.valueOf(rs.getLong("TRADENO")));
      holdPosition.setCommodityID(rs.getString("COMMODITYID"));
      holdPosition.setBuyOrSell(rs.getShort("BS_FLAG"));
      holdPosition.setOpenPrice(rs.getDouble("OPENPRICE"));
      holdPosition.setHoldPrice(rs.getDouble("HOLDPRICE"));
      holdPosition.setStopLossPrice(Double.valueOf(rs.getDouble("STOPLOSSPRICE")));
      holdPosition.setStopProfitPrice(Double.valueOf(rs.getDouble("STOPPROFITPRICE")));
      
      holdPosition.setHoldQty(Long.valueOf(rs.getLong("HOLDQTY")));
      holdPosition.setFrozenQty(Long.valueOf(rs.getLong("FROZENQTY")));
      holdPosition.setOpenQty(Long.valueOf(rs.getLong("OPENQTY")));
      
      holdPosition.setHoldTime(rs.getTimestamp("HOLDTIME"));
      holdPosition.setHoldMargin(rs.getDouble("HOLDMARGIN"));
      holdPosition.setFloatingLoss(rs.getDouble("FLOATINGLOSS"));
      holdPosition.setStatus(rs.getShort("STATUS"));
      holdPosition.setAtclearDate(rs.getTimestamp("ATCLEARDATE"));
      holdPosition.setDelayFee(rs.getDouble("DELAYFEE"));
      holdPosition.setO_firmID(rs.getString("O_FIRMID"));
      holdPosition.setO_buyOrSell(rs.getShort("O_BS_FLAG"));
      holdPosition.setModifyTime(rs.getTimestamp("MODIFYTIME"));
      
      return holdPosition;
    }
  }
  
  public List getWithdrawOrderList(Date updateTime)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select o.orderno orderNO, h.modifytime modifytime  from t_holdposition h , t_orders o where h.holdqty = 0 and h.modifytime >=?  and h.holdno = o.holdno and o.status = 1 order by h.modifytime ");
    
    return getJdbcTemplate().queryForList(sb.toString(), 
      new Object[] { updateTime });
  }
  
  public List<Long> getOrderNOByPL(Long holdNO, int type)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select orderNO from t_orders t where t.holdno=? and t.stoppl_flag=? and t.status=1 ");
    
    getJdbcTemplate().query(sb.toString(), 
      new Object[] { holdNO, Integer.valueOf(type) }, new RowMapper()
      {
        public Object mapRow(ResultSet rs, int rowNum)
          throws SQLException, DataAccessException
        {
          return Long.valueOf(rs.getLong("orderNO"));
        }
      });
  }
  
  public long openOrder(Order order)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'OpenOrder' method");
    }
    Assert.hasText(order.getFirmID());
    Assert.hasText(order.getCommodityID());
    Assert.notNull(order.getBuyOrSell());
    Assert.notNull(order.getPrice());
    Assert.notNull(order.getQuantity());
    
    OpenOrderStoredProcedure sfunc = new OpenOrderStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_FirmID", order.getFirmID());
    inputs.put("p_TraderID", order.getTraderID());
    inputs.put("p_CommodityID", order.getCommodityID());
    inputs.put("p_bs_flag", order.getBuyOrSell());
    inputs.put("p_OrderType", order.getOrderType());
    inputs.put("p_price", order.getPrice());
    inputs.put("p_TradePrice", order.getTradePrice());
    inputs.put("p_quantity", order.getQuantity());
    inputs.put("p_Margin", order.getMargin());
    inputs.put("p_Fee", order.getFee());
    inputs.put("p_StopLossPrice", order.getStopLossPrice());
    inputs.put("p_StopProfitPrice", order.getStopProfitPrice());
    inputs.put("p_O_Firmid", order.getOtherFirmID());
    inputs.put("p_ConsignerID", order.getConsignerID());
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    return ((Long)results.get("ID")).longValue();
  }
  
  private class OpenOrderStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_OpenOrder";
    
    public OpenOrderStoredProcedure(DataSource ds)
    {
      super("FN_T_OpenOrder");
      setFunction(true);
      declareParameter(new SqlOutParameter("ID", -5));
      declareParameter(new SqlParameter("p_FirmID", 12));
      declareParameter(new SqlParameter("p_TraderID", 12));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      declareParameter(new SqlParameter("p_bs_flag", 5));
      declareParameter(new SqlParameter("p_OrderType", 5));
      declareParameter(new SqlParameter("p_price", 8));
      declareParameter(new SqlParameter("p_TradePrice", 8));
      declareParameter(new SqlParameter("p_quantity", -5));
      declareParameter(new SqlParameter("p_Margin", 8));
      declareParameter(new SqlParameter("p_Fee", 8));
      declareParameter(new SqlParameter("p_StopLossPrice", 8));
      declareParameter(new SqlParameter("p_StopProfitPrice", 8));
      declareParameter(new SqlParameter("p_O_Firmid", 12));
      declareParameter(new SqlParameter("p_ConsignerID", 12));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public long closeMemberOrder(Order order, double factor)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'closeMemberOrder' method");
    }
    Assert.hasText(order.getFirmID());
    Assert.hasText(order.getCommodityID());
    Assert.notNull(order.getBuyOrSell());
    Assert.notNull(order.getPrice());
    Assert.notNull(order.getQuantity());
    
    CloseMemberOrderStoredProcedure sfunc = new CloseMemberOrderStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_FirmID", order.getFirmID());
    inputs.put("p_TraderID", order.getTraderID());
    inputs.put("p_CommodityID", order.getCommodityID());
    inputs.put("p_BS_Flag", order.getBuyOrSell());
    inputs.put("p_Price", order.getPrice());
    inputs.put("p_TradePrice", order.getTradePrice());
    inputs.put("p_Quantity", order.getQuantity());
    inputs.put("p_O_Firmid", order.getOtherFirmID());
    inputs.put("p_C_Factor", Double.valueOf(factor));
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    return ((Long)results.get("ID")).longValue();
  }
  
  private class CloseMemberOrderStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CloseOrder_Member";
    
    public CloseMemberOrderStoredProcedure(DataSource ds)
    {
      super("FN_T_CloseOrder_Member");
      setFunction(true);
      declareParameter(new SqlOutParameter("ID", -5));
      declareParameter(new SqlParameter("p_FirmID", 12));
      declareParameter(new SqlParameter("p_TraderID", 12));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      declareParameter(new SqlParameter("p_BS_Flag", 2));
      declareParameter(new SqlParameter("p_Price", 2));
      declareParameter(new SqlParameter("p_TradePrice", 2));
      declareParameter(new SqlParameter("p_Quantity", 2));
      declareParameter(new SqlParameter("p_O_Firmid", 12));
      declareParameter(new SqlParameter("p_C_Factor", 2));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public long closeOrder(Order order)
  {
    if (this.log.isDebugEnabled()) {
      this.log.debug("Entering 'closeOrder' method");
    }
    Assert.hasText(order.getFirmID());
    Assert.hasText(order.getCommodityID());
    Assert.notNull(order.getBuyOrSell());
    Assert.notNull(order.getPrice());
    Assert.notNull(order.getQuantity());
    
    CloseOrderStoredProcedure sfunc = new CloseOrderStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_FirmID", order.getFirmID());
    inputs.put("p_TraderID", order.getTraderID());
    inputs.put("p_CommodityID", order.getCommodityID());
    inputs.put("p_bs_flag", order.getBuyOrSell());
    inputs.put("p_OrderType", order.getOrderType());
    inputs.put("p_price", order.getPrice());
    inputs.put("p_TradePrice", order.getTradePrice());
    inputs.put("p_quantity", order.getQuantity());
    inputs.put("p_closeMode", order.getCloseMode());
    inputs.put("p_SpecHoldNo", order.getHoldNo());
    inputs.put("p_O_Firmid", order.getOtherFirmID());
    inputs.put("p_ConsignerID", order.getConsignerID());
    inputs.put("p_StopPL_Flag", Short.valueOf(order.getStopPLFlag()));
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    return ((Long)results.get("ID")).longValue();
  }
  
  private class CloseOrderStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CloseOrder";
    
    public CloseOrderStoredProcedure(DataSource ds)
    {
      super("FN_T_CloseOrder");
      setFunction(true);
      declareParameter(new SqlOutParameter("ID", -5));
      declareParameter(new SqlParameter("p_FirmID", 12));
      declareParameter(new SqlParameter("p_TraderID", 12));
      declareParameter(new SqlParameter("p_CommodityID", 12));
      declareParameter(new SqlParameter("p_bs_flag", 5));
      declareParameter(new SqlParameter("p_OrderType", 5));
      declareParameter(new SqlParameter("p_price", 8));
      declareParameter(new SqlParameter("p_TradePrice", 8));
      declareParameter(new SqlParameter("p_quantity", -5));
      declareParameter(new SqlParameter("p_closeMode", 5));
      declareParameter(new SqlParameter("p_SpecHoldNo", -5));
      declareParameter(new SqlParameter("p_O_Firmid", 12));
      declareParameter(new SqlParameter("p_ConsignerID", 12));
      declareParameter(new SqlParameter("p_StopPL_Flag", 5));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int consignerLogon(Consigner consigner)
  {
    String sql = "select * from T_Consigner where ConsignerID=?";
    
    Object[] params = { consigner.getConsignerID() };
    
    this.log.debug("sql: " + sql);
    this.log.debug("consignerID:" + params[0]);
    
    List lst = getJdbcTemplate().queryForList(sql, params);
    if ((lst == null) || (lst.size() <= 0)) {
      return -1;
    }
    Map map = (Map)lst.get(0);
    if (!((String)map.get("Password")).equals(
      StringUtil.encodePassword(consigner.getPassword(), 
      "MD5"))) {
      return -2;
    }
    if (((BigDecimal)map.get("Status")).intValue() == 1) {
      return -3;
    }
    return 0;
  }
  
  public Long openTrade(Trade trade)
  {
    OpenTradeStoredProcedure sfunc = new OpenTradeStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_OrderNo", trade.getOrderNo());
    inputs.put("p_C_Factor", trade.getContractFactor());
    inputs.put("p_TradeDate", trade.getTradeDate());
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    
    return Long.valueOf(((Long)results.get("ret")).longValue());
  }
  
  private class OpenTradeStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_OpenTrade";
    
    public OpenTradeStoredProcedure(DataSource ds)
    {
      super("FN_T_OpenTrade");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", -5));
      declareParameter(new SqlParameter("p_OrderNo", -5));
      declareParameter(new SqlParameter("p_C_Factor", 8));
      declareParameter(new SqlParameter("p_TradeDate", 91));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int closeTrade(Trade trade)
  {
    CloseTradeStoredProcedure sfunc = new CloseTradeStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_OrderNo", trade.getOrderNo());
    inputs.put("p_C_Factor", trade.getContractFactor());
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class CloseTradeStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_CloseTrade";
    
    public CloseTradeStoredProcedure(DataSource ds)
    {
      super("FN_T_CloseTrade");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_OrderNo", -5));
      declareParameter(new SqlParameter("p_C_Factor", 8));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int withdraw(Order order)
  {
    WithdrawStoredProcedure sfunc = new WithdrawStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_WithdrawerID", order.getWithdrawerID());
    inputs.put("p_OrderNo_W", order.getWithdrawID());
    inputs.put("p_WithdrawType", order.getWithdrawType());
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class WithdrawStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_Withdraw";
    
    public WithdrawStoredProcedure(DataSource ds)
    {
      super("FN_T_Withdraw");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_WithdrawerID", 12));
      declareParameter(new SqlParameter("p_OrderNo_W", -5));
      declareParameter(new SqlParameter("p_WithdrawType", 5));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int initTrade(Date tradeDate)
  {
    InitTradeStoredProcedure sfunc = new InitTradeStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_ClearDate", tradeDate);
    Map results = sfunc.execute(inputs);
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class InitTradeStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_InitTrade";
    
    public InitTradeStoredProcedure(DataSource ds)
    {
      super("FN_T_InitTrade");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_ClearDate", 91));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int reComputeFunds(int marginFBFlag)
  {
    ReComputeFundsStoredProcedure sfunc = new ReComputeFundsStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_MarginFBFlag", new Integer(marginFBFlag));
    this.log.debug("param:" + inputs);
    Map results = sfunc.execute(inputs);
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class ReComputeFundsStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_ReComputeFunds";
    
    public ReComputeFundsStoredProcedure(DataSource ds)
    {
      super("FN_T_ReComputeFunds");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_MarginFBFlag", 4));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void updateOrderTradePrice(Long orderNo, Double tradePrice)
  {
    String sql = "update T_Orders set TradePrice=? where OrderNo=? ";
    Object[] params = { tradePrice, orderNo };
    int[] types = { 8, -5 };
    getJdbcTemplate().update(sql, params, types);
  }
  
  public int clear_Do()
  {
    Clear_DoStoredProcedure sfunc = new Clear_DoStoredProcedure(
      getDataSource());
    Map inputs = new HashMap();
    Map results = sfunc.execute(inputs);
    
    return ((Integer)results.get("ret")).intValue();
  }
  
  private class Clear_DoStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_Clear_Do";
    
    public Clear_DoStoredProcedure(DataSource ds)
    {
      super("FN_Clear_Do");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public void clearBalancePrice()
  {
    SPClearBalancePrice sfunc = new SPClearBalancePrice(
      getDataSource());
    Map inputs = new HashMap();
    
    sfunc.execute(inputs);
  }
  
  private class SPClearBalancePrice
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "sp_clear_balance_price";
    
    public SPClearBalancePrice(DataSource ds)
    {
      super("sp_clear_balance_price");
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
