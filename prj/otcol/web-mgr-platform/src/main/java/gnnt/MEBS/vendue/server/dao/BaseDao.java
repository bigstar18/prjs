package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.util.Configuration;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BaseDao
{
  private static DataSource ds;
  public static boolean isRunWithServer = false;
  private static String jdbcusername;
  private static String jdbcpassword;
  private static String jdbcurl;
  
  private static void setDataSourceFromContext()
    throws Exception
  {
    if (!isRunWithServer) {
      return;
    }
    Properties localProperties = new Configuration().getSection("MEBS.QuotationDataSource");
    String str = "java:/comp/env/mgr";
    System.out.println("==========================================================" + localProperties.getProperty("JNDIName"));
    System.out.println();
    System.out.println("=========================");
    System.out.println("使用服务器连接池 JndiName: " + str);
    try
    {
      InitialContext localInitialContext = new InitialContext();
      if (localInitialContext == null) {
        throw new Exception("No Context");
      }
      ds = (DataSource)localInitialContext.lookup(str);
      if (ds == null) {
        System.out.println("没有得到合适的数据源, 请检查 服务器连接池配置(也可能是数据库服务器问题) JndiName: " + str);
      }
    }
    catch (Exception localException)
    {
      if (ds == null) {
        System.out.println("没有得到合适的数据源, 请检查 服务器连接池配置(也可能是数据库服务器问题) JndiName: " + str);
      }
      throw localException;
    }
  }
  
  protected Connection getConnection()
    throws Exception
  {
    Connection localConnection = null;
    try
    {
      if ((ds == null) && (isRunWithServer)) {
        setDataSourceFromContext();
      }
      if (isRunWithServer) {
        try
        {
          localConnection = ds.getConnection();
        }
        catch (Exception localException1)
        {
          localConnection = getJDBCConnection();
        }
      } else {
        localConnection = getJDBCConnection();
      }
      return localConnection;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      System.out.println(localException2.toString());
      throw new Exception("在获取数据连接对象时出现异常!");
    }
  }
  
  private Connection getJDBCConnection()
    throws Exception
  {
    Connection localConnection = null;
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      localConnection = DriverManager.getConnection(jdbcurl, jdbcusername, jdbcpassword);
      System.out.println("当前, 使用JDBC即时连接!");
    }
    catch (Exception localException)
    {
      System.out.println("JDBC 连接配置有误!");
      throw localException;
    }
    return localConnection;
  }
  
  protected void closeStatement(Statement paramStatement)
  {
    try
    {
      if (paramStatement != null) {
        paramStatement.close();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected void closeConnection(Connection paramConnection)
  {
    try
    {
      if (paramConnection != null) {
        paramConnection.close();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected void closeResultSet(ResultSet paramResultSet)
  {
    try
    {
      if (paramResultSet != null) {
        paramResultSet.close();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  static
  {
    try
    {
      setDataSourceFromContext();
      Properties localProperties = new Configuration().getSection("MEBS.DBConnection");
      jdbcurl = "" + localProperties.getProperty("Url");
      jdbcusername = "" + localProperties.getProperty("Username");
      jdbcpassword = "" + localProperties.getProperty("Password");
      System.out.println("==========**********************************************========" + jdbcurl);
    }
    catch (Exception localException)
    {
      System.out.println("==========**********************************************========" + jdbcurl);
    }
  }
}
