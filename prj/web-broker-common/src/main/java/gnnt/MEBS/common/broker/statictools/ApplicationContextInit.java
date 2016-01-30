package gnnt.MEBS.common.broker.statictools;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextInit
  implements ApplicationContextAware
{
  private static Log log = LogFactory.getLog(ApplicationContextInit.class);
  private static ApplicationContext springContext;
  private static Properties props;
  private static Properties errorCodeProps;

  public void setApplicationContext(ApplicationContext paramApplicationContext)
    throws BeansException
  {
    springContext = paramApplicationContext;
    props = getProps();
    errorCodeProps = getErrorProps();
  }

  public static Object getBean(String paramString)
  {
    Object localObject = null;
    if (springContext != null)
      localObject = springContext.getBean(paramString);
    return localObject;
  }

  private static Properties getErrorProps()
  {
    Properties localProperties = null;
    try
    {
      if (springContext != null)
        localProperties = (Properties)getBean("errorCode");
    }
    catch (NoSuchBeanDefinitionException localNoSuchBeanDefinitionException)
    {
      log.error("没有找到errorCode的名字！");
    }
    return localProperties;
  }

  private static Properties getProps()
  {
    Properties localProperties = null;
    try
    {
      if (springContext != null)
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

  public static ApplicationContext getApplicationContext()
  {
    return springContext;
  }
}