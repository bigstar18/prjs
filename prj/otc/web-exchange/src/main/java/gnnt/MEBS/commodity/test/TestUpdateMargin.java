package gnnt.MEBS.commodity.test;

import gnnt.MEBS.commodity.model.Margin;
import gnnt.MEBS.commodity.service.MarginService;
import java.io.PrintStream;
import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUpdateMargin
{
  public static void main(String[] args)
  {
    ApplicationContext factory = new ClassPathXmlApplicationContext(
      "commodity_JDBC.xml");
    MarginService marginService = (MarginService)factory.getBean("marginService");
    Margin margin = new Margin();
    margin.setCommodityId("XAUGSD");
    margin.setFirmId("Def_Customer");
    margin.setHolidayMargin(new BigDecimal(1.0014D));
    System.out.println(marginService.update(margin));
  }
}
