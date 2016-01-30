package gnnt.mebsv.hqservice.service.flash;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.service.flash.flashcurdata.BFlashDataServer;
import gnnt.mebsv.hqservice.service.flash.flashcurdata.subclass.FlashDataForDB;
import gnnt.mebsv.hqservice.service.flash.flashcurdata.subclass.FlashDataForRMI;
import java.util.Properties;

public class FlashDataThread extends Thread
{
  QuotationServer quotationServer;
  BFlashDataServer flashDate;
  long dataFlashSpace;
  String dataFlashType;

  public FlashDataThread(QuotationServer paramQuotationServer)
  {
    try
    {
      this.quotationServer = paramQuotationServer;
      this.dataFlashSpace = Long.parseLong(QuotationServer.params.getProperty("DataFlashSpace"));
      this.dataFlashType = QuotationServer.params.getProperty("DataFlashType");
      if ("0".equals(this.dataFlashType))
        this.flashDate = new FlashDataForDB(paramQuotationServer);
      else if ("1".equals(this.dataFlashType))
        this.flashDate = new FlashDataForRMI(paramQuotationServer);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void run()
  {
    while (!this.flashDate.chenckRunFlag())
      try
      {
        Thread.sleep(this.dataFlashSpace);
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    this.flashDate.initCurData();
    this.quotationServer.bDataLoaded = true;
    try
    {
      while (true)
      {
        this.quotationServer.m_time = QuotationServer.getCurDataDAO().getHqTime();
        if (this.quotationServer.isServiceClear)
          this.flashDate.initCurData();
        else if (this.flashDate.flashCurData())
          this.flashDate.processSort();
        try
        {
          Thread.sleep(this.dataFlashSpace);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      throw new RuntimeException(getName() + localException2.toString());
    }
  }
}