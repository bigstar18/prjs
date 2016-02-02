package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;
import java.util.concurrent.BlockingQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQHeartThread
  extends Thread
{
  private Log log = LogFactory.getLog(HQHeartThread.class);
  private BlockingQueue<HQBean> hqBeanQue;
  private HQStatus hqStatus;
  private long beforeSendTime = 0L;
  private long heartBeat = 1L;
  private HQBean hqHeart;
  private boolean isRun = true;
  
  public HQHeartThread(HQStatus hqStatus)
  {
    this.hqStatus = hqStatus;
    this.heartBeat = (Integer.valueOf(HQBeanFactory.getConfig("heartBeat")).intValue() * 1000);
    this.beforeSendTime = System.currentTimeMillis();
  }
  
  public void init(BlockingQueue<HQBean> hqBeanQue)
  {
    this.hqBeanQue = hqBeanQue;
    this.hqHeart = new HQBean();
    this.hqHeart.setServerContents("||||||");
    this.hqHeart.setDateType("7000");
  }
  
  private void setBeforeSendTime()
  {
    this.beforeSendTime = System.currentTimeMillis();
  }
  
  public void run()
  {
    while (this.isRun) {
      try
      {
        long diffSendTime = System.currentTimeMillis() - this.beforeSendTime;
        if (diffSendTime > this.heartBeat)
        {
          this.hqHeart.setServerContents(this.hqStatus.getStatus() + "||||||");
          this.hqHeart.setCommodityID(this.hqStatus.getStatus() + "||||||");
          this.hqHeart.setPriceTime(System.currentTimeMillis());
          this.hqBeanQue.offer(this.hqHeart);
          setBeforeSendTime();
        }
        sleep(100L);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
        this.log.error("send hqHeart error：" + e);
      }
    }
  }
}
