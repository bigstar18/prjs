package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.model.TradeTime;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public abstract interface TradeDate
{
  public abstract Date calClearDateByTradeDate(Date paramDate);
  
  public abstract Date calNextTradeDate(Date paramDate);
  
  public abstract Date calTradeDate();
  
  public abstract List<TradeTime> getTradeTimes(List<TradeTime> paramList);
  
  public abstract List<TradeTime> getAllTradeTimesByTD(Date paramDate);
  
  public abstract Date getRecoverDateByTime(String paramString)
    throws ParseException;
  
  public abstract boolean checkTradeDay(Date paramDate);
}
