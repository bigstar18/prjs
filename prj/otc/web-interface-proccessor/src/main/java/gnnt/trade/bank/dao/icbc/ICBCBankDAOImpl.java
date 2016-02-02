package gnnt.trade.bank.dao.icbc;

import gnnt.trade.bank.data.icbc.vo.Account;
import gnnt.trade.bank.data.icbc.vo.BankFirmRightValue;
import gnnt.trade.bank.data.icbc.vo.ProperBalanceValue;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.MarketAcount;
import gnnt.trade.bank.vo.TransferInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

public class ICBCBankDAOImpl
  extends ICBCBankDAO
{
  public ICBCBankDAOImpl()
    throws Exception
  {}
  
  public Vector<FirmInfo> getFirmInfo(String firmid, String bankid, String key)
  {
    log("===>>>查询扩充字段表内容" + new Date());
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
    log("===>>>插入数据到扩充字段表内容" + new Date());
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
    log("===>>>修改交易商注册信息表" + new Date());
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
  
  public Vector<BankTransferValue> getBankTransferList(String filter)
    throws SQLException, ClassNotFoundException
  {
    System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + 
      new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    BankTransferValue value = null;
    Vector<BankTransferValue> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid and bt.payacc = a1.code and bt.recacc = a2.code " + 
      




        filter;
      
      System.out.println("sql = " + sql);
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BankTransferValue();
        value.actionId = rs.getLong("actionId");
        value.createTime = rs.getTimestamp("createTime");
        value.funId = rs.getString("funId");
        value.id = rs.getLong("id");
        value.money = rs.getDouble("money");
        value.note = rs.getString("note");
        value.payAcc = rs.getString("payAcc");
        value.payBankId = rs.getString("payBankId");
        value.recAcc = rs.getString("recAcc");
        value.recBankId = rs.getString("recBankId");
        value.status = rs.getInt("status");
        value.updateTime = rs.getTimestamp("updateTime");
        
        value.payBankName = rs.getString("paybankname");
        value.recBankName = rs.getString("recbankname");
        value.payAccName = rs.getString("payaccname");
        value.recAccName = rs.getString("recAccName");
        value.recFirmId = rs.getString("recFirmId");
        
        value.capitalActionId = rs.getLong("capitalActionId");
        value.type = rs.getInt("type");
        value.info = rs.getString("info");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public Vector<BankTransferValue> getBankTransferList(String filter, Connection conn)
    throws SQLException, ClassNotFoundException
  {
    System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + 
      new Date());
    
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    BankTransferValue value = null;
    Vector<BankTransferValue> list = new Vector();
    try
    {
      sql = 
      




        "select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid and bt.payacc = a1.code and bt.recacc = a2.code " + filter;
      
      System.out.println("sql = " + sql);
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BankTransferValue();
        value.actionId = rs.getLong("actionId");
        value.createTime = rs.getTimestamp("createTime");
        value.funId = rs.getString("funId");
        value.id = rs.getLong("id");
        value.money = rs.getDouble("money");
        value.note = rs.getString("note");
        value.payAcc = rs.getString("payAcc");
        value.payBankId = rs.getString("payBankId");
        value.recAcc = rs.getString("recAcc");
        value.recBankId = rs.getString("recBankId");
        value.status = rs.getInt("status");
        value.updateTime = rs.getTimestamp("updateTime");
        
        value.payBankName = rs.getString("paybankname");
        value.recBankName = rs.getString("recbankname");
        value.payAccName = rs.getString("payaccname");
        value.recAccName = rs.getString("recAccName");
        value.recFirmId = rs.getString("recFirmId");
        
        value.capitalActionId = rs.getLong("capitalActionId");
        value.type = rs.getInt("type");
        value.info = rs.getString("info");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return list;
  }
  
  public Vector<BankValue> getBankList(String filter, Connection conn)
    throws SQLException
  {
    log("===>>>取得银行信息列表   getBankList  " + new Date());
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    BankValue value = null;
    Vector<BankValue> list = new Vector();
    try
    {
      sql = "select * from F_B_banks " + filter;
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BankValue();
        value.adapterClassname = rs.getString("adapterClassname");
        value.bankID = rs.getString("bankID");
        value.bankName = rs.getString("bankName");
        value.validFlag = rs.getInt("validFlag");
        value.beginTime = rs.getString("beginTime");
        value.endTime = rs.getString("endTime");
        value.control = rs.getInt("control");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return list;
  }
  
  public Vector<BankValue> getBankList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得银行信息列表   getBankList  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    BankValue value = null;
    Vector<BankValue> list = new Vector();
    try
    {
      conn = getConnection();
      
      sql = "select * from F_B_banks " + filter;
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BankValue();
        value.adapterClassname = rs.getString("adapterClassname");
        value.bankID = rs.getString("bankID");
        value.bankName = rs.getString("bankName");
        value.validFlag = rs.getInt("validFlag");
        value.beginTime = rs.getString("beginTime");
        value.endTime = rs.getString("endTime");
        value.control = rs.getInt("control");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public Vector<Account> getAccList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>获得银行端科目列表   getAccList " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    Account value = null;
    Vector<Account> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from F_B_Account " + filter;
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new Account();
        value.code = rs.getString("code");
        value.info = rs.getString("info");
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public long addBankTransfer(BankTransferValue val, Connection conn)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new Date());
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    long id = -1L;
    try
    {
      val.actionId = getActionID(conn);
      sql = "select seq_F_B_bankTransfer.nextval from dual";
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        id = rs.getLong(1);
      }
      sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc, money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";
      


      state = conn.prepareStatement(sql);
      state.setLong(1, id);
      state.setLong(2, val.actionId);
      state.setString(3, val.funId);
      state.setString(4, val.payBankId);
      state.setString(5, val.recBankId);
      state.setString(6, val.payAcc);
      state.setString(7, val.recAcc);
      state.setDouble(8, val.money);
      state.setInt(9, val.status);
      state.setString(10, val.note);
      state.setString(11, val.recFirmId);
      state.setLong(12, val.capitalActionId);
      state.setInt(13, val.type);
      state.setString(14, val.info);
      
      state.executeUpdate();
      System.out.println("【新增银行间资金划转流水】:流水号[" + id + "]" + "银行流水号[" + 
        val.funId + "]" + "市场流水号[" + val.actionId + "]" + "金额[" + 
        val.money + "]");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      id = -1L;
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return id;
  }
  
  public long addBankTransfer(BankTransferValue val)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new Date());
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    long id = -1L;
    
    Connection conn = null;
    long actionId;
    try
    {
      conn = getConnection();
      
      val.actionId = getActionID(conn);
      long actionId = val.actionId;
      sql = "select seq_F_B_bankTransfer.nextval from dual";
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        id = rs.getLong(1);
      }
      sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc, money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";
      


      state = conn.prepareStatement(sql);
      state.setLong(1, id);
      state.setLong(2, val.actionId);
      state.setString(3, val.funId);
      state.setString(4, val.payBankId);
      state.setString(5, val.recBankId);
      state.setString(6, val.payAcc);
      state.setString(7, val.recAcc);
      state.setDouble(8, val.money);
      state.setInt(9, val.status);
      state.setString(10, val.note);
      state.setString(11, val.recFirmId);
      state.setLong(12, val.capitalActionId);
      state.setInt(13, val.type);
      state.setString(14, val.info);
      
      state.executeUpdate();
      System.out.println("【新增银行间资金划转流水】:流水号[" + id + "]" + "银行流水号[" + 
        val.funId + "]" + "市场流水号[" + val.actionId + "]" + "金额[" + 
        val.money + "]");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      id = -1L;
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return actionId;
  }
  
  public long modBankTransfer(long id, int status, Connection conn)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>修改银行间资金划转流水记录状态   modBankTransfer  " + new Date());
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      sql = "update F_B_BankTransfer set status=?,updateTime=sysdate where id=?";
      
      state = conn.prepareStatement(sql);
      state.setInt(1, status);
      state.setLong(2, id);
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
      closeStatement(rs, state, null);
    }
    return result;
  }
  
  public Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue bfrv)
  {
    Vector<BankFirmRightValue> list = new Vector();
    String sql = "select * from F_B_BankCapitalResult where 1=1 ";
    if ((bfrv.bankId != null) && (!bfrv.bankId.trim().equals(""))) {
      sql = sql + "and bankId='" + bfrv.bankId.trim() + "' ";
    }
    if ((bfrv.firmId != null) && (!bfrv.firmId.trim().equals(""))) {
      sql = sql + "and firmId='" + bfrv.firmId.trim() + "' ";
    }
    if (bfrv.bdate != null) {
      sql = sql + " and trunc(bdate)=to_date('" + Tool.fmtDate(bfrv.bdate) + "','yyyy-MM-dd')";
    }
    System.out.println("银行传来的对账结果：" + sql);
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BankFirmRightValue bfr = new BankFirmRightValue();
        bfr.bankId = rs.getString("bankId");
        bfr.firmId = rs.getString("firmId");
        bfr.bankRight = rs.getDouble("bankRight");
        bfr.maketRight = rs.getDouble("maketRight");
        bfr.reason = rs.getInt("reason");
        bfr.bdate = rs.getTimestamp("bdate");
        list.add(bfr);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public Vector<BankFirmRightValue> getBankCapital(String filter)
  {
    Vector<BankFirmRightValue> list = new Vector();
    String sql = "select * from F_B_BankCapitalResult " + filter;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      System.out.println("银行传来的对账结果：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BankFirmRightValue bfr = new BankFirmRightValue();
        bfr.bankId = rs.getString("bankId");
        bfr.firmId = rs.getString("firmId");
        bfr.bankRight = rs.getDouble("bankRight");
        bfr.maketRight = rs.getDouble("maketRight");
        bfr.reason = rs.getInt("reason");
        bfr.bdate = rs.getTimestamp("bdate");
        list.add(bfr);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public long addProperBalance(ProperBalanceValue pbv)
  {
    long result = 0L;
    String sql = "insert into F_B_ProperBalance (bankId,allMoney,gongMoney,otherMoney,bdate) values(?,?,?,?,?)";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      if (pbv == null) {
        return -2L;
      }
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, pbv.bankId);
      state.setDouble(2, pbv.allMoney);
      state.setDouble(3, pbv.gongMoney);
      state.setDouble(4, pbv.otherMoney);
      state.setTimestamp(5, pbv.bdate);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public long updateProperBalance(ProperBalanceValue pbv)
  {
    long result = 0L;
    String sql = "update F_B_ProperBalance set allMoney=?,gongMoney=?,otherMoney=? where trunc(bdate)=to_date(?,'yyyy-MM-dd') and bankId=?";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      if (pbv == null) {
        return -2L;
      }
      System.out.println("修改总分平衡监管:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setDouble(1, pbv.allMoney);
      state.setDouble(2, pbv.gongMoney);
      state.setDouble(3, pbv.otherMoney);
      state.setString(4, Tool.fmtDate(pbv.bdate));
      state.setString(5, pbv.bankId);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public long delProperBalance(ProperBalanceValue pbv)
  {
    long result = 0L;
    String sql = "delete F_B_ProperBalance where 1=1 ";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      if (pbv != null)
      {
        if ((pbv.bankId != null) && (!pbv.bankId.trim().equals(""))) {
          sql = sql + " and bankId='" + pbv.bankId.trim() + "' ";
        }
        if (pbv.bdate != null) {
          sql = sql + " and trunc(bdate)=to_date('" + Tool.fmtDate(pbv.bdate) + "','yyyy-MM-dd')";
        }
      }
      System.out.println("删除总分平衡监管：" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue pbv)
  {
    Vector<ProperBalanceValue> list = new Vector();
    String sql = "select * from F_B_ProperBalance where 1=1 ";
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      if (pbv != null)
      {
        if ((pbv.bankId != null) && (!pbv.bankId.trim().equals(""))) {
          sql = sql + "and bankId='" + pbv.bankId.trim() + "' ";
        }
        if (pbv.bdate != null) {
          sql = sql + "and trunc(bdate)=to_date('" + Tool.fmtDate(pbv.bdate) + "','yyyy-MM-dd')";
        }
      }
      System.out.println("查询总分平衡监管：" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        ProperBalanceValue pb = new ProperBalanceValue();
        pb.bankId = rs.getString("bankId");
        pb.allMoney = rs.getDouble("allMoney");
        pb.gongMoney = rs.getDouble("gongMoney");
        pb.otherMoney = rs.getDouble("otherMoney");
        pb.bdate = rs.getTimestamp("bdate");
        list.add(pb);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public Vector<ProperBalanceValue> getProperBalance(String filter)
  {
    Vector<ProperBalanceValue> list = new Vector();
    String sql = "select * from F_B_ProperBalance " + filter;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        ProperBalanceValue pb = new ProperBalanceValue();
        pb.bankId = rs.getString("bankId");
        pb.allMoney = rs.getDouble("allMoney");
        pb.gongMoney = rs.getDouble("gongMoney");
        pb.otherMoney = rs.getDouble("otherMoney");
        pb.bdate = rs.getTimestamp("bdate");
        list.add(pb);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public long updateBankCapital(BankFirmRightValue bfrv)
  {
    long result = 0L;
    String sql = "update F_B_BankCapitalResult set bankRight=?,maketRight=?,reason=? where trunc(bdate)=to_date(?,'yyyy-MM-dd') and bankId=? and firmId=?";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      if (bfrv != null)
      {
        conn = getConnection();
        state = conn.prepareStatement(sql);
        state.setDouble(1, bfrv.bankRight);
        state.setDouble(2, bfrv.maketRight);
        state.setInt(3, bfrv.reason);
        state.setString(4, Tool.fmtDate(bfrv.bdate));
        state.setString(5, bfrv.bankId);
        state.setString(6, bfrv.firmId);
        state.executeUpdate();
      }
    }
    catch (SQLException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      result = -1L;
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public long addBankCapital(BankFirmRightValue bfrv)
  {
    long result = 0L;
    String sql = "insert into F_B_BankCapitalResult (bankId,firmId,bankRight,maketRight,reason,bdate) values(?,?,?,?,?,?)";
    Connection conn = null;
    PreparedStatement state = null;
    try
    {
      if (bfrv == null) {
        return -2L;
      }
      System.out.println(bfrv.toString());
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, bfrv.bankId);
      state.setString(2, bfrv.firmId);
      state.setDouble(3, bfrv.bankRight);
      state.setDouble(4, bfrv.maketRight);
      state.setInt(5, bfrv.reason);
      state.setTimestamp(6, bfrv.bdate);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<MarketAcount> getMarketAcount(String filter)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    MarketAcount value = null;
    Vector<MarketAcount> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from F_B_MARKETACOUNT where 1 = 1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      log("getMarketAcount" + sql);
      while (rs.next())
      {
        value = new MarketAcount();
        value.account = rs.getString("account");
        value.accountName = rs.getString("accountName");
        value.accountOherName = rs.getString("accountOherName");
        value.bankName = rs.getString("bankName");
        value.bankOherName = rs.getString("bankOherName");
        value.type = rs.getInt("type");
        value.bankFlag = rs.getInt("bankFlag");
        value.bankCode = rs.getString("bankCode");
        value.bankId = rs.getString("bankId");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public int addMarketAcount(MarketAcount val)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>添加市场账号   addMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      conn = getConnection();
      
      sql = "insert into f_b_marketacount (account, accountName, accountOherName, bankOherName, bankName, type, bankFlag, bankCode ,bankid)values ('" + 
        val.account + "','" + val.accountName + "','" + val.accountOherName + "','" + val.bankOherName + "','" + val.bankName + 
        "'," + val.type + "," + val.bankFlag + ",'" + val.bankCode + "','" + val.bankId + "')";
      
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
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int delMarketAcount(String account)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>删除市场账号   delMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    
    int result = 0;
    String sql;
    try
    {
      conn = getConnection();
      String sql = "delete from f_b_marketacount where account='" + account + "'";
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int modMarketAcount(MarketAcount value)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>修改市场账号   modMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    
    int result = 0;
    String sql;
    try
    {
      conn = getConnection();
      String sql = "update F_B_MARKETACOUNT set account = '" + value.account + "'," + " accountName = '" + 
        value.accountName + "'," + " accountOherName = '" + value.accountOherName + "'," + 
        " bankOherName = '" + value.bankOherName + "'," + " bankName = '" + value.bankName + "'," + 
        " type = " + value.type + "," + " bankFlag = " + value.bankFlag + "," + " bankCode = '" + 
        value.bankCode + "' where account = '" + value.account + "'";
      System.out.println(sql);
      state = conn.prepareStatement(sql);
      
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<TransferInfo> getTransferList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>转账流水查询   getTransferList  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    TransferInfo value = null;
    Vector<TransferInfo> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_transferinfo t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new TransferInfo();
        value.bankid = rs.getString("bankid");
        value.funId = rs.getString("funId");
        value.actionId = rs.getString("actionId");
        value.money = rs.getDouble("money");
        value.type = rs.getInt("type");
        value.createTime = rs.getTimestamp("createTime");
        value.outAccountName = rs.getString("outAccountName");
        value.outAccount = rs.getString("outAccount");
        value.inAccountName = rs.getString("inAccountName");
        value.inAccount = rs.getString("inAccount");
        value.bankTime = rs.getTimestamp("bankTime");
        value.status = rs.getInt("status");
        
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
  
  public int addTransfer(TransferInfo param)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      conn = getConnection();
      sql = "insert into f_b_transferinfo values('" + 
        param.funId + "','" + param.actionId + "'," + param.money + "," + param.type + ",sysdate,sysdate,'" + 
        param.outAccount + "','" + param.inAccount + "'," + param.status + ",'" + param.outAccountName + "','" + param.inAccountName + "','" + 
        param.bankid + "')";
      log(sql);
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
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int modTransfer(String actionId, String funID, int status, Timestamp bankTime, Connection conn)
    throws SQLException
  {
    log("===>>>修改转账资金流水记录状态   modTransfer  " + new Date());
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "update f_b_transferinfo set  status='" + status + "',FUNID='" + funID + "' where actionId='" + actionId + "'";
      state = conn.prepareStatement(sql);
      
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
}
