package gnnt.MEBS.member.broker.util;

import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SysData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Log logger = LogFactory.getLog(SysData.class);
  private static Properties props = getProps();
  private static final String beanConfig = "/brokerBeans.xml";
  private static ApplicationContext factory;
  
  public static void init()
  {
    factory = new ClassPathXmlApplicationContext("/brokerBeans.xml");
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
  
  public static Properties getProps()
  {
    Properties localProperties = null;
    try
    {
      localProperties = (Properties)getBean("brokerConfig");
    }
    catch (NoSuchBeanDefinitionException localNoSuchBeanDefinitionException)
    {
      logger.error("Can't found bean named config! Add it!");
    }
    return localProperties;
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
  
  public static String getConfig(String paramString)
  {
    if (props != null) {
      return props.getProperty(paramString);
    }
    return null;
  }
}
