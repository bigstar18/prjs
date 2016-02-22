package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.tradeweb.dao.OrdersDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Market;
import gnnt.MEBS.timebargain.tradeweb.model.Orders;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class OrdersDAOJdbc
  extends BaseDAOJdbc
  implements OrdersDAO
{
  private Log log = LogFactory.getLog(OrdersDAOJdbc.class);
  
  public List commodity_query(Orders orders)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append(" select a.*,b.* ")
      
      .append(" from T_Commodity a  ,T_C_ExchageRate b")
      .append(
      " where a.Status=1 and a.CommodityID = b.CommodityID AND  a.MarketDate<=sysdate ");
    
    Object[] params = (Object[])null;
    List lst = new ArrayList();
    if (!StringUtils.isEmpty(orders.getCommodityID()))
    {
      sb.append("and a.CommodityID=? ");
      lst.add(orders.getCommodityID());
    }
    sb.append(" order by a.DisplayNum ");
    params = lst.toArray();
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List holding_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.firmid,       a.commodityid,       a.bs_flag,       sum(a.holdqty) holdqty,       sum(a.holdfunds) holdfunds,       sum(a.floatingloss) floatingloss,       decode(sum(a.holdqty),0,0,sum(a.evenprice * a.holdqty) / sum(a.holdqty)) as eventprice,       decode(sum(a.holdqty),0,0,sum(a.OpenEvenPrice  * a.holdqty) / sum(a.holdqty)) as OpenEvenPrice,       sum(a.holdmargin) holdmargin,       sum(a.frozenqty) frozenqty  from T_FirmHoldSum a  where 1 = 1 ");
    









    Object[] params = (Object[])null;
    List lst = new ArrayList();
    if (!StringUtils.isEmpty(orders.getTraderID()))
    {
      sb.append(" and a.firmid =? ");
      lst.add(prvlg.getFirmId());
    }
    if (!StringUtils.isEmpty(orders.getCommodityID()))
    {
      sb.append(" and a.CommodityID=? ");
      lst.add(orders.getCommodityID());
    }
    sb.append(" group by a.firmid, a.commodityid, a.bs_flag ");
    sb.append(" order by a.CommodityID ");
    params = lst.toArray();
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List holding_detail_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append("select h.*, nvl(a.price, 0) StopLossPrice, nvl(b.price, 0) StopProfitPrice  from t_holdposition h,       (select o.holdno, o.price          from t_orders o         where o.stoppl_flag = 1           and o.status = 1) a,       (select o.holdno, o.price          from t_orders o         where o.stoppl_flag = 2           and o.status = 1) b where h.holdno = a.holdno(+)   and h.holdno = b.holdno(+)");
    











    Object[] params = (Object[])null;
    List lst = new ArrayList();
    if (!StringUtils.isEmpty(orders.getTraderID()))
    {
      sb.append(" and h.firmid =? ");
      lst.add(prvlg.getFirmId());
    }
    if (!StringUtils.isEmpty(orders.getCommodityID()))
    {
      sb.append(" and h.CommodityID=? ");
      lst.add(orders.getCommodityID());
    }
    sb.append(" order by h.CommodityID ");
    params = lst.toArray();
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List tradequery(long lastTradeNo)
  {
    String sql = "select * from T_Trade a Where TradeNo>? order by TradeNo";
    
    List linked = new LinkedList();
    Object[] params = (Object[])null;
    params = new Object[] { Long.valueOf(lastTradeNo) };
    linked = getJdbcTemplate()
      .query(sql, params, new TradeRowMapper(null));
    return linked;
  }
  
  public List getFirmList()
  {
    String sql = "Select FirmID from T_Firm ";
    return getJdbcTemplate().queryForList(sql);
  }
  
  public List firm_info(Privilege prvg)
  {
    List firmList = new ArrayList();
    
    StringBuffer sb = new StringBuffer();
    if (prvg.getFirmType().equals("0")) {
      sb.append("select a.lastbalance,a.balance,a.RuntimeFundIO in_out_Amount, g.frozenfunds ztIOF,b.cleardelayfee,e.RiskRate,e.Status c_Status,d.Status,b.RuntimeFL PL,d.Name,f.Name, ").append("b.runtimeClosePL close_pl,").append("b.runtimefee tradefee,").append("b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.RuntimeSettleMargin,").append("a.frozenmargin frozenMargin,").append("a.frozenfee frozenFee,").append("a.frozenfunds, ").append("(a.balance - a.frozenfunds) usefulFund ").append("from F_FIRMFUNDS a,T_Firm b,(select firmid,Status,Name from M_Trader where  traderID=?) d,T_Customer e,m_firm f,(select sum(frozenfunds) frozenfunds,moduleid ,firmId from f_frozenfunds where moduleid = 1 group by firmId,moduleid) g ").append(
        " where a.firmid = b.firmid  and e.firmid=b.firmid and b.firmID = ? and f.firmid=a.firmid and g.firmId(+) = b.firmId ");
    } else {
      sb.append("select a.lastbalance,a.balance,a.RuntimeFundIO in_out_Amount, g.frozenfunds ztIOF,b.cleardelayfee,e.RiskRate,e.Status c_Status,d.Status,b.RuntimeFL PL,d.Name tradeName,f.Name, ").append("b.runtimeClosePL close_pl,").append("b.runtimefee tradefee,").append("b.ClearMargin,b.clearfl,b.runtimemargin,b.runtimefl,b.RuntimeSettleMargin,").append("a.frozenmargin frozenMargin,").append("a.frozenfee frozenFee,").append("a.frozenfunds, ").append("(a.balance - a.frozenfunds) usefulFund ").append("from F_FIRMFUNDS a,T_Firm b,(select firmid,Status,Name from M_Trader where  traderID=?) d,t_Compmember e,m_firm  f,(select sum(frozenfunds) frozenfunds,moduleid ,firmId from f_frozenfunds where moduleid = 1 group by firmId,moduleid) g ").append(
        " where a.firmid = b.firmid  and e.m_firmid=b.firmid and b.firmID = ? and f.firmid=a.firmid and d.firmid=b.firmid and g.firmId(+) = b.firmId ");
    }
    Object[] params = (Object[])null;
    params = new Object[] { prvg.getTraderID(), prvg.getFirmId() };
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    Map map1 = getJdbcTemplate().queryForMap(sb.toString(), params);
    
    map1.put("FirmID", prvg.getFirmId());
    
    firmList.add(map1);
    
    return firmList;
  }
  
  public List firm_funds_info(Privilege prvg)
  {
    List firmList = new ArrayList();
    StringBuffer sb = new StringBuffer();
    
    sb
      .append("\nselect T_CompMember.NetFL,\n                 T_CompMember.FL_Accept,\n                   T_CompMember.FL_Hedge,\n                   F_FirmFunds.Balance - F_FirmFunds.FrozenFunds Balance,\n                   R_CM_Threshold.WarnTh,\n                   R_CM_Threshold.CU_F_WarnTh,\n                   R_CM_Threshold.FrozenTh,\n                   R_CM_Threshold.NetHoldWarnTh,\n                   decode(M_Memberinfo.Membertype,'B', R_Thresholdargs.Bm_Minriskfund, R_Thresholdargs.Cm_Minriskfund) Minriskfund,\n                   T_CompMember.Status,\n                   nvl((select sum(NetHold)\n                         from T_CM_HoldSum\n                        where M_FirmID = T_CompMember.M_FirmID),\n                       0) NetHold,\n                   nvl((select sum(ClosePL_Accept)\n                         from T_CM_HoldSum\n                        where M_FirmID = T_CompMember.M_FirmID),\n                       0) ClosePL_Accept,\n                   nvl((select sum(MaxNetHold)\n                         from T_CM_HoldSum\n                        where M_FirmID = T_CompMember.M_FirmID),\n                       0) MaxNetHold\n              from T_CompMember   T_CompMember,\n                   F_FirmFunds    F_FirmFunds,\n                   R_CM_Threshold R_CM_Threshold,\n                   R_Thresholdargs R_Thresholdargs,\n                   M_Memberinfo M_Memberinfo\n             where F_FirmFunds.firmid = T_CompMember.M_FirmID\n               and R_CM_Threshold.M_FirmID = T_CompMember.M_FirmID\n               and M_Memberinfo.Memberno=T_CompMember.M_FirmID\n               and T_CompMember.M_FirmID = ?");
    































    Object[] params = (Object[])null;
    params = new Object[] { prvg.getFirmId() };
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    Map map1 = getJdbcTemplate().queryForMap(sb.toString(), params);
    
    map1.put("FirmID", prvg.getFirmId());
    map1.put("Name", prvg.getFirmName());
    firmList.add(map1);
    
    return firmList;
  }
  
  public List customer_order_query(Privilege prvlg, String commodityID)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append(
      "select a.CommodityID,nvl(b.EvenPrice,0) BEvenPrice,nvl(b.HoldFunds,0)  BHoldFunds,nvl(b.HoldQty,0) BHoldQty,nvl(b.FloatingLoss,0) BFloatingLoss,nvl(c.EvenPrice,0) SEvenPrice,nvl(c.HoldFunds,0)  SHoldFunds,nvl(c.HoldQty,0) SHoldQty,nvl(c.FloatingLoss,0) SFloatingLoss ")
      
      .append(
      " from (select distinct O_FirmID,CommodityID from T_O_HoldSum where O_FirmID=? ) a,(select * from T_O_HoldSum where BS_Flag=1 and O_FirmID=?  ) b,(select * from T_O_HoldSum where BS_Flag=2  and O_FirmID=? ) c ")
      

      .append(
      " where a.CommodityID = b.CommodityID(+) and a.CommodityID = c.CommodityID(+) ");
    Object[] params = (Object[])null;
    List lst = new ArrayList();
    lst.add(prvlg.getFirmId());
    lst.add(prvlg.getFirmId());
    lst.add(prvlg.getFirmId());
    if (!StringUtils.isEmpty(commodityID))
    {
      sb.append(" and a.CommodityID=? ");
      lst.add(commodityID);
    }
    params = lst.toArray();
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List other_firm_query(Privilege prvlg)
  {
    StringBuffer sb = new StringBuffer();
    if (prvlg.getFirmType().equals("0")) {
      sb.append("select a.MemberNo,b.Name").append(" from M_CustomerInfo a,M_MemberInfo b ").append(
        " where a.CustomerNo=? and a.MemberNo = b.MemberNo ");
    } else {
      sb.append("select b.MemberNo,b.Name").append(" from M_MemberRelation a,M_S_MemberInfo b ").append(
        " where a.MemberNo=? and b.MemberNo = a.S_MemberNo order by a.sortno ");
    }
    Object[] params = (Object[])null;
    
    params = new Object[] { prvlg.getFirmId() };
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List other_firm_query_S(Privilege prvlg)
  {
    StringBuffer sb = new StringBuffer();
    if (prvlg.getFirmType().equals("0")) {
      sb.append("select a.MemberNo,b.Name").append(" from M_CustomerInfo a,M_MemberInfo b ").append(
        " where a.CustomerNo=? and a.MemberNo = b.MemberNo ");
    } else {
      sb.append("select * from (select b.MemberNo,b.Name,c.Status ").append(" from M_MemberRelation a,M_S_MemberInfo b,t_specialmember c ").append(
        " where a.MemberNo=? and b.MemberNo = a.S_MemberNo  and c.M_FirmID = a.S_MemberNo  and c.Status='N'  order by a.sortno)  where rownum<2 ");
    }
    Object[] params = (Object[])null;
    
    params = new Object[] { prvlg.getFirmId() };
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List firm_holdsum(Privilege prvlg)
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append("select * from T_CM_HoldSum t where t.m_firmid=? ");
    
    Object[] params = (Object[])null;
    params = new Object[] { prvlg.getFirmId() };
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List my_weekorder_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return order_query(orders, null, prvlg, sc);
  }
  
  public List my_order_query(Orders orders, Privilege prvlg, SortCondition sc)
  {
    return order_query(orders, null, prvlg, sc);
  }
  
  public List my_order_query(Orders orders, Privilege prvlg)
  {
    StringBuffer sb = new StringBuffer();
    Object[] params = (Object[])null;
    sb.append("select a.* ").append("from T_Orders a ").append(
      "where  a.Status in(1,2)");
    if (orders.getOrderNo() != null)
    {
      sb.append("and OrderNo=? ");
      params = new Object[] { orders.getOrderNo() };
    }
    else
    {
      List lst = new ArrayList();
      if (!StringUtils.isEmpty(orders.getTraderID()))
      {
        sb.append(" and firmid =?");
        lst.add(prvlg.getFirmId());
      }
      if (!StringUtils.isEmpty(orders.getCommodityID()))
      {
        sb.append("and CommodityID=? ");
        lst.add(orders.getCommodityID());
      }
      if (orders.getBS_Flag() != null)
      {
        sb.append("and BS_Flag=? ");
        lst.add(orders.getBS_Flag());
      }
      params = lst.toArray();
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  private List order_query(Orders orders, String tjStr, Privilege prvlg, SortCondition sc)
  {
    StringBuffer sb = new StringBuffer();
    Object[] params = (Object[])null;
    
    String startNum = sc.getStartNu();
    
    String reccnt = sc.getReccnt();
    
    String sortfLD = sc.getSortfLD();
    
    String isdesc = sc.getIsdesc() == 0 ? "asc" : "desc";
    if ((!"0".equals(startNum)) && (!"".equals(startNum)))
    {
      this.log.debug("==> 分页SQL,startNum=" + startNum);
      sb.append("select b.rownum r,b.* from ( select a.* ").append(
        "from T_Orders a where 1=1 ");
      if (!StringUtils.isEmpty(tjStr)) {
        sb.append(tjStr).append(" ");
      }
      if (orders.getOrderNo() != null)
      {
        sb.append("and OrderNo=? ");
        params = new Object[] { orders.getOrderNo() };
      }
      else
      {
        List lst = new ArrayList();
        if (!StringUtils.isEmpty(orders.getTraderID()))
        {
          sb.append(" and firmid =?");
          lst.add(prvlg.getFirmId());
        }
        if (!StringUtils.isEmpty(orders.getCommodityID()))
        {
          sb.append("and CommodityID=? ");
          lst.add(orders.getCommodityID());
        }
        if (orders.getBS_Flag() != null)
        {
          sb.append("and BS_Flag=? ");
          lst.add(orders.getBS_Flag());
        }
        if (!"queryAll".equalsIgnoreCase(orders.getUpdateTime()))
        {
          sb.append(" and UpdateTime>? ");
          lst.add(new Timestamp(
            Long.parseLong(orders.getUpdateTime())));
        }
        params = lst.toArray();
      }
      sb.append(" order by ").append(" " + sortfLD).append(" " + isdesc + ") b where rownum<=").append(reccnt).append(" and r>").append(startNum);
    }
    else
    {
      this.log.debug("==> 不分页SQL");
      sb.append("select a.* ").append("from T_Orders a where 1=1 ");
      if (!StringUtils.isEmpty(tjStr)) {
        sb.append(tjStr).append(" ");
      }
      if (orders.getOrderNo() != null)
      {
        sb.append("and OrderNo=? ");
        params = new Object[] { orders.getOrderNo() };
      }
      else
      {
        List lst = new ArrayList();
        if (!StringUtils.isEmpty(orders.getTraderID()))
        {
          sb.append(" and firmid =?");
          lst.add(prvlg.getFirmId());
        }
        if (!StringUtils.isEmpty(orders.getCommodityID()))
        {
          sb.append("and CommodityID=? ");
          lst.add(orders.getCommodityID());
        }
        if (orders.getBS_Flag() != null)
        {
          sb.append("and BS_Flag=? ");
          lst.add(orders.getBS_Flag());
        }
        if (!"queryAll".equalsIgnoreCase(orders.getUpdateTime()))
        {
          sb.append(" and UpdateTime>? ");
          lst.add(new Timestamp(
            Long.parseLong(orders.getUpdateTime())));
        }
        params = lst.toArray();
      }
    }
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    System.out.println("------------------------------------------------------------------------");
    System.out.println("sb.toString():/n" + sb.toString());
    System.out
      .println("------------------------------------------------------------------------");
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List market_query(Market market)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select * from T_A_Market where Status=1 ");
    Object[] params = (Object[])null;
    List lst = new ArrayList();
    if (!StringUtils.isEmpty(market.getMarketCode()))
    {
      sb.append("and MarketCode=? ");
      lst.add(market.getMarketCode());
    }
    params = lst.toArray();
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List commodity_data_query(Orders orders)
  {
    StringBuffer sb = new StringBuffer();
    sb
      .append(
      "select a.CommodityID,a.HighPrice,a.LowPrice,a.CurPrice,a.CreateTime,b.MinPriceMove ")
      .append(
      " from T_Quotation a,T_Commodity b where b.Status=1 and a.CommodityID=b.CommodityID ");
    
    Object[] params = (Object[])null;
    if ((orders != null) && (!StringUtils.isEmpty(orders.getCommodityID())))
    {
      sb.append(" and a.CommodityID=? ");
      params = new Object[] { orders.getCommodityID() };
    }
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public List getBroadcastList(long lastNo)
  {
    String sql = "select firmid, max(id) id  from (select firmid, id          from m_firm a,               (select nvl(max(id), -1) id                  from t_oknotice                 where RecipientType = 'C'                   and NoticeTarget = 'A') b         where a.firmtype = 'C'           and b.id>?        union        select customerno firmid, id          from m_customerinfo a,               (select nvl(max(id), -1) id, Recipient                  from t_oknotice                 where RecipientType = 'C'                   and NoticeTarget = 'M'                 group by Recipient) b         where a.memberno = b.Recipient           and b.id>?        union        select a.firmid, id          from m_trader a,               (select Recipient traderid, id                  from (select nvl(max(id), -1) id, Recipient                          from t_oknotice                         where RecipientType = 'C'                           and NoticeTarget = 'N'                         group by Recipient)) b         where a.traderid = b.traderid           and b.id>?        union        select firmid, id          from m_firm a,               (select nvl(max(id), -1) id, Recipient                  from t_oknotice                 where RecipientType = 'M'                   and NoticeTarget = 'A'  group by Recipient ) b         where a.firmtype = 'M'           and b.id>?        union        select Recipient firmid, id          from (select nvl(max(id), -1) id, Recipient                  from t_oknotice                 where RecipientType = 'M'                   and NoticeTarget = 'M'                 group by Recipient) b         where b.id>?        union        select customerno firmid, id          from m_customerinfo a,               (select nvl(max(id), -1) id, Recipient                  from t_oknotice                 where RecipientType = 'O'                   and NoticeTarget = 'A'                   and IsIncludeSub = 'Y'                 group by Recipient) b         where a.memberno = b.Recipient           and b.id>?        union        select customerno firmid, id          from m_b_customermappingbroker a,               (select nvl(max(id), -1) id, Recipient                  from t_oknotice                 where RecipientType = 'O'                   and NoticeTarget = 'M'                   and IsIncludeSub = 'Y'                 group by Recipient) b         where a.OrganizationNO = b.Recipient           and b.id>?) group by firmid";
    

































































    List linked = new LinkedList();
    Object[] params = (Object[])null;
    params = new Object[] { Long.valueOf(lastNo), Long.valueOf(lastNo), Long.valueOf(lastNo), Long.valueOf(lastNo), Long.valueOf(lastNo), Long.valueOf(lastNo), 
      Long.valueOf(lastNo) };
    linked = getJdbcTemplate().queryForList(sql, params);
    return linked;
  }
  
  public String getStatus(Privilege privilege)
  {
    String statusSql = "";
    if (privilege.getFirmType().equals("0")) {
      statusSql = "select a.status from t_customer a where a.firmid=? ";
    } else {
      statusSql = "select a.status from t_compmember a where a.m_firmId=? ";
    }
    return (String)getJdbcTemplate().queryForObject(statusSql, 
      new Object[] { privilege.getFirmId() }, String.class);
  }
  
  public Privilege getTradePrivilege(TraderInfo info)
  {
    Privilege privilege = new Privilege();
    String traderID = info.traderId;
    String firmID = info.firmId == null ? "" : info.firmId;
    privilege.setTraderID(traderID);
    privilege.setFirmId(firmID);
    privilege.setTraderName(info.traderName);
    privilege.setFirmName(info.firmName);
    
    privilege.setTraderType(info.type);
    privilege.setSessionID(Long.valueOf(info.auSessionId));
    

    String defFirmCode = "Def_Member";
    
    String m_FirmSql = "select FirmType from m_firm where FirmID=? ";
    Map m_FirmMap = getJdbcTemplate().queryForMap(m_FirmSql, 
      new Object[] { firmID });
    if (m_FirmMap != null)
    {
      String firmType = (String)m_FirmMap.get("FirmType");
      if (firmType.equals("S")) {
        privilege.setFirmType("2");
      } else if (firmType.equals("M")) {
        privilege.setFirmType("1");
      } else {
        privilege.setFirmType("0");
      }
    }
    String statusSql = "";
    if (privilege.getFirmType().equals("0")) {
      statusSql = "select a.status from t_customer a where a.firmid=? ";
    } else {
      statusSql = "select a.status,b.membertype from t_compmember a,m_memberinfo b where b.memberno=a.m_firmId and a.m_firmId=? ";
    }
    Map statusMap = getJdbcTemplate().queryForMap(statusSql, 
      new Object[] { privilege.getFirmId() });
    
    privilege.setStatus((String)statusMap.get("status"));
    if (privilege.getFirmType().equals("1")) {
      privilege.setMemberType((String)statusMap.get("membertype"));
    }
    if ((privilege.getStatus().equals("D")) || (
      (privilege.getStatus().equals("F")) && 
      (privilege.getFirmType().equals("0")))) {
      return privilege;
    }
    if (privilege.getFirmType().equals("0"))
    {
      StringBuffer sb = new StringBuffer();
      sb.append("select a.MemberNo,b.Name").append(
        " from M_CustomerInfo a,M_MemberInfo b ").append(
        " where a.CustomerNo=? and a.MemberNo = b.MemberNo ");
      
      Object[] params = (Object[])null;
      params = new Object[] { privilege.getFirmId() };
      
      this.log.debug("sql: " + sb.toString());
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          this.log.debug("params[" + i + "]: " + params[i]);
        }
      }
      List list = getJdbcTemplate().queryForList(sb.toString(), params);
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        privilege.setM_FirmID((String)map.get("MemberNo"));
        privilege.setM_FirmName((String)map.get("Name"));
      }
      else
      {
        privilege.setM_FirmID("");
        privilege.setM_FirmName("");
      }
    }
    else
    {
      StringBuffer sb = new StringBuffer();
      sb
        .append("select b.MemberNo,b.Name")
        .append(" from M_MemberRelation a,M_S_MemberInfo b ")
        .append(
        " where a.MemberNo=? and b.MemberNo = a.S_MemberNo order by a.sortno");
      Object[] params = (Object[])null;
      params = new Object[] { privilege.getFirmId() };
      
      this.log.debug("sql: " + sb.toString());
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          this.log.debug("params[" + i + "]: " + params[i]);
        }
      }
      List list = getJdbcTemplate().queryForList(sb.toString(), params);
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        privilege.setM_FirmID((String)map.get("MemberNo"));
        privilege.setM_FirmName((String)map.get("Name"));
      }
      else
      {
        privilege.setM_FirmID("");
        privilege.setM_FirmName("");
      }
      defFirmCode = "Def_S_Member";
    }
    String margain_sql = "select CommodityID, MarginAlgr,TradeMargin from T_C_Margin_RT where FirmID=?";
    
    List mar_List = getJdbcTemplate().queryForList(margain_sql, 
      new Object[] { firmID });
    if ((mar_List != null) && (mar_List.size() > 0))
    {
      Map firm_MarginRate = new HashMap();
      for (int i = 0; i < mar_List.size(); i++)
      {
        Map map = (Map)mar_List.get(i);
        Map value_Map = new HashMap();
        value_Map.put("MarginAlgr", (BigDecimal)map.get("MarginAlgr"));
        value_Map.put("TradeMargin", 
          (BigDecimal)map.get("TradeMargin"));
        firm_MarginRate.put((String)map.get("CommodityID"), value_Map);
      }
      privilege.setFirm_MarginRate(firm_MarginRate);
    }
    String fee_sql = " select CommodityID,FeeAlgr,FeeRate,FeeMode from T_C_Fee_RT where firmID=? ";
    List fee_List = getJdbcTemplate().queryForList(fee_sql, 
      new Object[] { firmID });
    if ((fee_List != null) && (fee_List.size() > 0))
    {
      Map firm_FeeRate = new HashMap();
      for (int i = 0; i < fee_List.size(); i++)
      {
        Map map = (Map)fee_List.get(i);
        Map value_Map = new HashMap();
        value_Map.put("FeeAlgr", (BigDecimal)map.get("FeeAlgr"));
        value_Map.put("FeeRate", (BigDecimal)map.get("FeeRate"));
        value_Map.put("FeeMode", (BigDecimal)map.get("FeeMode"));
        
        firm_FeeRate.put((String)map.get("CommodityID"), value_Map);
      }
      privilege.setFirm_FeeRate(firm_FeeRate);
    }
    String delayTrade_sql = " select commodityId,delaytradeType,delayTradeTime,isslippoint from t_c_delayTrade_rt where firmiD = ? ";
    List delayTrade_list = getJdbcTemplate().queryForList(delayTrade_sql, 
      new Object[] { firmID });
    if ((delayTrade_list != null) && (delayTrade_list.size() > 0))
    {
      Map delayTrade_map = new HashMap();
      for (int i = 0; i < delayTrade_list.size(); i++)
      {
        Map map = (Map)delayTrade_list.get(i);
        Map value_map = new HashMap();
        value_map.put("delaytradeType", (BigDecimal)map.get("delaytradeType"));
        value_map.put("delayTradeTime", (BigDecimal)map.get("delayTradeTime"));
        if (map.get("isslippoint").toString().equals("T")) {
          value_map.put("isslippoint", new BigDecimal(0));
        } else if (map.get("isslippoint").toString().equals("N")) {
          value_map.put("isslippoint", new BigDecimal(1));
        } else {
          value_map.put("isslippoint", new BigDecimal(2));
        }
        delayTrade_map.put(String.valueOf(map.get("CommodityID")), value_map);
      }
      privilege.setFirmDelayTrade(delayTrade_map);
    }
    String delayFee_sql = "select CommodityID, DelayFeeAlgr from T_Commodity where Status=1 ";
    List delayFee_List = getJdbcTemplate().queryForList(delayFee_sql);
    if ((delayFee_List != null) && (delayFee_List.size() > 0))
    {
      Map firm_DelayFee = new HashMap();
      for (int i = 0; i < delayFee_List.size(); i++)
      {
        Map map = (Map)delayFee_List.get(i);
        Map value_Map = new HashMap();
        value_Map.put("DelayFeeAlgr", 
          (BigDecimal)map.get("DelayFeeAlgr"));
        
        String commodityID = (String)map.get("CommodityID");
        
        String sub_sql = "select a.StepNo,a.FEEALGR DelayFeeAlgr,decode(a.FEEALGR,1,a.DelayFee,2,a.DelayFee*100,a.DelayFee) DelayFee,b.lowvalue,b.stepvalue from T_C_DelayFee a,T_A_StepDictionary b where  a.StepNo=b.StepNo  and b.laddercode='DelayDays' and a.FirmID=? and a.commodityID=? ";
        List sub_List = getJdbcTemplate().queryForList(sub_sql, 
          new Object[] { privilege.getM_FirmID(), commodityID });
        if ((sub_List == null) || (sub_List.size() == 0)) {
          sub_List = getJdbcTemplate().queryForList(sub_sql, 
            new Object[] { defFirmCode, commodityID });
        }
        if ((sub_List != null) && (sub_List.size() > 0))
        {
          List stepList = new ArrayList();
          for (int j = 0; j < sub_List.size(); j++)
          {
            Map sub_map = (Map)sub_List.get(j);
            stepList.add(sub_map);
          }
          value_Map.put("StepList", stepList);
        }
        firm_DelayFee.put(commodityID, value_Map);
      }
      privilege.setFirm_DelayFee(firm_DelayFee);
    }
    String sql = "Select CommodityID,Cancel_L_Open,Cancel_StopLoss,Cancel_StopProfit,M_B_Open,M_B_Close,L_B_Open,B_StopLoss,B_StopProfit,M_S_Open,M_S_Close,L_S_Open,S_StopLoss,S_StopProfit from T_C_TradeAuth_RT where firmID=? ";
    

    this.log.debug("sql:" + sql);
    this.log.debug("Param1: FirmID= " + firmID);
    Object[] param = { firmID };
    List list = getJdbcTemplate().queryForList(sql, param);
    if ((list != null) && (list.size() > 0))
    {
      Map firmTrade_Privilege = new HashMap();
      for (int i = 0; i < list.size(); i++)
      {
        Map map = (Map)list.get(i);
        Map<String, BigDecimal> value_Map = new HashMap();
        value_Map.put("Cancel_L_Open", 
          (BigDecimal)map.get("Cancel_L_Open"));
        value_Map.put("Cancel_StopLoss", 
          (BigDecimal)map.get("Cancel_StopLoss"));
        value_Map.put("Cancel_StopProfit", 
          (BigDecimal)map.get("Cancel_StopProfit"));
        value_Map.put("M_B_Open", (BigDecimal)map.get("M_B_Open"));
        value_Map.put("M_B_Close", (BigDecimal)map.get("M_B_Close"));
        value_Map.put("L_B_Open", (BigDecimal)map.get("L_B_Open"));
        value_Map.put("B_StopLoss", (BigDecimal)map.get("B_StopLoss"));
        value_Map.put("B_StopProfit", 
          (BigDecimal)map.get("B_StopProfit"));
        value_Map.put("M_S_Open", (BigDecimal)map.get("M_S_Open"));
        value_Map.put("M_S_Close", (BigDecimal)map.get("M_S_Close"));
        value_Map.put("L_S_Open", (BigDecimal)map.get("L_S_Open"));
        value_Map.put("S_StopLoss", (BigDecimal)map.get("S_StopLoss"));
        value_Map.put("S_StopProfit", 
          (BigDecimal)map.get("S_StopProfit"));
        
        firmTrade_Privilege.put(map.get("CommodityID"), value_Map);
      }
      privilege.setFirmTradePrivilege(firmTrade_Privilege);
    }
    checkMemberPrivilege(privilege);
    

    String noDisplay_sql = "Select  distinct CommodityID,Display from T_C_TradeAuth_RT where Display=0 and (firmID=? or firmID=?) ";
    this.log.debug("noDisplay_sql:" + noDisplay_sql);
    List noDisplayList = getJdbcTemplate().queryForList(noDisplay_sql, 
      new Object[] { firmID, privilege.getM_FirmID() });
    if ((noDisplayList != null) && (noDisplayList.size() > 0)) {
      for (int i = 0; i < noDisplayList.size(); i++)
      {
        Map map = (Map)noDisplayList.get(i);
        privilege.getNoDisplayPrivilege().put(
          (String)map.get("CommodityID"), null);
      }
    }
    String quotePoint_sql = "select CommodityID,QuotePoint_B,QuotePoint_S, QuotePoint_B_RMB,QuotePoint_S_RMB from T_C_QuotePoint_RT where M_FirmID=?";
    

    this.log.debug("quotePoint_sql:" + quotePoint_sql);
    this.log.debug("Param1: M_FirmID= " + privilege.getM_FirmID());
    List quotePoint_List = getJdbcTemplate().queryForList(quotePoint_sql, 
      new Object[] { privilege.getM_FirmID() });
    if ((quotePoint_List != null) && (quotePoint_List.size() > 0))
    {
      Map quotePointMap = new HashMap();
      for (int i = 0; i < quotePoint_List.size(); i++)
      {
        Map map = (Map)quotePoint_List.get(i);
        Map value_Map = new HashMap();
        value_Map.put("QuotePoint_B_RMB", 
          (BigDecimal)map.get("QuotePoint_B_RMB"));
        
        value_Map.put("QuotePoint_S_RMB", 
          (BigDecimal)map.get("QuotePoint_S_RMB"));
        
        value_Map.put("QuotePoint_B", 
          (BigDecimal)map.get("QuotePoint_B"));
        
        value_Map.put("QuotePoint_S", 
          (BigDecimal)map.get("QuotePoint_S"));
        
        quotePointMap.put((String)map.get("CommodityID"), value_Map);
      }
      privilege.setQuotePoint(quotePointMap);
    }
    if (privilege.getFirmType().equals("1"))
    {
      String my_QuotePoint_sql = "select CommodityID,QuotePoint_B,QuotePoint_S, QuotePoint_B_RMB,QuotePoint_S_RMB from T_C_QuotePoint_RT where M_FirmID=?";
      

      this.log.debug("my_QuotePoint_sql:" + my_QuotePoint_sql);
      this.log.debug("Param1: M_FirmID= " + privilege.getFirmId());
      List my_QuotePoint_List = getJdbcTemplate().queryForList(
        my_QuotePoint_sql, new Object[] { privilege.getFirmId() });
      if ((my_QuotePoint_List != null) && (my_QuotePoint_List.size() > 0))
      {
        Map quotePointMap = new HashMap();
        for (int i = 0; i < my_QuotePoint_List.size(); i++)
        {
          Map map = (Map)my_QuotePoint_List.get(i);
          Map value_Map = new HashMap();
          value_Map.put("QuotePoint_B_RMB", 
            (BigDecimal)map.get("QuotePoint_B_RMB"));
          
          value_Map.put("QuotePoint_S_RMB", 
            (BigDecimal)map.get("QuotePoint_S_RMB"));
          
          value_Map.put("QuotePoint_B", 
            (BigDecimal)map.get("QuotePoint_B"));
          
          value_Map.put("QuotePoint_S", 
            (BigDecimal)map.get("QuotePoint_S"));
          
          quotePointMap.put((String)map.get("CommodityID"), 
            value_Map);
        }
        privilege.setMyQuotePoint(quotePointMap);
      }
    }
    String orderPoint_sql = "select CommodityID, StopLossPoint,StopProfitPoint,L_Open_Point,M_OrderPoint,Min_M_OrderPoint,Max_M_OrderPoint from T_C_OrderPoint_RT  where FirmID=?";
    

    List orderPoint_List = getJdbcTemplate().queryForList(orderPoint_sql, 
      new Object[] { firmID });
    if ((orderPoint_List != null) && (orderPoint_List.size() > 0))
    {
      Map orderPointMap = new HashMap();
      for (int i = 0; i < orderPoint_List.size(); i++)
      {
        Map map = (Map)orderPoint_List.get(i);
        Map value_Map = new HashMap();
        value_Map.put("StopLossPoint", 
          (BigDecimal)map.get("StopLossPoint"));
        value_Map.put("StopProfitPoint", 
          (BigDecimal)map.get("StopProfitPoint"));
        value_Map.put("L_Open_Point", 
          (BigDecimal)map.get("L_Open_Point"));
        value_Map.put("M_OrderPoint", 
          (BigDecimal)map.get("M_OrderPoint"));
        value_Map.put("Min_M_OrderPoint", 
          (BigDecimal)map.get("Min_M_OrderPoint"));
        value_Map.put("Max_M_OrderPoint", 
          (BigDecimal)map.get("Max_M_OrderPoint"));
        orderPointMap.put((String)map.get("CommodityID"), value_Map);
      }
      privilege.setOrderPoint(orderPointMap);
    }
    String holdQty_sql = "select CommodityID, OneMaxOrderQty,OneMinOrderQty,MaxHOldQty from T_C_HoldQty_RT where FirmID=?";
    
    List holdQty = getJdbcTemplate().queryForList(holdQty_sql, 
      new Object[] { firmID });
    if ((holdQty != null) && (holdQty.size() > 0))
    {
      Map holdQtytMap = new HashMap();
      for (int i = 0; i < holdQty.size(); i++)
      {
        Map map = (Map)holdQty.get(i);
        Map value_Map = new HashMap();
        value_Map.put("OneMaxOrderQty", 
          (BigDecimal)map.get("OneMaxOrderQty"));
        value_Map.put("OneMinOrderQty", 
          (BigDecimal)map.get("OneMinOrderQty"));
        value_Map.put("MaxHOldQty", (BigDecimal)map.get("MaxHOldQty"));
        holdQtytMap.put((String)map.get("CommodityID"), value_Map);
      }
      privilege.setHoldQty(holdQtytMap);
    }
    return privilege;
  }
  
  public Date getDBTime()
  {
    String sql = "select sysdate from dual";
    
    this.log.debug("sql" + sql);
    
    return (Date)getJdbcTemplate().queryForObject(sql, Date.class);
  }
  
  private class TradeRowMapper
    implements RowMapper
  {
    private TradeRowMapper() {}
    
    public Object mapRow(ResultSet rs, int rowNum)
      throws SQLException
    {
      Trade trade = new Trade();
      trade.setOrderNo(Long.valueOf(rs.getLong("OrderNo")));
      trade.setTradeNo(Long.valueOf(rs.getLong("TradeNo")));
      trade.setTradeTime(rs.getTimestamp("TradeTime"));
      trade.setBS_Flag(Short.valueOf(rs.getShort("BS_Flag")));
      Short orderType = Short.valueOf((short)1);
      if (rs.getString("OC_Flag").equals("C")) {
        orderType = Short.valueOf((short)2);
      }
      trade.setOrderType(orderType);
      trade.setFirmID(rs.getString("FirmID"));
      trade.setCommodityID(rs.getString("CommodityID"));
      trade.setPrice(Double.valueOf(rs.getDouble("Price")));
      trade.setOpenPrice(Double.valueOf(rs.getDouble("OpenPrice")));
      trade.setQuantity(Long.valueOf(rs.getLong("Quantity")));
      trade.setClose_PL(Double.valueOf(rs.getDouble("Close_PL")));
      trade.setTradeFee(Double.valueOf(rs.getDouble("TradeFee")));
      trade.setHoldPrice(Double.valueOf(rs.getDouble("HoldPrice")));
      trade.setHoldTime(rs.getTimestamp("HoldTime"));
      trade.setTradeType(Integer.valueOf(rs.getInt("TradeType")));
      trade.setHoldNO(Long.valueOf(rs.getLong("HoldNo")));
      trade.setHoldNO(Long.valueOf(rs.getLong("HoldNo")));
      trade.setOtherFirmID(rs.getString("O_FirmID"));
      return trade;
    }
  }
  
  public List directfirmbreed_query(String firmID)
  {
    String sql = "select BreedID from T_E_DirectFirmBreed where FirmID=?";
    Object[] params = { firmID };
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    return getJdbcTemplate().queryForList(sql, params);
  }
  
  public List holdpositionbyprice(Orders orders, Privilege prvlg, SortCondition sc)
  {
    StringBuffer sb = new StringBuffer();
    
    sb
      .append("select h.CustomerID,h.CommodityID,h.BS_Flag,h.Price,sum(h.HoldQty+h.GageQty) QTY,sum(h.GageQty) GageQty,sum(h.HoldMargin) HoldMargin from T_HoldPosition h where h.FirmID=?");
    Object[] params = (Object[])null;
    
    List lst = new ArrayList();
    String firmID = prvlg.getFirmId();
    lst.add(firmID);
    if (!StringUtils.isEmpty(orders.getCommodityID()))
    {
      sb.append(" and h.CommodityID=? ");
      lst.add(orders.getCommodityID());
    }
    params = lst.toArray();
    
    this.log.debug("sql: " + sb.toString());
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        this.log.debug("params[" + i + "]: " + params[i]);
      }
    }
    sb.append("group by h.CustomerID,h.CommodityID,h.BS_Flag,h.Price order by h.Price");
    return getJdbcTemplate().queryForList(sb.toString(), params);
  }
  
  public void checkMemberPrivilege(Privilege privilege)
  {
    if (privilege.getFirmType().equals("0"))
    {
      Map<String, Map<String, BigDecimal>> otherFirmTrade_Privilege = new HashMap();
      
      String tradeAuth_sql = "Select CommodityID,Cancel_L_Open,Cancel_StopLoss,Cancel_StopProfit,M_B_Open,M_B_Close,L_B_Open,B_StopLoss,B_StopProfit,M_S_Open,M_S_Close,L_S_Open,S_StopLoss,S_StopProfit from T_C_TradeAuth_RT where firmID=? ";
      

      this.log.debug("sql:" + tradeAuth_sql);
      this.log.debug("Param1: FirmID= " + privilege.getM_FirmID());
      Object[] tradeAuth_param = { privilege.getM_FirmID() };
      List tradeAuthList = getJdbcTemplate().queryForList(tradeAuth_sql, 
        tradeAuth_param);
      Map<String, BigDecimal> value_Map;
      if ((tradeAuthList != null) && (tradeAuthList.size() > 0)) {
        for (int i = 0; i < tradeAuthList.size(); i++)
        {
          Map map = (Map)tradeAuthList.get(i);
          value_Map = new HashMap();
          value_Map.put("Cancel_L_Open", 
            (BigDecimal)map.get("Cancel_L_Open"));
          value_Map.put("Cancel_StopLoss", 
            (BigDecimal)map.get("Cancel_StopLoss"));
          value_Map.put("Cancel_StopProfit", 
            (BigDecimal)map.get("Cancel_StopProfit"));
          value_Map.put("M_B_Open", (BigDecimal)map.get("M_B_Open"));
          value_Map.put("M_B_Close", 
            (BigDecimal)map.get("M_B_Close"));
          value_Map.put("L_B_Open", (BigDecimal)map.get("L_B_Open"));
          value_Map.put("B_StopLoss", 
            (BigDecimal)map.get("B_StopLoss"));
          value_Map.put("B_StopProfit", 
            (BigDecimal)map.get("B_StopProfit"));
          value_Map.put("M_S_Open", (BigDecimal)map.get("M_S_Open"));
          value_Map.put("M_S_Close", 
            (BigDecimal)map.get("M_S_Close"));
          value_Map.put("L_S_Open", (BigDecimal)map.get("L_S_Open"));
          value_Map.put("S_StopLoss", 
            (BigDecimal)map.get("S_StopLoss"));
          value_Map.put("S_StopProfit", 
            (BigDecimal)map.get("S_StopProfit"));
          
          otherFirmTrade_Privilege.put(
            (String)map.get("CommodityID"), value_Map);
        }
      }
      Map<String, Map<String, BigDecimal>> firmTradePrivilege = privilege
        .getFirmTradePrivilege();
      if ((otherFirmTrade_Privilege != null) && (firmTradePrivilege != null)) {
        for (String commodityID : otherFirmTrade_Privilege.keySet())
        {
          Map<String, BigDecimal> otherFirmValueMap = 
            (Map)otherFirmTrade_Privilege.get(commodityID);
          Map<String, BigDecimal> firmValueMap = 
            (Map)firmTradePrivilege.get(commodityID);
          if (firmValueMap == null) {
            firmTradePrivilege.put(commodityID, otherFirmValueMap);
          }
        }
      }
    }
  }
}
