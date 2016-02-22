package gnnt.quotation;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.quotation.QuotationInterface;
import gnnt.quotation.util.DateUtil;
import gnnt.quotation.util.Utils;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessQuotationData
  extends Thread
{
  private final transient Log logger = LogFactory.getLog(ProcessQuotationData.class);
  private volatile boolean stop = false;
  public volatile boolean stopped = true;
  public final BlockingQueue<String> quotationQueue;
  QuotationInterface quotationInterface;
  
  public ProcessQuotationData(QuotationInterface quotationInterface)
  {
    this.quotationQueue = new LinkedBlockingQueue();
    this.quotationInterface = quotationInterface;
  }
  
  public void run()
  {
    this.logger.info("ProcessQuotationData is starting.  ");
    String msg = null;
    
    this.stopped = false;
    while (!this.stop) {
      try
      {
        msg = (String)this.quotationQueue.take();
        
        boolean succeed = false;
        try
        {
          succeed = process(msg);
        }
        catch (Exception e)
        {
          System.out.println("------" + new Date());
          e.printStackTrace();
          this.logger.error(" process data error,msg=" + msg + 
            ";error info=" + e.toString());
          errorException(e);
        }
        finally
        {
          if (!succeed) {
            this.logger.error("process fail. msg=" + msg);
          }
        }
        msg = null;
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
        this.logger.error(" take data exception,msg=" + msg + 
          ";exception info=" + e.getMessage());
      }
    }
  }
  
  public void errorException(Exception e)
  {
    StackTraceElement[] ste = e.getStackTrace();
    for (int i = 0; i < ste.length; i++) {
      this.logger.error(ste[i].toString());
    }
  }
  
  private boolean process(String msg)
  {
    this.logger.debug("quotationQueue Length=" + this.quotationQueue.size());
    this.logger.debug("process  msg=" + msg);
    if (this.quotationInterface == null)
    {
      this.logger.error("quotationInterface is null");
      return false;
    }
    String[] strs = msg.split("\\|", -1);
    if (strs.length < 4)
    {
      this.logger.error("msg format error!msg content=" + msg + 
        " the number of segments < 4：" + strs.length);
      return false;
    }
    if (strs[2].equals("7000"))
    {
      this.logger.debug("msg needn't process FUNCODE is 7000 !");
      return true;
    }
    if (!strs[2].equals("6000"))
    {
      this.logger.error("msg needn't process FUNCODE isn't 6000 !msg FUNCODE=" + 
        strs[2]);
      return false;
    }
    if (strs.length != 20)
    {
      this.logger.error("FUNCODE=6000 but msg format error !msg content=" + 
        msg + " the number of segments != 20：" + strs.length);
      return false;
    }
    if (!strs[4].equals("3"))
    {
      this.logger.error("msg needn't process,SUBFUNCODE isn't 3 !msg SUBFUNCODE=" + 
        strs[4]);
      return false;
    }
    Quotation quotation = new Quotation();
    quotation.setCommodityID(strs[6]);
    quotation.setOpenPrice(Double.valueOf(Utils.parseDouble(strs[15], 0.0D)));
    quotation.setClosePrice(Double.valueOf(Utils.parseDouble(strs[18], 0.0D)));
    quotation.setHighPrice(Double.valueOf(Utils.parseDouble(strs[16], 0.0D)));
    quotation.setLowPrice(Double.valueOf(Utils.parseDouble(strs[17], 0.0D)));
    quotation.setCurPrice(Double.valueOf(Utils.parseDouble(strs[12], 0.0D)));
    try
    {
      quotation.setUpdateTime(new Timestamp(DateUtil.convertStringToDate(
        "yyyyMMddHHmmss", strs[10] + strs[11]).getTime()));
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      this.logger.error("ParseException Date=" + strs[10] + " Time=" + 
        strs[11]);
      return false;
    }
    long startDate = 0L;
    if (this.logger.isDebugEnabled()) {
      startDate = System.currentTimeMillis();
    }
    this.quotationInterface.setQuotation(quotation);
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("quotationInterface setQuotation cost time =" + (
        System.currentTimeMillis() - startDate));
    }
    return true;
  }
  
  public List<String> shutdown()
  {
    this.logger.info("Stop ProcessQuotationData. ");
    this.stopped = true;
    this.stop = true;
    List<String> taskList = new ArrayList();
    this.quotationQueue.drainTo(taskList);
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    return taskList;
  }
}
