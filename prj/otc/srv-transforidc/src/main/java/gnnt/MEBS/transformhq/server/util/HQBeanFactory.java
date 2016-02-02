package gnnt.MEBS.transformhq.server.util;

import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class HQBeanFactory
  implements Serializable
{
  private static Log log = LogFactory.getLog(HQBeanFactory.class);
  private static final String beanConfig = "TFH_IDC.xml";
  private static BeanFactory factory;
  private static Properties props = getProps();
  
  public static void init()
  {
    ClassPathResource res = new ClassPathResource("TFH_IDC.xml");
    log.debug("初始化context:TFH_IDC.xml");
    factory = new XmlBeanFactory(res);
  }
  
  public static Object getBean(String beanId)
  {
    Object obj = null;
    if (factory == null) {
      synchronized (HQBeanFactory.class)
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
      synchronized (HQBeanFactory.class)
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
      log.error("没有找到config的名字！");
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
  
  public static void main(String[] args) {}
}
