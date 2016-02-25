package gnnt.MEBS.vendue.manage.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBBean
{
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  Statement stmt = null;
  
  public DBBean(String paramString)
  {
    try
    {
      InitialContext localInitialContext = new InitialContext();
      Context localContext = (Context)localInitialContext.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localContext.lookup(paramString);
      this.conn = localDataSource.getConnection();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public synchronized int executeUpdate(String paramString)
  {
    int i = -1;
    try
    {
      this.stmt = this.conn.createStatement();
      i = this.stmt.executeUpdate(paramString);
      return i;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      if (this.stmt != null) {
        try
        {
          this.stmt.close();
          this.conn.close();
        }
        catch (Exception localException2) {}
      }
    }
    return -1;
  }
  
  public ResultSet executeQuery(String paramString)
  {
    try
    {
      this.stmt = this.conn.createStatement();
      this.rs = this.stmt.executeQuery(paramString);
      return this.rs;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      if (this.stmt != null) {
        try
        {
          this.stmt.close();
          this.conn.close();
        }
        catch (Exception localException2) {}
      }
    }
    return null;
  }
  
  public void closeStmt()
  {
    try
    {
      if (this.stmt != null) {
        this.stmt.close();
      }
      if (this.rs != null) {
        this.rs.close();
      }
    }
    catch (Exception localException) {}
  }
  
  public void close()
  {
    try
    {
      if (this.stmt != null) {
        this.stmt.close();
      }
      if (this.rs != null) {
        this.rs.close();
      }
      if (this.conn != null) {
        this.conn.close();
      }
    }
    catch (Exception localException) {}
  }
}
