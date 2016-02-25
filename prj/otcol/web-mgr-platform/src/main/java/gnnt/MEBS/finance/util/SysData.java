package gnnt.MEBS.finance.util;

import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SysData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Log logger = LogFactory.getLog(SysData.class);
  private static final String beanConfig = "/financeBeans.xml";
  private static ApplicationContext factory;
  
  public static void init()
  {
    factory = new ClassPathXmlApplicationContext("/financeBeans.xml");
  }
  
  public static Object getBean(String paramString)
  {
    Object localObject1 = null;
    if (factory == null) {
      synchronized (SysData.class)
      {
        if (factory == null) {
          init();
        }
      }
    }
    if (factory != null) {
      localObject1 = factory.getBean(paramString);
    }
    return localObject1;
  }
  
  public static BeanFactory getBeanFactory()
  {
    if (factory == null) {
      synchronized (SysData.class)
      {
        if (factory == null) {
          init();
        }
      }
    }
    return factory;
  }
}
