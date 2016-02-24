package gnnt.trade.bank.dao;

import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.TradeDetailAccount;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CMBBankDAOImpl
  extends CMBBankDAO
{
  public CMBBankDAOImpl()
    throws Exception
  {}
  
  public Vector<FirmTradeStatus> getFirmTradeStatusList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得客户协议状态信息列表   getFirmTradeStatusList  " + new Date());
    System.out.println("===>>>取得客户协议状态信息列表   getFirmTradeStatusList  " + new Date());
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = null;
    FirmTradeStatus val = null;
    Vector<FirmTradeStatus> veVal = new Vector();
    try
    {
      conn = getConnection();
      System.out.println("链接数据库成功");
      sql = "select * from F_B_FIRMTRADESTATUS " + filter;
      stmt = conn.prepareStatement(sql);
      rs = stmt.executeQuery();
      while (rs.next())
      {
        val = new FirmTradeStatus();
        val.setBankId(rs.getString(1));
        val.setDealId(rs.getString(2));
        val.setCoBrn(rs.getString(3));
        val.setTxDate(rs.getString(4));
        val.setBankAcc(rs.getString(5));
        val.setFundAcc(rs.getString(6));
        val.setCustName(rs.getString(7));
        val.setCurCode(rs.getString(8));
        val.setStatus(rs.getString(9));
        veVal.add(val);
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
    }
    return veVal;
  }
  
  public Vector<TradeDetailAccount> getTradeDetailAccountList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得账户类交易对账信息列表   getTradeDetailAccountList  " + new Date());
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = null;
    TradeDetailAccount val = null;
    Vector<TradeDetailAccount> veVal = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from F_B_TRADEDETAILACC " + filter;
      stmt = conn.prepareStatement(sql);
      rs = stmt.executeQuery();
      while (rs.next())
      {
        val = new TradeDetailAccount();
        val.setBatchNo(rs.getString(1));
        val.setBankId(rs.getString(2));
        val.setDealId(rs.getString(3));
        val.setCoBrn(rs.getString(4));
        val.setTxDate(rs.getString(5));
        val.setTxTime(rs.getString(6));
        val.setBkSerial(rs.getString(7));
        val.setCoSerial(rs.getString(8));
        val.setBankAcc(rs.getString(9));
        val.setFundAcc(rs.getString(10));
        val.setCustName(rs.getString(11));
        val.setTradeSource(rs.getString(12));
        val.setBusinessCode(rs.getString(13));
        val.setCurCode(rs.getString(14));
        veVal.add(val);
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
    }
    return veVal;
  }
  
  public long addTradeDetailAccount(TradeDetailAccount val)
    throws SQLException
  {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rst = null;
    String sql = null;
    long id = 0L;
    try
    {
      conn = getConnection();
      sql = "insert into F_B_TRADEDETAILACC(BATCHNO,BANKID,DEALID,COBRN,TXDATE,TXTIME,BKSERIAL,COSERIAL,BANKACC,FUNDACC,CUSTNAME,TXOPT,TXCODE,CURCODE,COMPAREDATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, val.BatchNo);
      stmt.setString(2, val.BankId);
      stmt.setString(3, val.DealId);
      stmt.setString(4, val.CoBrn);
      stmt.setString(5, val.TxDate);
      stmt.setString(6, val.TxTime);
      stmt.setString(7, val.BkSerial);
      stmt.setString(8, val.CoSerial);
      stmt.setString(9, val.BankAcc);
      stmt.setString(10, val.FundAcc);
      stmt.setString(11, val.CustName);
      stmt.setString(12, val.TradeSource);
      stmt.setString(13, val.BusinessCode);
      stmt.setString(14, val.CurCode);
      stmt.setDate(15, val.compareDate);
      stmt.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      id = -1L;
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      id = -1L;
    }
    finally
    {
      closeStatement(rst, stmt, conn);
    }
    return id;
  }
  
  public long addFirmTradeStatus(FirmTradeStatus val)
    throws SQLException
  {
    log("===>>>添加客户协议状态   addFirmTradeStatus" + new Date());
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rst = null;
    String sql = null;
    long id = 0L;
    try
    {
      conn = getConnection();
      sql = "insert into F_B_FIRMTRADESTATUS(BANKID,DEALID,COBRN,TXDATE,BANKACC,FUNDACC,CUSTNAME,CURCODE,STATUS,COMPAREDATE) values(?,?,?,?,?,?,?,?,?,?)";
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, val.BankId);
      stmt.setString(2, val.DealId);
      stmt.setString(3, val.CoBrn);
      stmt.setString(4, val.TxDate);
      stmt.setString(5, val.BankAcc);
      stmt.setString(6, val.FundAcc);
      stmt.setString(7, val.CustName);
      stmt.setString(8, val.CurCode);
      stmt.setString(9, val.Status);
      stmt.setDate(10, val.compareDate);
      stmt.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      id = -1L;
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      id = -1L;
    }
    finally
    {
      closeStatement(rst, stmt, conn);
    }
    return id;
  }
}
