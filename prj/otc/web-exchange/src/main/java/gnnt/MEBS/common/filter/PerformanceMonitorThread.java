package gnnt.MEBS.common.filter;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.monitor.dao.PerformanceMonitorJDBCDao;
import gnnt.MEBS.trade.dao.SystemStatusDao;
import gnnt.MEBS.trade.model.SystemStatus;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerformanceMonitorThread
  extends Thread
{
  protected final transient Log logger = LogFactory.getLog(PerformanceMonitorThread.class);
  private String type;
  private int sleepTime;
  private String time;
  private PerformanceMonitorJDBCDao prJdbcDao;
  
  public PerformanceMonitorThread(String type, int sleepTime, String time, PerformanceMonitorJDBCDao prJdbcDao)
  {
    this.type = type;
    this.sleepTime = sleepTime;
    this.time = time;
    this.prJdbcDao = prJdbcDao;
  }
  
  public void run()
  {
    for (;;)
    {
      try
      {
        Thread.sleep(this.sleepTime);
      }
      catch (Exception localException) {}
      SystemStatusDao systemStatusDao = (SystemStatusDao)SpringContextHelper.getBean("systemStatusDao");
      SystemStatus status = (SystemStatus)systemStatusDao.getList(null, null).get(0);
      if ((status != null) && (status.getStatus().intValue() == 5)) {
        if ("1".equals(this.type)) {
          this.prJdbcDao.getOnlineNum();
        } else if ("2".equals(this.type)) {
          this.time = this.prJdbcDao.getOrderNum(this.time);
        } else if ("3".equals(this.type)) {
          this.time = this.prJdbcDao.getTradeNum(this.time);
        } else if ("4".equals(this.type)) {
          this.prJdbcDao.getHoldNum();
        }
      }
    }
  }
}
