package gnnt.MEBS.timebargain.server.dao.jdbc;

import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.timebargain.server.dao.FirmDAO;
import gnnt.MEBS.timebargain.server.model.Fee_RT;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.HoldQty_RT;
import gnnt.MEBS.timebargain.server.model.Margin_RT;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.QuotePoint_RT;
import gnnt.MEBS.timebargain.server.model.Trader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class FirmDAOJdbc
  extends BaseDAOJdbc
  implements FirmDAO
{
  private Log log = LogFactory.getLog(getClass());
  
  public Map<String, Member> getMemberMap()
  {
    Map<String, Member> memberMap = new HashMap();
    String sql = "select distinct M_FirmID from T_C_Quotation_RT ";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    for (int i = 0; i < lst.size(); i++)
    {
      Map map = (Map)lst.get(i);
      Member member = new Member();
      String m_FirmID = (String)map.get("M_FirmID");
      member.setM_FirmID(m_FirmID);
      memberMap.put(m_FirmID, member);
    }
    return memberMap;
  }
  
  public Member getOneMember(String firmID)
  {
    String sql = "select distinct M_FirmID from T_C_Quotation_RT where M_FirmId = ?";
    this.log.debug("query firm sql: " + sql);
    
    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int rowNum)
        throws SQLException
      {
        Member member = new Member();
        member.setM_FirmID(rs.getString("M_FirmID"));
        return member;
      }
    };
    return (Member)getJdbcTemplate().queryForObject(sql, 
      new Object[] { firmID }, mapper);
  }
  
  public Map<String, Margin_RT> getMarginMap(String firmID)
  {
    Map<String, Margin_RT> marginMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb
      .append("select CommodityID,MarginAlgr,TradeMargin,SettleMargin,HolidayMargin from T_C_Margin_RT where FirmID = ? ");
    Object[] params = { firmID };
    this.log.debug("sql:" + sb.toString());
    this.log.debug("firmID:" + params[0]);
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    for (int i = 0; i < lst.size(); i++)
    {
      Margin_RT m = new Margin_RT();
      m.setFirmID(firmID);
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      m.setCommodityID(commodityID);
      m.setMarginAlgr(((BigDecimal)map.get("MarginAlgr")).shortValue());
      m.setTradeMargin(((BigDecimal)map.get("TradeMargin"))
        .doubleValue());
      m.setSettleMargin(((BigDecimal)map.get("SettleMargin"))
        .doubleValue());
      m.setHolidayMargin(((BigDecimal)map.get("HolidayMargin"))
        .doubleValue());
      marginMap.put(commodityID, m);
    }
    return marginMap;
  }
  
  public Map<String, Fee_RT> getFeeMap(String firmID)
  {
    Map<String, Fee_RT> feeMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb
      .append("select CommodityID,FeeAlgr,FeeRate,FeeMode from T_C_Fee_RT where FirmID = ? ");
    Object[] params = { firmID };
    this.log.debug("sql:" + sb.toString());
    this.log.debug("firmID:" + params[0]);
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    for (int i = 0; i < lst.size(); i++)
    {
      Fee_RT m = new Fee_RT();
      m.setFirmID(firmID);
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      m.setCommodityID(commodityID);
      m.setFeeAlgr(((BigDecimal)map.get("FeeAlgr")).shortValue());
      m.setFeeRate(((BigDecimal)map.get("FeeRate")).doubleValue());
      m.setFeeMode(((BigDecimal)map.get("FeeMode")).shortValue());
      feeMap.put(commodityID, m);
    }
    return feeMap;
  }
  
  public Map<String, HoldQty_RT> getHoldQtyMap(String firmID)
  {
    Map<String, HoldQty_RT> holdQtyMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb
      .append("select CommodityID,OneMaxOrderQty,OneMinOrderQty,MaxHoldQty from T_C_HoldQty_RT where FirmID = ? ");
    Object[] params = { firmID };
    this.log.debug("sql:" + sb.toString());
    this.log.debug("firmID:" + params[0]);
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    for (int i = 0; i < lst.size(); i++)
    {
      HoldQty_RT m = new HoldQty_RT();
      m.setFirmID(firmID);
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      m.setCommodityID(commodityID);
      m.setOneMaxOrderQty(((BigDecimal)map.get("OneMaxOrderQty"))
        .longValue());
      m.setOneMinOrderQty(((BigDecimal)map.get("OneMinOrderQty"))
        .longValue());
      m.setMaxHoldQty(((BigDecimal)map.get("MaxHoldQty")).longValue());
      holdQtyMap.put(commodityID, m);
    }
    return holdQtyMap;
  }
  
  public Map<String, QuotePoint_RT> getQuotePointMap(String m_FirmID)
  {
    Map<String, QuotePoint_RT> quotePointMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb.append("select a.CommodityID, QuotePoint_B_RMB, QuotePoint_S_RMB  from T_C_QuotePoint_RT a, t_commodity b where b.status = 1   and a.commodityid = b.commodityid   and M_FirmID = ?");
    



    Object[] params = { m_FirmID };
    this.log.debug("sql:" + sb.toString());
    this.log.debug("m_FirmID:" + params[0]);
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    for (int i = 0; i < lst.size(); i++)
    {
      QuotePoint_RT m = new QuotePoint_RT();
      m.setM_FirmID(m_FirmID);
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      m.setCommodityID(commodityID);
      m.setQuotePoint_B(((BigDecimal)map.get("QuotePoint_B_RMB"))
        .doubleValue());
      m.setQuotePoint_S(((BigDecimal)map.get("QuotePoint_S_RMB"))
        .doubleValue());
      quotePointMap.put(commodityID, m);
    }
    return quotePointMap;
  }
  
  public Map<String, Quotation_RT> getQuotationMap(String m_FirmID)
  {
    Map<String, Quotation_RT> quotationMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    sb
      .append("select a.CommodityID,       CurPrice_B,       CurPrice_S,       ClearPrice_B,       ClearPrice_S,       Y_ClearPrice_B,       Y_ClearPrice_S,       UpdateTime  from T_C_Quotation_RT a,t_commodity b where b.status=1 and a.commodityid=b.commodityid and M_FirmID = ?");
    








    Object[] params = { m_FirmID };
    this.log.debug("sql:" + sb.toString());
    this.log.debug("m_FirmID:" + params[0]);
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    for (int i = 0; i < lst.size(); i++)
    {
      Quotation_RT m = new Quotation_RT();
      m.setM_FirmID(m_FirmID);
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      m.setCommodityID(commodityID);
      m.setCurPrice_B(((BigDecimal)map.get("CurPrice_B")).doubleValue());
      m.setCurPrice_S(((BigDecimal)map.get("CurPrice_S")).doubleValue());
      m.setClearPrice_B(((BigDecimal)map.get("ClearPrice_B"))
        .doubleValue());
      m.setClearPrice_S(((BigDecimal)map.get("ClearPrice_S"))
        .doubleValue());
      m.setY_ClearPrice_B(((BigDecimal)map.get("Y_ClearPrice_B"))
        .doubleValue());
      m.setY_ClearPrice_S(((BigDecimal)map.get("Y_ClearPrice_S"))
        .doubleValue());
      m.setUpdateTime((Timestamp)map.get("UpdateTime"));
      quotationMap.put(commodityID, m);
    }
    return quotationMap;
  }
  
  public Trader getOneTrader(String traderID)
  {
    String sql = " select TraderID,Name,FirmID from M_Trader where TraderID=? ";
    this.log.debug("query trader sql: " + sql);
    
    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int rowNum)
        throws SQLException
      {
        Trader trader = new Trader();
        trader.setTraderID(rs.getString("TraderID"));
        trader.setName(rs.getString("Name"));
        trader.setFirmID(rs.getString("FirmID"));
        return trader;
      }
    };
    return (Trader)getJdbcTemplate().queryForObject(sql, 
      new Object[] { traderID }, mapper);
  }
  
  public Firm getOneFirm(String firmID)
  {
    String sql = "select FirmID,TradeStatus from T_Firm where firmid = ?";
    this.log.debug("query firm sql: " + sql);
    
    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int rowNum)
        throws SQLException
      {
        Firm firm = new Firm();
        firm.setFirmID(rs.getString("FirmID"));
        firm.setStatus(rs.getString("TradeStatus").charAt(0));
        return firm;
      }
    };
    return (Firm)getJdbcTemplate().queryForObject(sql, 
      new Object[] { firmID }, mapper);
  }
  
  public void updateTraderOnLineInfo(String traderID, String ip, int status)
  {
    String sql = "";
    Object[] params = (Object[])null;
    int[] types = (int[])null;
    if ((ip != null) && (ip.length() > 0))
    {
      sql = "update m_trader set TraderIP=?,OnlineStatus=?,OnlineTime=sysdate  where TraderID=? ";
      params = new Object[] { ip, Integer.valueOf(status), traderID };
      types = new int[] { 12, 4, 12 };
    }
    else
    {
      sql = "update m_trader set OnlineStatus=?,OnlineTime=sysdate  where TraderID=? ";
      params = new Object[] { Integer.valueOf(status), traderID };
      types = new int[] { 4, 12 };
    }
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void updateTraderDownLine()
  {
    String sql = "update m_trader set OnlineStatus=0 ";
    
    this.log.debug("sql: " + sql);
    
    getJdbcTemplate().update(sql);
  }
  
  public void addGlobalLog(String operator, String operatorIP, int operatorType, String operatorContent, int operatorResult)
  {
    String sql = "insert into c_globallog_all(id,operator,operatetime,operatetype,operateip,operatecontent,operateresult)  values(SEQ_C_GLOBALLOG.Nextval,?, sysdate,?,?,?,?)";
    
    Object[] params = { operator, Integer.valueOf(operatorType), operatorIP, 
      operatorContent, Integer.valueOf(operatorResult) };
    int[] types = { 12, 4, 12, 
      12, 4 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public int changePhonePassowrd(String firmID, String passwordOld, String password)
  {
    String sql = "select PhonePWD from M_CustomerInfo where CustomerNo=? ";
    Object[] params = { firmID };
    String phonePWD = (String)getJdbcTemplate().queryForObject(sql, 
      params, String.class);
    if ((phonePWD != null) && 
      (!phonePWD.equals(MD5.getMD5(
      firmID, passwordOld)))) {
      return -1;
    }
    sql = "update M_CustomerInfo set PhonePWD=? where CustomerNo=?";
    params = new Object[] {
      MD5.getMD5(firmID, password), 
      firmID };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
    return 0;
  }
  
  public int changePassowrd(String traderID, String passwordOld, String password)
  {
    String sql = "select Password from M_Trader t where t.traderid=? ";
    Object[] params = { traderID };
    String pwd = (String)getJdbcTemplate().queryForObject(sql, params, 
      String.class);
    if ((pwd != null) && 
      (!pwd.equals(MD5.getMD5(traderID, 
      passwordOld)))) {
      return -1;
    }
    sql = "update M_trader set password=?,forceChangePwd=0 where traderId=?";
    params = new Object[] {
      MD5.getMD5(traderID, password), 
      traderID };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
    
    sql = "update c_m_user set password=? where id=?";
    params = new Object[] {
      MD5.getMD5(traderID, password), 
      traderID };
    
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params);
    
    return 0;
  }
  
  public long checkDelegateInfo(String memberID, String customerID, String phonePassword)
  {
    String sql = "select MemberNo,PhonePWD from M_CustomerInfo where CustomerNo=? ";
    Object[] params = { customerID };
    List list = getJdbcTemplate().queryForList(sql, params);
    if ((list == null) || (list.size() == 0)) {
      return -2L;
    }
    Map map = (Map)list.get(0);
    String memberNO = (String)map.get("MemberNo");
    String phonePWD = (String)map.get("PhonePWD");
    if (!memberID.equals(memberNO)) {
      return -1L;
    }
    if ((phonePWD != null) && 
      (!phonePWD.equals(MD5.getMD5(
      customerID, phonePassword)))) {
      return -3L;
    }
    return 0L;
  }
  
  public long checkDelegateInfo(String memberID, String customerID)
  {
    String sql = "select MemberNo,PhonePWD from M_CustomerInfo where CustomerNo=? ";
    Object[] params = { customerID };
    List list = getJdbcTemplate().queryForList(sql, params);
    if ((list == null) || (list.size() == 0)) {
      return -2L;
    }
    Map map = (Map)list.get(0);
    String memberNO = (String)map.get("MemberNo");
    String phonePWD = (String)map.get("PhonePWD");
    if (!memberID.equals(memberNO)) {
      return -1L;
    }
    return 0L;
  }
}
