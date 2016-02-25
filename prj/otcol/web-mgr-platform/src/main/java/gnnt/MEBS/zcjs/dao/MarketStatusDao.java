package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.MarketStatus;
import java.util.List;

public class MarketStatusDao
  extends DaoHelperImpl
{
  public void update(MarketStatus paramMarketStatus)
  {
    String str = "update Z_MarketStatus set currentState=?,nextState=?,tradeTimeSerialNumber=?,isAuto=? where marketId=?";
    Object[] arrayOfObject = { Integer.valueOf(paramMarketStatus.getCurrentState()), Integer.valueOf(paramMarketStatus.getNextState()), Integer.valueOf(paramMarketStatus.getTradeTimeSerialNumber()), paramMarketStatus.getIsAuto(), paramMarketStatus.getMarketId() };
    int[] arrayOfInt = { 2, 2, 2, 1, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public MarketStatus getObject(String paramString)
  {
    String str = "select * from Z_MarketStatus where marketId=" + paramString + "";
    MarketStatus localMarketStatus = new MarketStatus();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new MarketStatus()));
    if ((localList != null) && (localList.size() > 0)) {
      localMarketStatus = (MarketStatus)localList.get(0);
    }
    return localMarketStatus;
  }
}
