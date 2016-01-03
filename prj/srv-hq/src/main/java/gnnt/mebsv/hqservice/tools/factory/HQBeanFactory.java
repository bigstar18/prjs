package gnnt.mebsv.hqservice.tools.factory;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class HQBeanFactory
{
  private static Log log = LogFactory.getLog(HQBeanFactory.class);
  private static final String beanConfig = "HQSerice.xml";
  private static BeanFactory factory;
  private static Properties props = getProps();

  public static void init()
  {
    ClassPathResource localClassPathResource = new ClassPathResource("HQSerice.xml");
    log.debug("初始化context:HQSerice.xml");
    factory = new XmlBeanFactory(localClassPathResource);
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

  public static Object getBean(String paramString)
  {
    Object localObject1 = null;
    if (factory == null)
      synchronized (HQBeanFactory.class)
      {
        if (factory == null)
          init();
      }
    if (factory != null)
      localObject1 = factory.getBean(paramString);
    return localObject1;
  }

  public static BeanFactory getBeanFactory()
  {
    if (factory == null)
      synchronized (HQBeanFactory.class)
      {
        if (factory == null)
          init();
      }
    return factory;
  }

  public static String getConfig(String paramString)
  {
    if (props != null)
      return props.getProperty(paramString);
    return null;
  }
}