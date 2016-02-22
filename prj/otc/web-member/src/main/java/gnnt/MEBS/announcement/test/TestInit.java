package gnnt.MEBS.announcement.test;

import gnnt.MEBS.announcement.dao.TradeDao;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import java.io.PrintStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInit
{
  public static void main(String[] args)
    throws InterruptedException
  {
    String[] beanConfig = { "config_JDBC.xml", "/announcement_default.xml", "/account_default.xml", "/query_JNDI.xml", "/broke_default.xml", "/commonBeansAchieve.xml", "/globalLog_default.xml" };
    ApplicationContext factory = new ClassPathXmlApplicationContext(beanConfig);
    PageInfo pageInfo = new PageInfo();
    pageInfo.setTotalRecords(-1);
    pageInfo.setPageNo(100000);
    pageInfo.addOrderField("tradeNo", true);
    TradeDao tradeDao = (TradeDao)factory.getBean("tradeDao");
    System.out.println(1111111);
    tradeDao.getList(null, pageInfo);
    for (int i = 0; i < 12; i++)
    {
      System.out.println(i);
      tradeDao.getList(null, pageInfo);
    }
    Thread thread = new Thread();
    Thread.sleep(5000L);
    tradeDao.getList(null, pageInfo);
  }
}
