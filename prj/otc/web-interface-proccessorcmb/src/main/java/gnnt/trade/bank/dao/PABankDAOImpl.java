package gnnt.trade.bank.dao;

import gnnt.trade.bank.data.sfz.vo.BatCustDzBChild;
import gnnt.trade.bank.data.sfz.vo.BatCustDzFailChild;
import gnnt.trade.bank.data.sfz.vo.BatFailResultChild;
import gnnt.trade.bank.data.sfz.vo.BatQsChild;
import gnnt.trade.bank.data.sfz.vo.KXHfailChild;
import gnnt.trade.bank.data.sfz.vo.PAConstant;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class PABankDAOImpl
  extends PABankDAO
{
  public PABankDAOImpl()
    throws Exception
  {}
  
  public Vector<FirmBalanceValue> getFlote(String[] firmIDs)
    throws SQLException, ClassNotFoundException
  {
    Vector<FirmBalanceValue> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = "";
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String firms = "";
      for (firmID : firmIDs) {
        if ((firmID != null) && (firmID.trim().length() > 0)) {
          firms = firms + "'" + firmID.trim() + "',";
        }
      }
      if ((firms != null) && (firms.trim().length() > 0)) {
        filter = filter + " and firmid in (" + firms.substring(0, firms.length() - 1) + ") ";
      }
    }
    try
    {
      sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter + " group by firmid order by firmid";
      log(sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmBalanceValue value = new FirmBalanceValue();
        value.firmId = rs.getString("firmID");
        value.floatingloss = rs.getDouble("floatingloss");
        result.add(value);
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
  
  public int delFirmBalanceError(String bankID, String date)
    throws SQLException, ClassNotFoundException
  {
    log("删除相应的银行对账失败文件信息  delFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    String filter = " where 1=1 ";
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID='" + bankID.trim() + "'";
    }
    if ((date != null) && (date.trim().length() > 0))
    {
      java.util.Date dd = Tool.getDateTime(date);
      filter = filter + " and trunc(TRANDATETIME)=to_date('" + Tool.fmtDate(dd) + "','yyyy-MM-dd')";
    }
    try
    {
      sql = "delete F_B_FIRMBALANCEERROR " + filter;
      System.out.println("sql:" + sql);
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int addFirmBalanceError(BatFailResultChild fbe, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("添加银行对账失败文件  addFirmBalanceError  时间：" + Tool.fmtTime(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_FIRMBALANCEERROR (TRANDATETIME,COUNTERID,SUPACCTID,FUNCCODE,CUSTACCTID,CUSTNAME,THIRDCUSTID,THIRDLOGNO,CCYCODE,FREEZEAMOUNT,UNFREEZEAMOUNT,ADDTRANAMOUNT,CUTTRANAMOUNT,PROFITAMOUNT,LOSSAMOUNT,TRANHANDFEE,TRANCOUNT,NEWBALANCE,NEWFREEZEAMOUNT,NOTE,RESERVE,RSPCODE,RSPMSG,BANKID,CREATETIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
      

      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setDate(1, new java.sql.Date(Tool.getDateTime(fbe.TranDateTime).getTime()));
      state.setString(2, fbe.CounterId);
      state.setString(3, fbe.SupAcctId);
      state.setString(4, fbe.FuncCode);
      state.setString(5, fbe.CustAcctId);
      state.setString(6, fbe.CustName);
      state.setString(7, fbe.ThirdCustId);
      state.setString(8, fbe.ThirdLogNo);
      state.setString(9, fbe.CcyCode);
      state.setDouble(10, fbe.FreezeAmount);
      state.setDouble(11, fbe.UnfreezeAmount);
      state.setDouble(12, fbe.AddTranAmount);
      state.setDouble(13, fbe.CutTranAmount);
      state.setDouble(14, fbe.ProfitAmount);
      state.setDouble(15, fbe.LossAmount);
      state.setDouble(16, fbe.TranHandFee);
      state.setInt(17, fbe.TranCount);
      state.setDouble(18, fbe.NewBalance);
      state.setDouble(19, fbe.NewFreezeAmount);
      state.setString(20, fbe.Note);
      state.setString(21, fbe.Reserve);
      state.setString(22, fbe.RspCode);
      state.setString(23, fbe.RspMsg);
      state.setString(24, bankID);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<BatCustDzBChild> getFirmBalanceFile(String ThirdCustId, String bankID, java.util.Date date)
    throws SQLException, ClassNotFoundException
  {
    log("查询交易商银行余额信息  getFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
    Vector<BatCustDzBChild> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " where 1=1 ";
    if ((ThirdCustId != null) && (ThirdCustId.trim().length() > 0)) {
      filter = filter + " and THIRDCUSTID='" + ThirdCustId.trim() + "'";
    }
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and BANKID='" + bankID.trim() + "'";
    }
    if (date != null) {
      filter = filter + " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
    }
    try
    {
      sql = "select * from F_B_FIRMBALANCE " + filter;
      System.out.println("sql:" + sql);
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BatCustDzBChild bcd = new BatCustDzBChild();
        bcd.CustAcctId = rs.getString("CUSTACCTID");
        bcd.CustName = rs.getString("CUSTNAME");
        bcd.ThirdCustId = rs.getString("THIRDCUSTID");
        bcd.Status = rs.getInt("STATUS");
        bcd.Type = rs.getInt("TYPE");
        bcd.IsTrueCont = rs.getInt("ISTRUECONT");
        bcd.Balance = rs.getDouble("BALANCE");
        bcd.UsrBalance = rs.getDouble("USRBALANCE");
        bcd.FrozenBalance = rs.getDouble("FROZENBALANCE");
        bcd.Interest = rs.getDouble("INTEREST");
        result.add(bcd);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public int modFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("修改交易商子账号信息  modFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "update F_B_FIRMBALANCE set CUSTACCTID=?,CUSTNAME=?,STATUS=?,TYPE=?,ISTRUECONT=?,BALANCE=?,USRBALANCE=?,FROZENBALANCE=?,INTEREST=? where BANKID=? and THIRDCUSTID=? and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, child.CustAcctId);
      state.setString(2, child.CustName);
      state.setInt(3, child.Status);
      state.setInt(4, child.Type);
      state.setInt(5, child.IsTrueCont);
      state.setDouble(6, child.Balance);
      state.setDouble(7, child.UsrBalance);
      state.setDouble(8, child.FrozenBalance);
      state.setDouble(9, child.Interest);
      state.setString(10, bankID);
      state.setString(11, child.ThirdCustId);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int addFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("添加交易商余额文件  addFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_FIRMBALANCE (CUSTACCTID,CUSTNAME,THIRDCUSTID,STATUS,TYPE,ISTRUECONT,BALANCE,USRBALANCE,FROZENBALANCE,INTEREST,BANKID,TRADEDATE,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, child.CustAcctId);
      state.setString(2, child.CustName);
      state.setString(3, child.ThirdCustId);
      state.setInt(4, child.Status);
      state.setInt(5, child.Type);
      state.setInt(6, child.IsTrueCont);
      state.setDouble(7, child.Balance);
      state.setDouble(8, child.UsrBalance);
      state.setDouble(9, child.FrozenBalance);
      state.setDouble(10, child.Interest);
      state.setString(11, bankID);
      state.setDate(12, new java.sql.Date(date.getTime()));
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<KXHfailChild> getFirmKXH1(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("根据签约日期查询签解约信息  getFirmKXH  " + Tool.fmtTime(new java.util.Date()));
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    KXHfailChild result = null;
    Vector<KXHfailChild> KXHfailList = new Vector();
    try
    {
      sql = "select * from F_B_FIRMKXH where 1=1 " + filter;
      System.out.println("sql:" + sql);
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        result = new KXHfailChild();
        result.account1 = rs.getString("account1");
        result.account1Name = rs.getString("account1Name");
        result.counterId = rs.getString("counterId");
        result.firmID = rs.getString("firmID");
        result.funID = rs.getString("funID");
        result.status = rs.getInt("status");
        result.tradeDate = rs.getDate("tradeDate");
        result.type = rs.getInt("type");
        KXHfailList.add(result);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return KXHfailList;
  }
  
  public KXHfailChild getFirmKXH(String funID)
    throws SQLException, ClassNotFoundException
  {
    log("根据银行流水号查询签解约信息  getFirmKXH  " + Tool.fmtTime(new java.util.Date()));
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    KXHfailChild result = null;
    try
    {
      sql = "select * from F_B_FIRMKXH where funID='" + funID + "'";
      System.out.println("sql:" + sql);
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      if (rs.next())
      {
        result = new KXHfailChild();
        result.account1 = rs.getString("account1");
        result.account1Name = rs.getString("account1Name");
        result.counterId = rs.getString("counterId");
        result.firmID = rs.getString("firmID");
        result.funID = rs.getString("funID");
        result.status = rs.getInt("status");
        result.tradeDate = rs.getDate("tradeDate");
        result.type = rs.getInt("type");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int addFirmKXH(KXHfailChild child, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("添加会员的签解约信息  addFirmKXH  " + Tool.fmtTime(new java.util.Date()));
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      sql = "insert into F_B_FIRMKXH (FUNID,STATUS,ACCOUNT1,TYPE,ACCOUNT1NAME,FIRMID,TRADEDATE,COUNTERID,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,sysdate)";
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, child.funID);
      state.setInt(2, child.status);
      state.setString(3, child.account1);
      state.setInt(4, child.type);
      state.setString(5, child.account1Name);
      state.setString(6, child.firmID);
      state.setDate(7, new java.sql.Date(child.tradeDate.getTime()));
      state.setString(8, child.counterId);
      state.setString(9, bankID);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int delBatCustDz(java.util.Date date, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("删除对账不平记录  delBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    String filter = " where 1=1 ";
    if (date != null) {
      filter = filter + " and trunc(tradedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
    }
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankid='" + bankID + "'";
    }
    try
    {
      sql = "delete F_B_BATCUSTFILE " + filter;
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int addBatCustDz(BatCustDzFailChild child, java.util.Date date, String bankID)
    throws SQLException, ClassNotFoundException
  {
    log("添加银行对账不平记录  addBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
    int result = 0;
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_BATCUSTFILE (CUSTACCTID,CUSTNAME,THIRDCUSTID,BANKBALANCE,BANKFROZEN,MAKETBALANCE,MAKETFROZEN,BALANCEERROR,FROZENERROR,NOTE,TRADEDATE,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
      conn = getConnection();
      state = conn.prepareStatement(sql);
      state.setString(1, child.CustAcctId);
      state.setString(2, child.CustName);
      state.setString(3, child.ThirdCustId);
      state.setDouble(4, child.BankBalance);
      state.setDouble(5, child.BankFrozen);
      state.setDouble(6, child.MaketBalance);
      state.setDouble(7, child.MaketFrozen);
      state.setDouble(8, child.BalanceError);
      state.setDouble(9, child.FrozenError);
      state.setString(10, child.Note);
      state.setDate(11, new java.sql.Date(date.getTime()));
      state.setString(12, bankID);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int addBankQS(BankQSVO bq, Connection conn)
    throws SQLException
  {
    log("添加清算日期表");
    int result = 0;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      state = conn.prepareStatement("select * from F_B_BankQSDate where to_char(TRADEDATE,'yyyy-MM-dd')='" + Tool.fmtDate(bq.tradeDate) + "' and bankid = '" + bq.bankID + "'");
      rs = state.executeQuery();
      if (!rs.next())
      {
        closeStatement(rs, state, null);
        sql = "insert into F_B_BankQSDate (BANKID,TRADEDATE,TRADETYPE,TRADESTATUS,NOTE) values (?,?,?,?,?)";
        state = conn.prepareStatement(sql);
        state.setString(1, bq.bankID);
        state.setDate(2, new java.sql.Date(bq.tradeDate.getTime()));
        state.setInt(3, bq.tradeType);
        state.setInt(4, bq.tradeStatus);
        state.setString(5, bq.note);
        result = state.executeUpdate();
      }
      else
      {
        log("清算日期[" + bq.tradeDate + "]银行[" + bq.bankID + "]清算记录已存在");
        result = 1;
      }
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
  
  public Map<String, BatQsChild> getQSChild(String bankID, Set<String> firmIDs, Set<String> notFirmIDs, java.util.Date qdate, Connection conn)
    throws SQLException
  {
    log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
    Map<String, BatQsChild> result = new HashMap();
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-nvl(th.mktfee,0)) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,abs(nvl(th.mktfee,0)) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    










      PAConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + PAConstant.bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select firmid,0-nvl(sum(mktfee),0) mktfee from t_trade_h where cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy/MM/dd') group by cleardate,firmid) th, " + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and tf.firmid(+) = ffb.firmID" + 
      " and ffb.firmid = th.firmid(+)";
    

    log("sql:" + sql);
    try
    {
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BatQsChild value = new BatQsChild();
        value.AddTranAmount = 0.0D;
        value.CutTranAmount = 0.0D;
        if (rs.getDouble("qyChange") >= 0.0D)
        {
          value.ProfitAmount = rs.getDouble("qyChange");
          value.LossAmount = 0.0D;
        }
        else
        {
          value.ProfitAmount = 0.0D;
          value.LossAmount = (-rs.getDouble("qyChange"));
        }
        value.TranHandFee = rs.getDouble("fee");
        value.FreezeAmount = 0.0D;
        value.UnfreezeAmount = 0.0D;
        value.NewBalance = rs.getDouble("capital");
        value.NewFreezeAmount = 0.0D;
        value.ThirdCustId = rs.getString("contact");
        value.CustAcctId = rs.getString("account1");
        value.CustName = rs.getString("accountName");
        value.ThirdLogNo = getActionID();
        value.TranDateTime = Tool.fmtDateTime(qdate);
        value.toDhykAmount = 0.0D;
        value.yeDhykAmount = 0.0D;
        value.toQianAmount = 0.0D;
        value.yeQianAmount = 0.0D;
        result.put(value.ThirdCustId, value);
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
    return result;
  }
  
  public java.util.Date getMaxBankQSList(String bankID, java.util.Date date, Connection conn)
    throws SQLException
  {
    log("查询清算日期表");
    java.util.Date result = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      sql = "select max(tradeDate) tradeDate from F_B_BankQSDATE where bankid='" + bankID + "' and trunc(tradeDate)<to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      log(sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      if (rs.next()) {
        result = rs.getDate("tradeDate");
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
  
  public Vector<KXHfailChild> getBankOpen(String bankID, String[] firmIDs, int status, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    log("查询某一天的签约信息  getBankOpen   时间：" + Tool.fmtDate(new java.util.Date()));
    Vector<KXHfailChild> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " where 1=1 ";
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankID='" + bankID.trim() + "' ";
    }
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String str = "('aa'";
      for (firmID : firmIDs) {
        str = str + ",'" + firmID + "' ";
      }
      filter = filter + " and firmID in" + str + ") ";
    }
    if (status > 0) {
      filter = filter + " and status=" + status + " ";
    }
    if (tradeDate != null) {
      filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
    }
    try
    {
      sql = "select * from f_b_firmkxh " + filter;
      System.out.println("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        KXHfailChild value = new KXHfailChild();
        value.funID = rs.getString("funID");
        value.status = rs.getInt("status");
        value.account1 = rs.getString("account1");
        value.type = rs.getInt("type");
        value.account1Name = rs.getString("account1Name");
        value.firmID = rs.getString("firmID");
        value.tradeDate = rs.getDate("tradeDate");
        value.counterId = rs.getString("counterId");
        result.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public java.util.Date getlastDate(java.util.Date date, Connection conn)
    throws SQLException
  {
    java.util.Date result = null;
    String sql = "select max(B_Date) bdate from f_firmbalance t where to_char(B_Date,'yyyy-MM-dd')<'" + Tool.fmtDate(date) + "'";
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      if (rs.next()) {
        result = rs.getDate("bdate");
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
    if (result == null) {
      result = Tool.strToDate("1900-01-01");
    }
    return result;
  }
  
  public BankQSVO getBankQS(String bankID, java.util.Date date, Connection conn)
    throws SQLException
  {
    log("查询某天清算结果");
    BankQSVO bqs = new BankQSVO();
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      sql = "select * from F_B_BankQSDATE where bankid='" + bankID + "' and trunc(tradeDate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      if (rs.next())
      {
        bqs = new BankQSVO();
        bqs.bankID = bankID;
        bqs.note = rs.getString("note");
        bqs.tradeDate = date;
        bqs.tradeStatus = rs.getInt("tradeStatus");
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
    return bqs;
  }
  
  public Vector<BatFailResultChild> getFirmBalanceError(String[] firmIDs, String bankID, java.util.Date date)
    throws SQLException, ClassNotFoundException
  {
    log("查询银行对账失败信息  getFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
    Vector<BatFailResultChild> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " where 1=1 ";
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String str = "('aa'";
      for (i = 0; i < firmIDs.length; i++) {
        str = str + ",'" + firmIDs[i] + "'";
      }
      str = str + ") ";
      filter = filter + " and ThirdCustId in " + str;
    }
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and bankid='" + bankID.trim() + "' ";
    }
    if (date != null) {
      filter = filter + " and trunc(TranDateTime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
    }
    try
    {
      sql = "select * from f_b_firmbalanceerror " + filter;
      System.out.println("sql:" + sql);
      log("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BatFailResultChild value = new BatFailResultChild();
        value.TranDateTime = Tool.fmtTime(rs.getDate("TRANDATETIME"));
        value.CounterId = rs.getString("COUNTERID");
        value.SupAcctId = rs.getString("SUPACCTID");
        value.FuncCode = rs.getString("FUNCCODE");
        value.CustAcctId = rs.getString("CUSTACCTID");
        value.CustName = rs.getString("CUSTNAME");
        value.ThirdCustId = rs.getString("THIRDCUSTID");
        value.ThirdLogNo = rs.getString("THIRDLOGNO");
        value.CcyCode = rs.getString("CCYCODE");
        value.FreezeAmount = rs.getDouble("FREEZEAMOUNT");
        value.UnfreezeAmount = rs.getDouble("UNFREEZEAMOUNT");
        value.AddTranAmount = rs.getDouble("ADDTRANAMOUNT");
        value.CutTranAmount = rs.getDouble("CUTTRANAMOUNT");
        value.ProfitAmount = rs.getDouble("PROFITAMOUNT");
        value.LossAmount = rs.getDouble("LOSSAMOUNT");
        value.TranHandFee = rs.getDouble("TRANHANDFEE");
        value.TranCount = rs.getInt("TRANCOUNT");
        value.NewBalance = rs.getDouble("NEWBALANCE");
        value.NewFreezeAmount = rs.getDouble("NEWFREEZEAMOUNT");
        value.Note = rs.getString("NOTE");
        value.Reserve = rs.getString("RESERVE");
        value.RspCode = rs.getString("RSPCODE");
        value.RspMsg = rs.getString("RSPMSG");
        result.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public Vector<BatCustDzFailChild> getBatCustDz(String[] firmIDs, String bankID, java.util.Date date)
    throws SQLException, ClassNotFoundException
  {
    log("查询银行对账不平文件  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
    Vector<BatCustDzFailChild> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " where 1=1 ";
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String str = " and ThirdCustId in ('aa'";
      for (i = 0; i < firmIDs.length; i++) {
        str = str + ",'" + firmIDs[i] + "'";
      }
      filter = filter + str + ") ";
    }
    if ((bankID != null) && (bankID.trim().length() > 0)) {
      filter = filter + " and BANKID='" + bankID.trim() + "'";
    }
    if (date != null) {
      filter = filter + " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
    }
    try
    {
      sql = "select * from F_B_BATCUSTFILE " + filter;
      System.out.println("sql:" + sql);
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BatCustDzFailChild value = new BatCustDzFailChild();
        value.CustAcctId = rs.getString("CustAcctId");
        value.CustName = rs.getString("CustName");
        value.ThirdCustId = rs.getString("ThirdCustId");
        value.BankBalance = rs.getDouble("BankBalance");
        value.BankFrozen = rs.getDouble("BankFrozen");
        value.MaketBalance = rs.getDouble("MaketBalance");
        value.MaketFrozen = rs.getDouble("MaketFrozen");
        value.BalanceError = rs.getDouble("BalanceError");
        value.FrozenError = rs.getDouble("FrozenError");
        value.Note = rs.getString("Note");
        result.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public FirmBalanceValue availableBalance(String filter)
  {
    log("===>>>查询交易商市场可用资金  availableBalance  " + new java.util.Date());
    
    FirmBalanceValue fbv = new FirmBalanceValue();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      conn = getConnection();
      sql = " select firmid,balance,FROZENFUNDS,lastbalance from f_firmfunds " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        fbv.setFirmId(rs.getString(1));
        fbv.setAvilableBalance(rs.getDouble(2) - rs.getDouble(3));
        fbv.setMarketBalance(rs.getDouble(2));
        fbv.setFrozenBalance(rs.getDouble(3));
        fbv.setLastBalance(rs.getDouble(4));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return fbv;
  }
  
  public long getActionID()
  {
    log("===>>>取得市场业务流水号   getActionID  " + new java.util.Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    long id = 0L;
    try
    {
      conn = getConnection();
      
      sql = "select seq_F_B_action.nextval from dual";
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        id = rs.getLong(1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return id;
  }
  
  public int modBankQS(BankQSVO bq, Connection conn)
    throws SQLException
  {
    log("===>>>更新清算结果状态  modBankQS  " + new java.util.Date());
    PreparedStatement state = null;
    String sql = null;
    int result = -1;
    try
    {
      int n = 1;
      sql = "update F_B_BankQSDate set TRADESTATUS=? where bankid=? and to_char(TRADEDATE,'yyyy-MM-dd')=?";
      conn.setAutoCommit(false);
      state = conn.prepareStatement(sql);
      state.setInt(n++, bq.tradeStatus);
      state.setString(n++, bq.bankID);
      state.setString(n++, Tool.fmtDate(bq.tradeDate));
      int i = state.executeUpdate();
      if (i == 1)
      {
        result = 0;
        conn.commit();
      }
      else
      {
        result = -1;
        conn.rollback();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      conn.setAutoCommit(true);
      closeStatement(null, state, null);
    }
    return result;
  }
}
