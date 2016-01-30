package gnnt.MEBS.common.core.jms;

import gnnt.MEBS.common.core.util.GnntBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MsgTask
  extends Task
{
  private final transient Log logger = LogFactory.getLog(MsgTask.class);
  private int type;
  private String userID;
  private String msg;
  
  public MsgTask(int paramInt, String paramString1, String paramString2)
  {
    this.type = paramInt;
    this.userID = paramString1;
    this.msg = paramString2;
  }
  
  public void run()
  {
    try
    {
      switch (this.type)
      {
      case 1: 
        ProducerToolSendTopic localProducerToolSendTopic1 = (ProducerToolSendTopic)GnntBeanFactory.getBean("frontProducerTopic");
        localProducerToolSendTopic1.initialize();
        localProducerToolSendTopic1.produceMessage(this.msg);
        break;
      case 2: 
        ProducerToolSendTopic localProducerToolSendTopic2 = (ProducerToolSendTopic)GnntBeanFactory.getBean("mgrProducerTopic");
        localProducerToolSendTopic2.initialize();
        localProducerToolSendTopic2.produceMessage(this.msg);
        break;
      case 3: 
        ProducerToolSendQueue localProducerToolSendQueue1 = (ProducerToolSendQueue)GnntBeanFactory.getBean("producerQueue");
        localProducerToolSendQueue1.initialize();
        localProducerToolSendQueue1.produceMessage("front." + this.userID, this.msg);
        break;
      case 4: 
        ProducerToolSendQueue localProducerToolSendQueue2 = (ProducerToolSendQueue)GnntBeanFactory.getBean("producerQueue");
        localProducerToolSendQueue2.initialize();
        localProducerToolSendQueue2.produceMessage("mgr." + this.userID, this.msg);
      }
    }
    catch (Exception localException)
    {
      this.logger.error("发送消息失败；消息内容" + info());
    }
  }
  
  public String info()
  {
    return "MsgTask[type=" + this.type + "userID=" + this.userID + "msg=" + this.msg + "]";
  }
  
  public boolean needExecuteImmediate()
  {
    return false;
  }
}
