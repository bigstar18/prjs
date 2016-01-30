package gnnt.MEBS.timebargain.mgr.util;

import java.io.PrintStream;
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
  private static final String beanConfig = "configfiles/*/spring/spring_baseBean.xml";
  private static ApplicationContext factory;
  private static Properties props = getProps();

  public static void init()
  {
    factory = new ClassPathXmlApplicationContext("configfiles/*/spring/spring_baseBean.xml");
  }

  public static Object getBean(String beanId)
  {
    Object obj = null;

    if (factory == null) {
      synchronized (SysData.class) {
        if (factory == null) {
          init();
        }
      }
    }
    if (factory != null)
      obj = factory.getBean(beanId);
    return obj;
  }

  public static BeanFactory getBeanFactory()
  {
    if (factory == null) {
      synchronized (SysData.class) {
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
    try {
      conf = (Properties)getBean("financeConfig");
    } catch (NoSuchBeanDefinitionException e) {
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

  public static void main(String[] args) {
    System.out.println("====" + getConfig("moneyType"));
  }
}