package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface StatQueryDAO
  extends DAO
{
  public abstract List getCustomerFundss(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int getCustomerFundssCount(QueryConditions paramQueryConditions);
  
  public abstract List getCustomerFundsTable(StatQuery paramStatQuery);
  
  public abstract int getOrderCount(QueryConditions paramQueryConditions);
  
  public abstract List getOrders(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int getTradesCount(QueryConditions paramQueryConditions);
  
  public abstract List getOrderSum(QueryConditions paramQueryConditions);
  
  public abstract List getOrderSums(QueryConditions paramQueryConditions);
  
  public abstract List getTrades(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract List getTradesSums(QueryConditions paramQueryConditions);
  
  public abstract List getTradesSum(QueryConditions paramQueryConditions);
  
  public abstract int getFirmHoldPositionsCount(QueryConditions paramQueryConditions);
  
  public abstract List getFirmHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract List getFirmHoldPositionsSum(QueryConditions paramQueryConditions);
  
  public abstract int getHoldPositionCount(QueryConditions paramQueryConditions);
  
  public abstract List getHoldPositions(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract List getQuotations(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int getQuotationCount(QueryConditions paramQueryConditions);
  
  public abstract int getSpecFrozenHoldsCount();
  
  public abstract List getSpecFrozenHolds(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int getHoldsCount(QueryConditions paramQueryConditions);
  
  public abstract List getHoldPositionsDetail(QueryConditions paramQueryConditions, int paramInt1, int paramInt2);
  
  public abstract int queryCommodityCount();
  
  public abstract int queryHisCommodityCount();
  
  public abstract List getCurCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2);
  
  public abstract List getHisCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2);
  
  public abstract String getSysdate1();
  
  public abstract List getBrokerHoldPositions(QueryConditions paramQueryConditions);
  
  public abstract List getBrokerFunds(QueryConditions paramQueryConditions);
  
  public abstract List getBrokerTrades(QueryConditions paramQueryConditions);
  
  public abstract List getFirmCategory();
}
