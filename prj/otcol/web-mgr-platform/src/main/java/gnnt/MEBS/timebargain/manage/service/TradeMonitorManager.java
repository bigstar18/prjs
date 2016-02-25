package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.TradeMonitorDAO;
import gnnt.MEBS.timebargain.manage.util.ObjSet;
import java.util.List;

public abstract interface TradeMonitorManager
{
  public abstract void setTradeMonitorDAO(TradeMonitorDAO paramTradeMonitorDAO);
  
  public abstract ObjSet getQuotation(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getOrders(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getTrade(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getTradeStatistic(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getFirmHoldSum(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getFirmHoldSumByCmd(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getFundsAnalysis(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getAnalyseInfo(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract ObjSet getFirmTradeQuantity(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List getFirmInfo(String paramString);
  
  public abstract List getFirmHold(String paramString);
}
