package gnnt.MEBS.common.core.jms;

import java.io.PrintStream;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerQueueTool
  implements MessageListener
{
  private String user = ActiveMQConnection.DEFAULT_USER;
  private String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private String url = "failover://tcp://172.16.2.23:61616";
  private String subject = "front.admin";
  private Destination destination = null;
  private Connection connection = null;
  private Session session = null;
  private MessageConsumer consumer = null;
  
  private void initialize()
    throws JMSException, Exception
  {
    ActiveMQConnectionFactory localActiveMQConnectionFactory = new ActiveMQConnectionFactory(this.user, this.password, this.url);
    this.connection = localActiveMQConnectionFactory.createConnection();
    this.session = this.connection.createSession(false, 1);
    this.destination = this.session.createQueue(this.subject);
    this.consumer = this.session.createConsumer(this.destination);
  }
  
  public void consumeMessage()
    throws JMSException, Exception
  {
    initialize();
    this.connection.start();
    System.out.println("Consumer:->Begin listening...");
    this.consumer.setMessageListener(this);
  }
  
  public void close()
    throws JMSException
  {
    System.out.println("Consumer:->Closing connection");
    if (this.consumer != null) {
      this.consumer.close();
    }
    if (this.session != null) {
      this.session.close();
    }
    if (this.connection != null) {
      this.connection.close();
    }
  }
  
  public void onMessage(Message paramMessage)
  {
    try
    {
      if ((paramMessage instanceof TextMessage))
      {
        TextMessage localTextMessage = (TextMessage)paramMessage;
        String str = localTextMessage.getText();
        System.out.println("Consumer:->Received: " + str);
      }
      else
      {
        System.out.println("Consumer:->Received: " + paramMessage);
      }
    }
    catch (JMSException localJMSException)
    {
      localJMSException.printStackTrace();
    }
  }
  
  public static void main(String[] paramArrayOfString)
    throws JMSException, Exception
  {
    ConsumerQueueTool localConsumerQueueTool = new ConsumerQueueTool();
    System.out.println("failover://tcp://localhost:61616------------");
    localConsumerQueueTool.consumeMessage();
    Thread.sleep(50000L);
    localConsumerQueueTool.close();
  }
}
