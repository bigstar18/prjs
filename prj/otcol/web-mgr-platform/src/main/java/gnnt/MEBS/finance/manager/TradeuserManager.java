package gnnt.MEBS.finance.manager;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.util.SysData;
import gnnt.MEBS.finance.dao.TradeuserDao;
import gnnt.MEBS.finance.unit.Tradeuser;
import java.util.List;

public class TradeuserManager
{
  public static List getTradeusers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    TradeuserDao localTradeuserDao = (TradeuserDao)SysData.getBean("tradeuserDao");
    return localTradeuserDao.getTradeusers(paramQueryConditions, paramPageInfo);
  }
  
  public static void createTradeuser(Tradeuser paramTradeuser)
  {
    TradeuserDao localTradeuserDao = (TradeuserDao)SysData.getBean("tradeuserDao");
    localTradeuserDao.createTradeuser(paramTradeuser);
  }
  
  public static Tradeuser getTradeuserById(String paramString)
  {
    TradeuserDao localTradeuserDao = (TradeuserDao)SysData.getBean("tradeuserDao");
    return localTradeuserDao.getTradeuserById(paramString);
  }
  
  public static void updateTradeuser(Tradeuser paramTradeuser)
  {
    TradeuserDao localTradeuserDao = (TradeuserDao)SysData.getBean("tradeuserDao");
    localTradeuserDao.updateTradeuser(paramTradeuser);
  }
  
  public static void repairTradeuser(String paramString1, String paramString2)
  {
    TradeuserDao localTradeuserDao = (TradeuserDao)SysData.getBean("tradeuserDao");
    localTradeuserDao.repairTradeuser(paramString1, paramString2);
  }
}
