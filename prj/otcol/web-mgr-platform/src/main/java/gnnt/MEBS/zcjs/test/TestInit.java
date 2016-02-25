package gnnt.MEBS.zcjs.test;

import gnnt.MEBS.zcjs.util.SysDataTest;
import java.io.PrintStream;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:zcjsBeansTest.xml");
    String str = SysDataTest.getConfig("quantityDecimalsString");
    System.out.println("finish");
  }
}
