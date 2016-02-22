package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.timebargain.server.trade.PriceOrder;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang.StringEscapeUtils;

public class Test
{
  public static void main(String[] args)
  {
    System.out.println(MD5.getMD5("112003002975875", 
      "111111"));
    System.out.println(StringEscapeUtils.unescapeXml("-_!@#$%^&amp;*,.?"));
    


    SortedSet<PriceOrder> buyUpQueue = new TreeSet(
      descComparator);
    for (int i = 0; i < 10; i++)
    {
      PriceOrder priceOrder = new PriceOrder(i);
      buyUpQueue.add(priceOrder);
    }
    for (Iterator it = buyUpQueue.iterator(); it.hasNext();)
    {
      PriceOrder priceOrder = (PriceOrder)it.next();
      System.out.println(priceOrder.price);
    }
    List<PriceOrder> delList = new ArrayList();
    for (PriceOrder priceOrder : buyUpQueue) {
      if (priceOrder.price <= 5.0D) {
        delList.add(priceOrder);
      }
    }
    buyUpQueue.removeAll(delList);
    for (Iterator it = buyUpQueue.iterator(); it.hasNext();)
    {
      PriceOrder priceOrder = (PriceOrder)it.next();
      System.out.println(priceOrder.price);
    }
  }
  
  static Comparator<PriceOrder> descComparator = new Comparator()
  {
    public int compare(PriceOrder o1, PriceOrder o2)
    {
      if (o1.price == o2.price) {
        return 0;
      }
      if (o1.price < o2.price) {
        return -1;
      }
      return 1;
    }
  };
}
