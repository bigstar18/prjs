package gnnt.MEBS.monitor.dao;

import gnnt.MEBS.base.dao.jdbc.DaoHelper;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("performanceMonitorJDBCDao")
public class PerformanceMonitorJDBCDao
  extends DaoHelper
{
  public void getOnlineNum()
  {
    String sql = "select count(*) as num from m_trader m where m.onlinestatus=1";
    List list = queryBySQL(sql);
    String insertSql = "insert into t_performanceMonitor(id,type,num,datetime,categoryType)   values(SEQ_t_performanceMonitor.nextval,'1', ?, sysdate,'')";
    

    Object[] params = { ((Map)list.get(0)).get("NUM") };
    
    int[] dataTypes = { 2 };
    updateBySQL(insertSql, params, dataTypes);
  }
  
  public String getOrderNum(String time)
  {
    String groupSql = "select round(count(*)/5,1) as num,o.commodityid as commodityid from t_orders o where o.ordertime > to_date('" + 
      time + "','yyyy-MM-dd HH24:mi:ss') group by o.commodityid";
    if ("".equals(time)) {
      groupSql = "select round(count(*)/5,1) as num,commodityid from t_orders group by commodityid";
    }
    List list = queryBySQL(groupSql);
    
    List dateList = queryBySQL("select sysdate from dual");
    Timestamp sysdate = (Timestamp)((Map)dateList.get(0)).get("SYSDATE");
    

    String insertSql = "insert into t_performanceMonitor(id,type,num,datetime,categoryType)  values(SEQ_t_performanceMonitor.nextval,'2', ?, ?,?)";
    
    long sumNum = 0L;
    if ((list != null) && (list.size() > 0))
    {
      for (int i = 0; i < list.size(); i++)
      {
        BigDecimal num = (BigDecimal)((Map)list.get(i)).get("NUM");
        Object[] params = { Double.valueOf(Math.ceil(num.doubleValue())), sysdate, 
          ((Map)list.get(i)).get("COMMODITYID") };
        int[] dataTypes = { 2, 93, 
          12 };
        sumNum += Double.valueOf(Math.ceil(num.doubleValue())).longValue();
        updateBySQL(insertSql, params, dataTypes);
      }
      Object[] params = { Long.valueOf(sumNum), sysdate, "All" };
      int[] dataTypes = { 2, 93, 12 };
      updateBySQL(insertSql, params, dataTypes);
    }
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(sysdate);
  }
  
  public String getTradeNum(String time)
  {
    String groupSql = "select round(count(*)/5,1) as num,t.commodityid as commodityid from t_trade t where t.tradetime > to_date('" + 
      time + "','yyyy-MM-dd HH24:mi:ss') group by t.commodityid";
    if ("".equals(time)) {
      groupSql = "select round(count(*)/5,1) as num,t.commodityid as commodityid from t_trade t group by t.commodityid";
    }
    List list = queryBySQL(groupSql);
    
    List dateList = queryBySQL("select sysdate from dual");
    Timestamp sysdate = (Timestamp)((Map)dateList.get(0)).get("SYSDATE");
    
    String insertSql = "insert into t_performanceMonitor(id,type,num,datetime,categoryType)  values(SEQ_t_performanceMonitor.nextval,'3', ?, ?,?)";
    
    long sumNum = 0L;
    if ((list != null) && (list.size() > 0))
    {
      for (int i = 0; i < list.size(); i++)
      {
        BigDecimal num = (BigDecimal)((Map)list.get(i)).get("NUM");
        Object[] params = { Double.valueOf(Math.ceil(num.doubleValue())), sysdate, 
          ((Map)list.get(i)).get("COMMODITYID") };
        int[] dataTypes = { 2, 93, 
          12 };
        sumNum += Double.valueOf(Math.ceil(num.doubleValue())).longValue();
        updateBySQL(insertSql, params, dataTypes);
      }
      Object[] params = { Long.valueOf(sumNum), sysdate, "All" };
      int[] dataTypes = { 2, 93, 12 };
      updateBySQL(insertSql, params, dataTypes);
    }
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(sysdate);
  }
  
  public void getHoldNum()
  {
    String sql = "select sum(t.holdqty) as holdqty,t.commodityid from t_holdposition t  group by commodityid";
    List list = queryBySQL(sql);
    

    List dateList = queryBySQL("select sysdate from dual");
    Timestamp sysdate = (Timestamp)((Map)dateList.get(0)).get("SYSDATE");
    
    String insertSql = "insert into t_performanceMonitor(id,type,num,datetime,categoryType)  values(SEQ_t_performanceMonitor.nextval,'4', ?, ?,?)";
    
    long sumHoldqty = 0L;
    if ((list != null) && (list.size() > 0))
    {
      for (int i = 0; i < list.size(); i++)
      {
        BigDecimal holdQty = 
          (BigDecimal)((Map)list.get(i)).get("HOLDQTY");
        Object[] params = { holdQty, sysdate, 
          ((Map)list.get(i)).get("COMMODITYID") };
        int[] dataTypes = { 2, 93, 
          12 };
        sumHoldqty += holdQty.longValue();
        updateBySQL(insertSql, params, dataTypes);
      }
      Object[] params = { Long.valueOf(sumHoldqty), sysdate, "All" };
      int[] dataTypes = { 2, 93, 12 };
      updateBySQL(insertSql, params, dataTypes);
    }
  }
  
  public List getList(String sql)
  {
    return queryBySQL(sql);
  }
}
