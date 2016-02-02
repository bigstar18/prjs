package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.PricefiterInterFace.BurrPriceFiterInterFace;
import gnnt.MEBS.transformhq.server.PricefiterInterFace.SecondPriceFiterInterFace;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;
import gnnt.MEBS.transformhq.server.util.Arith;
import gnnt.MEBS.transformhq.server.util.DateUtil;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckAndSendHQBean
{
  private Log log = LogFactory.getLog(CheckAndSendHQBean.class);
  private Map<String, InCommodity> inCommodity;
  private BlockingQueue<HQBean> hqBeanQue;
  private boolean isSecondPrice = true;
  private boolean isBurrPrice = true;
  private BurrPriceFiterInterFace burrPrice;
  private SecondPriceFiterInterFace secondPrice;
  DecimalFormat df = new DecimalFormat("#.########");
  
  public CheckAndSendHQBean(BlockingQueue<HQBean> hqBeanQue)
  {
    this.hqBeanQue = hqBeanQue;
    
    this.inCommodity = ((Map)HQBeanFactory.getBean("inCommodity"));
    
    this.isBurrPrice = Boolean.valueOf(HQBeanFactory.getConfig("isBurrPrice")).booleanValue();
    if (this.isBurrPrice)
    {
      this.burrPrice = ((BurrPriceFiterInterFace)HQBeanFactory.getBean("burrPrice"));
      this.burrPrice.setInCommodity(this.inCommodity);
      this.log.info("初始化毛刺价格过滤对象完成");
    }
    this.isSecondPrice = Boolean.valueOf(HQBeanFactory.getConfig("isSecondPrice")).booleanValue();
    if (this.isSecondPrice)
    {
      this.secondPrice = ((SecondPriceFiterInterFace)HQBeanFactory.getBean("secondPrice"));
      this.secondPrice.init(this);
      this.log.info("初始化同一秒价格过滤对象完成");
    }
  }
  
  public void burrPriceCheck(HQBean hqBean)
  {
    if (this.isBurrPrice)
    {
      int check = this.burrPrice.checkPrice(hqBean);
      if (check == 0)
      {
        secondPriceCheck(hqBean);
      }
      else if (check == 2)
      {
        String commodityId = hqBean.getCommodityID();
        secondPriceCheck((HQBean)this.burrPrice.getGlitchPLst(commodityId).getLast());
        this.burrPrice.getGlitchPLst(commodityId).clear();
      }
    }
    else
    {
      secondPriceCheck(hqBean);
    }
  }
  
  public void secondPriceCheck(HQBean hqBean)
  {
    this.log.info("检查毛刺数据完成：" + hqBean.toString());
    if (this.isSecondPrice) {
      this.secondPrice.setHQBean(hqBean);
    } else {
      offerHQBean(hqBean);
    }
  }
  
  public void offerHQBean(HQBean hqBean)
  {
    this.log.info("价格放入待发送队列：" + hqBean.toString());
    String commodityID = hqBean.getCommodityID();
    if (this.inCommodity.containsKey(commodityID))
    {
      String inCommodityID = ((InCommodity)this.inCommodity.get(commodityID)).getInCommodityId();
      hqBean.setCommodityID(inCommodityID);
      
      String randomPrice = changePrice(hqBean.getPrice(), ((InCommodity)this.inCommodity.get(commodityID)).getRandomStart(), ((InCommodity)this.inCommodity.get(commodityID)).getrandomEnd());
      hqBean.setPrice(randomPrice);
      hqBean.setServerContents(transClientHQ(hqBean));
      this.hqBeanQue.offer(hqBean);
      



      int sendNum = ((InCommodity)this.inCommodity.get(commodityID)).getRepeatSend();
      for (int i = 0; i < sendNum - 1; i++)
      {
        HQBean hqAG2 = new HQBean();
        hqAG2.setCommodityID(inCommodityID + i + 2);
        hqAG2.setPrice(hqBean.getPrice());
        hqAG2.setPriceTime(hqBean.getPriceTime());
        hqAG2.setHqBeanInetAddress(hqBean.getHqBeanInetAddress());
        hqAG2.setServerContents(transClientHQ(hqAG2));
        this.hqBeanQue.offer(hqAG2);
      }
    }
  }
  
  private String transClientHQ(HQBean hqBean)
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
  
  public String changePrice(String price, int randomStart, int randomEnd)
  {
    if ((randomEnd < randomStart) || (randomStart <= 0) || (randomEnd <= 0)) {
      return price;
    }
    double random = Arith.div(Arith.formatD(Math.random(), randomEnd - randomStart + 1), Math.pow(10.0D, randomStart - 1));
    
    return String.valueOf(this.df.format(Arith.add(Double.valueOf(price).doubleValue(), random)));
  }
  
  public Map<String, InCommodity> getIncommodity()
  {
    return this.inCommodity;
  }
}
