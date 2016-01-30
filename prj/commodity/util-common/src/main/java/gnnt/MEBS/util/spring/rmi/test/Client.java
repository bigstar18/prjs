package gnnt.MEBS.util.spring.rmi.test;

import java.io.PrintStream;
import java.util.ArrayList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client
{
  public static void main(String[] args)
  {
    ArrayList list = new ArrayList();
    list.add("rmi_client.xml");

    ApplicationContext content = new ClassPathXmlApplicationContext(
      (String[])list
      .toArray(new String[list.size()]));

    ITest test = (ITest)content.getBean("test");

    System.out.println(test.test());
  }
}