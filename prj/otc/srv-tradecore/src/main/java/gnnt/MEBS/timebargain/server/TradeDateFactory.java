package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.model.TradeTime;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeDateFactory
{
  private static final Log log = LogFactory.getLog(TradeDateFactory.class);
  public static final short TYPE_TODAY = 0;
  public static final short TYPE_CROSSDAY = 1;
  
  public static TradeDate createTradeDate(short type)
  {
    TradeDate tradeDate = null;
    switch (type)
    {
    case 0: 
      tradeDate = new TradeDateToday();
      break;
    case 1: 
      tradeDate = new TradeDateCrossDay();
    }
    return tradeDate;
  }
  
  public static void main(String[] args)
  {
    TradeDate td = createTradeDate();
    System.out.println("TradeDate:" + td.calTradeDate());
    List lst = td.getAllTradeTimesByTD(td.calTradeDate());
    for (int i = 0; i < lst.size(); i++)
    {
      TradeTime tt = (TradeTime)lst.get(i);
      log.debug("TradeTime::" + tt);
    }
  }
}
