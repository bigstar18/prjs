package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.DelayDAO;
import gnnt.MEBS.timebargain.manage.model.Delay;
import gnnt.MEBS.timebargain.manage.model.DelayStatusLocal;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class DelayDAOJdbc
  extends BaseDAOJdbc
  implements DelayDAO
{
  public Delay getDelayById(Long paramLong)
  {
    String str1 = "select * from T_A_DelayTradeTime where 1=1 ";
    if (paramLong != null) {
      str1 = str1 + " and sectionID = " + paramLong;
    }
    Delay localDelay = null;
    List localList = getJdbcTemplate().queryForList(str1);
    if ((localList != null) && (localList.size() > 0))
    {
      localDelay = new Delay();
      Map localMap = (Map)localList.get(0);
      Long localLong = Long.valueOf(Long.parseLong(localMap.get("SectionID") + ""));
      String str2 = localMap.get("Name") + "";
      String str3 = localMap.get("StartTime") + "";
      String str4 = localMap.get("EndTime") + "";
      String str5 = localMap.get("StartTime") + "";
      String str6 = localMap.get("EndTime") + "";
      Short localShort1 = Short.valueOf(Short.parseShort(localMap.get("Type") + ""));
      Short localShort2 = Short.valueOf(Short.parseShort(localMap.get("Status") + ""));
      String str7 = localMap.get("ModifyTime") + "";
      localDelay.setSectionID(localLong);
      localDelay.setName(str2);
      localDelay.setStartTime(str3);
      localDelay.setEndTime(str4);
      localDelay.setStartMiddleTime(str5);
      localDelay.setEndMiddleTime(str6);
      localDelay.setType(localShort1);
      localDelay.setStatus(localShort2);
      localDelay.setModifyTime(str7);
    }
    return localDelay;
  }
  
  public Delay getDelayByType(short paramShort)
  {
    String str1 = "select * from T_A_DelayTradeTime where 1=1 ";
    str1 = str1 + " and type = " + paramShort;
    Delay localDelay = null;
    List localList = getJdbcTemplate().queryForList(str1);
    if ((localList != null) && (localList.size() > 0))
    {
      localDelay = new Delay();
      Map localMap = (Map)localList.get(0);
      Long localLong = Long.valueOf(Long.parseLong(localMap.get("SectionID") + ""));
      String str2 = localMap.get("Name") + "";
      String str3 = localMap.get("StartTime") + "";
      String str4 = localMap.get("EndTime") + "";
      String str5 = localMap.get("StartTime") + "";
      String str6 = localMap.get("EndTime") + "";
      Short localShort1 = Short.valueOf(Short.parseShort(localMap.get("Type") + ""));
      Short localShort2 = Short.valueOf(Short.parseShort(localMap.get("Status") + ""));
      String str7 = localMap.get("ModifyTime") + "";
      localDelay.setSectionID(localLong);
      localDelay.setName(str2);
      localDelay.setStartTime(str3);
      localDelay.setEndTime(str4);
      localDelay.setStartMiddleTime(str5);
      localDelay.setEndMiddleTime(str6);
      localDelay.setType(localShort1);
      localDelay.setStatus(localShort2);
      localDelay.setModifyTime(str7);
    }
    return localDelay;
  }
  
  public void insertDelaySection(Delay paramDelay)
  {
    String str = "insert into T_A_DelayTradeTime (SectionID,Name,StartTime,EndTime,Type,Status,ModifyTime) values (?,?,?,?,?,?,sysdate)";
    Object[] arrayOfObject = { paramDelay.getSectionID(), paramDelay.getName(), paramDelay.getStartTime(), paramDelay.getEndTime(), paramDelay.getType(), paramDelay.getStatus() };
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
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void updateDelaySection(Delay paramDelay)
  {
    String str = "update T_A_DelayTradeTime set Name=?,StartTime=?,EndTime=?,Type=?,Status=?,ModifyTime=sysdate where sectionID = ?";
    Object[] arrayOfObject = { paramDelay.getName(), paramDelay.getStartTime(), paramDelay.getEndTime(), paramDelay.getType(), paramDelay.getStatus(), paramDelay.getSectionID() };
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
      throw new RuntimeException("修改失败！");
    }
  }
  
  public void deleteDelaySectionById(String paramString)
  {
    String str1 = "";
    if ((paramString != null) && (!"".equals(paramString))) {
      str1 = "删除失败！";
    } else {
      str1 = "已无此记录！";
    }
    String str2 = "delete from T_A_DelayTradeTime where sectionID = " + paramString;
    try
    {
      getJdbcTemplate().update(str2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException(str1);
    }
  }
  
  public DelayStatusLocal getDelayStatus()
  {
    String str1 = "select t.*,to_char(t.TradeDate,'yyyy-MM-dd') relTradeDate from T_DelayStatus t";
    DelayStatusLocal localDelayStatusLocal = null;
    List localList = getJdbcTemplate().queryForList(str1);
    if ((localList != null) && (localList.size() > 0))
    {
      localDelayStatusLocal = new DelayStatusLocal();
      Map localMap = (Map)localList.get(0);
      String str2 = localMap.get("relTradeDate") + "";
      String str3 = localMap.get("status") + "";
      Long localLong = null;
      if (localMap.get("sectionID") != null) {
        localLong = Long.valueOf(Long.parseLong(localMap.get("sectionID") + ""));
      }
      String str4 = localMap.get("note") + "";
      String str5 = localMap.get("RecoverTime") + "";
      localDelayStatusLocal.setTradeDate(str2);
      localDelayStatusLocal.setSectionID(localLong);
      localDelayStatusLocal.setStatus(str3);
      localDelayStatusLocal.setNote(str4);
      localDelayStatusLocal.setRecoverTime(str5);
    }
    return localDelayStatusLocal;
  }
  
  public List getDelayOrdersList(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.* from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    String str3;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str3 = "T_H_DelayOrders a where 1=1 ";
      localStringBuffer.append(str3);
    }
    else
    {
      str3 = "T_DelayOrders a where 1=1 ";
      localStringBuffer.append(str3);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" order by a.firmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getDelayTradeList(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.* from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    String str2 = (String)paramQueryConditions.getConditionValue("isOne");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    String str3;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str3 = "T_H_DelayTrade a where 1=1 ";
      localStringBuffer.append(str3);
    }
    else
    {
      str3 = "T_DelayTrade a where 1=1 ";
      localStringBuffer.append(str3);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" order by a.firmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getDelayQuotationList(QueryConditions paramQueryConditions)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.* from ");
    String str1 = (String)paramQueryConditions.getConditionValue("isQryHis");
    Object[] arrayOfObject = null;
    paramQueryConditions.removeCondition("isQryHis");
    paramQueryConditions.removeCondition("isOne");
    String str2;
    if ((str1 != null) && (str1.equalsIgnoreCase("true")))
    {
      str2 = "T_H_DelayQuotation a where 1=1 ";
      localStringBuffer.append(str2);
    }
    else
    {
      str2 = "T_DelayQuotation a where 1=1 ";
      localStringBuffer.append(str2);
      paramQueryConditions.removeCondition("a.ClearDate", ">=");
      paramQueryConditions.removeCondition("a.ClearDate", "<=");
    }
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and ").append(paramQueryConditions.getFieldsSqlClause());
    }
    localStringBuffer.append(" order by a.commodityID");
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null) {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public Object getObject(Class paramClass, Serializable paramSerializable)
  {
    return null;
  }
  
  public List getObjects(Class paramClass)
  {
    return null;
  }
  
  public void removeObject(Class paramClass, Serializable paramSerializable) {}
  
  public void saveObject(Object paramObject) {}
}
