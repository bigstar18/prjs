package gnnt.MEBS.common.core.jms;

import gnnt.MEBS.common.core.Server;
import gnnt.MEBS.common.core.dao.SysSchedulerDAO;
import gnnt.MEBS.common.core.po.MarketInfoPO;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProducerToolSendQueue
{
  private Log log = LogFactory.getLog(getClass());
  private String user = ActiveMQConnection.DEFAULT_USER;
  private String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private String url = "failover://tcp://172.16.2.23:61616";
  private Connection connection = null;
  private Session session = null;
  private boolean initFlag = false;
  
  public ProducerToolSendQueue()
    throws JMSException, Exception
  {}
  
  public synchronized void initialize()
    throws JMSException, Exception
  {
    if (!this.initFlag)
    {
      initJMSConnectionMsgFromDB();
      ActiveMQConnectionFactory localActiveMQConnectionFactory = new ActiveMQConnectionFactory(this.user, this.password, this.url);
      this.connection = localActiveMQConnectionFactory.createConnection();
      this.session = this.connection.createSession(false, 1);
      this.initFlag = true;
    }
  }
  
  private void initJMSConnectionMsgFromDB()
  {
    MarketInfoPO localMarketInfoPO = Server.getInstance().getSysSchedulerDAO().getMarketInfo("JMSBrokerURL");
    if ((localMarketInfoPO != null) && (localMarketInfoPO.getInfoValue() != null) && (localMarketInfoPO.getInfoValue().trim().length() > 0)) {
      this.url = localMarketInfoPO.getInfoValue().trim();
    }
    if ((this.url != null) && (!this.url.startsWith("failover://"))) {
      this.url = ("failover://" + this.url);
    }
    localMarketInfoPO = Server.getInstance().getSysSchedulerDAO().getMarketInfo("JMSBrokerUserName");
    if ((localMarketInfoPO != null) && (localMarketInfoPO.getInfoValue() != null) && (localMarketInfoPO.getInfoValue().trim().length() > 0)) {
      this.user = localMarketInfoPO.getInfoValue().trim();
    }
    localMarketInfoPO = Server.getInstance().getSysSchedulerDAO().getMarketInfo("JMSBrokerUserName");
    if ((localMarketInfoPO != null) && (localMarketInfoPO.getInfoValue() != null) && (localMarketInfoPO.getInfoValue().trim().length() > 0)) {
      this.password = localMarketInfoPO.getInfoValue().trim();
    }
    this.log.info("从数据库获取信息后的jMS信息 url<" + this.url + ">user<" + this.user + ">password<" + this.password + ">");
  }
  
  public void produceMessage(String paramString1, String paramString2)
  {
    MessageProducer localMessageProducer = null;
    Queue localQueue = null;
    try
    {
      localQueue = this.session.createQueue(paramString1);
      localMessageProducer = this.session.createProducer(localQueue);
      localMessageProducer.setDeliveryMode(1);
      TextMessage localTextMessage = this.session.createTextMessage(paramString2);
      this.log.debug("Producer:->Sending message: " + paramString2);
      localMessageProducer.send(localTextMessage);
      this.log.debug("Producer:->Message sent complete!");
      return;
    }
    catch (JMSException localJMSException2)
    {
      this.log.info("JMSException Reconnect");
      try
      {
        close();
      }
      catch (JMSException localJMSException4)
      {
        this.log.error("reconnect close exception " + localJMSException4);
      }
      try
      {
        initialize();
      }
      catch (JMSException localJMSException5)
      {
        this.log.error("reconnect initialize JMSException " + localJMSException5);
      }
      catch (Exception localException)
      {
        this.log.error("reconnect initialize Exception " + localException);
      }
    }
    finally
    {
      if (localMessageProducer != null) {
        try
        {
          localMessageProducer.close();
        }
        catch (JMSException localJMSException6)
        {
          this.log.error("producer.close() Exception " + localJMSException6);
        }
      }
    }
  }
  
  public void close()
    throws JMSException
  {
    this.log.debug("Producer:->Closing connection");
    if (this.session != null) {
      this.session.close();
    }
    if (this.connection != null) {
      this.connection.close();
    }
  }
  
  public static void main(String[] paramArrayOfString)
    throws JMSException, Exception
  {}
}
