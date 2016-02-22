package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.HoldPosition;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Trade;
import java.util.Date;
import java.util.List;

public abstract interface TradeDAO
  extends DAO
{
  public abstract List<Order> getNotTradeOrders();
  
  public abstract List getWithdrawOrderList(Date paramDate);
  
  public abstract long openOrder(Order paramOrder);
  
  public abstract long closeOrder(Order paramOrder);
  
  public abstract int consignerLogon(Consigner paramConsigner);
  
  public abstract Long openTrade(Trade paramTrade);
  
  public abstract long closeMemberOrder(Order paramOrder, double paramDouble);
  
  public abstract int closeTrade(Trade paramTrade);
  
  public abstract int withdraw(Order paramOrder);
  
  public abstract int initTrade(Date paramDate);
  
  public abstract int reComputeFunds(int paramInt);
  
  public abstract void updateOrderTradePrice(Long paramLong, Double paramDouble);
  
  public abstract int clear_Do();
  
  public abstract void clearBalancePrice();
  
  public abstract List<HoldPosition> getHoldPosition(Long paramLong);
  
  public abstract List<Long> getOrderNOByPL(Long paramLong, int paramInt);
}
