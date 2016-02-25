package gnnt.MEBS.member.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("brokerBeansTest.xml");
  }
}
