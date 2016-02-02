package gnnt.MEBS.timebargain.server.dao;

import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DAOBeanFactory
  implements Serializable
{
  private static Log log = LogFactory.getLog(DAOBeanFactory.class);
  private static final long serialVersionUID = 1L;
  private static final String beanConfig = "MEBS_timebargain.xml";
  private static BeanFactory factory;
  private static Properties props = getProps();
  
  public static void init()
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("MEBS_timebargain.xml");
    log.debug("初始化context:MEBS_timebargain.xml");
    factory = localClassPathXmlApplicationContext;
  }
  
  public static Object getBean(String paramString)
  {
    Object localObject1 = null;
    if (factory == null) {
      synchronized (DAOBeanFactory.class)
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
      synchronized (DAOBeanFactory.class)
      {
        if (factory == null) {
          init();
        }
      }
    }
    return factory;
  }
  
  public static Properties getProps()
  {
    Properties localProperties = null;
    try
    {
      localProperties = (Properties)getBean("config");
    }
    catch (NoSuchBeanDefinitionException localNoSuchBeanDefinitionException)
    {
      log.error("没有找到config的名字！");
    }
    return localProperties;
  }
  
  public static String getConfig(String paramString)
  {
    if (props != null) {
      return props.getProperty(paramString);
    }
    return null;
  }
  
  public static void main(String[] paramArrayOfString) {}
}
