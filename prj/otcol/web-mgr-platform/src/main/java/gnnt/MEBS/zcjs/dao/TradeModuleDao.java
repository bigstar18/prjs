package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.TradeModule;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeModuleDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(TradeModuleDao.class);
  
  public TradeModule getObject(String paramString)
  {
    String str = "select * from M_TRADEMODULE where moduleId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new TradeModule()));
    TradeModule localTradeModule = null;
    if ((localList != null) && (localList.size() > 0)) {
      localTradeModule = (TradeModule)localList.get(0);
    }
    return localTradeModule;
  }
}
