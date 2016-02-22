package gnnt.MEBS.common.util;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SysDataTest
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Log logger = LogFactory.getLog(SysDataTest.class);
  private static final String[] beanConfig = { "/commonBeansTest.xml", "/config_default.xml" };
  private static ApplicationContext factory;
  private static Properties props = getProps();
  
  public static void init()
  {
    logger.debug("初始化context:" + beanConfig);
    factory = new ClassPathXmlApplicationContext(beanConfig);
  }
  
  public static Object getBean(String beanId)
  {
    Object obj = null;
    if (factory == null) {
      synchronized (SysDataTest.class)
      {
        if (factory == null) {
          init();
        }
      }
    }
    if (factory != null) {
      obj = factory.getBean(beanId);
    }
    return obj;
  }
  
  public static BeanFactory getBeanFactory()
  {
    if (factory == null) {
      synchronized (SysDataTest.class)
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
    Properties conf = null;
    try
    {
      conf = (Properties)getBean("commonConfig");
    }
    catch (NoSuchBeanDefinitionException e)
    {
      logger.error("Can't found bean named config! Add it!");
    }
    return conf;
  }
  
  public static String getConfig(String name)
  {
    if (props != null) {
      return props.getProperty(name);
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    System.out.println("====" + getConfig("moneyType"));
  }
}
