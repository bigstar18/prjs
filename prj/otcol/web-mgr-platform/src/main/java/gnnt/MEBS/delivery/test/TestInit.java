package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.services.MenuService;
import java.io.PrintStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("wareHouseBeansTest.xml");
    MenuService localMenuService = (MenuService)localClassPathXmlApplicationContext.getBean("w_menuService");
    System.out.println("执行完毕");
  }
}
