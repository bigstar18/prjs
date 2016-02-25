package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

public class TestRightMenu
{
  public static void main(String[] paramArrayOfString)
  {
    RightService localRightService = (RightService)SysDataTest.getBean("rightService");
    Right localRight1 = localRightService.getRightByFilter(0L);
    System.out.println(localRight1.getName());
    Set localSet1 = localRight1.getRightSet();
    Iterator localIterator1 = localSet1.iterator();
    while (localIterator1.hasNext())
    {
      Right localRight2 = (Right)localIterator1.next();
      System.out.println(localRight2.getName());
      Set localSet2 = localRight2.getRightSet();
      Iterator localIterator2 = localSet2.iterator();
      while (localIterator2.hasNext())
      {
        Right localRight3 = (Right)localIterator2.next();
        System.out.println(localRight3.getName());
        Set localSet3 = localRight3.getRightSet();
        Iterator localIterator3 = localSet3.iterator();
        while (localIterator3.hasNext())
        {
          Right localRight4 = (Right)localIterator3.next();
          System.out.println(localRight4.getName());
          System.out.println(localRight4.getUrl());
          Set localSet4 = localRight4.getRightSet();
          System.out.println(localSet4.size());
        }
      }
    }
  }
}
