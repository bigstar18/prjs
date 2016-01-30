
package gnnt.MEBS.common.mgr.servlet;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.activemq.web.AjaxServlet;

/**
 * <P>类说明：继承 jms 的 ajaxServlet 以在 web.xml 中将指定 jms 的 ajaxServlet 替换为本 servlet，以读取数据库配置的 jms 地址
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-11-1上午10:43:34|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class GNNTJmsAjaxServlet extends AjaxServlet{
	/** 序列化编号 */
	private static final long serialVersionUID = 2538638263012152495L;

	public void init() throws ServletException {
		resetBrokerURL();
		super.init();
	}

	/**
	 * 
	 * 重定向 BrokerURL 地址
	 * <br/><br/>
	 */
	private void resetBrokerURL(){
		String parameterName = "org.apache.activemq.brokerURL";
		String factoryName = "org.apache.activemq.connectionFactory";
		ServletContext servletContext=this.getServletContext();
		if(servletContext.getAttribute(factoryName) == null){
			try {
				//获取 ApplicationContext 属性
				Field contextField = servletContext.getClass().getDeclaredField("context");
				contextField.setAccessible(true);//由于 ApplicationContext 属性为私有变量，所以要使得外部允许访问私有变量
				Object context = contextField.get(servletContext);//获取 ApplicationContext 实例
	
				//获取修改 parameter 的方法
				Method setInitParameterMethod = context.getClass().getDeclaredMethod("setInitParameter",String.class,String.class);
				setInitParameterMethod.invoke(context, new Object[]{parameterName,getBrokerURLFromDB(servletContext,parameterName)});//执行修改 parameter 的方法
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(servletContext.getInitParameter(parameterName));
	}

	/**
	 * 
	 * 从数据库获取 tcp 连接
	 * <br/><br/>
	 * @return
	 */
	private String getBrokerURLFromDB(ServletContext servletContext,String parameterName){
		String brokerURL = servletContext.getInitParameter(parameterName);
		StandardService standardService=(StandardService)ApplicationContextInit.getBean("com_standardService");
		List<Map<Object, Object>> list = standardService.getListBySql("select INFOVALUE from C_MARKETINFO where INFONAME='JMSBrokerURL'");
		if(list != null && list.size()>0){
			Map<Object, Object> map = list.get(0);
			if(map != null && map.get("INFOVALUE") != null){
				brokerURL=map.get("INFOVALUE").toString();
			}
		}
		return brokerURL;
	}
}

