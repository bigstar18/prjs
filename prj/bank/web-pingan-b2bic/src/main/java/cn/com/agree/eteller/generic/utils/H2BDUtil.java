package cn.com.agree.eteller.generic.utils;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2BDUtil
{
  private static String url = "jdbc:h2:file:./WebRoot/WEB-INF/db/eteller_pingan_db";
  private static String username = "pingan";
  private static String password = "pa888888";
  private static JdbcConnectionPool cp = JdbcConnectionPool.create(url, username, password);
  
  public static Connection getConnection()
    throws SQLException
  {
    if (cp != null) {
      return cp.getConnection();
    }
    return null;
  }
  
  public static void closeDB()
  {
    if (cp != null)
    {
      cp.dispose();
      cp = null;
    }
  }
}
