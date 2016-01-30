package gnnt.MEBS.common.broker.statictools;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GnntBeanFactory
{
  private static Log log = LogFactory.getLog(GnntBeanFactory.class);
  private static final String dataSourceConfig = "jdbc_DataSource.xml";
  private static final String beanConfig = "spring.xml";
  private static final String errorCodeConfig = "spring_errorcode.xml";
  private static volatile BeanFactory factory;
  private static Properties props = getProps();
  private static Properties errorCodeProps = getErrorCodeProps();

  private static void init()
  {
    log.debug("初始化context:spring.xml");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("jdbc_DataSource.xml");
    localArrayList.add("spring.xml");
    localArrayList.add("spring_errorcode.xml");
    try
    {
      factory = new ClassPathXmlApplicationContext((String[])localArrayList.toArray(new String[localArrayList.size()]));
      ((AbstractApplicationContext)factory).registerShutdownHook();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      log.error("加载配置文件时发生错误" + localException);
    }
  }

  public static Object getBean(String paramString)
  {
    Object localObject1 = null;
    if (factory == null)
      synchronized (GnntBeanFactory.class)
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
      synchronized (GnntBeanFactory.class)
      {
        if (factory == null)
          init();
      }
    return factory;
  }

  private static Properties getProps()
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

  private static Properties getErrorCodeProps()
  {
    Properties localProperties = null;
    try
    {
      localProperties = (Properties)getBean("errorCode");
    }
    catch (NoSuchBeanDefinitionException localNoSuchBeanDefinitionException)
    {
      log.error("没有找到config的名字！");
    }
    return localProperties;
  }

  public static String getConfig(String paramString)
  {
    if (props != null)
      return props.getProperty(paramString);
    return null;
  }

  public static String getErrorInfo(String paramString)
  {
    if (errorCodeProps != null)
      return errorCodeProps.getProperty(paramString);
    return null;
  }
}