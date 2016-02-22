package gnnt.MEBS.commodity.test;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Margin;
import gnnt.MEBS.commodity.service.MarginService;
import java.io.PrintStream;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJdbc
{
  public static void main(String[] args)
  {
    ApplicationContext factory = new ClassPathXmlApplicationContext(
      "commodity_JDBC.xml");
    MarginService marginService = (MarginService)factory.getBean("marginService");
    QueryConditions qc = new QueryConditions();
    qc.addCondition("margin.firmId", "=", "Def_Customer");
    qc.addCondition("margin.commodityId", "=", "XAUGSD");
    try
    {
      List<Margin> list = marginService.getList(qc, null);
      System.out.println(list.size());
      for (Margin margin : list) {
        System.out.println(margin.getCommodityName());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    System.out.println("dddddd");
  }
}
