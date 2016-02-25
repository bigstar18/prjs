package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Broadcast;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class BroadcastDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BroadcastDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_broadcast ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<Broadcast> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from z_broadcast ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Broadcast()));
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_BROADCAST.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public Broadcast getObject(long paramLong)
  {
    Broadcast localBroadcast = null;
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    String str = "select * from z_broadcast where broadcastid=?";
    List localList = getJdbcTemplate().query(str, arrayOfObject, new CommonRowMapper(new Broadcast()));
    this.logger.debug("getEnterWareListById.size()" + localList.size());
    if (localList.size() > 0) {
      localBroadcast = (Broadcast)localList.get(0);
    }
    return localBroadcast;
  }
  
  public int update(Broadcast paramBroadcast)
  {
    paramBroadcast.setBroadcastCreateTime(new Date());
    try
    {
      String str = "update Z_broadcast set broadcastTitle=?,broadcastSender=?,broadcastContent=?,broadcastSendTime=?,broadcastCreateTime=?,broadcastFirmId=?  where broadcastId=? ";
      Object[] arrayOfObject = { paramBroadcast.getBroadcastTitle(), paramBroadcast.getBroadcastSender(), paramBroadcast.getBroadcastContent(), paramBroadcast.getBroadcastSendTime(), paramBroadcast.getBroadcastCreateTime(), paramBroadcast.getBroadcastFirmId(), Long.valueOf(paramBroadcast.getBroadcastId()) };
      int[] arrayOfInt = { 12, 12, 12, 93, 93, 12, 2 };
      updateBySQL(str, arrayOfObject, arrayOfInt);
      return 1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public void add(Broadcast paramBroadcast)
  {
    paramBroadcast.setBroadcastId(getId());
    paramBroadcast.setBroadcastCreateTime(new Date());
    String str = "insert into Z_broadcast (broadcastId,broadcastTitle,broadcastSender,broadcastContent,broadcastSendTime,broadcastCreateTime,broadcastFirmId) values(?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramBroadcast.getBroadcastId()), paramBroadcast.getBroadcastTitle(), paramBroadcast.getBroadcastSender(), paramBroadcast.getBroadcastContent(), paramBroadcast.getBroadcastSendTime(), paramBroadcast.getBroadcastCreateTime(), paramBroadcast.getBroadcastFirmId() };
    int[] arrayOfInt = { 2, 12, 12, 12, 93, 93, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(Broadcast paramBroadcast)
  {
    String str = "delete from z_broadcast where BroadcastId=?";
    Object[] arrayOfObject = { Long.valueOf(paramBroadcast.getBroadcastId()) };
    updateBySQL(str, arrayOfObject);
  }
}
