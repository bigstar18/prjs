package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.MarginAdjust;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Trade;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public abstract interface TradeDAO
  extends DAO
{
  public abstract List getNotTradeOrders();
  
  public abstract long openOrder(Order paramOrder);
  
  public abstract long closeOrder(Order paramOrder);
  
  public abstract int consignerLogon(Consigner paramConsigner);
  
  public abstract Timestamp floatingComputer(String paramString1, String paramString2, Timestamp paramTimestamp);
  
  public abstract void updateExitCommodity();
  
  public abstract int updateTrade(Trade paramTrade1, Trade paramTrade2);
  
  public abstract int withdraw(Order paramOrder);
  
  public abstract int updateQuotation(Quotation paramQuotation);
  
  public abstract long getCustomerHoldSumFrozenQty();
  
  public abstract int balance();
  
  public abstract int settleProcess(String paramString);
  
  public abstract int initTrade(Date paramDate);
  
  public abstract int reComputeFunds(int paramInt);
  
  public abstract long getMaxMatcherTradeNo();
  
  public abstract List getCommodityMarginAdjustList();
  
  public abstract void adjustCommodityMargin(MarginAdjust paramMarginAdjust);
  
  public abstract List getFirmMarginAdjustList();
  
  public abstract void adjustFirmMargin(MarginAdjust paramMarginAdjust);
  
  public abstract void deductCloseOrder(Order paramOrder);
  
  public abstract void updateCommodityFirmMaxHoldQty(String paramString, Long paramLong);
  
  public abstract long sellBillOrder(Order paramOrder);
  
  public abstract long gageCloseOrder(Order paramOrder);
}
