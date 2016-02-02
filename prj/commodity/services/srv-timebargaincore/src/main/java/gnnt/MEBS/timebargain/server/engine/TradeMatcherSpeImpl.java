package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerShell;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.SysLog;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import org.apache.commons.logging.Log;

public class TradeMatcherSpeImpl
  extends TradeMatcherImpl
{
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
            if (localOrder != null) {
              if ((paramOrder.getSpecialOrderFlag().shortValue() != localOrder.getSpecialOrderFlag().shortValue()) && (!paramOrder.getCustomerID().equalsIgnoreCase(localOrder.getCustomerID())))
              {
                if (paramOrder.getSpecialOrderFlag().shortValue() == 1)
                {
                  withDrawSpecialOrder(localPriceOrder, paramOrder, 0);
                  return;
                }
                withDrawSpecialOrder(localPriceOrder, localOrder, 1);
              }
              else
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
          if (i <= 5) {
            ((Quotation)this.te.quotations.get(paramOrder.getCommodityID())).updateTop5Synchronized(this.commodityOrder);
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
            if (localOrder != null) {
              if ((paramOrder.getSpecialOrderFlag().shortValue() != localOrder.getSpecialOrderFlag().shortValue()) && (!paramOrder.getCustomerID().equalsIgnoreCase(localOrder.getCustomerID())))
              {
                if (paramOrder.getSpecialOrderFlag().shortValue() == 1)
                {
                  withDrawSpecialOrder(localPriceOrder, paramOrder, 0);
                  return;
                }
                withDrawSpecialOrder(localPriceOrder, localOrder, 1);
              }
              else
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
          if (i <= 5) {
            ((Quotation)this.te.quotations.get(paramOrder.getCommodityID())).updateTop5Synchronized(this.commodityOrder);
          }
          this.logger.debug("position:" + i + " updateTime:" + ((Quotation)this.te.quotations.get(paramOrder.getCommodityID())).updateTime);
          return;
        }
      } while (paramOrder.getRemainQty().longValue() > 0L);
    }
  }
  
  void withDrawSpecialOrder(PriceOrder paramPriceOrder, Order paramOrder, int paramInt)
  {
    if (paramInt == 0)
    {
      OrderTrace.getInstance().writeCancelOrder(paramOrder.getOrderNo());
      Order localOrder1 = new Order();
      localOrder1.setWithdrawerID(null);
      localOrder1.setWithdrawID(paramOrder.getOrderNo());
      localOrder1.setWithdrawType(new Short((short)10));
      localOrder1.setQuantity(paramOrder.getRemainQty());
      int j = Server.getInstance().getTradeDAO().withdraw(localOrder1);
      if (j == -100)
      {
        this.logger.info("自动撤委托号为" + paramOrder.getOrderNo() + "的单时执行撤单存储失败！");
        Server.getInstance().getServerDAO().insertSysLog(new SysLog("自动撤单失败", 1502, 0));
      }
    }
    else
    {
      paramPriceOrder.orderQueue.remove(0);
      paramPriceOrder.quantity -= paramOrder.getRemainQty().longValue();
      if (paramPriceOrder.quantity == 0L) {
        if (paramOrder.getBuyOrSell().shortValue() == 1) {
          this.commodityOrder.buyQueue.remove(paramPriceOrder);
        } else if (paramOrder.getBuyOrSell().shortValue() == 2) {
          this.commodityOrder.sellQueue.remove(paramPriceOrder);
        }
      }
      int i = this.commodityOrder.locatePrice(paramOrder);
      if ((i <= 5) && (!ServerShell.RunModeIsSIM)) {
        ((Quotation)this.te.getQuotations().get(paramOrder.getCommodityID())).updateTop5Synchronized(this.commodityOrder);
      }
      this.logger.debug("position:" + i + " updateTime:" + ((Quotation)this.te.getQuotations().get(paramOrder.getCommodityID())).updateTime);
      this.logger.debug("Cancel Order No:" + paramOrder.getOrderNo() + " canceled quantity:" + paramOrder.getRemainQty());
      OrderTrace.getInstance().writeCancelOrder(paramOrder.getOrderNo());
      Order localOrder2 = new Order();
      localOrder2.setWithdrawerID(null);
      localOrder2.setWithdrawID(paramOrder.getOrderNo());
      localOrder2.setWithdrawType(new Short((short)10));
      localOrder2.setQuantity(paramOrder.getRemainQty());
      int k = Server.getInstance().getTradeDAO().withdraw(localOrder2);
      if (k == -100)
      {
        this.logger.info("自动撤委托号为" + paramOrder.getOrderNo() + "的单时执行撤单存储失败！");
        Server.getInstance().getServerDAO().insertSysLog(new SysLog("自动撤单失败", 1502, 0));
      }
    }
  }
}
