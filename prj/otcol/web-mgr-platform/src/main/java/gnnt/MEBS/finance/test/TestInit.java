package gnnt.MEBS.finance.test;

import java.io.PrintStream;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("financeBeansTest.xml");
    System.out.println("执行完毕");
  }
}
