package gnnt.mebsv.hqtrans.dao;

import gnnt.mebsv.hqtrans.model.DayDataFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class HQTransDAO
{
  public Properties params = null;
  public Connection conn = null;
  
  public Connection getConn()
    throws SQLException
  {
    if (this.conn == null) {
      createConn();
    }
    return this.conn;
  }
  
  public void closeConn(Connection paramConnection)
  {
    try
    {
      if (paramConnection != null) {
        paramConnection.close();
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }
  
  private synchronized void createConn()
    throws SQLException
  {
    if (this.conn == null)
    {
      try
      {
        Class.forName(this.params.getProperty("DBDriver"));
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        localClassNotFoundException.printStackTrace();
        throw new SQLException(localClassNotFoundException.toString());
      }
      String str1 = this.params.getProperty("DBUrl");
      String str2 = this.params.getProperty("DBUser");
      String str3 = this.params.getProperty("DBPassword");
      if ((str2 == null) || (str2.equals(""))) {
        this.conn = DriverManager.getConnection(str1);
      } else {
        this.conn = DriverManager.getConnection(str1, str2, str3);
      }
    }
  }
  
  public void closeStatement(ResultSet paramResultSet, Statement paramStatement)
  {
    try
    {
      if (paramResultSet != null) {
        paramResultSet.close();
      }
    }
    catch (SQLException localSQLException1)
    {
      localSQLException1.printStackTrace();
    }
    try
    {
      if (paramStatement != null) {
        paramStatement.close();
      }
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
    }
  }
  
  public abstract HashMap getAllProductCode(String paramString)
    throws SQLException;
  
  public abstract DayDataFile[] getDayData(String paramString1, String paramString2, int paramInt)
    throws SQLException;
  
  public abstract DayDataFile[] getMinData(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws SQLException;
  
  public abstract Map<String, Integer> getMarketStatus()
    throws SQLException;
}
