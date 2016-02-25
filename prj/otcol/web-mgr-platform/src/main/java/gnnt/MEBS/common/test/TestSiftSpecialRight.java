package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.util.SysDataTest;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

public class TestSiftSpecialRight
{
  public static void main(String[] paramArrayOfString)
  {
    RightService localRightService = (RightService)SysDataTest.getBean("rightService");
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("url", "notLike", "%*%");
    List localList = localRightService.getRightList(localQueryConditions, null);
    System.out.println(localList.size());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Right localRight = (Right)localIterator.next();
      System.out.println(localRight.getName());
      System.out.println(localRight.getUrl());
    }
  }
}
