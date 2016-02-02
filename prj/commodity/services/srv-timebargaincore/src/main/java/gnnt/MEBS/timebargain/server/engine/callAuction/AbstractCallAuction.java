package gnnt.MEBS.timebargain.server.engine.callAuction;

import gnnt.MEBS.timebargain.server.engine.TradeMatcher;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCallAuction
  implements CallAuction
{
  public void refreshTradeMatchers(long paramLong, Double paramDouble, LinkedList<Order> paramLinkedList1, LinkedList<Order> paramLinkedList2, List<TradeMatcher> paramList)
  {
    Order localOrder1 = null;
    Order localOrder2 = null;
    long l1 = 0L;
    for (int i = 0; (i < paramLinkedList1.size()) && (l1 < paramLong); i++)
    {
      localOrder1 = (Order)paramLinkedList1.get(i);
      do
      {
        long l2 = 0L;
        localOrder2 = (Order)paramLinkedList2.getFirst();
        if ((localOrder2 != null) && (localOrder2.getPrice().doubleValue() <= localOrder1.getPrice().doubleValue())) {
          if (localOrder2.getRemainQty().longValue() <= localOrder1.getRemainQty().longValue())
          {
            paramLinkedList2.removeFirst();
            l2 = localOrder2.getRemainQty().longValue();
            ((TradeMatcher)paramList.get(0)).matchTrade(localOrder1, localOrder2, paramDouble.doubleValue(), l2, null);
          }
          else
          {
            l2 = localOrder1.getRemainQty().longValue();
            ((TradeMatcher)paramList.get(0)).matchTrade(localOrder1, localOrder2, paramDouble.doubleValue(), l2, null);
          }
        }
        l1 += l2;
      } while ((localOrder1.getRemainQty().longValue() > 0L) && (l1 < paramLong));
    }
  }
}
