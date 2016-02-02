package gnnt.MEBS.timebargain.server.engine.callAuction.impl;

import gnnt.MEBS.timebargain.server.engine.TradeMatcher;
import gnnt.MEBS.timebargain.server.engine.callAuction.AbstractCallAuction;
import gnnt.MEBS.timebargain.server.engine.callAuction.vo.CallAuctionDataBean;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CallAuctionMaxQtyImpl
  extends AbstractCallAuction
{
  final transient Log logger = LogFactory.getLog(getClass());
  
  public CallAuctionDataBean aggregatePricingByCommodity(List<Order> paramList, List<TradeMatcher> paramList1, double paramDouble, String paramString)
  {
    CallAuctionDataBean localCallAuctionDataBean = new CallAuctionDataBean();
    localCallAuctionDataBean.setCommodityId(paramString);
    LinkedList localLinkedList1 = new LinkedList();
    LinkedList localLinkedList2 = new LinkedList();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashSet localHashSet = new HashSet();
    for (int i = 0; i < paramList.size(); i++)
    {
      Order localOrder = (Order)paramList.get(i);
      if (localOrder.getPrice().doubleValue() != 0.0D)
      {
        localHashSet.add(localOrder.getPrice());
        int j;
        int k;
        int m;
        Long localLong;
        if (localOrder.getBuyOrSell().shortValue() == 1)
        {
          if (localLinkedList1.size() == 0)
          {
            localLinkedList1.add(localOrder);
          }
          else
          {
            j = 0;
            k = localLinkedList1.size();
            for (m = 0; m < k; m++)
            {
              if (localOrder.getPrice().doubleValue() > ((Order)localLinkedList1.get(m)).getPrice().doubleValue())
              {
                localLinkedList1.add(m, localOrder);
                j = 1;
              }
              else if ((localOrder.getOrderType().shortValue() == 2) && (localOrder.getCloseFlag().shortValue() == 2) && (localOrder.getPrice().doubleValue() == ((Order)localLinkedList1.get(m)).getPrice().doubleValue()))
              {
                localLinkedList1.add(m, localOrder);
                j = 1;
              }
              if (j != 0) {
                break;
              }
            }
            if (j == 0) {
              localLinkedList1.addLast(localOrder);
            }
          }
          localLong = (Long)localHashMap1.get(localOrder.getPrice());
          if (localLong == null) {
            localHashMap1.put(localOrder.getPrice(), localOrder.getQuantity());
          } else {
            localHashMap1.put(localOrder.getPrice(), Long.valueOf(localLong.longValue() + localOrder.getQuantity().longValue()));
          }
        }
        else
        {
          if (localLinkedList2.size() == 0)
          {
            localLinkedList2.add(localOrder);
          }
          else
          {
            j = 0;
            k = localLinkedList2.size();
            for (m = 0; m < k; m++)
            {
              if (localOrder.getPrice().doubleValue() < ((Order)localLinkedList2.get(m)).getPrice().doubleValue())
              {
                localLinkedList2.add(m, localOrder);
                j = 1;
              }
              else if ((localOrder.getOrderType().shortValue() == 2) && (localOrder.getCloseFlag().shortValue() == 2) && (localOrder.getPrice().doubleValue() == ((Order)localLinkedList2.get(m)).getPrice().doubleValue()))
              {
                localLinkedList2.add(m, localOrder);
                j = 1;
              }
              if (j != 0) {
                break;
              }
            }
            if (j == 0) {
              localLinkedList2.addLast(localOrder);
            }
          }
          localLong = (Long)localHashMap2.get(localOrder.getPrice());
          if (localLong == null) {
            localHashMap2.put(localOrder.getPrice(), localOrder.getQuantity());
          } else {
            localHashMap2.put(localOrder.getPrice(), Long.valueOf(localLong.longValue() + localOrder.getQuantity().longValue()));
          }
        }
      }
    }
    this.logger.debug("All Price:" + localHashSet);
    Iterator localIterator1 = localHashSet.iterator();
    long l1 = 0L;
    Object localObject = new Double(0.0D);
    while (localIterator1.hasNext())
    {
      long l3 = 0L;
      long l4 = 0L;
      Double localDouble1 = (Double)localIterator1.next();
      Iterator localIterator2 = localHashMap1.keySet().iterator();
      Double localDouble2;
      while (localIterator2.hasNext())
      {
        localDouble2 = (Double)localIterator2.next();
        if (localDouble2.doubleValue() >= localDouble1.doubleValue()) {
          l3 += ((Long)localHashMap1.get(localDouble2)).intValue();
        }
      }
      this.logger.debug("Price:" + localDouble1 + " buy may trade:" + l3);
      localIterator2 = localHashMap2.keySet().iterator();
      while (localIterator2.hasNext())
      {
        localDouble2 = (Double)localIterator2.next();
        if (localDouble2.doubleValue() <= localDouble1.doubleValue()) {
          l4 += ((Long)localHashMap2.get(localDouble2)).intValue();
        }
      }
      this.logger.debug("Price:" + localDouble1 + " sell may trade:" + l4);
      long l2 = l3 <= l4 ? l3 : l4;
      if (l2 > l1)
      {
        l1 = l2;
        localObject = localDouble1;
      }
    }
    this.logger.debug("Right Price:" + localObject + " maxQty:" + l1);
    if (l1 > 0L) {
      refreshTradeMatchers(l1, (Double)localObject, localLinkedList1, localLinkedList2, paramList1);
    }
    localCallAuctionDataBean.setQuantity(l1);
    return localCallAuctionDataBean;
  }
}
