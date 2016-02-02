package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CIBBankDAOImpl
  extends CIBBankDAO
{
  public CIBBankDAOImpl()
    throws Exception
  {}
  
  public int addZFPH(ZFPHValue zfph, Connection conn)
    throws SQLException
  {
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_ZFPH (BANKID,TRADEDATE,CURRENCY,TYPE,LASTACCOUNTBALANCE,ACCOUNTBALANCE,RESULT,CREATEDATE) values (?,?,?,?,?,?,?,sysdate)";
      state = conn.prepareStatement(sql);
      state.setString(1, zfph.bankID);
      state.setDate(2, new java.sql.Date(zfph.tradeDate == null ? 0L : zfph.tradeDate.getTime()));
      state.setString(3, zfph.currency);
      state.setInt(4, zfph.type);
      state.setBigDecimal(5, zfph.lastAccountBalance);
      state.setBigDecimal(6, zfph.accountBalance);
      state.setInt(7, zfph.result);
      result = state.executeUpdate();
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
  
  public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result)
    throws SQLException, ClassNotFoundException
  {
    Vector<ZFPHValue> resultbak = null;
    Connection conn = null;
    try
    {
      conn = getConnection();
      resultbak = getZFPH(bankID, tradeDate, result, conn);
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(null, null, conn);
    }
    return resultbak;
  }
  
  public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn)
    throws SQLException
  {
    Vector<ZFPHValue> vector = new Vector();
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = "";
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID='" + bankID.trim() + "' ";
    }
    if (tradeDate != null) {
      filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
    }
    if (result >= 0) {
      filter = filter + " and result=" + result + " ";
    }
    try
    {
      sql = "select * from F_B_ZFPH where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        ZFPHValue value = new ZFPHValue();
        value.accountBalance = rs.getBigDecimal("accountBalance");
        value.bankID = rs.getString("bankID");
        value.currency = rs.getString("currency");
        value.lastAccountBalance = rs.getBigDecimal("lastAccountBalance");
        value.result = rs.getInt("result");
        value.tradeDate = rs.getDate("tradeDate");
        value.type = rs.getInt("type");
        vector.add(value);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return vector;
  }
  
  public int delZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn)
    throws SQLException
  {
    int rst = 0;
    PreparedStatement state = null;
    String sql = null;
    String filter = "";
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID='" + bankID.trim() + "' ";
    }
    if (tradeDate != null) {
      filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
    }
    if (result >= 0) {
      filter = filter + " and result=" + result + " ";
    }
    try
    {
      sql = "delete F_B_ZFPH where 1=1 " + filter;
      
      System.out.println("DEL_SQL:" + sql);
      
      state = conn.prepareStatement(sql);
      rst = state.executeUpdate();
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return rst;
  }
  
  public int addFFHD(FFHDValue ffhd, Connection conn)
    throws SQLException
  {
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_FFHD (BANKID,TRADEDATE,FIRMID,ACCOUNT,CURRENCY,TYPE,REASION,CREATEDATE,balanceM,cashM,billM,useBalanceM,frozenCashM,frozenLoanM,balanceB,cashB,billB,useBalanceB,frozenCashB,frozenLoanB) values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)";
      state = conn.prepareStatement(sql);
      if ((ffhd != null) && (ffhd.getFdv() != null))
      {
        Vector<FirmDateValue> vv = ffhd.getFdv();
        for (FirmDateValue ff : vv)
        {
          state.setString(1, ff.bankID);
          state.setDate(2, new java.sql.Date(ff.tradeDate.getTime()));
          state.setString(3, ff.firmID);
          state.setString(4, ff.account);
          state.setString(5, ff.currency);
          state.setInt(6, ff.type);
          state.setInt(7, ff.reason);
          state.setDouble(8, ff.balanceM);
          state.setDouble(9, ff.cashM);
          state.setDouble(10, ff.billM);
          state.setDouble(11, ff.useBalanceM);
          state.setDouble(12, ff.frozenCashM);
          state.setDouble(13, ff.frozenLoanM);
          state.setDouble(14, ff.balanceB);
          state.setDouble(15, ff.cashB);
          state.setDouble(16, ff.billB);
          state.setDouble(17, ff.useBalanceB);
          state.setDouble(18, ff.frozenCashB);
          state.setDouble(19, ff.frozenLoanB);
          result = state.executeUpdate();
        }
      }
      System.out.println("ADD_SQL:" + sql);
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
  
  public Vector<FirmDateValue> getFFHD(String firmID, String bankID, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    Vector<FirmDateValue> result = null;
    String filter = "";
    if ((firmID != null) && (firmID.trim().length() > 0)) {
      filter = filter + " and firmID='" + firmID.trim() + "'";
    }
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID = '" + bankID.trim() + "'";
    }
    if (tradeDate != null) {
      filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
    }
    Connection conn = null;
    try
    {
      conn = getConnection();
      result = getFFHD(filter, conn);
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(null, null, conn);
    }
    return result;
  }
  
  public Vector<FirmDateValue> getFFHD(String filter, Connection conn)
    throws SQLException
  {
    Vector<FirmDateValue> result = new Vector();
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      sql = "select * from F_B_FFHD where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmDateValue fd = new FirmDateValue();
        fd.bankID = rs.getString("bankid");
        fd.tradeDate = rs.getDate("tradeDate");
        fd.firmID = rs.getString("firmID");
        fd.account = rs.getString("account");
        fd.currency = rs.getString("currency");
        fd.type = rs.getInt("type");
        fd.reason = rs.getInt("reasion");
        fd.balanceM = rs.getDouble("balanceM");
        fd.cashM = rs.getDouble("cashM");
        fd.billM = rs.getDouble("billM");
        fd.useBalanceM = rs.getDouble("useBalanceM");
        fd.frozenCashM = rs.getDouble("frozenCashM");
        fd.frozenLoanM = rs.getDouble("frozenLoanM");
        fd.balanceB = rs.getDouble("balanceB");
        fd.cashB = rs.getDouble("cashB");
        fd.billB = rs.getDouble("billB");
        fd.useBalanceB = rs.getDouble("useBalanceB");
        fd.frozenCashB = rs.getDouble("frozenCashB");
        fd.frozenLoanB = rs.getDouble("frozenLoanB");
        result.add(fd);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return result;
  }
  
  public int delFFHD(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
    throws SQLException
  {
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    String filter = "";
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID='" + bankID.trim() + "' ";
    }
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String firms = "";
      for (firmID : firmIDs) {
        if ((firmID != null) && (firmID.trim().length() > 0)) {
          firms = firms + "'" + firmID.trim() + "',";
        }
      }
      if ((firms != null) && (firms.trim().length() > 0)) {
        filter = filter + " and firmID in (" + firms.trim().substring(0, firms.trim().length() - 1) + ")";
      }
    }
    if (tradeDate != null) {
      filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
    }
    try
    {
      sql = "delete F_B_FFHD where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      result = state.executeUpdate();
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
  
  public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZQSValue result = new RZQSValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "";
    sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-nvl(th.mktfee,0)) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,abs(nvl(th.mktfee,0)) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    










      bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select firmid,0-nvl(sum(mktfee),0) mktfee from t_trade_h where cleardate=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy/MM/dd') group by cleardate,firmid) th, " + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and tf.firmid(+) = ffb.firmID" + 
      " and ffb.firmid = th.firmid(+)";
    try
    {
      conn = getConnection();
      
      System.out.println("清算sql：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      MarketRightValue mrv = new MarketRightValue();
      mrv.maketMoney = new BigDecimal("0");
      while (rs.next())
      {
        FirmRightValue value = new FirmRightValue();
        value.actionID = String.valueOf(getActionID());
        value.firmID = rs.getString("contact");
        value.account = rs.getString("account");
        value.availableBalance = rs.getDouble("mqyChange");
        value.billMoney = 0.0D;
        value.cash = 0.0D;
        value.cashMoney = 0.0D;
        value.credit = 0.0D;
        value.firmErrorMoney = rs.getDouble("mqyChange");
        mrv.bankErrorMoney += rs.getDouble("qyChange");
        mrv.maketMoney = mrv.maketMoney.add(rs.getBigDecimal("fee"));
        System.out.println("交易商[" + rs.getString("contact") + "]的自有资金[" + rs.getBigDecimal("fee") + "]总自有资金[" + mrv.maketMoney + "]");
        log("交易商[" + rs.getString("contact") + "]的自有资金[" + rs.getBigDecimal("fee") + "]总自有资金[" + mrv.maketMoney + "]");
        result.putFrv(value);
      }
      mrv.bankErrorMoney = (0.0D - mrv.bankErrorMoney);
      result.setMarketRight(mrv);
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZDZValue result = new RZDZValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "";
    try
    {
      conn = getConnection();
      sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-nvl(th.mktfee,0)) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,abs(nvl(th.mktfee,0)) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
      










        bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
        " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + bankID + "' and f.isOpen=1 ) fbf ," + 
        " (select firmid,0-nvl(sum(mktfee),0) mktfee from t_trade_h where cleardate=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy/MM/dd') group by cleardate,firmid) th, " + 
        " (select * from t_firm) tf" + 
        " where fbf.firmID=ffb.firmID(+)" + 
        " and tf.firmid(+) = ffb.firmID" + 
        " and ffb.firmid = th.firmid(+)";
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmDZValue value = new FirmDZValue();
        value.firmID = rs.getString("contact");
        value.account = rs.getString("account");
        value.firmRights = rs.getDouble("capital");
        value.cashRights = 0.0D;
        value.billRights = 0.0D;
        value.availableBalance = rs.getDouble("capital");
        value.butfunds = rs.getDouble("capital");
        value.cash = 0.0D;
        value.credit = 0.0D;
        value.quanyibh = rs.getDouble("mqyChange");
        
        result.putFdv(value);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
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
}
