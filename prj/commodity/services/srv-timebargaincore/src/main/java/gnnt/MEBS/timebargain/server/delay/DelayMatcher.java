package gnnt.MEBS.timebargain.server.delay;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelayMatcher
  extends Thread
{
  private final Log log = LogFactory.getLog(getClass());
  private DelayDAO delayDAO;
  private boolean threadEnd = false;
  private long timeSpace;
  public static final int RUNSTATUS_RUNING = 0;
  public static final int RUNSTATUS_STOP = 1;
  private int runStatus = 1;
  private int neutralRunStatus = 1;
  
  public void init(Server paramServer)
  {
    this.timeSpace = Long.parseLong(DAOBeanFactory.getConfig("DelayMatchSpace"));
    this.delayDAO = ((DelayDAO)DAOBeanFactory.getBean("delayDAO"));
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.runStatus == 0) {
        settleMatch();
      }
      if (this.neutralRunStatus == 0) {
        neutralMatch();
      }
      try
      {
        sleep(this.timeSpace);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  public void settleMatch()
  {
    int i = this.delayDAO.settleMatch();
    if (i < 0) {
      this.log.error("交收申报撮合出错，返回值=" + i);
    }
  }
  
  public void neutralMatch()
  {
    int i = this.delayDAO.neutralMatch();
    if (i < 0) {
      this.log.error("中立仓申报撮合出错，返回值=" + i);
    }
  }
  
  public void close()
  {
    this.log.info("正在关闭延期实时撮合线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭延期实时撮合线程！");
  }
  
  public void setRunStatus(int paramInt)
  {
    this.runStatus = paramInt;
  }
  
  public void setNeutralRunStatus(int paramInt)
  {
    this.neutralRunStatus = paramInt;
  }
}
