package gnnt.MEBS.vendue.manage.log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class OptLog
{
  Connection conn = null;
  
  public OptLog(String paramString)
  {
    try
    {
      InitialContext localInitialContext = new InitialContext();
      Context localContext = (Context)localInitialContext.lookup("java:/comp/env");
      DataSource localDataSource = (DataSource)localContext.lookup(paramString);
      this.conn = localDataSource.getConnection();
    }
    catch (Exception localException1)
    {
      localException1 = localException1;
      try
      {
        this.conn.close();
      }
      catch (Exception localException2) {}
      localException1.printStackTrace();
    }
    finally {}
  }
  
  public synchronized int log(int paramInt, String paramString1, String paramString2)
  {
    int i = -1;
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = null;
    ResultSet localResultSet = null;
    Statement localStatement = null;
    long l = 0L;
    String str2 = null;
    try
    {
      localStringBuffer.append("select SP_V_OPERATIONLOG.nextval as seqID from dual");
      localStatement = this.conn.createStatement();
      localResultSet = localStatement.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        str1 = localResultSet.getString("seqID");
      }
      l = System.currentTimeMillis();
      Date localDate = new Date(l);
      Time localTime = new Time(l);
      str2 = localDate.toString() + " " + localTime.toString();
      localStringBuffer = new StringBuffer("insert into v_operationlog(id,logindex,remark,userid,operationTime)");
      localStringBuffer.append(" values(" + str1 + "," + paramInt + ",'" + paramString2 + "','" + paramString1 + "',to_date('" + str2 + "','yyyy-mm-dd hh24-mi-ss'))");
      i = localStatement.executeUpdate(localStringBuffer.toString());
      localStatement.close();
      this.conn.close();
      int k = i;
      return k;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      int j = -1;
      return j;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localStatement != null) {
          localStatement.close();
        }
        if (this.conn != null) {
          this.conn.close();
        }
      }
      catch (Exception localException4) {}
    }
  }
}
