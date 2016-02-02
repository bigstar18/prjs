package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Trade;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import org.apache.commons.logging.Log;

public class TradeMatcherImpl
  extends AbstractTradeMatcher
{
  private long sWithdrawSleepTime;
  private long diffWithdrawSleepTime;
  
  public void run()
  {
    Quotation localQuotation = (Quotation)this.te.quotations.get(this.commodityCode);
    if (localQuotation.curPrice.doubleValue() == 0.0D) {
      this.preTradePrice = localQuotation.closePrice.doubleValue();
    } else {
      this.preTradePrice = localQuotation.curPrice.doubleValue();
    }
    Order localOrder = null;
    this.logger.info("*** Trade Matcher started.CmdtyCode:" + this.commodityCode);
    try
    {
      while (!this.stop)
      {
        if (this.te.getStatus() == 0)
        {
          this.commodityOrder.isMatching = true;
          while (!this.orders.isEmpty())
          {
            this.sWithdrawSleepTime = System.currentTimeMillis();
            while (this.commodityOrder.getWithdrawCnt() > 0)
            {
              try
              {
                sleep(1L);
              }
              catch (Exception localException1)
              {
                this.logger.error("Trade Matche sleep(1) Error!", localException1);
              }
              this.diffWithdrawSleepTime = (System.currentTimeMillis() - this.sWithdrawSleepTime);
              if (this.diffWithdrawSleepTime > 3000L)
              {
                this.commodityOrder.setWithdrawCnt(0);
                this.logger.info("撮合时等待撤单释放锁3秒超时，计数器清0");
              }
            }
            synchronized (this.commodityOrder)
            {
              try
              {
                if (!this.orders.isEmpty())
                {
                  localOrder = (Order)this.orders.remove(0);
                  this.logger.debug("-------match:" + localOrder.getOrderNo() + " cmdty:" + localOrder.getCommodityID() + " qty:" + localOrder.getRemainQty());
                  if (localOrder.getRemainQty().longValue() > 0L) {
                    matchOrder(localOrder);
                  }
                }
              }
              catch (Exception localException4)
              {
                this.logger.error("Trade Matche Error!", localException4);
              }
            }
          }
          this.commodityOrder.isMatching = false;
        }
        try
        {
          sleep(this.te.getMatchInterval());
        }
        catch (Exception localException2) {}
      }
    }
    catch (Exception localException3)
    {
      this.logger.error("***** TradeMatcher thread down ! *****", localException3);
    }
  }
  
  public void matchOrder(Order paramOrder)
  {
    if ((paramOrder.getOrderType().shortValue() == 2) && (2 == paramOrder.getCloseFlag().shortValue()) && (paramOrder.getPrice().intValue() == 0)) {
      if (paramOrder.getBuyOrSell().shortValue() == 1) {
        paramOrder.setPrice(Double.valueOf(this.commodityOrder.spreadUpLmt));
      } else {
        paramOrder.setPrice(Double.valueOf(this.commodityOrder.spreadDownLmt));
      }
    }
    int i = 100;
    PriceOrder localPriceOrder;
    Order localOrder;
    long l;
    Quotation localQuotation;
    if (paramOrder.getBuyOrSell().shortValue() == 1)
    {
      localPriceOrder = null;
      localOrder = null;
      l = 0L;
      do
      {
        if (this.commodityOrder.sellQueue.size() > 0) {
          localPriceOrder = (PriceOrder)this.commodityOrder.sellQueue.first();
        } else {
          localPriceOrder = null;
        }
        if ((localPriceOrder != null) && (localPriceOrder.price <= paramOrder.getPrice().doubleValue()))
        {
          do
          {
            try
            {
              localOrder = (Order)localPriceOrder.orderQueue.get(0);
            }
            catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
            {
              localOrder = null;
            }
            if (localOrder != null)
            {
              if (localOrder.getRemainQty().longValue() <= paramOrder.getRemainQty().longValue())
              {
                localPriceOrder.orderQueue.remove(0);
                l = localOrder.getRemainQty().longValue();
              }
              else
              {
                l = paramOrder.getRemainQty().longValue();
              }
              localPriceOrder.quantity -= l;
              if (localPriceOrder.quantity == 0L) {
                this.commodityOrder.sellQueue.remove(localPriceOrder);
              }
              matchTrade(paramOrder, localOrder, localOrder.getPrice().doubleValue(), l, Short.valueOf((short)2), this.tradePriceType);
            }
            if (paramOrder.getRemainQty().longValue() <= 0L) {
              break;
            }
          } while (localPriceOrder.quantity > 0L);
        }
        else
        {
          this.commodityOrder.enqueueOrder(paramOrder);
          i = this.commodityOrder.locatePrice(paramOrder);
          if (i <= 5)
          {
            String str1 = paramOrder.getCommodityID();
            localQuotation = (Quotation)this.te.quotations.get(str1);
            localQuotation.updateTop5(this.commodityOrder);
            this.te.quotationsClone.put(str1, (Quotation)localQuotation.clone());
          }
          this.logger.debug("position:" + i + " updateTime:" + ((Quotation)this.te.quotations.get(paramOrder.getCommodityID())).updateTime);
          return;
        }
      } while (paramOrder.getRemainQty().longValue() > 0L);
    }
    else
    {
      localPriceOrder = null;
      localOrder = null;
      l = 0L;
      do
      {
        if (this.commodityOrder.buyQueue.size() > 0) {
          localPriceOrder = (PriceOrder)this.commodityOrder.buyQueue.first();
        } else {
          localPriceOrder = null;
        }
        if ((localPriceOrder != null) && (localPriceOrder.price >= paramOrder.getPrice().doubleValue()))
        {
          do
          {
            try
            {
              localOrder = (Order)localPriceOrder.orderQueue.get(0);
            }
            catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
            {
              localOrder = null;
            }
            if (localOrder != null)
            {
              if (localOrder.getRemainQty().longValue() <= paramOrder.getRemainQty().longValue())
              {
                localPriceOrder.orderQueue.remove(0);
                l = localOrder.getRemainQty().longValue();
              }
              else
              {
                l = paramOrder.getRemainQty().longValue();
              }
              localPriceOrder.quantity -= l;
              if (localPriceOrder.quantity == 0L) {
                this.commodityOrder.buyQueue.remove(localPriceOrder);
              }
              matchTrade(localOrder, paramOrder, localOrder.getPrice().doubleValue(), l, Short.valueOf((short)1), this.tradePriceType);
            }
            if (paramOrder.getRemainQty().longValue() <= 0L) {
              break;
            }
          } while (localPriceOrder.quantity > 0L);
        }
        else
        {
          this.commodityOrder.enqueueOrder(paramOrder);
          i = this.commodityOrder.locatePrice(paramOrder);
          if (i <= 5)
          {
            String str2 = paramOrder.getCommodityID();
            localQuotation = (Quotation)this.te.quotations.get(str2);
            localQuotation.updateTop5(this.commodityOrder);
            this.te.quotationsClone.put(str2, (Quotation)localQuotation.clone());
          }
          this.logger.debug("position:" + i + " updateTime:" + ((Quotation)this.te.quotations.get(paramOrder.getCommodityID())).updateTime);
          return;
        }
      } while (paramOrder.getRemainQty().longValue() > 0L);
    }
  }
  
  public void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort)
  {
    matchTrade(paramOrder1, paramOrder2, paramDouble, paramLong, paramShort, 1);
  }
  
  public void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort, int paramInt)
  {
    if (paramInt == 2)
    {
      paramDouble = getMiddleTradePrice(paramOrder1.getPrice().doubleValue(), paramOrder2.getPrice().doubleValue(), this.preTradePrice);
      this.preTradePrice = paramDouble;
    }
    paramOrder1.setTradeQty(Long.valueOf(paramOrder1.getTradeQty().longValue() + paramLong));
    paramOrder2.setTradeQty(Long.valueOf(paramOrder2.getTradeQty().longValue() + paramLong));
    int i = 1;
    if (this.quotationTwoSide) {
      i = 2;
    }
    String str = paramOrder1.getCommodityID();
    this.logger.debug("match trade:" + paramOrder1.getOrderNo() + " " + paramOrder2.getOrderNo() + " " + paramDouble);
    Trade localTrade1 = new Trade();
    localTrade1.setM_TradeNo(Long.valueOf(this.te.getNextTradeNo()));
    localTrade1.setOrderNo(paramOrder1.getOrderNo());
    localTrade1.setPrice(Double.valueOf(paramDouble));
    localTrade1.setQuantity(Long.valueOf(paramLong));
    Trade localTrade2 = new Trade();
    localTrade2.setM_TradeNo(Long.valueOf(this.te.getNextTradeNo()));
    localTrade2.setOrderNo(paramOrder2.getOrderNo());
    localTrade2.setPrice(Double.valueOf(paramDouble));
    localTrade2.setQuantity(Long.valueOf(paramLong));
    localTrade1.setM_TradeNo_Opp(localTrade2.getM_TradeNo());
    localTrade2.setM_TradeNo_Opp(localTrade1.getM_TradeNo());
    if (paramOrder1.getFirmID().compareTo(paramOrder2.getFirmID()) <= 0) {
      this.tradeback.callback(localTrade1, localTrade2);
    } else {
      this.tradeback.callback(localTrade2, localTrade1);
    }
    Quotation localQuotation = (Quotation)this.te.quotations.get(str);
    if (localQuotation.openPrice.doubleValue() == 0.0D)
    {
      localQuotation.openPrice = Double.valueOf(paramDouble);
      localQuotation.highPrice = Double.valueOf(paramDouble);
      localQuotation.lowPrice = Double.valueOf(paramDouble);
    }
    if (paramDouble > localQuotation.highPrice.doubleValue()) {
      localQuotation.highPrice = Double.valueOf(paramDouble);
    }
    if (paramDouble < localQuotation.lowPrice.doubleValue()) {
      localQuotation.lowPrice = Double.valueOf(paramDouble);
    }
    localQuotation.curPrice = Double.valueOf(paramDouble);
    localQuotation.curAmount = Long.valueOf(paramLong * i);
    if (paramOrder1.getOrderType().shortValue() == 1)
    {
      localQuotation.openAmount = Long.valueOf(localQuotation.openAmount.longValue() + paramLong);
      localQuotation.buyOpenAmount = Long.valueOf(localQuotation.buyOpenAmount.longValue() + paramLong);
    }
    else if (paramOrder1.getOrderType().shortValue() == 2)
    {
      localQuotation.closeAmount = Long.valueOf(localQuotation.closeAmount.longValue() + paramLong);
      localQuotation.buyCloseAmount = Long.valueOf(localQuotation.buyCloseAmount.longValue() + paramLong);
    }
    if (paramOrder2.getOrderType().shortValue() == 1)
    {
      localQuotation.openAmount = Long.valueOf(localQuotation.openAmount.longValue() + paramLong);
      localQuotation.sellOpenAmount = Long.valueOf(localQuotation.sellOpenAmount.longValue() + paramLong);
    }
    else if (paramOrder2.getOrderType().shortValue() == 2)
    {
      localQuotation.closeAmount = Long.valueOf(localQuotation.closeAmount.longValue() + paramLong);
      localQuotation.sellCloseAmount = Long.valueOf(localQuotation.sellCloseAmount.longValue() + paramLong);
    }
    if ((paramOrder1.getOrderType().shortValue() == 1) && (paramOrder2.getOrderType().shortValue() == 1))
    {
      localQuotation.reserveCount = Long.valueOf(localQuotation.reserveCount.longValue() + paramLong * i);
      localQuotation.reserveChange = Long.valueOf(localQuotation.reserveChange.longValue() + paramLong * i);
    }
    else if ((paramOrder1.getOrderType().shortValue() == 2) && (paramOrder2.getOrderType().shortValue() == 2))
    {
      localQuotation.reserveCount = Long.valueOf(localQuotation.reserveCount.longValue() - paramLong * i);
      localQuotation.reserveChange = Long.valueOf(localQuotation.reserveChange.longValue() - paramLong * i);
    }
    localQuotation.totalMoney = Double.valueOf(localQuotation.totalMoney.doubleValue() + paramDouble * paramLong * i * ((CommodityOrder)this.te.commodityOrders.get(str)).factor);
    localQuotation.totalAmount = Long.valueOf(localQuotation.totalAmount.longValue() + paramLong * i);
    this.logger.debug("Quotation:totalMoney:" + localQuotation.totalMoney + " totalAmount:" + localQuotation.totalAmount + " fac:" + ((CommodityOrder)this.te.commodityOrders.get(str)).factor);
    double d = localQuotation.totalMoney.doubleValue() / (localQuotation.totalAmount.longValue() * ((CommodityOrder)this.te.commodityOrders.get(str)).factor);
    d = Math.round(d / ((CommodityOrder)this.te.commodityOrders.get(str)).minPriceMove) * ((CommodityOrder)this.te.commodityOrders.get(str)).minPriceMove;
    localQuotation.spread = Double.valueOf(localQuotation.curPrice.doubleValue() - localQuotation.yesterBalancePrice.doubleValue());
    localQuotation.price = Double.valueOf(d);
    if (paramShort == null)
    {
      localQuotation.inAmount = Long.valueOf(localQuotation.inAmount.longValue() + paramLong);
      localQuotation.outAmount = Long.valueOf(localQuotation.outAmount.longValue() + paramLong);
    }
    else if (paramShort.shortValue() == 1)
    {
      localQuotation.inAmount = Long.valueOf(localQuotation.inAmount.longValue() + paramLong * i);
    }
    else if (paramShort.shortValue() == 2)
    {
      localQuotation.outAmount = Long.valueOf(localQuotation.outAmount.longValue() + paramLong * i);
    }
    localQuotation.updateTop5(this.commodityOrder);
    this.te.quotationsClone.put(str, (Quotation)localQuotation.clone());
  }
}
