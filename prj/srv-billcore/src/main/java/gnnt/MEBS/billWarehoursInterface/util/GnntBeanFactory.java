package gnnt.MEBS.billWarehoursInterface.util;

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
  private static volatile BeanFactory factory;
  private static Properties props = getProps();
  private static Properties errorCodeProps = getErrorCodeProps();
  
  public static void init()
  {
    log.debug("初始化context:billWarehoursInterface.xml");
    
    ArrayList<String> list = new ArrayList();
    list.add("billWarehoursInterface.xml");
    list.add("billWarehoursInterface_Errorcode.xml");
    list.add("billWarehoursInterface_Rmi_Server.xml");
    list.add("billWarehoursInterface_Rmi_Client.xml");
    try
    {
      factory = new ClassPathXmlApplicationContext(
        (String[])list.toArray(new String[list.size()]));
      
      ((AbstractApplicationContext)factory).registerShutdownHook();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log.error("加载配置文件时发生错误" + e);
    }
  }
  
  public static Object getBean(String beanId)
  {
    Object obj = null;
    if (factory == null) {
      synchronized (GnntBeanFactory.class)
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
      synchronized (GnntBeanFactory.class)
      {
        if (factory == null) {
          init();
        }
      }
    }
    return factory;
  }
  
  private static Properties getProps()
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
  
  private static Properties getErrorCodeProps()
  {
    Properties conf = null;
    try
    {
      conf = (Properties)getBean("errorCode");
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
  
  public static String getErrorInfo(String errorCode)
  {
    if (errorCodeProps != null) {
      return errorCodeProps.getProperty(errorCode);
    }
    return null;
  }
}
