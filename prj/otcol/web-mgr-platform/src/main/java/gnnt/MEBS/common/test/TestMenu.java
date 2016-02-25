package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.services.MenuService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

public class TestMenu
{
  public static void main(String[] paramArrayOfString)
  {
    MenuService localMenuService = (MenuService)SysDataTest.getBean("menuService");
    List localList = localMenuService.getMenuByFilter(-1, 0, 0);
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Menu localMenu = (Menu)localIterator.next();
        System.out.println("m.getName():" + localMenu.getName());
        System.out.println("m.getUrl()：" + localMenu.getUrl());
      }
    }
  }
}
