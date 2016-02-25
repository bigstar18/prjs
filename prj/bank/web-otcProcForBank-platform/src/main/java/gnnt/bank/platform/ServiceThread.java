package gnnt.bank.platform;

import java.util.Calendar;
import org.apache.log4j.Logger;

public class ServiceThread
  extends Thread
{
  private CapitalProcessor m_processor = null;
  
  public ServiceThread(CapitalProcessor processor)
  {
    this.m_processor = processor;
  }
  
  private void log(String content)
  {
    Logger plog = Logger.getLogger("Servicelog");
    plog.debug(content);
  }
  
  public void run()
  {
    log("========>启动处理器对象服务线程<=======");
    String compareTime = this.m_processor.getConfig("CompareTime");
    String compareMode = this.m_processor.getConfig("CompareMode");
    int hour = 0;
    int minute = 0;
    int second = 0;
    boolean run = false;
    String[] time = compareTime.split(":");
    if ((compareMode.equals("0")) && (time.length >= 3)) {
      try
      {
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        second = Integer.parseInt(time[2]);
        run = true;
      }
      catch (Exception e)
      {
        log("配置文件中自动对账时间的格式错误！");
      }
    }
    boolean isRun = false;
    while (run)
    {
      Calendar sysCalendar = Calendar.getInstance();
      
      Calendar calendar = Calendar.getInstance();
      calendar.set(11, hour);
      calendar.set(12, minute);
      calendar.set(13, second);
      if (sysCalendar.before(calendar)) {
        isRun = true;
      }
      if ((sysCalendar.after(calendar)) && (isRun))
      {
        try
        {
          if (this.m_processor.setMoneyInfo(null, sysCalendar.getTime()) != 0) {
            log("给银行发送数据错误!");
          } else {
            log(sysCalendar.getTime() + "给银行发送数据成功!");
          }
          if (this.m_processor.getBankCompareInfo(null, sysCalendar.getTime()) != 0) {
            log("获取银行对账数据错误!");
          } else {
            log(sysCalendar.getTime() + "获取银行对账数据成功!");
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        isRun = false;
      }
      try
      {
        sleep(20000L);
      }
      catch (InterruptedException e)
      {
        log("自动对账sleep错误：" + e.getMessage());
      }
    }
  }
}
