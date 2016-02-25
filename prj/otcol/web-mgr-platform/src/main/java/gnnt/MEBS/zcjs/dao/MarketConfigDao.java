package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.MarketConfig;
import java.util.List;

public class MarketConfigDao
  extends DaoHelperImpl
{
  public MarketConfig getObject(String paramString)
  {
    String str = "select * from Z_MarketConfig where marketId=?";
    Object[] arrayOfObject = { paramString };
    MarketConfig localMarketConfig = null;
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new MarketConfig()));
    if ((localList.size() > 0) && (localList != null)) {
      localMarketConfig = (MarketConfig)localList.get(0);
    }
    return localMarketConfig;
  }
  
  public void update(MarketConfig paramMarketConfig)
  {
    String str = "update Z_MarketConfig set financestatus=?,submitEndTime=?  where marketId=? ";
    Object[] arrayOfObject = { Integer.valueOf(paramMarketConfig.getFinanceStatus()), paramMarketConfig.getSubmitEndTime(), paramMarketConfig.getMarketId() };
    int[] arrayOfInt = { 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
