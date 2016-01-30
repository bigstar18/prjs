package gnnt.MEBS.common.mgr.statictools;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 系统BeanFactory
 * 
 * 通过配置文件路径方式加载类到Spring容器中
 * 
 * 在web环境下用于在不启动tomcat服务测试Action Service DAO 等类
 * 
 * @author xuejt
 * 
 */
public class GnntBeanFactory {
	private static Log log = LogFactory.getLog(GnntBeanFactory.class);

	/**
	 * spring context file path
	 */
	private final static String dataSourceConfig = "jdbc_DataSource.xml";

	private final static String beanConfig = "spring.xml";

	private final static String errorCodeConfig = "spring_errorcode.xml";

	private volatile static BeanFactory factory;

	private static Properties props = getProps();

	private static Properties errorCodeProps = getErrorCodeProps();

	/**
	 * 初始化context对象，通过beanConfig指定的context file
	 */
	private static void init() {
		log.debug("初始化context:" + beanConfig);

		ArrayList<String> list = new ArrayList<String>();
		list.add(dataSourceConfig);
		list.add(beanConfig);
		list.add(errorCodeConfig);

		try {
			factory = new ClassPathXmlApplicationContext(list
					.toArray(new String[list.size()]));
			// 保证虚拟机退出之前 spring中singtleton对象自定义销毁方法会执行
			((AbstractApplicationContext) factory).registerShutdownHook();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加载配置文件时发生错误" + e);
		}
		// 关闭spring容器 仅供参考
		// ((AbstractApplicationContext)GnntBeanFactory.getBeanFactory()).close();
	}

	/**
	 * 通过beanId得到factory中的bean实例
	 * 
	 * @param beanId
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		Object obj = null;

		if (factory == null) {
			synchronized (GnntBeanFactory.class) {
				if (factory == null) {
					init();
				}
			}
		}
		if (factory != null)
			obj = factory.getBean(beanId);
		return obj;
	}

	/**
	 * 获得BeanFactory实例
	 * 
	 * @return the BeanFactory
	 */
	public static BeanFactory getBeanFactory() {
		if (factory == null) {
			synchronized (GnntBeanFactory.class) {
				if (factory == null) {
					init();
				}
			}
		}
		return factory;
	}

	/**
	 * 获得系统配置属性对象
	 * 
	 * @return 属性对象
	 */
	private static Properties getProps() {
		Properties conf = null;
		try {
			conf = (Properties) getBean("config");
		} catch (NoSuchBeanDefinitionException e) {
			log.error("没有找到config的名字！");
		}
		return conf;
	}

	/**
	 * 获得系统错误码表属性对象
	 * 
	 * @return 错误码表属性对象
	 */
	private static Properties getErrorCodeProps() {
		Properties conf = null;
		try {
			conf = (Properties) getBean("errorCode");
		} catch (NoSuchBeanDefinitionException e) {
			log.error("没有找到config的名字！");
		}
		return conf;
	}


	/**
	 * 得到系统配置属性
	 * 
	 * @param name
	 * @return 属性字符值
	 */
	public static String getConfig(String name) {
		if (props != null)
			return (String) props.getProperty(name);
		else
			return null;
	}

	/**
	 * 获取错误码对应的详细信息
	 * 
	 * @param errorCode
	 *            错误码
	 * @return 详细信息
	 */
	public static String getErrorInfo(String errorCode) {
		if (errorCodeProps != null)
			return (String) errorCodeProps.getProperty(errorCode);
		else
			return null;
	}
}
