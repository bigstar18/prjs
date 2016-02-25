package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.TradeCtlDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class TradeCtlDAOJdbc
  extends BaseDAOJdbc
  implements TradeCtlDAO
{
  private Log log = LogFactory.getLog(TradeCtlDAOJdbc.class);
  
  public List getHolds()
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.*,b.M_CustomerID,c.MarketName,d.commodityid,t.customername ")
      .append("from HoldPosition a , CM_CustomerMap b, Market c ,commodity d,customer t where b.MarketCode=c.MarketCode and b.CustomerID=a.CustomerID and t.CustomerID=a.CustomerID and a.Uni_Cmdty_Code like c.MarketCode || '%' and a.Uni_Cmdty_Code=d.Uni_Cmdty_Code");
    

    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getTrades()
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.*,b.M_CustomerID,c.MarketName ")
      .append("from Trade a , CM_CustomerMap b, Market c where b.MarketCode=c.MarketCode and b.CustomerID=a.CustomerID and a.Uni_Cmdty_Code like c.MarketCode || '%' ");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getOrders()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.*,b.M_CustomerID,c.MarketName from Orders a , CM_CustomerMap b, Market c where b.MarketCode=c.MarketCode and b.CustomerID=a.CustomerID and a.Uni_Cmdty_Code like c.MarketCode || '%' ");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getActions()
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append(
      "select a.*,")
      .append(
      "case when a.Status in(2,3) then case when a.OrderType=1 then '订立' when a.OrderType=2 then '转让' when a.OrderType=3 then '强平' else '' end else '' end as OrderType,")
      .append(
      "case when a.Status in(2,3) then a.TradeQty  else a.Quantity-a.TradeQty end as Quantity,")
      .append(
      "case when a.Status in(5,6) then '撤单' when a.Status in(2,3) then '入单' else '' end as Action,")
      .append(
      "b.M_CustomerID,c.MarketName from Orders a , CM_CustomerMap b, Market c where b.MarketCode=c.MarketCode and b.CustomerID=a.CustomerID and a.Uni_Cmdty_Code like c.MarketCode || '%' and a.OrderType<>4 and a.Status not in(1,4)");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getFunds(Date clearDate)
  {
    this.log.debug("======getFunds.parm[clearDate]:" + clearDate);
    StringBuffer sb = new StringBuffer();
    Object[] params = (Object[])null;
    sb.append("select a.CustomerID,max(a.Balance) Balance,max(a.MaxOverdraft) MaxOverdraft,max(a.FrozenFunds) FrozenFunds,max(a.RuntimeFL) RuntimeFL,")
      .append("max(a.ClearFL) ClearFL,max(a.RuntimeMargin) RuntimeMargin,max(a.ClearMargin) ClearMargin,")
      .append("max(a.VirtualFunds) VirtualFunds,nvl(sum(c.Close_PL),0) Close_PL,nvl(sum(c.TradeFee),0) TradeFee,max(a.CustomerName) CustomerName,")
      .append("nvl(max(a.MaxHoldQty),0) MaxHoldQty,nvl(max(d.HoldQty),0) UsedQty,max(nvl(a.MaxHoldQty,0)-nvl(d.HoldQty,0)) UsefulQty,nvl(max(e.NotTradeQty),0) NotTradeQty,")
      .append("nvl(max(a.Balance+a.ClearFL-a.RuntimeFL-a.FrozenFunds+a.ClearMargin-a.RuntimeMargin+a.VirtualFunds + a.MaxOverdraft),0) UsefulFunds ");
    if (clearDate == null)
    {
      sb.append("from FundTradePropsView a, Trade c,").append("(select CustomerID, nvl(sum(HoldQty),0) HoldQty from HoldPosition group by CustomerID) d,").append("(select CustomerID,nvl(sum(Quantity-TradeQty),0) NotTradeQty from Orders where OrderType<>4 and Status in(1,2,4) group by CustomerID) e ").append("where a.CustomerID = c.CustomerID(+) and a.CustomerID = d.CustomerID(+) and a.CustomerID = e.CustomerID(+) ").append("group by a.CustomerID order by a.CustomerID ");
    }
    else
    {
      sb.append("from ( select d.CustomerName,e.*,nvl(c.MaxHoldQty,nvl(b.MaxHoldQty,a.MaxHoldQty)) MaxHoldQty,").append("nvl(c.MinClearDeposit,nvl(b.MinClearDeposit,a.MinClearDeposit)) MinClearDeposit,").append("nvl(c.MaxOverdraft,nvl(b.MaxOverdraft,a.MaxOverdraft)) MaxOverdraft ").append("from CustomerDefProps a,GroupTradeProps b,CustomerTradeProps c,Customer d,HisCustomerFunds e ").append("where d.CustomerID = c.CustomerID(+) and d.GroupID = b.GroupID and d.CustomerID = e.CustomerID and to_char(e.ClearDate,'yyyyMMdd')=to_char(?,'yyyyMMdd') and d.Status <> 2 ").append(") a, HisTrade c,").append("(select CustomerID, nvl(sum(HoldQty),0) HoldQty from HisHoldPosition where to_char(ClearDate,'yyyyMMdd')=to_char(?,'yyyyMMdd')  group by CustomerID) d,").append("(select CustomerID,nvl(sum(Quantity-TradeQty),0) NotTradeQty from HisOrders where to_char(ClearDate,'yyyyMMdd')=to_char(?,'yyyyMMdd') and OrderType<>4 and Status in(1,2,4) group by CustomerID) e ").append("where a.CustomerID = c.CustomerID(+) and a.CustomerID = d.CustomerID(+) and a.CustomerID = e.CustomerID(+) and to_char(?,'yyyyMMdd')=to_char(c.ClearDate(+),'yyyyMMdd') ").append("group by a.CustomerID order by a.CustomerID ");
      params = new Object[] { clearDate, clearDate, clearDate, clearDate };
    }
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getTradeErrors()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select * from AppLog where Action in('01','02','03','04','05') order by Action");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getSpecFrozenHolds()
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.*,o.*")
      .append(" from T_SpecFrozenHold a ,T_Orders o where a.A_OrderNo = o.A_OrderNo")
      .append(" order by a.A_OrderNo");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getHoldPositions(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.*,m.name firmName,(a.GageQty+a.HoldQty) as dh, ");
    
    String isQryHis = (String)conditions.getConditionValue("isQryHis");
    
    Object[] params = (Object[])null;
    conditions.removeCondition("isQryHis");
    if ((isQryHis != null) && (isQryHis.equalsIgnoreCase("true")))
    {
      String table = "a.holdtime from T_H_HoldPosition  ";
      sb.append(table);
    }
    else
    {
      String table = " a.holdtime from T_HoldPosition  ";
      sb.append(table);
      conditions.removeCondition("a.cleardate", ">=");
      conditions.removeCondition("a.cleardate", "<=");
    }
    sb.append(" a,M_firm m where m.firmID = a.firmID");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    if (isQryHis != null) {
      isQryHis.equalsIgnoreCase("true");
    }
    sb.append(" order by a.firmID");
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getHoldPositions1()
  {
    String str = "select a.*,(a.GageQty+a.HoldQty) as dh,d.commodityid,a.holdtime from T_HoldPosition a,  T_commodity d,T_Firm t where  t.FirmID=a.FirmID and a.commodityid=d.commodityid  order by a.holdtime ";
    this.log.debug("sql: " + str);
    
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getForceClose(String sectionId)
  {
    StringBuffer sb = new StringBuffer();
    
    sb
    
      .append("select a.commodityid,a.bs_flag,a.holdqty,a.evenprice,a.floatingloss,b.firmID,m.name name,b.mincleardeposit,(FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) usefulFunds,c.lastbalance leftoverPrice ")
      .append("from t_firmholdsum a, t_firm b, f_firmfunds c,m_firm m ")
      .append("where a.firmid = b.firmid and a.firmid = c.firmid and a.firmId = m.firmId ");
    if ((sectionId != null) && (!"".equals(sectionId))) {
      sb.append("and a.commodityid in(select commodityid from T_A_CommodityTradeProp where sectionid='" + sectionId + "') ");
    }
    sb.append("and (((c.lastbalance + b.MaxOverdraft) < b.mincleardeposit) or ((FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) < b.mincleardeposit )) ").append("and a.holdqty > 0 ").append("order by a.firmID");
    System.out.println("sqlsql:" + sb.toString());
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getTradeTime()
  {
    String sql = "select sectionId,name from T_A_TradeTime";
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List getTraderInfo(String firmID)
  {
    String sql = "select b.*, (FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) usefulFunds from t_firmholdsum a ,t_firm b where a.firmid=b.firmid and (FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) <b.mincleardeposit and b.firmID = '" + 
    
      firmID + "'";
    this.log.debug("sql: " + sql);
    this.log.debug("firmID: " + firmID);
    return getJdbcTemplate().queryForList(sql);
  }
  
  /**
   * @deprecated
   */
  public List getTradeResponds()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select * from TradeRespond");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public double getCumputeMargin(String commodityID, String firmID, String bs_flag, String quantity, String price)
  {
    double relBS_flag = 0.0D;
    if ((bs_flag != null) && (!"".equals(bs_flag))) {
      relBS_flag = Double.parseDouble(bs_flag);
    }
    long relQuantity = 1L;
    double relPrice = 0.0D;
    if ((price != null) && (!"".equals(price))) {
      relPrice = Double.parseDouble(price);
    }
    CumputeMarginStoredProcedure sfunc = new CumputeMarginStoredProcedure(getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_firmID", firmID);
    inputs.put("p_commodityID", commodityID);
    inputs.put("p_bs_flag", Double.valueOf(relBS_flag));
    inputs.put("p_quantity", Long.valueOf(relQuantity));
    inputs.put("p_price", Double.valueOf(relPrice));
    Map results = sfunc.execute(inputs);
    printMap(results);
    double ret = ((Double)results.get("ret")).doubleValue();
    return ret;
  }
  
  private class CumputeMarginStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "fn_t_computemargin";
    
    public CumputeMarginStoredProcedure(DataSource ds)
    {
      super("fn_t_computemargin");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 8));
      declareParameter(new SqlParameter("p_firmID", 12));
      declareParameter(new SqlParameter("p_commodityID", 12));
      declareParameter(new SqlParameter("p_bs_flag", 2));
      declareParameter(new SqlParameter("p_quantity", 2));
      declareParameter(new SqlParameter("p_price", 2));
      
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  private static void printMap(Map results)
  {
    Iterator it = results.entrySet().iterator();
    while (it.hasNext()) {}
  }
  
  public double getForceCloseMoney(Orders order)
  {
    String sql = "select FN_T_COMPUTEFORCECLOSEQTY('" + order.getFirmID() + "','" + order.getCommodityID() + "'," + order.getBS_Flag() + "," + 1 + "," + order.getSpecPrice() + "," + order.getPrice() + ") from dual";
    

    double ret = ((Double)getJdbcTemplate().queryForObject(sql, Double.class)).doubleValue();
    
    this.log.debug("ret: " + ret);
    return ret;
  }
  
  private class ComputeMarginStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_COMPUTEFORCECLOSEQTY";
    
    public ComputeMarginStoredProcedure(DataSource ds)
    {
      super("FN_T_COMPUTEFORCECLOSEQTY");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_firmid", 12));
      declareParameter(new SqlParameter("p_commodityid", 12));
      declareParameter(new SqlParameter("p_bs_flag", 2));
      declareParameter(new SqlParameter("p_quantity", 2));
      declareParameter(new SqlParameter("p_price", 2));
      declareParameter(new SqlParameter("p_forceCloseprice", 2));
      
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
  
  public int getHoldsCount(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select count(*) sum  ");
    
    String isQryHis = (String)conditions.getConditionValue("isQryHis");
    
    Object[] params = (Object[])null;
    conditions.removeCondition("isQryHis");
    if ((isQryHis != null) && (isQryHis.equalsIgnoreCase("true")))
    {
      String table = "  from T_H_HoldPosition  ";
      sb.append(table);
    }
    else
    {
      String table = "   from T_HoldPosition  ";
      sb.append(table);
      conditions.removeCondition("a.cleardate", ">=");
      conditions.removeCondition("a.cleardate", "<=");
    }
    sb.append(" a,M_firm m where m.firmID = a.firmID");
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    if (isQryHis != null) {
      isQryHis.equalsIgnoreCase("true");
    }
    sb.append(" order by a.firmID");
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    List list = getJdbcTemplate().queryForList(sb.toString(), params);
    if ((list == null) || (list.size() == 0)) {
      return 0;
    }
    Map map = (Map)list.get(0);
    return Integer.parseInt(map.get("sum"));
  }
  
  public List getHoldPositions(QueryConditions conditions, int rowStart, int rowEnd)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append(" select * from (  select rownum r,Q.* from (select a.*,m.name firmName,(a.GageQty+a.HoldQty) as dh ");
    
    String isQryHis = (String)conditions.getConditionValue("isQryHis");
    
    Object[] params = (Object[])null;
    conditions.removeCondition("isQryHis");
    if ((isQryHis != null) && (isQryHis.equalsIgnoreCase("true")))
    {
      String table = "  from T_H_HoldPosition  ";
      sb.append(table);
    }
    else
    {
      String table = "  from T_HoldPosition  ";
      sb.append(table);
      conditions.removeCondition("a.cleardate", ">=");
      conditions.removeCondition("a.cleardate", "<=");
    }
    sb.append(" a,M_firm m where m.firmID = a.firmID");
    













    String orderby = "";
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && 
      (conditions.getConditionValue("orderby", "=") != null))
    {
      orderby = (String)conditions.getConditionValue("orderby", "=");
      conditions.removeCondition("orderby", "=");
    }
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    if (orderby.equals("")) {
      sb.append(" order by a.firmID");
    } else {
      sb.append(orderby);
    }
    sb.append(" ) Q ) where r between " + rowStart + " and " + rowEnd + " ");
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public int getSpecFrozenHoldsCount()
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select count(*)  specCount")
      .append(" from T_SpecFrozenHold a ,T_Orders o where a.A_OrderNo = o.A_OrderNo")
      .append(" order by a.A_OrderNo");
    
    this.log.debug("sql: " + sb.toString());
    List list = getJdbcTemplate().queryForList(sb.toString());
    Map map = (Map)list.get(0);
    String specCount = String.valueOf(map.get("specCount"));
    System.out.println("指定平仓的总数量为：" + specCount);
    return Integer.parseInt(specCount);
  }
  
  public List getSpecFrozenHolds(QueryConditions conditions, int rowStart, int rowEnd)
  {
    Object[] params = (Object[])null;
    StringBuffer sb = new StringBuffer();
    sb.append("select * from ( ")
      .append("select rownum r, o.ConsignerID,o.commodityid,o.price,a.a_orderno,a.a_holdno,a.frozenqty ")
      .append(" from T_SpecFrozenHold a ,T_Orders o where a.A_OrderNo = o.A_OrderNo");
    
    String orderby = "";
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && 
      (conditions.getConditionValue("orderby", "=") != null))
    {
      orderby = (String)conditions.getConditionValue("orderby", "=");
      conditions.removeCondition("orderby", "=");
    }
    sb.append(orderby).append("  ) where r between " + rowStart + " and " + rowEnd + " ");
    
    this.log.debug("sql: " + sb.toString());
    System.out.println(sb.toString());
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getDetailForceClose()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select a.commodityid,a.bs_flag,a.holdqty,a.evenprice,b.firmID,b.mincleardeposit,(FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) usefulFunds,c.lastbalance leftoverPrice ")
      .append("from t_firmholdsum a, t_firm b, f_firmfunds c ")
      .append("where a.firmid = b.firmid and a.firmid = c.firmid ")
      .append("and (((c.lastbalance + b.MaxOverdraft) < b.mincleardeposit) or ((FN_F_GetRealFunds(a.FirmID,0) + b.VirtualFunds + b.MaxOverdraft) < b.mincleardeposit )) ")
      .append("and a.holdqty > 0 ")
      .append("order by a.firmID");
    
    this.log.debug("sql: " + sb.toString());
    
    return getJdbcTemplate().queryForList(sb.toString());
  }
  
  public List getHoldPositionsDetail(QueryConditions conditions, int rowStart, int rowEnd)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select *")
      .append("  from (select rownum r, Q.*")
      .append("          from (select * from (select ag.*, (s.holdqty - s.FrozenQty) maycloseQty")
      .append("                  from (select a.firmid,")
      .append("                               a.customerid,")
      .append("                               a.commodityid,")
      .append("                               a.bs_flag,")
      .append("                               sum(a.holdqty) holdqty,")
      .append("                               sum(a.openqty) openqty,")
      .append("                               sum(a.gageqty) gageqty")
      .append("                          from T_HoldPosition a");
    

    Object[] params = (Object[])null;
    sb.append("                         where 1 = 1");
    String orderby = "";
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && 
      (conditions.getConditionValue("orderby", "=") != null))
    {
      orderby = (String)conditions.getConditionValue("orderby", "=");
      conditions.removeCondition("orderby", "=");
    }
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    sb.append("                         and a.deadline is not null");
    sb.append("                         group by a.firmid,")
      .append("                                  a.customerid,")
      .append("                                  a.commodityid,")
      .append("                                  a.bs_flag) ag,")
      .append("                       t_customerholdsum s")
      .append("                 where ag.commodityid = s.commodityid")
      .append("                   and ag.customerid = s.customerid")
      .append("                   and ag.bs_flag = s.bs_flag ")
      .append("                   and ag.holdqty > 0")
      .append("                   and (s.holdqty - s.FrozenQty) > 0)");
    if (orderby.equals("")) {
      sb.append("             order by commodityid, firmid, bs_flag");
    } else {
      sb.append(orderby);
    }
    sb.append(" ) Q) where r between ").append(rowStart).append(" and ").append(rowEnd);
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    List list = getJdbcTemplate().queryForList(sb.toString(), params);
    if ((list != null) && (list.size() > 0)) {
      for (int i = 0; i < list.size(); i++)
      {
        Map map = (Map)list.get(i);
        String firmID = map.get("FIRMID").toString();
        String customerID = map.get("CUSTOMERID").toString();
        String commodityID = map.get("COMMODITYID").toString();
        String bs_flag = map.get("BS_FLAG").toString();
        
        long holdqty = Long.parseLong(map.get("HOLDQTY").toString());
        
        long specHoldqty = getSpecHoldqty(firmID, customerID, commodityID, bs_flag);
        long notradeqty = getNotradeqty(firmID, customerID, commodityID, bs_flag);
        
        holdqty = holdqty - specHoldqty - notradeqty;
        
        map.put("HOLDQTY", Long.valueOf(holdqty));
      }
    }
    return list;
  }
  
  public long getSpecHoldqty(String firmID, String customerID, String commodityID, String bs_flag)
  {
    long result = 0L;
    
    String sql = "select nvl(specHoldqty, 0) specHoldqty from (select h.firmid,h.customerid, h.commodityid, h.bs_flag,nvl(sum(s.frozenqty),0) specHoldqty from t_holdposition h join T_SpecFrozenHold s on h.a_holdno = s.a_holdno where h.remainday = 0 and h.deadline is not null and h.firmid = '" + 
    


      firmID + "' " + 
      "and h.customerid = '" + customerID + "' " + 
      "and h.commodityid = '" + commodityID + "' " + 
      "and h.bs_flag = '" + bs_flag + "'" + 
      "group by h.firmid,h.customerid, h.commodityid, h.bs_flag)";
    List<Map> list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("SPECHOLDQTY").toString());
    }
    return result;
  }
  
  public long getNotradeqty(String firmID, String customerID, String commodityID, String bs_flag)
  {
    long result = 0L;
    
    String sql = "select nvl(notradeqty, 0) notradeqty from (select o.firmid,o.customerid,o.commodityid,o.bs_flag,(nvl(sum(o.quantity),0) - nvl(sum(o.tradeqty),0)) notradeqty from t_orders o join t_commodity c on o.commodityid = c.commodityid where o.closeflag = 2 and  o.status in (1,2) and o.firmid = '" + 
    


      firmID + "' " + 
      "and o.customerid = '" + customerID + "' " + 
      "and o.commodityid = '" + commodityID + "' " + 
      "and o.bs_flag != '" + bs_flag + "'" + 
      "group by o.firmid,o.customerid,o.commodityid,o.bs_flag)";
    List list = getJdbcTemplate().queryForList(sql);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      result = Long.parseLong(map.get("NOTRADEQTY").toString());
    }
    return result;
  }
  
  public int getHoldsDetailCount(QueryConditions conditions)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select count(firmid) sum")
      .append("          from (select ag.*, (s.holdqty - s.FrozenQty) maycloseQty")
      .append("                  from (select a.firmid,")
      .append("                               a.customerid,")
      .append("                               a.commodityid,")
      .append("                               a.bs_flag,")
      .append("                               sum(a.holdqty) holdqty,")
      .append("                               sum(a.openqty) openqty,")
      .append("                               sum(a.gageqty) gageqty")
      .append("                          from T_HoldPosition a");
    
    Object[] params = (Object[])null;
    sb.append("                         where 1 = 1");
    String orderby = "";
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && 
      (conditions.getConditionValue("orderby", "=") != null))
    {
      orderby = (String)conditions.getConditionValue("orderby", "=");
      conditions.removeCondition("orderby", "=");
    }
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      params = conditions.getValueArray();
      sb.append(" and ").append(conditions.getFieldsSqlClause());
    }
    sb.append("                         and a.deadline is not null");
    sb.append("                         group by a.firmid,")
      .append("                                  a.customerid,")
      .append("                                  a.commodityid,")
      .append("                                  a.bs_flag) ag,")
      .append("                       t_customerholdsum s")
      .append("                 where ag.commodityid = s.commodityid")
      .append("                   and ag.customerid = s.customerid")
      .append("                   and ag.bs_flag = s.bs_flag ")
      .append("                   and ag.holdqty > 0")
      .append("                   and (s.holdqty - s.FrozenQty) > 0)");
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    List list = getJdbcTemplate().queryForList(sb.toString(), params);
    if ((list == null) || (list.size() == 0)) {
      return 0;
    }
    Map map = (Map)list.get(0);
    return Integer.parseInt(map.get("sum"));
  }
}
