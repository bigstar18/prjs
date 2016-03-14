package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.BroadcastDAO;
import gnnt.MEBS.timebargain.manage.model.Broadcast;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

public class BroadcastDAOJdbc
  extends BaseDAOJdbc
  implements BroadcastDAO
{
  private Log log = LogFactory.getLog(BroadcastDAOJdbc.class);
  
  public Broadcast getBroadcastById(Long paramLong)
  {
    Assert.notNull(paramLong);
    String str = "select * from T_BROADCAST where id=?";
    Object[] arrayOfObject = { paramLong };
    this.log.debug("sql: " + str);
    this.log.debug("id:" + arrayOfObject[0]);
    Broadcast localBroadcast = null;
    try
    {
      localBroadcast = (Broadcast)getJdbcTemplate().queryForObject(str, arrayOfObject, new BroadcastRowMapper());
      return localBroadcast;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("T_BROADCAST表中消息ID[" + paramLong + "]的记录不存在！");
    }
  }
  
  public Broadcast getBroadcastWaitById(Long paramLong)
  {
    String str = "select * from T_NOTSENDBROADCAST where id = " + paramLong;
    this.log.debug("sql: " + str);
    Broadcast localBroadcast = null;
    try
    {
      localBroadcast = (Broadcast)getJdbcTemplate().queryForObject(str, new BroadcastRowMapper());
      return localBroadcast;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("T_NOTSENDBROADCAST[" + paramLong + "]的记录不存在！");
    }
  }
  
  public List getBroadcasts(Broadcast paramBroadcast)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,decode(status,0,'未查看',1,'已查看','') status from T_BROADCAST a");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getBroadcastWait()
  {
    String str = "select a.* from T_NOTSENDBROADCAST a ";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getBroadcastAready()
  {
    String str = "select * from T_BROADCAST where status = 0";
    return getJdbcTemplate().queryForList(str);
  }
  
  public List getHisBroadcast(String paramString)
  {
    String str = "";
    if ((paramString != null) && (!"".equals(paramString))) {
      str = "select hb.*,to_char(hb.clearDate,'yyyy-MM-dd') clearDate from T_H_BROADCAST hb " + paramString;
    } else {
      str = "select hb.*,to_char(hb.clearDate,'yyyy-MM-dd') clearDate from T_H_BROADCAST hb";
    }
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public void insertBroadcast(Broadcast paramBroadcast)
  {
    String str = "insert into T_BROADCAST(id,title,content,status,FirmID,createTime,author,sendTime) values(SEQ_T_BROADCAST.nextval,?,?,0,?,sysdate,?,sysdate)";
    Object[] arrayOfObject = { paramBroadcast.getTitle(), paramBroadcast.getContent(), paramBroadcast.getCustomerID(), paramBroadcast.getAuthor() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("执行存储失败！");
    }
  }
  
  public void insertHisBroadcast(Broadcast paramBroadcast)
  {
    String str = "insert into T_NOTSENDBROADCAST (id,title,content,status,FirmID,createTime,author,sendTime) values (SEQ_T_NOTSENDBROADCAST.nextval,?,?,?,?,sysdate,?,to_date(to_char(sysdate,'yyyy-MM-dd')||?,'yyyy-MM-dd hh24:mi:ss'))";
    Object[] arrayOfObject = { paramBroadcast.getTitle(), paramBroadcast.getContent(), paramBroadcast.getStatus(), paramBroadcast.getCustomerID(), paramBroadcast.getAuthor(), paramBroadcast.getSendTime() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void updateBroadcast(Broadcast paramBroadcast)
  {
    String str1 = "insert into T_BROADCAST(id,title,content,status,FirmID,createTime,author,sendTime) values(SEQ_T_BROADCAST.nextval,?,?,0,?,sysdate,?,sysdate)";
    Object[] arrayOfObject = { paramBroadcast.getTitle(), paramBroadcast.getContent(), paramBroadcast.getCustomerID(), paramBroadcast.getAuthor() };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str1, arrayOfObject);
    }
    catch (Exception localException1)
    {
      throw new RuntimeException("提前发送失败！");
    }
    String str2 = "delete from T_NOTSENDBROADCAST where id = " + paramBroadcast.getId();
    try
    {
      getJdbcTemplate().update(str2);
    }
    catch (Exception localException2)
    {
      throw new RuntimeException("添加成功，但删除未发送表失败！");
    }
  }
  
  public void updateBroadcastWait(Broadcast paramBroadcast)
  {
    String str = "update T_NOTSENDBROADCAST set title=?,content=?,author=?,sendTime=to_date(to_char(sysdate,'yyyy-MM-dd')||?,'yyyy-MM-dd hh24:mi:ss'),status=? where id=?";
    Object[] arrayOfObject = { paramBroadcast.getTitle(), paramBroadcast.getContent(), paramBroadcast.getAuthor(), paramBroadcast.getSendTime(), paramBroadcast.getStatus(), paramBroadcast.getId() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("发送失败！");
    }
  }
  
  public void deleteBroadcastById(Long paramLong)
  {
    Assert.notNull(paramLong);
    String str = "delete from T_BROADCAST where id=?";
    Object[] arrayOfObject = { paramLong };
    this.log.debug("sql: " + str);
    this.logger.debug("id: " + arrayOfObject[0]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteBroadcastWaitById(Long paramLong)
  {
    String str = "delete from T_NOTSENDBROADCAST where id = " + paramLong;
    this.log.debug("sql: " + str);
    try
    {
      getJdbcTemplate().update(str);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("删除失败！");
    }
  }
  
  public void deleteBroadcastAreadyById(Long paramLong)
  {
    String str = "delete from T_BROADCAST where id = " + paramLong;
    this.log.debug("sql: " + str);
    try
    {
      getJdbcTemplate().update(str);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("删除失败！");
    }
  }
  
  private static void printMap(Map paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext()) {}
  }
  
  public void hisBroadcast()
  {
    String str1 = "select n.* from T_NOTSENDBROADCAST n where n.sendTime <= sysdate and n.status = 0";
    List localList = getJdbcTemplate().queryForList(str1);
    if ((localList != null) && (localList.size() > 0))
    {
      String str2 = "insert into T_BROADCAST(id,title,content,status,FirmID,createTime,author,sendTime) values(SEQ_T_BROADCAST.nextval,?,?,?,?,?,?,?)";
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        Short localShort1 = null;
        String str3 = "";
        String str4 = "";
        Short localShort2 = null;
        String str5 = "";
        Date localDate1 = null;
        String str6 = "";
        Date localDate2 = null;
        if (localMap.get("title") != null) {
          str3 = localMap.get("title").toString();
        }
        if (localMap.get("content") != null) {
          str4 = localMap.get("content").toString();
        }
        if (localMap.get("status") != null) {
          localShort2 = Short.valueOf(Short.parseShort(localMap.get("status").toString()));
        }
        if (localMap.get("FirmID") != null) {
          str5 = localMap.get("FirmID").toString();
        }
        if (localMap.get("createTime") != null) {
          localDate1 = (Date)localMap.get("createTime");
        }
        if (localMap.get("author") != null) {
          str6 = localMap.get("author").toString();
        }
        if (localMap.get("sendTime") != null) {
          localDate2 = (Date)localMap.get("sendTime");
        }
        Object[] arrayOfObject = { str3, str4, localShort2, str5, localDate1, str6, localDate2 };
        this.log.debug("sql2: " + str2);
        for (int j = 0; j < arrayOfObject.length; j++) {
          this.log.debug("params[" + j + "]: " + arrayOfObject[j]);
        }
        try
        {
          getJdbcTemplate().update(str2, arrayOfObject);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          throw new RuntimeException("定时发送失败！");
        }
        if (localMap.get("id") != null) {
          localShort1 = Short.valueOf(Short.parseShort(localMap.get("id").toString()));
        }
        String str7 = "delete from T_NOTSENDBROADCAST where id = " + localShort1;
        this.log.debug("sql3: " + str7);
        getJdbcTemplate().update(str7);
      }
    }
  }
  
  public Broadcast getHisBroadcastByKye(Broadcast paramBroadcast)
  {
    String str = "select * from t_h_broadcast where clearDate = to_date('" + paramBroadcast.getSendTime() + "','yyyy-MM-dd') and id = ?";
    Object[] arrayOfObject = { paramBroadcast.getId() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return (Broadcast)getJdbcTemplate().queryForObject(str, arrayOfObject, new HisBroadcastRowMapper());
  }
  
  class HisBroadcastRowMapper
    implements RowMapper
  {
    HisBroadcastRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToHisBroadcast(paramResultSet);
    }
    
    private Broadcast rsToHisBroadcast(ResultSet paramResultSet)
      throws SQLException
    {
      Broadcast localBroadcast = new Broadcast();
      localBroadcast.setId(new Long(paramResultSet.getLong("id")));
      localBroadcast.setTitle(paramResultSet.getString("title"));
      localBroadcast.setContent(paramResultSet.getString("content"));
      localBroadcast.setCreateTime(paramResultSet.getTimestamp("clearDate"));
      return localBroadcast;
    }
  }
  
  private class BroadcastAddStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "BroadcastAdd";
    
    public BroadcastAddStoredProcedure(DataSource paramDataSource)
    {
      super(paramDataSource,"BroadcastAdd");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_title", 12));
      declareParameter(new SqlParameter("p_content", 12));
      declareParameter(new SqlParameter("p_status", 5));
      declareParameter(new SqlParameter("p_customerID", 12));
      declareParameter(new SqlParameter("p_author", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  class BroadcastRowMapper
    implements RowMapper
  {
    BroadcastRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToBroadcast(paramResultSet);
    }
    
    private Broadcast rsToBroadcast(ResultSet paramResultSet)
      throws SQLException
    {
      Broadcast localBroadcast = new Broadcast();
      localBroadcast.setId(new Long(paramResultSet.getLong("id")));
      localBroadcast.setTitle(paramResultSet.getString("title"));
      localBroadcast.setContent(paramResultSet.getString("content"));
      localBroadcast.setStatus(new Short(paramResultSet.getShort("status")));
      localBroadcast.setCustomerID(paramResultSet.getString("firmID"));
      localBroadcast.setCreateTime(paramResultSet.getTimestamp("createTime"));
      localBroadcast.setSendTime(paramResultSet.getString("SendTime"));
      localBroadcast.setAuthor(paramResultSet.getString("author"));
      return localBroadcast;
    }
  }
}
