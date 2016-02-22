package gnnt.MEBS.common.test;

import java.io.PrintStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] args)
  {
    ApplicationContext factory = new ClassPathXmlApplicationContext(
      "commonBeansTest.xml");
    System.out.println("finish");
  }
}
