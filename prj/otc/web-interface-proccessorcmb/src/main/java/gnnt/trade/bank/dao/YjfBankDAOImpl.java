package gnnt.trade.bank.dao;

import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CompareSumMoney;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
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
import org.apache.log4j.Logger;

public class YjfBankDAOImpl
  extends YjfBankDAO
{
  public YjfBankDAOImpl()
    throws Exception
  {}
  
  public Vector<BankQSVO> getBankQSDate(String filter)
    throws SQLException
  {
    Vector<BankQSVO> list = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "";
    try
    {
      conn = getConnection();
      sql = "select * from f_b_bankqsdate " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        BankQSVO vo = new BankQSVO();
        vo.bankID = rs.getString("bankid");
        vo.tradeDate = rs.getDate("tradedate");
        vo.tradeType = rs.getInt("tradetype");
        vo.tradeStatus = rs.getInt("tradestatus");
        vo.note = rs.getString("note");
        vo.createDate = rs.getDate("createdate");
        list.add(vo);
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
    return list;
  }
  
  public int addBankQS(BankQSVO bq, Connection conn)
    throws SQLException
  {
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_BankQSDate (BANKID,TRADEDATE,TRADETYPE,TRADESTATUS,NOTE) values (?,?,?,?,?)";
      state = conn.prepareStatement(sql);
      state.setString(1, bq.bankID);
      state.setDate(2, new java.sql.Date(bq.tradeDate.getTime()));
      state.setInt(3, bq.tradeType);
      state.setInt(4, bq.tradeStatus);
      state.setString(5, bq.note);
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
  
  public int modBankQS(BankQSVO bq, Connection conn)
    throws SQLException
  {
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "update F_B_BankQSDate set TRADESTATUS=? where BANKID=? and TRADEDATE=?";
      state = conn.prepareStatement(sql);
      state.setInt(1, bq.tradeStatus);
      state.setString(2, bq.bankID);
      state.setDate(3, new java.sql.Date(bq.tradeDate.getTime()));
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
    System.out.println("修改清算日期表的结果：" + result);
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
  
  public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZQSValue result = new RZQSValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " ";
    String fcfilter1 = " ";
    String fcfilter2 = " ";
    String fcfilter2y = " ";
    String thfilter = " ";
    String thfiltery = " ";
    try
    {
      conn = getConnection();
      if ((bankID != null) && (bankID.trim().length() > 0)) {
        filter = filter + " and bankID='" + bankID.trim() + "' ";
      }
      if ((firmIDs != null) && (firmIDs.length > 0))
      {
        String firms = "";
        for (String firmID : firmIDs) {
          if ((firmID != null) && (firmID.trim().length() > 0)) {
            firms = firms + "'" + firmID.trim() + "',";
          }
        }
        if ((firms != null) && (firms.trim().length() > 0))
        {
          firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
          filter = filter + firms;
          fcfilter1 = fcfilter1 + firms;
          fcfilter2 = fcfilter2 + firms;
          fcfilter2y = fcfilter2y + firms;
          thfilter = thfilter + firms;
          thfiltery = thfiltery + firms;
        }
      }
      if (tradeDate != null)
      {
        result.tradeDate = tradeDate;
        java.util.Date yestoday = getlastDate(tradeDate, conn);
        fcfilter1 = fcfilter1 + " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        fcfilter2 = fcfilter2 + " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        thfilter = thfilter + " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        if (yestoday != null)
        {
          fcfilter2y = fcfilter2y + " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
          thfiltery = thfiltery + " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
        }
      }
      sql = 
      











        "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.contact contact,fbf.accountName accountName,nvl((ffb.capital-ffb.lastcapital-ffb.fundio),0) qyChange,nvl((ffb.tradefee),0) fee,nvl(ffb.capital,0),nvl(ffb.fundio,0) from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + bankID + "' and f.isOpen=1 ) fbf ," + " (select * from t_firm) tf" + " where fbf.firmID=ffb.firmID(+)" + " and tf.firmid(+) = ffb.firmID";
      System.out.println("清算sql：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      MarketRightValue mrv = new MarketRightValue();
      mrv.maketMoney = new BigDecimal("0");
      while (rs.next())
      {
        FirmRightValue value = new FirmRightValue();
        value.actionID = rs.getString("actionid");
        value.firmID = rs.getString("contact");
        value.tradeFee = rs.getString("fee");
        value.settleFee = "0";
        value.account = rs.getString("account");
        value.availableBalance = rs.getDouble("qyChange");
        value.billMoney = 0.0D;
        value.cash = 0.0D;
        value.cashMoney = 0.0D;
        value.credit = 0.0D;
        value.firmErrorMoney = rs.getDouble("qyChange");
        mrv.bankErrorMoney += rs.getDouble("qyChange");
        

        mrv.maketMoney = mrv.maketMoney.add(rs.getBigDecimal("fee"));
        result.putFrv(value);
      }
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
  
  public java.util.Date getMaxBankQSSuccessDate(String bankID, java.util.Date date, Connection conn)
    throws SQLException
  {
    java.util.Date result = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      sql = "select max(tradeDate) tradeDate from F_B_BankQSDATE where bankid='" + bankID + "' and tradestatus=0 and trunc(tradeDate)<to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
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
  
  public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZDZValue result = new RZDZValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " ";
    String fcfilter1 = " ";
    String fcfilter2 = " ";
    String fcfilter2y = " ";
    String thfilter = " ";
    String thfiltery = " ";
    try
    {
      conn = getConnection();
      if ((bankID != null) && (bankID.trim().length() > 0)) {
        filter = filter + " and bankID='" + bankID.trim() + "' ";
      }
      if ((firmIDs != null) && (firmIDs.length > 0))
      {
        String firms = "";
        for (String firmID : firmIDs) {
          if ((firmID != null) && (firmID.trim().length() > 0)) {
            firms = firms + "'" + firmID.trim() + "',";
          }
        }
        if ((firms != null) && (firms.trim().length() > 0))
        {
          firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
          filter = filter + firms;
          fcfilter1 = fcfilter1 + firms;
          fcfilter2 = fcfilter2 + firms;
          fcfilter2y = fcfilter2y + firms;
          thfilter = thfilter + firms;
          thfiltery = thfiltery + firms;
        }
      }
      if (tradeDate != null)
      {
        result.tradeDate = tradeDate;
        java.util.Date yestoday = getlastDate(tradeDate, conn);
        fcfilter1 = fcfilter1 + " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        fcfilter2 = fcfilter2 + " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        thfilter = thfilter + " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        if (yestoday != null)
        {
          fcfilter2y = fcfilter2y + " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
          thfiltery = thfiltery + " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
        }
      }
      sql = 
      











        "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.contact contact,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio) qyChange,(ffb.tradefee) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + bankID + "' and f.isOpen=1 ) fbf ," + " (select * from t_firm) tf" + " where fbf.firmID=ffb.firmID(+)" + " and tf.firmid(+) = ffb.firmID";
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
        value.cash = 0.0D;
        value.credit = 0.0D;
        value.quanyibh = rs.getDouble("qyChange");
        value.butfunds = rs.getDouble("capital");
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
  
  public Vector<CompareResult> getBankNoInfo(String bankID, java.util.Date date, int[] pageinfo)
    throws SQLException, ClassNotFoundException
  {
    Vector<CompareResult> result = null;
    Connection conn = null;
    try
    {
      conn = getConnection();
      result = getBankNoInfo(bankID, date, conn, pageinfo);
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
  
  public Vector<CompareResult> getBankNoInfo(String bankID, java.util.Date date, Connection conn, int[] pageinfo)
    throws SQLException
  {
    log("查询市场有，银行没有的流水：");
    Vector<CompareResult> result = new Vector();
    PreparedStatement state = null;
    String sql = null;
    ResultSet rs = null;
    CompareResult value = null;
    try
    {
      sql = 
      





        "select fbc.firmid firmID,fbc.contact contact,fbf.account account,fbc.type type,fbc.money money ,fbc.banktime tradeDate,fbc.actionid actionID,fbc.funid funID,fbc.bankid bankID,fbc.status status  from f_b_capitalinfo fbc,f_b_firmidandaccount fbf  where fbc.type in (0,1) and fbc.firmid = fbf.firmid(+) and fbc.bankid=fbf.bankid(+) and fbc.status not in (1,9)  and not exists (select funid from f_b_bankcompareinfo fbb where fbc.funid=fbb.funid  and fbc.contact=fbb.firmid and fbc.money=fbb.money and trunc(fbc.createtime)=trunc(fbb.comparedate)  and fbc.bankid=fbb.bankid and fbc.type=fbb.type and fbb.status=0)  and fbc.bankid='" + bankID + "' and trunc(createtime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      log("查询市场有，银行没有的流水sql：" + sql);
      if ((pageinfo == null) || (pageinfo.length <= 0))
      {
        state = conn.prepareStatement(sql);
        rs = state.executeQuery();
      }
      else
      {
        rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
      }
      while (rs.next())
      {
        value = new CompareResult();
        value.account = rs.getString("account");
        value.actionID = rs.getLong("actionID");
        value.bankID = rs.getString("bankID");
        value.contact = rs.getString("contact");
        value.errorType = 3;
        value.firmID = rs.getString("firmID");
        value.funID = rs.getString("funID");
        value.money = rs.getDouble("money");
        value.tradeDate = rs.getDate("tradeDate");
        value.type = rs.getInt("type");
        value.status = rs.getInt("status");
        result.add(value);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    return result;
  }
  
  public void log(String string)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(string);
  }
  
  public Vector<CompareResult> getMarketNoInfo(String bankID, java.util.Date date, int[] pageinfo)
    throws SQLException, ClassNotFoundException
  {
    Vector<CompareResult> result = null;
    Connection conn = null;
    try
    {
      conn = getConnection();
      result = getMarketNoInfo(bankID, date, conn, pageinfo);
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
  
  public Vector<CompareResult> getMarketNoInfo(String bankID, java.util.Date date, Connection conn, int[] pageinfo)
    throws SQLException
  {
    log("查询银行有，市场没有的流水");
    Vector<CompareResult> result = new Vector();
    PreparedStatement state = null;
    String sql = null;
    ResultSet rs = null;
    CompareResult value = null;
    try
    {
      sql = 
      





        "select fbf.firmid firmID,fbf.account account,fbb.firmid contact,fbb.funid funID,fbb.comparedate tradeDate,fbb.money money,fbb.type type,fbb.bankid bankID from f_b_bankcompareinfo fbb,f_b_firmidandaccount fbf where fbb.firmid=fbf.contact(+)  and fbb.bankid=fbf.bankid(+) and fbb.status=0  and not exists (select funid from f_b_capitalinfo fbc where fbb.funid=fbc.funid and fbb.firmid=fbc.contact  and fbb.bankid=fbc.bankid and fbb.type=fbc.type and fbb.money=fbc.money  and trunc(fbb.comparedate)=trunc(fbc.createtime) and fbc.status=0)  and fbb.bankid='" + bankID + "' and trunc(fbb.comparedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      log("查询银行有，市场没有的流水sql:" + sql);
      if ((pageinfo == null) || (pageinfo.length <= 0))
      {
        state = conn.prepareStatement(sql);
        rs = state.executeQuery();
      }
      else
      {
        rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
      }
      while (rs.next())
      {
        value = new CompareResult();
        value.account = rs.getString("account");
        value.bankID = rs.getString("bankID");
        value.contact = rs.getString("contact");
        value.errorType = 2;
        value.firmID = rs.getString("firmID");
        value.funID = rs.getString("funID");
        value.money = rs.getDouble("money");
        value.tradeDate = rs.getDate("tradeDate");
        value.type = rs.getInt("type");
        result.add(value);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    return result;
  }
  
  public Vector<CompareSumMoney> sumCompareMoney(String bankID, java.util.Date date)
    throws SQLException, ClassNotFoundException
  {
    log("查询市场和银行转账成功出入金信息    sumCompareMoney  ");
    Vector<CompareSumMoney> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      CompareSumMoney bankmoney = new CompareSumMoney(bankID, date, 1);
      CompareSumMoney marketmoney = new CompareSumMoney(bankID, date, 2);
      result.add(bankmoney);
      result.add(marketmoney);
      conn = getConnection();
      String bank = "select count(*) rowsize,nvl(sum(info.money),0) money,info.type type,info.bankid bankID,bank.bankname bankname,1 mb from f_b_bankcompareinfo info,f_b_banks bank where info.bankid=bank.bankid(+) and info.status=0 and info.bankid='" + bankID + "' and trunc(info.comparedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') group by info.bankID,info.type,bank.bankname ";
      String market = "select count(*) rowsize,nvl(sum(info.money), 0) money,info.type type, info.bankid bankID, bank.bankname bankname, 2 mb from f_b_capitalinfo info, f_b_banks bank where info.bankid = bank.bankid(+) and info.status=0 and info.type in (0,1) and info.bankid = '" + bankID + "' and trunc(info.createtime) = to_date('" + Tool.fmtDate(date) + "', 'yyyy-MM-dd') group by info.bankID, info.type, bank.bankname ";
      sql = bank + " union all " + market;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        if (1 == rs.getInt("mb"))
        {
          bankmoney.bankName = rs.getString("bankname");
          if (rs.getInt("type") == 0)
          {
            bankmoney.inMoneyCount = rs.getInt("rowsize");
            bankmoney.inMoney = rs.getDouble("money");
          }
          else
          {
            bankmoney.outMoneyCount = rs.getInt("rowsize");
            bankmoney.outMoney = rs.getDouble("money");
          }
        }
        else
        {
          marketmoney.bankName = rs.getString("bankname");
          if (rs.getInt("type") == 0)
          {
            marketmoney.inMoneyCount = rs.getInt("rowsize");
            marketmoney.inMoney = rs.getDouble("money");
          }
          else
          {
            marketmoney.outMoneyCount = rs.getInt("rowsize");
            marketmoney.outMoney = rs.getDouble("money");
          }
        }
      }
      if (bankmoney.bankName == null) {
        bankmoney.bankName = marketmoney.bankName;
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
}
