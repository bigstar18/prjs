package gnnt.MEBS.logonService.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GnntBeanFactory {
	private static Log logger = LogFactory.getLog(GnntBeanFactory.class);
	private static volatile BeanFactory factory;
	private static Properties config;

	public static String getConfig(String paramString) {
		initConfig();
		if (config != null)
			return config.getProperty(paramString);
		return null;
	}

	private static void initConfig() {
		if (config == null)
			synchronized (GnntBeanFactory.class) {
				if (config == null)
					try {
						config = (Properties) getBean("config");
					} catch (Exception localException) {
						logger.error("获取配置文件通用配置信息异常", localException);
					}
			}
	}

	public static Object getBean(String paramString) {
		init();
		if (factory != null)
			return factory.getBean(paramString);
		return null;
	}

	public static BeanFactory getBeanFactory() {
		init();
		return factory;
	}

	private static void init() {
		if (factory == null) {
			synchronized (GnntBeanFactory.class) {
				if (factory == null) {
					logger.info("初始化 类实例生成工厂");
					String fileName = "logonService.xml";
					try {
						factory = new ClassPathXmlApplicationContext(fileName);
						// 保证虚拟机退出之前 spring 中 singtleton 对象自定义销毁方法执行
						((AbstractApplicationContext) factory).registerShutdownHook();
					} catch (Exception e) {
						logger.error("加载 Spring 文件 " + fileName + "异常：", e);
						System.exit(0);// 关闭服务
					}
				}
			}
		}
	}
}