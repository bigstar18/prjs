package gnnt.MEBS.timebargain.server;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public abstract interface TradeDate
{
  public abstract Date calClearDate();
  
  public abstract List getTradeTimes(Date paramDate);
  
  public abstract Date getRecoverDateByTime(String paramString)
    throws ParseException;
}
