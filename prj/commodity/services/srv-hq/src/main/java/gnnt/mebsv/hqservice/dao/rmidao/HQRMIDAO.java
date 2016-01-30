package gnnt.mebsv.hqservice.dao.rmidao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public abstract class HQRMIDAO
{
  public Properties params = null;
  public Connection conn = null;

  public Connection getConn()
    throws SQLException
  {
    if (this.conn == null)
      createConn();
    return this.conn;
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
      String str1 = this.params.getProperty("RMIDBUrl");
      String str2 = this.params.getProperty("DBPassword");
      String str3 = this.params.getProperty("DBUser");
      if ((str3 == null) || (str3.equals("")))
        this.conn = DriverManager.getConnection(str1);
      else
        this.conn = DriverManager.getConnection(str1, str3, str2);
    }
  }

  public void closeStatement(ResultSet paramResultSet, Statement paramStatement, Connection paramConnection)
  {
    try
    {
      if (paramResultSet != null)
        paramResultSet.close();
    }
    catch (SQLException localSQLException1)
    {
      localSQLException1.printStackTrace();
    }
    try
    {
      if (paramStatement != null)
        paramStatement.close();
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
    }
  }

  public abstract Map getHQRMI(String paramString);
}