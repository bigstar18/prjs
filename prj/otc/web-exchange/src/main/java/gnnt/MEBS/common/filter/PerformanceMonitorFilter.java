package gnnt.MEBS.common.filter;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.monitor.dao.PerformanceMonitorJDBCDao;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PerformanceMonitorFilter
  implements Filter
{
  protected final transient Log logger = LogFactory.getLog(PerformanceMonitorFilter.class);
  
  public void destroy() {}
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    chain.doFilter(request, response);
  }
  
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    PerformanceMonitorJDBCDao perJdbcDao = (PerformanceMonitorJDBCDao)
      SpringContextHelper.getBean("performanceMonitorJDBCDao");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    String time = "";
    
    List dateList = perJdbcDao.queryBySQL("select sysdate from dual");
    Timestamp sysdate = (Timestamp)((Map)dateList.get(0)).get("SYSDATE");
    time = df.format(sysdate);
    PerformanceMonitorThread onlineThread = new PerformanceMonitorThread("1", 5000, "", perJdbcDao);
    PerformanceMonitorThread orderThread = new PerformanceMonitorThread("2", 5000, time, perJdbcDao);
    PerformanceMonitorThread tradeThread = new PerformanceMonitorThread("3", 5000, time, perJdbcDao);
    PerformanceMonitorThread holdThread = new PerformanceMonitorThread("4", 5000, time, perJdbcDao);
    
    onlineThread.start();
    orderThread.start();
    tradeThread.start();
    holdThread.start();
  }
}
