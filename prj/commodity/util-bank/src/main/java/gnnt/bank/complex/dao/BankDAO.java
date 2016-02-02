package gnnt.bank.complex.dao;

import gnnt.bank.complex.util.Util;
import gnnt.bank.complex.vo.FirmFundsBankValue;
import gnnt.bank.complex.vo.FirmFundsValue;
import gnnt.bank.complex.vo.FirmPlatformMsg;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.RZQS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class BankDAO
{
  private String DBUrl;
  private String DBUser;
  private String DBPwd;
  private String JNDIName;
  private String DBConnType;
  protected DataSource dataSource = null;
  
  protected BankDAO()
    throws Exception
  {
    try
    {
      this.DBUrl = Util.getConfig("DBUrl");
      this.DBUser = Util.getConfig("DBUser");
      this.DBPwd = Util.getConfig("DBPassword");
      this.JNDIName = Util.getConfig("JNDIName");
      this.DBConnType = Util.getConfig("DBConnType");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
    if (this.DBConnType.equalsIgnoreCase("JNDIName"))
    {
      InitialContext ic = null;
      try
      {
        ic = new InitialContext();
        this.dataSource = ((DataSource)ic.lookup(this.JNDIName));
      }
      catch (NamingException e)
      {
        e.printStackTrace();
        throw e;
      }
    }
  }
  
  public DataSource getDataSource()
  {
    return this.dataSource;
  }
  
  public Connection getConnection()
    throws SQLException, ClassNotFoundException
  {
    if (this.DBConnType.equalsIgnoreCase("JNDIName")) {
      return this.dataSource.getConnection();
    }
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      throw e;
    }
    return DriverManager.getConnection(this.DBUrl, this.DBUser, this.DBPwd);
  }
  
  public void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (state != null) {
        state.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public abstract long getActionID();
  
  public abstract int modCapitalInfoNote(long paramLong, String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract long addCapitalInfo(CapitalValue paramCapitalValue, Connection paramConnection)
    throws SQLException;
  
  public abstract Date getlastDate(Date paramDate, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<CapitalValue> getCapitalList(Map<String, Object> paramMap, Connection paramConnection)
    throws SQLException;
  
  public abstract Vector<FirmPlatformMsg> getPlatformMsg(Map<String, Object> paramMap)
    throws SQLException;
  
  public abstract int addPlatformMsg(FirmPlatformMsg paramFirmPlatformMsg, Connection paramConnection)
    throws SQLException;
  
  public abstract int delPlatformMsg(FirmPlatformMsg paramFirmPlatformMsg, Connection paramConnection)
    throws SQLException;
  
  public abstract int delPlatformMsg(FirmPlatformMsg paramFirmPlatformMsg)
    throws SQLException;
  
  public abstract Vector<CapitalValue> getCapitalList(Map<String, Object> paramMap)
    throws SQLException;
  
  public abstract int modCapitalInfoStatus(long paramLong, String paramString, int paramInt, Timestamp paramTimestamp, Connection paramConnection)
    throws SQLException;
  
  public abstract FirmFundsValue getFirmFunds(String paramString)
    throws SQLException;
  
  public abstract Vector<FirmFundsBankValue> getFirmFundsBank(String paramString)
    throws SQLException;
  
  public abstract int addPlatformMsg(FirmPlatformMsg paramFirmPlatformMsg)
    throws SQLException;
  
  public abstract Vector<RZQS> getRZData(Date paramDate)
    throws SQLException;
  
  public abstract boolean checkRZData(Date paramDate)
    throws SQLException;
  
  public abstract boolean isTradeDate(Date paramDate)
    throws SQLException;
}
