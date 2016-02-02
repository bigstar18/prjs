package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Trade;
import java.io.PrintStream;
import java.util.LinkedList;

public class TradeCallbackMem
  implements TradeCallback
{
  TradeCallback tback;
  static int bt;
  Thread thread;
  LinkedList<Trade[]> buffer;
  
  public TradeCallbackMem(TradeCallback paramTradeCallback, int paramInt)
  {
    this.tback = paramTradeCallback;
    bt = paramInt;
    this.buffer = new LinkedList();
    this.thread = new Thread()
    {
      public void run()
      {
        for (;;)
        {
          try
          {
            sleep(TradeCallbackMem.bt);
          }
          catch (Exception localException) {}
          if (TradeCallbackMem.this.buffer.size() > 0) {
            System.out.println("--TradeCallbackMem. size:" + TradeCallbackMem.this.buffer.size());
          }
          for (int i = 0; i < TradeCallbackMem.this.buffer.size(); i++) {
            if (!TradeCallbackMem.this.buffer.isEmpty()) {
              synchronized (TradeCallbackMem.class)
              {
                Trade[] arrayOfTrade = (Trade[])TradeCallbackMem.this.buffer.removeFirst();
                TradeCallbackMem.this.tback.callback(arrayOfTrade[0], arrayOfTrade[1]);
              }
            }
          }
        }
      }
    };
    this.thread.start();
  }
  
  public void callback(Trade paramTrade1, Trade paramTrade2)
  {
    Trade[] arrayOfTrade = new Trade[2];
    arrayOfTrade[0] = paramTrade1;
    arrayOfTrade[1] = paramTrade2;
    synchronized (TradeCallbackMem.class)
    {
      this.buffer.addLast(arrayOfTrade);
    }
  }
}
