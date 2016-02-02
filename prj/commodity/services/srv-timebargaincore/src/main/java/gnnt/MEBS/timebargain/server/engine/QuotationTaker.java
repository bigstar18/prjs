package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Quotation;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationTaker
  extends Thread
{
  private final transient Log logger = LogFactory.getLog(QuotationTaker.class);
  private TradeEngine te;
  private QuotationCallback quotationCallback;
  private QuotationCallback hqCallback;
  private Timestamp lastHqTime;
  private Long lastTotal;
  private volatile boolean stop = false;
  
  QuotationTaker(TradeEngine paramTradeEngine, QuotationCallback paramQuotationCallback)
  {
    this.te = paramTradeEngine;
    this.quotationCallback = paramQuotationCallback;
    this.hqCallback = new HQCallback();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(2008, 1, 1);
    this.lastHqTime = new Timestamp(localCalendar.getTimeInMillis());
    this.lastTotal = Long.valueOf(0L);
  }
  
  public void pleaseStop()
  {
    this.stop = true;
  }
  
  public void run()
  {
    while (!this.stop)
    {
      try
      {
        if ((this.te.getStatus() == 0) || (this.te.getStatus() == 1)) {
          pushQuotation();
        }
      }
      catch (Exception localException1)
      {
        this.logger.error("Quotation Push Error!", localException1);
      }
      try
      {
        sleep(300L);
      }
      catch (Exception localException2) {}
    }
  }
  
  public void pushQuotation()
  {
    List localList = this.te.queryQuotation();
    Quotation localQuotation = null;
    for (int i = 0; i < localList.size(); i++)
    {
      localQuotation = (Quotation)localList.get(i);
      this.hqCallback.callback(localQuotation);
      this.quotationCallback.callback(localQuotation);
    }
  }
}
