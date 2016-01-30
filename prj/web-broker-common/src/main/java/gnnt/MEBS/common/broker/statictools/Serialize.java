package gnnt.MEBS.common.broker.statictools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.webframe.strutsinterceptor.WriteLogInterceptor;
import java.io.PrintStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Serialize
{
  private static final transient Log logger = LogFactory.getLog(WriteLogInterceptor.class);

  public static String serializeToXml(StandardModel paramStandardModel)
  {
    XStream localXStream = new XStream();
    return localXStream.toXML(paramStandardModel);
  }

  public static StandardModel deSerializeFromXml(String paramString)
  {
    try
    {
      XStream localXStream = new XStream(new DomDriver());
      return (StandardModel)localXStream.fromXML(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      logger.warn("str=" + paramString + ";不能反序列化");
    }
    return null;
  }

  public static void main(String[] paramArrayOfString)
  {
    Broker localBroker = new Broker();
    localBroker.setName("name");
    localBroker.setIpAddress("ipAddress");
    String str = serializeToXml(localBroker);
    System.out.println(str);
  }
}