package gnnt.MEBS.timebargain.server.riskcontrol;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdateRiskRateAllThread
  extends Thread
{
  private Log log = LogFactory.getLog(getClass());
  private long timespace;
  private boolean threadEnd = false;
  private int status = 1;
  
  public void init(long timespace)
  {
    this.timespace = timespace;
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.status == 0) {
        try
        {
          Server.getRiskcontrolDAO().updateRiskRateAll();
        }
        catch (Exception e)
        {
          this.log.error("更新所有交易员风险率失败，原因：" + e);
        }
      }
      try
      {
        sleep(this.timespace);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  public void close()
  {
    this.log.info("正在关闭更新所有交易员风险率线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭更新所有交易员风险率线程！");
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
}
