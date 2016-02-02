package gnnt.MEBS.transformhq.server.test;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.quotation.CheckAndSendHQBean;
import gnnt.MEBS.transformhq.server.quotation.QuotationManager;
import gnnt.MEBS.transformhq.server.util.Arith;
import gnnt.MEBS.transformhq.server.util.DateUtil;
import java.util.Date;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQBeanTest
  extends Thread
{
  private static Log log = LogFactory.getLog(HQBeanTest.class);
  private boolean isRun = true;
  private double minPrice = 20.0D;
  private double maxPrice = 22.0D;
  private long diffTime = 2000L;
  private QuotationManager manager;
  
  public void init(double minPrice, double maxPrice, long diffTime)
  {
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.diffTime = diffTime;
  }
  
  public void run()
  {
    Random random = new Random();
    try
    {
      for (;;)
      {
        HQBean hqBean = new HQBean();
        String randomPrice = String.valueOf(Arith.format(random.nextDouble() * (this.maxPrice - this.minPrice) + this.minPrice, 3));
        hqBean.setCommodityID("XAG");
        hqBean.setDateType("6000");
        hqBean.setPrice(randomPrice);
        hqBean.setFormerPrice(randomPrice);
        String transHQ = transClientHQ(hqBean);
        hqBean.setServerContents(transHQ);
        log.info("产生行情为:" + transHQ);
        this.manager.getCheckAndSendHQBean().burrPriceCheck(hqBean);
        sleep(this.diffTime);
      }
    }
    catch (Exception e)
    {
      log.error("make hq error:" + e);
    }
  }
  
  public String transClientHQ(HQBean hqBean)
  {
    hqBean.setDateType("6000");
    StringBuffer sb = new StringBuffer();
    Date curDate = new Date();
    String dateStr = DateUtil.formatDate(curDate, "yyyyMMdd");
    String timeStr = DateUtil.formatDate(curDate, "HHmmssSSS");
    sb.append("|3||");
    sb.append(hqBean.getCommodityID());
    sb.append("|250|23||")
      .append(dateStr).append("|")
      .append(timeStr).append("|");
    sb.append(hqBean.getPrice());
    sb.append("|||||||");
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    HQBeanTest hqBeanTest = new HQBeanTest();
    hqBeanTest.init(20.0D, 22.0D, 2000L);
    hqBeanTest.start();
  }
}
