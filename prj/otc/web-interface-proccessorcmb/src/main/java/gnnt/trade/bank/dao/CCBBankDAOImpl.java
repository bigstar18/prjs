package gnnt.trade.bank.dao;

import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CCBBankDAOImpl
  extends CCBBankDAO
{
  public CCBBankDAOImpl()
    throws Exception
  {}
  
  public int modfirmuser(FirmValue value, String key)
  {
    log("===>>>修改交易商注册信息表" + new java.util.Date());
    Vector<PreparedStatement> ve = new Vector();
    Connection conn = null;
    PreparedStatement state1 = null;
    PreparedStatement state2 = null;
    PreparedStatement state3 = null;
    int result = 0;
    String sql1 = "update f_b_firmuser set firmname = ? ,cardType = ? ,card = ?  where firmid=?";
    

    String sql2 = "update f_b_firmidandaccount set accountname = ?,cardType = ? ,card = ? where firmid=? ";
    

    String sql3 = "update f_b_firminfo set value=? where firmid=? and bankid=? and key=?";
    try
    {
      conn = getConnection();
      conn.setAutoCommit(false);
      state1 = conn.prepareStatement(sql1);
      state1.setString(1, value.firmName);
      state1.setString(2, value.cardType);
      state1.setString(3, value.card);
      state1.setString(4, value.firmID);
      state1.executeUpdate();
      ve.add(state1);
      
      state2 = conn.prepareStatement(sql2);
      state2.setString(1, value.firmName);
      state2.setString(2, value.cardType);
      state2.setString(3, value.card);
      state2.setString(4, value.firmID);
      state2.executeUpdate();
      ve.add(state2);
      
      state3 = conn.prepareStatement(sql3);
      state3.setString(1, value.firminfo.value);
      state3.setString(2, value.firmID);
      state3.setString(3, value.firminfo.bankid);
      state3.setString(4, key);
      state3.executeUpdate();
      conn.commit();
      ve.add(state3);
    }
    catch (Exception e)
    {
      log("修改交易商注册信息表" + Tool.getExceptionTrace(e));
      result = -1;
      try
      {
        conn.rollback();
      }
      catch (SQLException e1)
      {
        log("修改交易商注册信息表异常：数据回滚异常" + Tool.getExceptionTrace(e1));
      }
    }
    finally
    {
      closePreparedStatement(null, ve, conn);
    }
    return result;
  }
  
  public int updateFirmInfo(String firmid, String bankid, String key, String value)
  {
    log("===>>>修改扩展字段表" + new java.util.Date());
    int result = 0;
    String sql = "update f_b_firminfo set value=? where firmid=? and bankid=? and key=?";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, value);
      state.setString(2, firmid);
      state.setString(3, bankid);
      state.setString(4, key);
      state.executeUpdate();
    }
    catch (Exception e)
    {
      log("修改扩展字段表异常:" + Tool.getExceptionTrace(e));
      result = -1;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<FirmInfo> getFirmInfo(String firmid, String bankid, String key)
  {
    log("===>>>查询扩充字段表内容" + new java.util.Date());
    Vector<FirmInfo> ve = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "select * from f_b_firminfo where bankid=? and firmid=? and key =?";
    try
    {
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, bankid);
      state.setString(2, firmid);
      state.setString(3, key);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmInfo finfo = new FirmInfo();
        finfo.bankid = rs.getString("bankid");
        finfo.key = rs.getString("key");
        finfo.value = rs.getString("value");
        finfo.firmid = rs.getString("firmid");
        ve.add(finfo);
        log(finfo.toString());
      }
    }
    catch (Exception e)
    {
      log("查询firminfo异常：" + Tool.getExceptionTrace(e));
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return ve;
  }
  
  public int insertFirmInfo(FirmValue value, String key)
  {
    log("===>>>插入数据到扩充字段表内容" + new java.util.Date());
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = "insert into f_b_firminfo(firmid,bankid,key,value) values(?,?,?,?)";
    try
    {
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, value.firmID);
      state.setString(2, value.firminfo.bankid);
      state.setString(3, key);
      state.setString(4, value.firminfo.value);
      state.executeUpdate();
    }
    catch (Exception e)
    {
      log("新增firminfo数据异常:" + Tool.getExceptionTrace(e));
      result = -1;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<FirmValue> getFirmList3(String filter, String type, String key, String bankid)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得交易账号信息列表   getFirmList  ");
    Vector<FirmValue> list = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    FirmValue value = null;
    try
    {
      conn = getConnection();
      if (type.equals("member")) {
        sql = 
        
          "select fbf.* ,fbfi.value,fbfi.key,fbfi.bankid from f_b_firmuser fbf,m_customerinfo mc,(select * from f_b_firminfo where bankid='" + bankid + "' and key='" + key + "') fbfi where mc.customerno=fbf.firmid " + " and fbfi.firmid(+)=fbf.firmid " + filter;
      } else if (type.equals("Backstage")) {
        sql = 
        
          "select fbfu.*,fbfi.value,fbfi.key,fbfi.bankid from F_B_FirmUser fbfu,(select * from f_b_firminfo where bankid='" + bankid + "' and key='" + key + "') fbfi where 1=1 and " + "fbfi.firmid(+)=fbfu.firmid " + filter;
      } else {
        return list;
      }
      log(sql);
      rs = PageQuery.executeQuery(conn, state, rs, sql, this.pageinfo);
      while (rs.next())
      {
        value = new FirmValue();
        FirmInfo firminfos = new FirmInfo();
        value.firmID = rs.getString("FIRMID");
        value.firmName = rs.getString("FIRMNAME");
        value.status = rs.getInt("STATUS");
        value.registerDate = rs.getDate("REGISTERDATE");
        value.logoutDate = rs.getDate("LOGOUTDATE");
        value.password = rs.getString("PASSWORD");
        value.contact = rs.getString("contact");
        value.cardType = rs.getString("cardtype");
        value.card = rs.getString("card");
        firminfos.value = rs.getString("value");
        firminfos.bankid = rs.getString("bankid");
        firminfos.key = rs.getString("key");
        value.firminfo = firminfos;
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    closeStatement(rs, state, conn);
    
    return list;
  }
  
  public int modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime, String text, Connection conn)
    throws SQLException
  {
    log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      String funidf = "";
      String bankTimef = "";
      int n = 1;
      if (bankTime != null) {
        bankTimef = ",bankTime=?";
      }
      if ((funID == null) || (funID.trim().length() <= 0)) {
        funID = "";
      } else {
        funidf = ",funid2=?";
      }
      sql = "update F_B_capitalInfo set OLDFUNID=FUNID,OLDSTATUS=STATUS, status=?,NOTE=?,FUNID=?" + bankTimef + funidf + " where id=?";
      state = conn.prepareStatement(sql);
      state.setInt(n++, status);
      state.setString(n++, text);
      state.setString(n++, funID.trim());
      if (bankTime != null) {
        state.setTimestamp(n++, bankTime);
      }
      if ((funidf != null) && (funidf.trim().length() > 0)) {
        state.setString(n++, funID);
      }
      state.setLong(n++, id);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public FirmValue getFirmValue(FirmValue value)
  {
    log("===>>>取得交易账号   getFirm  ");
    FirmValue result = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    Connection conn = null;
    try
    {
      conn = getConnection();
      sql = "select fbf.*,fbfi.value,fbfi.key,fbfi.bankid from F_B_FirmUser fbf,(select * from F_B_FIRMINFO where bankid=? and key=? ) fbfi where fbf.firmid=fbfi.firmid(+) and fbf.firmid=?";
      

      state = conn.prepareStatement(sql);
      state.setString(1, value.firminfo.bankid);
      state.setString(2, value.firminfo.key);
      state.setString(3, value.firmID);
      rs = state.executeQuery();
      FirmInfo firminfos = new FirmInfo();
      if (rs.next())
      {
        result = new FirmValue();
        result.firmID = rs.getString("FIRMID");
        result.firmName = rs.getString("FIRMNAME");
        result.status = rs.getInt("STATUS");
        result.registerDate = rs.getDate("REGISTERDATE");
        result.logoutDate = rs.getDate("LOGOUTDATE");
        result.password = rs.getString("PASSWORD");
        result.contact = rs.getString("contact");
        result.cardType = rs.getString("cardtype");
        result.card = rs.getString("card");
        firminfos.value = rs.getString("value");
        firminfos.bankid = rs.getString("bankid");
        firminfos.key = rs.getString("key");
        result.firminfo = firminfos;
      }
    }
    catch (Exception e)
    {
      log("取得交易账号   getFirm 异常" + Common.getExceptionTrace(e));
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public long addCCBQS(Vector<FirmBalance> vector, java.util.Date tradeDate, Connection conn)
    throws SQLException
  {
    long result = 0L;
    String sql = null;
    PreparedStatement state = null;
    try
    {
      for (FirmBalance fb : vector)
      {
        System.out.println("addCCBQS--FeeMoney:" + fb.FeeMoney + "--QYChangeMoney:" + fb.QYChangeMoney);
        sql = "insert into F_B_CCBQS (actionId,bankid,firmId,fee,QYChangeMoney,money,CRJSum,lastMoney,CREATEDATE,flag) values(seq_F_B_action.nextval,?,?,?,?,?,?,?,?,?)";
        System.out.println(sql);
        state = conn.prepareStatement(sql);
        state.setString(1, fb.bankID);
        state.setString(2, fb.firmID);
        state.setDouble(3, fb.FeeMoney);
        state.setDouble(4, fb.QYChangeMoney);
        state.setDouble(5, fb.QYMoney);
        state.setDouble(6, fb.fundio);
        state.setDouble(7, fb.lastQYMoney);
        state.setDate(8, new java.sql.Date(tradeDate.getTime()));
        state.setString(9, "0");
        result = state.executeUpdate();
      }
    }
    catch (SQLException e)
    {
      result = -1L;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public long delCCBQS(java.util.Date date, Connection conn)
    throws SQLException
  {
    long result = 0L;
    String sql = null;
    PreparedStatement state = null;
    try
    {
      sql = "delete from F_B_CCBQS where trunc(CREATEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      System.out.println("delCCBQS：" + sql);
      state = conn.prepareStatement(sql);
      

      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1L;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public Vector<FirmBalance> getCCBQS(Connection conn, String filter)
    throws SQLException
  {
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    
    Vector<FirmBalance> result = new Vector();
    try
    {
      sql = "select * from F_B_CCBQS where 1=1  " + filter;
      
      System.out.println("getCCBQS：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmBalance fb = new FirmBalance();
        fb.firmID = rs.getString("firmid");
        fb.bankID = rs.getString("bankid");
        fb.FeeMoney = rs.getDouble("Fee");
        fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
        fb.QYMoney = rs.getDouble("money");
        fb.lastQYMoney = rs.getDouble("lastMoney");
        fb.date = rs.getDate("CREATEDATE");
        fb.actionId = rs.getString("actionId");
        fb.falg = rs.getString("flag");
        result.add(fb);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public Vector<FirmBalance> getCCBQS(Connection conn, List<String> list)
    throws SQLException
  {
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    
    Vector<FirmBalance> result = new Vector();
    try
    {
      sql = "select * from F_B_CCBQS where 1=1 and actionid in(";
      for (String temp : list) {
        if (((String)list.get(list.size() - 1)).equals(temp)) {
          sql = sql + temp + ")";
        } else {
          sql = sql + temp + ",";
        }
      }
      System.out.println("getCCBQS：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmBalance fb = new FirmBalance();
        fb.firmID = rs.getString("firmid");
        fb.bankID = rs.getString("bankid");
        fb.FeeMoney = rs.getDouble("Fee");
        fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
        fb.QYMoney = rs.getDouble("money");
        fb.lastQYMoney = rs.getDouble("lastMoney");
        fb.date = rs.getDate("CREATEDATE");
        fb.actionId = rs.getString("actionId");
        fb.falg = rs.getString("flag");
        result.add(fb);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public long updateCCBQS(String actionId, String falg, Connection conn)
    throws SQLException
  {
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      sql = "update F_B_CCBQS set flag='" + falg + "' where actionId='" + actionId + "'";
      System.out.println("updateCCBQS：" + sql);
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public Map<String, Double> getFirmBanlanceMap(Connection conn, String filter)
    throws SQLException
  {
    Map<String, Double> map = new HashMap();
    Statement state = null;
    ResultSet rs = null;
    try
    {
      System.out.println("getFirmBanlanceMap" + filter);
      state = conn.createStatement();
      rs = state.executeQuery(filter);
      while (rs.next())
      {
        String contact = rs.getString(1);
        Double money = Double.valueOf(rs.getDouble(2));
        map.put(contact, money);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return map;
  }
}
