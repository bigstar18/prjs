package gnnt.MEBS.util.spring.rmi.test;

import java.util.ArrayList;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server
{
  public static void main(String[] args)
  {
    ArrayList list = new ArrayList();
    list.add("rmi_server.xml");

    new ClassPathXmlApplicationContext(
      (String[])list
      .toArray(new String[list.size()]));
  }
}