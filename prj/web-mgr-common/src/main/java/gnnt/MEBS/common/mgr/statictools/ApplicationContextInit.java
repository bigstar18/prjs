package gnnt.MEBS.common.mgr.statictools;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 通过实现ApplicationContextAware接口，在spring容器启动时获取spring容器对象<br>
 * 
 * @author xuejt
 * 
 */
public class ApplicationContextInit implements ApplicationContextAware {
	private static Log log = LogFactory.getLog(ApplicationContextInit.class);

	/**
	 * ApplicationContext
	 */
	private static ApplicationContext springContext;

	/**
	 * 系统配置信息
	 */
	private static Properties props;

	/**
	 * 错误配置信息
	 */
	private static Properties errorCodeProps;

	/*
	 * 注入ApplicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// 在加载Spring时自动获得context
		springContext = applicationContext;
		props = getProps();
		errorCodeProps=getErrorProps();
	}

	/**
	 * 通过beanId得到factory中的bean实例
	 * 
	 * @param beanId
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		Object obj = null;
		if (springContext != null)
			obj = springContext.getBean(beanId);
		return obj;
	}

	/**
	 * 获得系统错误码表属性对象
	 * 
	 * @return 错误码表属性对象
	 */
	private static Properties getErrorProps() {
		Properties conf = null;
		try {
			if (springContext != null)
				conf = (Properties) getBean("errorCode");
		} catch (NoSuchBeanDefinitionException e) {
			log.error("没有找到errorCode的名字！");
		}
		return conf;
	}
	
	/**
	 * 获得系统配置属性对象
	 * 
	 * @return 属性对象
	 */
	private static Properties getProps() {
		Properties conf = null;
		try {
			if (springContext != null)
				conf = (Properties) getBean("config");
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
	 * @param errorCode 错误码
	 * @return			详细信息
	 */
	public static String getErrorInfo(String errorCode) {
		if (errorCodeProps != null)
			return (String) errorCodeProps.getProperty(errorCode);
		else
			return null;
	}

	/**
	 * 获取ApplicationContext
	 * 
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return springContext;
	}

}
