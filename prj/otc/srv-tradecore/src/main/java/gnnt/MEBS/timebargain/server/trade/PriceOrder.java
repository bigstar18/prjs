package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.model.Order;
import java.util.LinkedList;
import java.util.List;

public class PriceOrder
{
  public double price;
  public List<Order> orderQueue;
  
  public PriceOrder(double price)
  {
    this.price = price;
    this.orderQueue = new LinkedList();
  }
  
  public long quantity = 0L;
  
  public void addOrder(Order order)
  {
    this.orderQueue.add(order);
    this.quantity += order.getQuantity().longValue();
  }
  
  public boolean equals(Object podr)
  {
    if (this.price == ((PriceOrder)podr).price) {
      return true;
    }
    return false;
  }
  
  public String toString()
  {
    return "\npriceOrder: price=" + this.price + ",quantity=" + this.quantity + ",orderQueue:" + this.orderQueue.toString() + "\n";
  }
}
