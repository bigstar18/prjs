package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.server.beans.dtobeans.Broadcast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BroadcastDao
  extends BaseDao
{
  private Timestamp newestUpdateTime = null;
  private Timestamp currentDbTime = null;
  private List newestBroadcastTitleList = null;
  
  public List getNewestBroadcastTitleList()
  {
    return this.newestBroadcastTitleList;
  }
  
  public Timestamp getNewestUpdateTime()
  {
    return this.newestUpdateTime;
  }
  
  public Timestamp getCurrentDbTime()
  {
    return this.currentDbTime;
  }
  
  public List fetchAllBroadcastId()
    throws Exception
  {
    ResultSet localResultSet = null;
    Connection localConnection = getConnection();
    Statement localStatement = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localStatement = localConnection.createStatement();
      localResultSet = localStatement.executeQuery("select id from v_broadcast where type = 1");
      while (localResultSet.next())
      {
        Long localLong = new Long(localResultSet.getLong("id"));
        localArrayList.add(localLong);
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      closeResultSet(localResultSet);
      closeStatement(localStatement);
      closeConnection(localConnection);
    }
    return localArrayList;
  }
  
  public void fetchBroadcast(Timestamp paramTimestamp)
    throws Exception
  {
    ResultSet localResultSet = null;
    Connection localConnection = getConnection();
    Statement localStatement = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      this.newestUpdateTime = null;
      this.currentDbTime = null;
      localStatement = localConnection.createStatement();
      localResultSet = localStatement.executeQuery("select sysdate from dual");
      if (localResultSet.next()) {
        this.currentDbTime = localResultSet.getTimestamp("sysdate");
      }
      localResultSet.close();
      localStatement.close();
      String str = null;
      localPreparedStatement = null;
      if (paramTimestamp == null)
      {
        str = "select ID, title, author, type, sendtime, endtime, createtime, updatetime from v_broadcast where type = 1 order by updatetime desc";
        localPreparedStatement = localConnection.prepareStatement(str);
      }
      else
      {
        str = "select ID, title, author, type, sendtime, endtime, createtime, updatetime from v_broadcast where type = 1 and updatetime > ? order by updatetime desc";
        localPreparedStatement = localConnection.prepareStatement(str);
        localPreparedStatement.setTimestamp(1, paramTimestamp);
      }
      localResultSet = localPreparedStatement.executeQuery();
      this.newestBroadcastTitleList = new ArrayList();
      while (localResultSet.next())
      {
        if ((this.newestUpdateTime == null) || (localResultSet.getTimestamp("updatetime").compareTo(this.newestUpdateTime) > 0)) {
          this.newestUpdateTime = localResultSet.getTimestamp("updatetime");
        }
        Broadcast localBroadcast = new Broadcast();
        localBroadcast.setAuthor(localResultSet.getString("author") == null ? "" : localResultSet.getString("author"));
        localBroadcast.setCreatetime(localResultSet.getTimestamp("createtime"));
        localBroadcast.setId(new Long(localResultSet.getLong("id")));
        localBroadcast.setTitle(localResultSet.getString("title"));
        localBroadcast.setType(localResultSet.getString("type") == null ? "" : localResultSet.getString("type"));
        localBroadcast.setUpdatetime(localResultSet.getTimestamp("updatetime"));
        localBroadcast.setSendtime(Timestamp.valueOf("1901-01-01 00:00:00.0"));
        localBroadcast.setEndtime(Timestamp.valueOf("2999-12-01 00:00:00.0"));
        this.newestBroadcastTitleList.add(localBroadcast);
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      closeResultSet(localResultSet);
      closeStatement(localStatement);
      closeStatement(localPreparedStatement);
      closeConnection(localConnection);
    }
  }
}
