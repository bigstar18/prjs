package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.OrdersDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class OrdersDAOJdbc
  extends BaseDAOJdbc
  implements OrdersDAO
{
  private Log log = LogFactory.getLog(OrdersDAOJdbc.class);
  
  public List noTradeList(Orders orders, String sectionId, String traderId)
  {
    Assert.hasText(orders.getTraderID());
    
    String sql = "select a.*,c.CommodityID from T_ORDERS a,T_COMMODITY c where a.OrderType<>4 and a.CommodityID=c.CommodityID and a.Status in(1,2) ";
    if ((sectionId != null) && (!"".equals(sectionId))) {
      sql = sql + "and a.commodityId in (select commodityId from T_A_CommodityTradeProp where sectionid='" + sectionId + "') ";
    }
    if (traderId != null) {
      sql = sql + "and a.traderId like '%" + traderId + "%' ";
    }
    sql = sql + "order by a.OrderTime";
    
    Object[] params = (Object[])null;
    this.log.debug("sql: " + sql);
    


    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getNotMarketCodeOrders()
  {
    String sql = "select a.*,b.MarketName,c.commodityId from Orders a,Market b,commodity c where b.MarketCode = substr(a.CommodityID,0,2) and a.CommodityID=c.CommodityID and a.OrderType<>4 and a.M_OrderNo is null and a.Status<>5 order by a.OrderTime desc ";
    
    this.log.debug("sql: " + sql);
    
    return getJdbcTemplate().queryForList(sql);
  }
  
  public void updateMarketOrderNo(Long a_orderNo, Long m_orderNo)
  {
    String sql = "update Orders set M_OrderNo=? where A_OrderNo=?";
    Object[] params = { m_orderNo, a_orderNo };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
  }
  
  public List queryWithdrawReason()
  {
    String sql = "select a.*,b.MarketName from Orders a,Market b where a.withdrawtype is not null and a.CommodityID like b.marketCode || '%' ";
    
    this.log.debug("sql: " + sql);
    
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List holdMonitor(long holdQty)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select customerid, sum(HoldQty) HoldQty,sum(HoldFunds) HoldFunds,sum(FloatingLoss) FloatingLoss,")
      .append("sum(HoldFunds)/sum(HoldQty) EvenPrice,sum(FrozenQty) FrozenQty ")
      .append("from customerholdsum group by customerid having sum(holdqty) > ? ");
    
    Object[] params = { new Long(holdQty) };
    
    this.log.debug("sql: " + sb.toString());
    this.log.debug("holdQty:" + params[0]);
    
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List fundMonitor(double monFund)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.CustomerID,max(a.Balance) Balance,max(a.MaxOverdraft) MaxOverdraft,max(a.FrozenFunds) FrozenFunds,max(a.RuntimeFL) RuntimeFL,")
      .append("max(a.ClearFL) ClearFL,max(a.RuntimeMargin) RuntimeMargin,max(a.ClearMargin) ClearMargin,")
      .append("max(a.VirtualFunds) VirtualFunds,nvl(sum(c.Close_PL),0) Close_PL,nvl(sum(c.TradeFee),0) TradeFee,max(a.CustomerName) CustomerName,max(a.GroupID) GroupID,")
      .append("nvl(max(a.Balance+a.ClearFL-a.RuntimeFL-a.FrozenFunds+a.ClearMargin-a.RuntimeMargin+a.VirtualFunds + a.MaxOverdraft),0) UsefulFunds ")
      .append("from FundTradePropsView a,Trade c where a.CustomerID = c.CustomerID(+) ")
      .append(" and nvl(a.Balance+a.ClearFL-a.RuntimeFL-a.FrozenFunds+a.ClearMargin-a.RuntimeMargin+a.VirtualFunds + a.MaxOverdraft,0) <= ? ")
      .append("group by a.CustomerID order by a.CustomerID ");
    Object[] params = { new Double(monFund) };
    
    this.log.debug("sql: " + sb.toString());
    this.log.debug("monFund:" + params[0]);
    
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public Map performMonitor()
  {
    Map map = new HashMap();
    String sql = "select count(*) from Orders where OrderType<>4 and M_OrderNo is null and Status<>5";
    int notMarketNoNum = getJdbcTemplate().queryForInt(sql);
    map.put("notMarketNoNum", new Integer(notMarketNoNum));
    sql = "select count(*) from Orders";
    long orderNum = getJdbcTemplate().queryForLong(sql);
    map.put("orderNum", new Long(orderNum));
    sql = "select count(*) from Orders where OrderType=1";
    long openOrderNum = getJdbcTemplate().queryForLong(sql);
    map.put("openOrderNum", new Long(openOrderNum));
    sql = "select count(*) from Orders where OrderType=2";
    long closeOrderNum = getJdbcTemplate().queryForLong(sql);
    map.put("closeOrderNum", new Long(closeOrderNum));
    sql = "select count(*) from Orders where OrderType=2 and CloseFlag=2";
    long forceOrderNum = getJdbcTemplate().queryForLong(sql);
    map.put("forceOrderNum", new Long(forceOrderNum));
    sql = "select count(*) from Orders where OrderType=4";
    long withdrawOrderNum = getJdbcTemplate().queryForLong(sql);
    map.put("withdrawOrderNum", new Long(withdrawOrderNum));
    sql = "select count(*) from Trade";
    long tradeNum = getJdbcTemplate().queryForLong(sql);
    map.put("tradeNum", new Long(tradeNum));
    sql = "select sum(HoldQty) from CustomerHoldSum";
    long holdNum = getJdbcTemplate().queryForLong(sql);
    map.put("holdNum", new Long(holdNum));
    sql = "select sum(FrozenQty) from SpecFrozenHold";
    long specHoldNum = getJdbcTemplate().queryForLong(sql);
    map.put("specHoldNum", new Long(specHoldNum));
    sql = "select count(*) from Commodity where Status=0";
    int commodityNum = getJdbcTemplate().queryForInt(sql);
    map.put("commodityNum", new Long(commodityNum));
    sql = "select count(*) from Orders where Status in(5,6)";
    long withdrawNum = getJdbcTemplate().queryForLong(sql);
    map.put("withdrawNum", new Long(withdrawNum));
    
    sql = "select count(*) from AppLog where to_char(createTime,'yyyyMMdd')=to_char(sysdate,'yyyyMMdd') and Action in('01','02','03','04','05')";
    long tradeErrNum = getJdbcTemplate().queryForLong(sql);
    map.put("tradeErrNum", new Long(tradeErrNum));
    sql = "select count(*) from Broadcast where to_char(createTime,'yyyyMMdd')=to_char(sysdate,'yyyyMMdd')";
    int broadcastNum = getJdbcTemplate().queryForInt(sql);
    map.put("broadcastNum", new Integer(broadcastNum));
    return map;
  }
  
  public List procLog(QueryConditions conditions)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer("select * from T_DBLog ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" where ").append(conditions.getFieldsSqlClause());
    }
    sb.append(" order by err_date desc");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void deleteProcLog(QueryConditions conditions)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer("delete from T_DBLog ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" where ").append(conditions.getFieldsSqlClause());
    }
    this.log.debug("sql: " + sb.toString());
    
    getJdbcTemplate().update(sb.toString(), params);
  }
  
  public List sysLog(QueryConditions conditions, String querytype)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer("select * from T_SYSLOG where action in ");
    if ("0".equals(querytype)) {
      sb.append(" (01,02,03) ");
    } else if ("1".equals(querytype)) {
      sb.append(" (04,05,06) ");
    }
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    sb.append(" order by CreateTime desc ");
    
    this.log.debug("sql: " + sb.toString());
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void deleteSysLog(QueryConditions conditions)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer("delete from T_SYSLOG ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" where ").append(conditions.getFieldsSqlClause());
    }
    this.log.debug("sql: " + sb.toString());
    
    getJdbcTemplate().update(sb.toString(), params);
  }
  
  /**
   * @deprecated
   */
  public String getMarketCodeByTradeRespondID(Long id)
  {
    String marketCode = null;
    Assert.notNull(id);
    String sql = "select substr(b.CommodityID,0,2) MarketCode from TradeRespond a,Orders b where a.M_OrderNo=b.M_OrderNo and id=?";
    
    Object[] params = { id };
    
    this.log.debug("sql: " + sql);
    this.log.debug("id:" + params[0]);
    try
    {
      marketCode = (String)getJdbcTemplate().queryForObject(sql, params, String.class);
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      this.log.error("--getMarketCodeByTradeRespondID err:" + e.getMessage());
    }
    return marketCode;
  }
  
  public void deleteTradeRespondById(Long id)
  {
    Assert.notNull(id);
    String sql = "delete from TradeRespond where id=?";
    Object[] params = { id };
    this.log.debug("sql: " + sql);
    this.log.debug("id: " + params[0]);
    getJdbcTemplate().update(sql, params);
  }
  
  public void deleteTradeRespond()
  {
    String sql = "delete from TradeRespond";
    Object[] params = (Object[])null;
    this.log.debug("sql: " + sql);
    getJdbcTemplate().update(sql, params);
  }
  
  public boolean isTradeProcessByID(Long m_TradeNo, String marketCode)
  {
    boolean bRet = false;
    String sql = "select count(*) from Trade where M_TradeNo = ? and CommodityID like ? || '%'";
    Object[] params = { m_TradeNo, marketCode };
    
    this.log.debug("sql: " + sql);
    this.log.debug("M_TradeNo:" + params[0]);
    this.log.debug("MarketCode:" + params[1]);
    if (getJdbcTemplate().queryForInt(sql, params) > 0) {
      bRet = true;
    }
    return bRet;
  }
  
  public void updateTradeRespond(String M_TradeNo, int status)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("update TradeRespond set Status=" + status + " where M_TradeNo=" + M_TradeNo + " ");
    Object[] params = (Object[])null;
    
    this.log.debug("sql: " + sb.toString());
    getJdbcTemplate().update(sb.toString(), params);
  }
  
  public List getTradeRespondsByStatus(Short status)
  {
    String sql = "select * from TradeRespond   where Status = ? order by M_TradeNo ";
    Object[] params = { status };
    
    this.log.debug("sql: " + sql);
    this.log.debug("status:" + params[0]);
    
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List getTradeResponds()
  {
    String sql = "select a.M_OrderNo,a.M_TradeNo,nvl(a.M_HoldNo,'') M_HoldNo,a.Price,a.Quantity,a.TradeTime,a.Close_PL,nvl(a.M_HoldNo_Closed,'') M_HoldNo_Closed,nvl(a.HoldTime,'') HoldTime,a.BS_Flag,a.Status,a.M_CustomerID,substr(a.CommodityID,3,length(a.CommodityID)) CommodityID from TradeRespond a order by a.Status, a.M_TradeNo ";
    Object[] params = (Object[])null;
    
    this.log.debug("sql: " + sql);
    
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public String getFirmIDToDefine(String customerID)
  {
    String sql2 = "select count(*) from T_CUSTOMER where customerid = '" + customerID + "'";
    try
    {
      int i = getJdbcTemplate().queryForInt(sql2);
      if (i > 0)
      {
        String sql = "select f.firmid from T_firm f, T_CUSTOMER c where c.firmid = f.firmid and c.customerid = ?";
        Object[] params = {
          customerID };
        
        this.log.debug("sql: " + sql);
        this.log.debug("params[0]: " + params[0]);
        return (String)getJdbcTemplate().queryForObject(sql, params, String.class);
      }
      return "";
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      throw new RuntimeException("此交易客户ID不存在！");
    }
  }
  
  public List traderLog(QueryConditions conditions)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer("select * from M_TraderLog ");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" where ").append(conditions.getFieldsSqlClause());
    }
    sb.append(" order by OccurTime desc ");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void lockHoldPosition(Order ov)
  {
    String sql = " select * from t_holdposition  where customerid = '" + 
      ov.getCustomerID() + "' " + 
      " and commodityid = '" + ov.getCommodityID() + "' " + 
      " and bs_flag != " + ov.getBuyOrSell() + 
      " for update ";
    
    this.log.debug("sql: " + sql);
    
    getJdbcTemplate().queryForList(sql);
  }
}
