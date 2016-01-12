package gnnt.MEBS.timebargain.mgr.dao.monitor;

import gnnt.MEBS.timebargain.mgr.util.ObjSet;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("monitorSetDao")
public class MonitorSetDao
{
  protected final Log log = LogFactory.getLog(getClass());
  private JdbcTemplate jdbcTemplate;

  public JdbcTemplate getJdbcTemplate()
  {
    return this.jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public ObjSet getQuotation(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = "select  * from(select count(a.commodityid) from t_quotation a,(select commodityid,count(*) ordercnt from t_orders group by commodityid) b,(select commodityid,count(*) tradecnt from t_trade group by t_trade.commodityid) c where a.commodityid=b.commodityid(+) and a.commodityid=c.commodityid(+)) ";

    String sql = "select  * from(select a.*,(CASE WHEN a.CurPrice = 0 then 0 else a.CurPrice - a.YesterBalancePrice END ) relSpread,b.ordercnt,c.tradecnt from t_quotation a,(select commodityid,count(*) ordercnt from t_orders group by commodityid) b,(select commodityid,count(*) tradecnt from t_trade group by t_trade.commodityid) c where a.commodityid=b.commodityid(+) and a.commodityid=c.commodityid(+)) ";

    System.out.println("relSpread=" + sql);
    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getOrders(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = "select count(A_OrderNo) from t_Orders";
    String sql = " select decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag,decode(OrderType, 1, '订立', 2, '转让') as OrderType,decode(Status, 1, '已委托', 2, '部分成交',3,'全部成交',4,'正在撤单',5,'全部撤单',6,'部分成交后撤单',7,'撤单委托成功',8,'撤单委托失败') as Status,commodityid,FirmID,Price,Quantity,A_OrderNo,to_char(ordertime, 'hh24:mi:ss') as ordertime,to_char(WithdrawTime, 'hh24:mi:ss') as WithdrawTime,(Quantity-TradeQty) as SurplusQuantity,CustomerID from t_orders ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getTrade(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = "select count(*)   from (select sum(Quantity) as Quantity,m_tradeno,a_tradeno,to_char(TradeTime, 'hh24:mi:ss') as TradeTime, FirmID,CustomerID,commodityid,decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag,decode(OrderType, 1, '订立', 2, '转让') as OrderType,Price,A_OrderNo,M_TradeNo_Opp from t_trade group by m_tradeno,a_tradeno,TradeTime,FirmID,CustomerID,commodityid,BS_Flag,OrderType,Quantity,Price,A_OrderNo,M_TradeNo_Opp) a, (select to_char(ordertime, 'hh24:mi:ss') as ordertime,Price as orderPrice,A_OrderNo from t_orders) c where c.A_OrderNo = a.a_orderno ";

    String sql = "  select a.Quantity,a.m_tradeno,a.a_tradeno,a.TradeTime,a.firmid,a.CustomerID,a.commodityid,a.BS_Flag,a.OrderType,a.Price,a.M_TradeNo_Opp,a.oppfirmId,c.A_OrderNo,c.orderPrice,c.ordertime   from (select sum(Quantity) as Quantity,m_tradeno,a_tradeno,to_char(TradeTime, 'hh24:mi:ss') as TradeTime, FirmID,CustomerID,commodityid,decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag,decode(OrderType, 1, '订立', 2, '转让') as OrderType,Price,A_OrderNo,M_TradeNo_Opp,oppfirmId from t_trade group by m_tradeno,a_tradeno,TradeTime,FirmID,CustomerID,commodityid,BS_Flag,OrderType,Quantity,Price,A_OrderNo,oppfirmId,M_TradeNo_Opp) a, (select to_char(ordertime, 'hh24:mi:ss') as ordertime,Price as orderPrice,A_OrderNo from t_orders) c where c.A_OrderNo = a.a_orderno ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getTradeStatistic(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from (select commodityid from t_trade  group by commodityid ) a,(select commodityid from t_Quotation ) b where a.commodityid=b.commodityid ";

    String sql = " select a.perQuantity,a.commodityid,b.TotalAmount,b.TotalMoney,b.ReserveCount,b.Price  from (select round(sum(quantity)/count(*),4) as perQuantity,commodityid from t_trade  group by commodityid ) a,(select TotalAmount,TotalMoney,ReserveCount,Price,commodityid from t_Quotation ) b where a.commodityid=b.commodityid ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getFirmHoldSum(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from t_FirmHoldSum ";
    String sql = " select HoldQty,FirmID,decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag,to_char(round(EvenPrice,2),'fm999,999,999,999,990.00') EvenPrice,commodityid from t_FirmHoldSum ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getFirmHoldSumByCmd(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from t_commodity a,(select sum(HoldQty) as HoldQty,round(sum(EvenPrice) / count(*), 4) as EvenPrice,commodityid,decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag  from t_FirmHoldSum group by commodityid, bs_flag) b where a.commodityid = b.commodityid(+) ";

    String sql = " select a.commodityid, nvl(b.HoldQty,0) as HoldQty, nvl(b.EvenPrice,0) as HoldQty, b.bs_flag  from t_commodity a,(select sum(HoldQty) as HoldQty,round(sum(EvenPrice) / count(*), 4) as EvenPrice,commodityid,decode(BS_Flag, 1, '买', 2, '卖') as BS_Flag  from t_FirmHoldSum group by commodityid, bs_flag) b where a.commodityid = b.commodityid(+) ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getFundsAnalysis(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from ( select a.firmid from  t_firm a, (select firmid, sum(HoldQty) as HoldQty, sum(HoldFunds) as HoldFunds   from t_FirmHoldSum   group by firmid) c,  f_firmfunds d where  a.firmid = c.firmid(+)  and a.firmid = d.firmid ) ";

    String sql = "select * from( select a.FirmID,b.unQuantity,CASE nvl(c.HoldFunds,0) WHEN 0 THEN 999.99 ELSE (case when a.runtimemargin = 0 then 1 else (case when round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 > 999.99 then 999.99 when round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 < -999.99 then -999.99 else round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 end)end)  END fundsSafeRate,decode(Status, 0, '正常',1, '禁止交易',2,'退市') as Status,a.runtimemargin,a.runtimefl,d.Balance - d.frozenfunds as usableFunds,nvl(c.HoldQty,0) as HoldQty  from  t_firm a, (select sum(Quantity - TradeQty) unQuantity,firmID from t_orders where  Status in (1,2) group by firmID) b, (select firmid, sum(HoldQty) as HoldQty, sum(HoldFunds) as HoldFunds   from t_FirmHoldSum  group by firmid) c,  f_firmfunds d   where a.firmid = c.firmid(+) and a.firmid = b.firmID(+)  and a.firmid = d.firmid) ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getAnalyseInfo(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from (select firmid from t_firm) f,     (select firmid, count(*) as orderscnt from t_orders group by firmid) a,     (select firmid,            count(*) as tradecnt,            sum(quantity) as totalTradeQuantity,           sum(Price * ContractFactor * quantity) as totalTradePrice      from t_trade a, t_Commodity b    where a.commodityid = b.commodityid     group by firmid) b,   (select firmid, sum(HoldQty) as holdQtyCnt      from t_FirmHoldSum    group by firmid) c,  (select firmid, sum(a.quantity) o_quantity    from t_trade a   where ordertype = 1   group by firmid) d, (select firmid, sum(a.quantity) l_quantity     from t_trade a   where ordertype = 2   group by firmid) e where f.firmid = a.firmid(+) and f.firmid = b.firmid(+) and f.firmid = c.firmid(+) and f.firmid = d.firmid(+) and f.firmid = e.firmid(+) ";

    String sql = " select f.firmid, nvl(a.orderscnt,0) as orderscnt, nvl(b.tradecnt,0) as tradecnt, nvl(b.totalTradeQuantity,0) as totalTradeQuantity, nvl(b.totalTradePrice,0) as totalTradePrice, nvl(c.holdQtyCnt,0) as holdQtyCnt, nvl(d.o_quantity,0) as o_quantity, nvl(e.l_quantity,0) as l_quantity  from (select firmid from t_firm) f,     (select firmid, count(*) as orderscnt from t_orders group by firmid) a,     (select firmid,            count(*) as tradecnt,            sum(quantity) as totalTradeQuantity,           sum(Price * ContractFactor * quantity) as totalTradePrice      from t_trade a, t_Commodity b    where a.commodityid = b.commodityid     group by firmid) b,   (select firmid, sum(HoldQty) as holdQtyCnt      from t_FirmHoldSum    group by firmid) c,  (select firmid, sum(a.quantity) o_quantity    from t_trade a   where ordertype = 1   group by firmid) d, (select firmid, sum(a.quantity) l_quantity     from t_trade a   where ordertype = 2   group by firmid) e where f.firmid = a.firmid(+) and f.firmid = b.firmid(+) and f.firmid = c.firmid(+) and f.firmid = d.firmid(+) and f.firmid = e.firmid(+) ";

    if ((filter != null) && (!filter.trim().equals("")))
    {
      sqlTotalCount = sqlTotalCount + filter;
      sql = sql + filter;
    }

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public ObjSet getFirmTradeQuantity(int pageSize, int pageIndex, String filter, String orderBy)
  {
    int totalCount = 0;
    String sqlTotalCount = " select count(*) from (select firmid, sum(Quantity) as Quantity from t_trade" + 
      filter + 
      " group by firmid )";

    String sql = " select firmid, sum(Quantity) as Quantity from t_trade" + 
      filter + 
      " group by firmid ";

    if ((orderBy != null) && (!orderBy.trim().equals("")))
    {
      sql = sql + " order by " + orderBy;
    }

    totalCount = this.jdbcTemplate.queryForInt(sqlTotalCount);
    this.log.debug("sqlTotalCount: " + sqlTotalCount);

    if ((pageSize == 0) || (pageIndex == 0))
    {
      pageSize = totalCount;
      pageIndex = 1;
    }

    int startCount = (pageIndex - 1) * pageSize + 1;
    int endCount = pageIndex * pageSize;

    sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + 
      "where num between " + startCount + " and " + endCount;

    this.log.debug("sql: " + sql);
    return ObjSet.getInstance(this.jdbcTemplate.queryForList(sql), 
      totalCount, pageSize, pageIndex);
  }

  public List getFirmInfo(String firmID)
  {
    String sql = "select a.*,m.name firmName,m.phone,CASE nvl(c.HoldFunds,0) WHEN 0 THEN 999.99 ELSE (case when a.runtimemargin = 0 then 1 else (case when round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 > 999.99 then 999.99 when round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 < -999.99 then -999.99 else round((a.runtimemargin + d.Balance - d.frozenfunds)/a.runtimemargin,4)*100 end)end)  END fundsSafeRate from t_firm a,(select firmid,  sum(HoldQty) as HoldQty,   sum(HoldFunds) as HoldFunds from t_FirmHoldSum  group by firmid) c, f_firmfunds d,M_firm m where a.firmid = c.firmid(+) and a.firmid = d.firmid and m.firmID = a.firmID and a.firmid = '" + 
      firmID + "'";
    return this.jdbcTemplate.queryForList(sql);
  }

  public List getFirmHold(String firmID)
  {
    String sql = "select a.* from T_FirmHoldSum a  where a.firmID = '" + 
      firmID + "' order by a.firmID";
    return this.jdbcTemplate.queryForList(sql);
  }
}