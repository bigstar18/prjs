package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

public class TestSelectRightLazy
{
  public static void main(String[] paramArrayOfString)
  {
    RightService localRightService = (RightService)SysDataTest.getBean("rightService");
    Right localRight1 = localRightService.loadRightById(0L);
    System.out.println(localRight1.getName());
    Set localSet1 = localRight1.getRightSet();
    System.out.println(localSet1 == null);
    if (localSet1 != null)
    {
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
        }
      }
    }
  }
}
