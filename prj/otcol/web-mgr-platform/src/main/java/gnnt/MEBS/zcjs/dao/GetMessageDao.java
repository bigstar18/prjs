package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Broadcast;
import gnnt.MEBS.zcjs.model.TradeMsgFeedback;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

public class GetMessageDao
  extends DaoHelper
{
  public List<TradeMsgFeedback> getMessageList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_trademsgfeedback ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new TradeMsgFeedback()));
  }
  
  public List<Broadcast> getBroadcastMessageList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_broadcast  where sysdate>=broadcastsendtime and trunc(broadcastsendtime) = trunc(sysdate)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Broadcast()));
  }
  
  public void updateRead(long paramLong)
  {
    String str = "update z_trademsgfeedback set isRead='Y' where tradeMsgFeedbackId=" + paramLong;
    updateBySQL(str);
  }
  
  public List<TradeMsgFeedback> getTradeMsgFeedbackList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_trademsgfeedback ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new TradeMsgFeedback()));
  }
  
  public List<Map<String, Object>> getAllTraderId()
  {
    String str = "select distinct t.traderid from z_trademsgfeedback t ";
    return queryBySQL(str);
  }
}
