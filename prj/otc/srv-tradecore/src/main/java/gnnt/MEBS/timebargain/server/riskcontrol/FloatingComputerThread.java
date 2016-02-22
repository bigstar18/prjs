package gnnt.MEBS.timebargain.server.riskcontrol;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FloatingComputerThread
  extends Thread
{
  private Log log = LogFactory.getLog(getClass());
  private Timestamp lastWorkTime = null;
  private long timespace;
  private boolean threadEnd = false;
  private String m_FirmID = null;
  private int status = 1;
  
  public void init(long timespace, String m_FirmID)
  {
    this.timespace = timespace;
    this.m_FirmID = m_FirmID;
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.status == 0) {
        try
        {
          this.lastWorkTime = Server.getRiskcontrolDAO().floatingComputer(this.m_FirmID, this.lastWorkTime);
        }
        catch (Exception e)
        {
          this.log.error("浮亏线程计算浮亏失败，原因：" + e);
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
    this.log.info("正在关闭计算浮亏线程！会员交易商=" + this.m_FirmID);
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭计算浮亏线程！会员交易商=" + this.m_FirmID);
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Timestamp getLastWorkTime()
  {
    return this.lastWorkTime;
  }
  
  public void setLastWorkTime(Timestamp lastWorkTime)
  {
    this.lastWorkTime = lastWorkTime;
  }
}
