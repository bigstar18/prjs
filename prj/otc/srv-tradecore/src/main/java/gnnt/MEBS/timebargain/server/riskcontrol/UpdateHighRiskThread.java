package gnnt.MEBS.timebargain.server.riskcontrol;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import gnnt.MEBS.timebargain.server.quotation.QuotationEngine;
import gnnt.MEBS.timebargain.server.quotation.QuotationObservable;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdateHighRiskThread
  extends Thread
  implements Observer
{
  private Log log = LogFactory.getLog(getClass());
  private boolean threadEnd = false;
  private Object runLock = new Object();
  
  public UpdateHighRiskThread()
  {
    for (QuotationObservable quotationObservable : QuotationEngine.getQuotationObservableMap().values()) {
      quotationObservable.addObserver(this);
    }
  }
  
  public void update(Observable quotationObservable, Object arg)
  {
    this.log.debug("UpdateHighRiskThread quotation price has been changed to " + ((QuotationObservable)quotationObservable).getCurPrice());
    notifyRun();
  }
  
  public void run()
  {
    while (!this.threadEnd) {
      try
      {
        waitRun();
        
        Server.getRiskcontrolDAO().updateRiskRateHigh();
      }
      catch (Exception e)
      {
        this.log.error("更新高风险率线程失败，原因：" + e);
      }
    }
  }
  
  public void notifyRun()
  {
    synchronized (this.runLock)
    {
      this.runLock.notifyAll();
    }
  }
  
  public void waitRun()
  {
    synchronized (this.runLock)
    {
      try
      {
        this.runLock.wait();
      }
      catch (InterruptedException e)
      {
        this.log.error("等待运行锁失败，原因：" + e.getMessage());
      }
    }
  }
  
  public void close()
  {
    this.log.info("正在关闭更新高风险率线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭更新高风险率线程！");
  }
}
