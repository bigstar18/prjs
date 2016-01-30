package gnnt.mebsv.hqservice.service.flash;

import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.service.IO.CodeGeneration;
import gnnt.mebsv.hqservice.service.flash.flashmarket.FlashMarketServer;
import gnnt.mebsv.hqservice.service.server.ReceiverThread;
import java.util.Properties;

public class FlashMarketInfoThread extends Thread
{
  long marketInfoFlashSpace;
  FlashMarketServer flashServer;
  public static boolean initFlag = false;

  public FlashMarketInfoThread(QuotationServer paramQuotationServer)
  {
    try
    {
      this.marketInfoFlashSpace = Long.parseLong(QuotationServer.params.getProperty("marketInfoFlashSpace"));
      this.flashServer = new FlashMarketServer(paramQuotationServer);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void run()
  {
    this.flashServer.initQuotationData();
    initFlag = true;
    try
    {
      while (true)
      {
        this.flashServer.flashMarketInfo();
        if (this.flashServer.checkTDateChange())
        {
          this.flashServer.clearData();
          this.flashServer.flashProductInfo(true);
          ReceiverThread.flashThread.statusChangeInit(QuotationServer.getInstance());
        }
        else
        {
          this.flashServer.setIsClear();
        }
        this.flashServer.flashProductInfo(false);
        try
        {
          Thread.sleep(this.marketInfoFlashSpace);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException(localException.toString());
    }
  }
}