package gnnt.bank.platform.dao;

import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.BankSetBalance;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class BankFeeAndOffsetDao
{
  private String DBUrl;
  private String DBUser;
  private String DBPwd;
  private String JNDIName;
  private String DBConnType;
  protected DataSource dataSource = null;
  
  protected BankFeeAndOffsetDao()
    throws Exception
  {
    try
    {
      this.DBUrl = Tool.getConfig("DBUrl");
      this.DBUser = Tool.getConfig("DBUser");
      this.DBPwd = Tool.getConfig("DBPassword");
      this.JNDIName = Tool.getConfig("JNDIName");
      this.DBConnType = Tool.getConfig("DBConnType");
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
    System.out.print("------------------关闭成功--------------");
  }
  
  public abstract Set<List<Object>> userQuery();
  
  public abstract boolean insertTransferRights(String paramString1, String paramString2, String paramString3, double paramDouble);
  
  public abstract Set<List<Object>> TransferRightsQuery(String paramString1, String paramString2);
  
  public abstract Set<List<Object>> MarketFeeOnBankQuery(String paramString1, String paramString2);
  
  public abstract Vector<BankSetBalance> getSetBalance(String paramString)
    throws SQLException, ClassNotFoundException;
}
