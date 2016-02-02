package gnnt.MEBS.timebargain.plugin.condition.test;

import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.plugin.condition.model.Sort;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class TestCase
{
  public static void main(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    ConditionOrder localConditionOrder1 = new ConditionOrder();
    localConditionOrder1.setOrderNo(Long.valueOf(1L));
    localConditionOrder1.setConditionPrice(Double.valueOf(1800.0D));
    ConditionOrder localConditionOrder2 = new ConditionOrder();
    localConditionOrder2.setConditionPrice(Double.valueOf(1900.0D));
    localConditionOrder2.setOrderNo(Long.valueOf(2L));
    ConditionOrder localConditionOrder3 = new ConditionOrder();
    localConditionOrder3.setConditionPrice(Double.valueOf(1700.0D));
    localConditionOrder3.setOrderNo(Long.valueOf(3L));
    localArrayList.add(localConditionOrder1);
    localArrayList.add(localConditionOrder2);
    localArrayList.add(localConditionOrder3);
    Collections.sort(localArrayList, new Sort());
    System.out.println("first------>" + ((ConditionOrder)localArrayList.get(0)).getConditionPrice());
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      ConditionOrder localConditionOrder4 = (ConditionOrder)localIterator.next();
      if (localConditionOrder4.getOrderNo().longValue() == 1L)
      {
        System.out.println("remove-->" + localConditionOrder4.getOrderNo());
        localArrayList.remove(localConditionOrder4);
      }
    }
  }
}
