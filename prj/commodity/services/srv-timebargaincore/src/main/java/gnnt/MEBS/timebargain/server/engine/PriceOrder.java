package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Order;
import java.util.LinkedList;
import java.util.List;

public class PriceOrder
{
  public double price;
  public List<Order> orderQueue;
  public long quantity = 0L;
  boolean isSpreadLmt = false;
  
  PriceOrder(double paramDouble)
  {
    this.price = paramDouble;
    this.orderQueue = new LinkedList();
  }
  
  void addOrder(Order paramOrder)
  {
    if (((paramOrder.getOrderType().shortValue() == 2) && (paramOrder.getCloseFlag().shortValue() == 2)) || ((paramOrder.getOrderType().shortValue() == 2) && (this.isSpreadLmt)))
    {
      Order localOrder = null;
      for (int i = 0; i < this.orderQueue.size(); i++)
      {
        localOrder = (Order)this.orderQueue.get(i);
        if ((localOrder.getOrderType().shortValue() != 2) || (localOrder.getCloseFlag().shortValue() != 2)) {
          if ((localOrder.getOrderType().shortValue() == 2) && (this.isSpreadLmt))
          {
            if ((paramOrder.getOrderType().shortValue() == 2) && (paramOrder.getCloseFlag().shortValue() == 2))
            {
              this.orderQueue.add(i, paramOrder);
              this.quantity += paramOrder.getRemainQty().longValue();
            }
          }
          else
          {
            this.orderQueue.add(i, paramOrder);
            this.quantity += paramOrder.getRemainQty().longValue();
            return;
          }
        }
      }
    }
    this.orderQueue.add(paramOrder);
    this.quantity += paramOrder.getRemainQty().longValue();
  }
  
  public boolean equals(Object paramObject)
  {
    return this.price == ((PriceOrder)paramObject).price;
  }
  
  public String toString()
  {
    return "\npriceOrder: price=" + this.price + ",quantity=" + this.quantity + ",orderQueue:" + this.orderQueue.toString() + "\n";
  }
  
  public PriceOrder clone()
  {
    PriceOrder localPriceOrder = new PriceOrder(this.price);
    localPriceOrder.orderQueue.addAll(this.orderQueue);
    return localPriceOrder;
  }
}
