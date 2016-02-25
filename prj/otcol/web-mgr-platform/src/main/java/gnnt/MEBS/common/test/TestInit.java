package gnnt.MEBS.common.test;

import java.io.PrintStream;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("commonBeansTest.xml");
    System.out.println("finish");
  }
}
