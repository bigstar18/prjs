package gnnt.trade.bank.dao.jsb;

import gnnt.trade.bank.vo.MarketAcount;
import gnnt.trade.bank.vo.MarketCashBalance;
import gnnt.trade.bank.vo.TransferInfo;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSBBankDAOImpl
  extends JSBBankDAO
{
  private final transient Log logger = LogFactory.getLog("Processorlog");
  
  public JSBBankDAOImpl()
    throws Exception
  {}
  
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
  
  public Vector<MarketCashBalance> getMarketCashBalance(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>头寸、收益结算流水查询   getMarketCashBalance  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    MarketCashBalance value = null;
    Vector<MarketCashBalance> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_marketcashbalance t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new MarketCashBalance();
        value.bankID = rs.getString("bankid");
        value.bankName = rs.getString("bankName");
        value.lastCash = rs.getDouble("lastCash");
        value.cash = rs.getDouble("cash");
        value.cashAMT = rs.getDouble("cashAMT");
        value.cashIO = rs.getDouble("cashIO");
        value.lastFee = rs.getDouble("lastFee");
        value.fee = rs.getDouble("fee");
        value.feeAMT = rs.getDouble("feeAMT");
        value.feeIO = rs.getDouble("feeIO");
        value.tradeDate = rs.getDate("tradeDate");
        
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
}
