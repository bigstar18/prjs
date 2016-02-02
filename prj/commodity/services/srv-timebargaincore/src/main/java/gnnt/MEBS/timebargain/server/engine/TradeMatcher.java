package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Order;

public abstract interface TradeMatcher
{
  public abstract void init(TradeEngine paramTradeEngine, String paramString);
  
  public abstract void start();
  
  public abstract void pleaseStop();
  
  public abstract void matchOrder(Order paramOrder);
  
  public abstract void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort);
  
  public abstract void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort, int paramInt);
}
