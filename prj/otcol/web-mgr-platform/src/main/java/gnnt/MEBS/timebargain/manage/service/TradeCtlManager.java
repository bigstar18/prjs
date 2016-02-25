package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.TradeCtlDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.Date;
import java.util.List;

public abstract interface TradeCtlManager
{
  public abstract void setTradeCtlDAO(TradeCtlDAO paramTradeCtlDAO);
  
  public abstract List getHolds();
  
  public abstract List getHoldPositions(QueryConditions paramQueryConditions);
  
  public abstract List getHoldPositions1();
  
  public abstract List getTrades();
  
  public abstract List getOrders();
  
  public abstract List getActions();
  
  public abstract List getFunds(Date paramDate);
  
  public abstract List getTradeErrors();
  
  public abstract List getSpecFrozenHolds();
  
  public abstract int getSpecFrozenHoldsCount();
  
  public abstract List getForceClose(String paramString);
  
  public abstract List getTradeTime();
  
  public abstract List getDetailForceClose();
  
  public abstract List getTradeResponds();
  
  public abstract List getTraderInfo(String paramString);
  
  public abstract double getCumputeMargin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract double getForceCloseMoney(Orders paramOrders);
  
  public abstract int getHoldsCount(QueryConditions paramQueryConditions);
  
  public abstract List getHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract List getSpecFrozenHolds(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract List getHoldPositionsDetail(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int getHoldsDetailCount(QueryConditions paramQueryConditions);
}
