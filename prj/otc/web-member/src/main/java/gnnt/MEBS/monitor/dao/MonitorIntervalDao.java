package gnnt.MEBS.monitor.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("mIntervalDao")
public class MonitorIntervalDao
  extends BaseDao
{
  public int getMInterval()
  {
    int interval = 3000;
    List list = querySql("select t.monitorrefresh from R_THRESHOLDARGS t");
    if ((list != null) && (list.get(0) != null))
    {
      int value = Integer.parseInt(list.get(0).toString());
      value *= 1000;
      if (value > interval) {
        interval = value;
      }
    }
    return interval;
  }
  
  public Class getEntityClass()
  {
    return null;
  }
}
