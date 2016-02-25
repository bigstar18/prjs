package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.StatQueryDAO;
import gnnt.MEBS.timebargain.manage.dao.TradeCtlDAO;
import gnnt.MEBS.timebargain.manage.model.Orders;
import gnnt.MEBS.timebargain.manage.service.TradeCtlManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.Date;
import java.util.List;

public class TradeCtlManagerImpl
  extends BaseManager
  implements TradeCtlManager
{
  private TradeCtlDAO dao;
  private StatQueryDAO statQueryDAO;
  
  public void setStatQueryDAO(StatQueryDAO paramStatQueryDAO)
  {
    this.statQueryDAO = paramStatQueryDAO;
  }
  
  public void setTradeCtlDAO(TradeCtlDAO paramTradeCtlDAO)
  {
    this.dao = paramTradeCtlDAO;
  }
  
  public List getHolds()
  {
    return this.dao.getHolds();
  }
  
  public List getHoldPositions(QueryConditions paramQueryConditions)
  {
    return this.dao.getHoldPositions(paramQueryConditions);
  }
  
  public List getHoldPositions1()
  {
    return this.dao.getHoldPositions1();
  }
  
  public List getTrades()
  {
    return this.dao.getTrades();
  }
  
  public List getOrders()
  {
    return this.dao.getOrders();
  }
  
  public List getActions()
  {
    return this.dao.getActions();
  }
  
  public List getFunds(Date paramDate)
  {
    return this.dao.getFunds(paramDate);
  }
  
  public List getTradeErrors()
  {
    return this.dao.getTradeErrors();
  }
  
  public List getSpecFrozenHolds()
  {
    return this.dao.getSpecFrozenHolds();
  }
  
  public List getForceClose(String paramString)
  {
    return this.dao.getForceClose(paramString);
  }
  
  public List getTradeTime()
  {
    return this.dao.getTradeTime();
  }
  
  public List getTradeResponds()
  {
    return this.dao.getTradeResponds();
  }
  
  public List getTraderInfo(String paramString)
  {
    return this.dao.getTraderInfo(paramString);
  }
  
  public double getCumputeMargin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    return this.dao.getCumputeMargin(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public double getForceCloseMoney(Orders paramOrders)
  {
    return this.dao.getForceCloseMoney(paramOrders);
  }
  
  public int getHoldsCount(QueryConditions paramQueryConditions)
  {
    return this.statQueryDAO.getHoldsCount(paramQueryConditions);
  }
  
  public List getHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.statQueryDAO.getHoldPositionsDetail(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int getSpecFrozenHoldsCount()
  {
    return this.statQueryDAO.getSpecFrozenHoldsCount();
  }
  
  public List getSpecFrozenHolds(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.statQueryDAO.getSpecFrozenHolds(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public List getDetailForceClose()
  {
    return this.dao.getForceClose(null);
  }
  
  public List getHoldPositionsDetail(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    return this.dao.getHoldPositionsDetail(paramQueryConditions, paramInt1, paramInt2);
  }
  
  public int getHoldsDetailCount(QueryConditions paramQueryConditions)
  {
    return this.dao.getHoldsDetailCount(paramQueryConditions);
  }
}
