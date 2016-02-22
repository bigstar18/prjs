package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.service.RightService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

public class TestSelectShowRight
{
  public static void main(String[] args)
  {
    RightService rightService = (RightService)SysDataTest.getBean("rightService");
    Right right = rightService.getRightByFilter(0L);
    System.out.println(right.getName());
    Set<Right> rightSet = right.getRightSet();
    Iterator localIterator2;
    for (Iterator localIterator1 = rightSet.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Right r = (Right)localIterator1.next();
      
      System.out.println(r.getName());
      Set<Right> rightChildSet = r.getRightSet();
      localIterator2 = rightChildSet.iterator(); continue;Right r1 = (Right)localIterator2.next();
      
      System.out.println(r1.getName());
      Set<Right> grandchildSet = r1.getRightSet();
      for (Right r2 : grandchildSet)
      {
        System.out.println(r2.getName());
        Set<Right> greatGrandsonSet = r2.getRightSet();
        
        System.out.println(greatGrandsonSet.size());
      }
    }
  }
}
