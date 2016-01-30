package gnnt.MEBS.timebargain.mgr.statictools;

import java.io.Serializable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SysData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static final String beanConfig = "/spring_jndi_DataSource.xml";
  private static ApplicationContext factory;

  public static void init()
  {
    factory = new ClassPathXmlApplicationContext("/spring_jndi_DataSource.xml");
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
}