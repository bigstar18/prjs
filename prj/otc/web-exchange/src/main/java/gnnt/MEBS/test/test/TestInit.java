package gnnt.MEBS.test.test;

import gnnt.MEBS.test.dao.DelayfeeTestDao;
import java.io.PrintStream;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] args)
  {
    try
    {
      String[] path = { "classpath:test_JDBC.xml", "classpath:config_default.xml" };
      ApplicationContext factory = new ClassPathXmlApplicationContext(path);
      DelayfeeTestDao delayfeeTestDao = (DelayfeeTestDao)factory.getBean("delayfeeTestDao");
      List entytiList = delayfeeTestDao.getEntityList(null, null);
      for (Object object : entytiList) {
        System.out.println(object);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
