package gnnt.MEBS.base.util;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SysData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Log logger = LogFactory.getLog(SysData.class);
  private static final String beanConfig = "/baseBeans.xml";
  private static BeanFactory factory;
  private static Properties props = getProps();
  
  public static void init()
  {
    ClassPathResource res = new ClassPathResource("/baseBeans.xml");
    logger.debug("初始化context:/baseBeans.xml");
    factory = new XmlBeanFactory(res);
  }
  
  public static Object getBean(String beanId)
  {
    Object obj = null;
    if (factory == null) {
      synchronized (SysData.class)
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
      synchronized (SysData.class)
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
      conf = (Properties)getBean("config");
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
