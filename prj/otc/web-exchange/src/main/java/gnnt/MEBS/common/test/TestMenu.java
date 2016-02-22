package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.service.MenuService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.List;

public class TestMenu
{
  public static void main(String[] args)
  {
    MenuService menuService = (MenuService)SysDataTest.getBean("menuService");
    List<Menu> list = menuService.getMenuByFilter(-1, 0, 0);
    if (list != null) {
      for (Menu m : list)
      {
        System.out.println("m.getName():" + m.getName());
        System.out.println("m.getUrl()：" + m.getUrl());
      }
    }
  }
}
