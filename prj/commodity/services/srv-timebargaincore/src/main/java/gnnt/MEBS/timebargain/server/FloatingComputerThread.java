package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.TradeDAO;
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
  private String fromFirm = null;
  private String toFirm = null;
  private TradeDAO tradeDAO;
  private int status = 1;
  
  public void init(FloatingComputer paramFloatingComputer, String paramString1, String paramString2)
  {
    this.timespace = paramFloatingComputer.getTimespace();
    this.tradeDAO = paramFloatingComputer.getTradeDAO();
    this.fromFirm = paramString1;
    this.toFirm = paramString2;
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.status == 0) {
        try
        {
          this.lastWorkTime = this.tradeDAO.floatingComputer(this.fromFirm, this.toFirm, this.lastWorkTime);
        }
        catch (Exception localException)
        {
          this.log.error("浮亏线程计算浮亏失败，原因：" + localException);
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
    this.log.info("正在关闭计算浮亏线程！开始交易商=" + this.fromFirm);
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭计算浮亏线程！开始交易商=" + this.fromFirm);
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public Timestamp getLastWorkTime()
  {
    return this.lastWorkTime;
  }
  
  public void setLastWorkTime(Timestamp paramTimestamp)
  {
    this.lastWorkTime = paramTimestamp;
  }
}
