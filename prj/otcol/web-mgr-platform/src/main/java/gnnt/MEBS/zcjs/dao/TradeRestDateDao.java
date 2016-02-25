package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.zcjs.model.TradeRestDate;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeRestDateDao
  extends DaoHelper
{
  public final transient Log logger = LogFactory.getLog(TradeRestDateDao.class);
  
  public TradeRestDate getObject(String paramString)
  {
    TradeRestDate localTradeRestDate = null;
    String str = "select * from Z_TradeRestDate where marketId=?";
    Object[] arrayOfObject = { paramString };
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new TradeRestDate()));
    if ((localList.size() > 0) && (localList != null)) {
      localTradeRestDate = (TradeRestDate)localList.get(0);
    }
    return localTradeRestDate;
  }
  
  public void update(TradeRestDate paramTradeRestDate)
  {
    String str = "update Z_TradeRestDate set weekRest=?,yearRest=?  where marketId=? ";
    Object[] arrayOfObject = { paramTradeRestDate.getWeekRest(), paramTradeRestDate.getYearRest(), paramTradeRestDate.getMarketId() };
    int[] arrayOfInt = { 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
