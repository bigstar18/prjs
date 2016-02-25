package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.TradeTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeTimeDao
  extends DaoHelper
{
  public final transient Log logger = LogFactory.getLog(TradeTimeDao.class);
  
  public List<TradeTime> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_TradeTime";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, null, paramPageInfo, new CommonRowMapper(new TradeTime()));
  }
  
  public TradeTime getObject(int paramInt)
  {
    String str = "select * from Z_TradeTime where serialnumber=?";
    Object[] arrayOfObject = { Integer.valueOf(paramInt) };
    TradeTime localTradeTime = null;
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new TradeTime()));
    if ((localList.size() > 0) && (localList != null)) {
      localTradeTime = (TradeTime)localList.get(0);
    }
    return localTradeTime;
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_TradeTime ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void update(TradeTime paramTradeTime)
  {
    String str = "update Z_TradeTime set startTime=?,endTime=?  where serialNumber=? ";
    Object[] arrayOfObject = { paramTradeTime.getStartTime(), paramTradeTime.getEndTime(), Integer.valueOf(paramTradeTime.getSerialNumber()) };
    int[] arrayOfInt = { 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void add(TradeTime paramTradeTime)
  {
    String str = "insert into Z_TradeTime (serialNumber,startTime,endTime) values(?,?,?)";
    Object[] arrayOfObject = { Integer.valueOf(paramTradeTime.getSerialNumber()), paramTradeTime.getStartTime(), paramTradeTime.getEndTime() };
    int[] arrayOfInt = { 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public int getId()
  {
    String str = "select max(SerialNumber) as serialNumber from Z_TradeTime";
    int i = queryForInt(str, null);
    return i;
  }
  
  public void delete(TradeTime paramTradeTime)
  {
    String str = "delete from z_tradeTime where serialNumber=?";
    Object[] arrayOfObject = { Integer.valueOf(paramTradeTime.getSerialNumber()) };
    updateBySQL(str, arrayOfObject);
  }
}
