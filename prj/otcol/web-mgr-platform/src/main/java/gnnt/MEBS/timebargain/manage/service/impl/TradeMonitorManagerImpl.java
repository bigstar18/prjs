package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.TradeMonitorDAO;
import gnnt.MEBS.timebargain.manage.service.TradeMonitorManager;
import gnnt.MEBS.timebargain.manage.util.ObjSet;
import java.util.List;

public class TradeMonitorManagerImpl
  extends BaseManager
  implements TradeMonitorManager
{
  private TradeMonitorDAO dao;
  
  public void setTradeMonitorDAO(TradeMonitorDAO paramTradeMonitorDAO)
  {
    this.dao = paramTradeMonitorDAO;
  }
  
  public ObjSet getQuotation(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getQuotation(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getOrders(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getOrders(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getTrade(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getTrade(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getTradeStatistic(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getTradeStatistic(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getFirmHoldSum(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getFirmHoldSum(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getFirmHoldSumByCmd(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getFirmHoldSumByCmd(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getFundsAnalysis(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getFundsAnalysis(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getAnalyseInfo(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getAnalyseInfo(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public ObjSet getFirmTradeQuantity(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return this.dao.getFirmTradeQuantity(paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public List getFirmInfo(String paramString)
  {
    return this.dao.getFirmInfo(paramString);
  }
  
  public List getFirmHold(String paramString)
  {
    return this.dao.getFirmHold(paramString);
  }
}
