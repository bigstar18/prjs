package gnnt.MEBS.common.mgr.webframe.strutsinterceptor;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.service.StandardService;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 异常拦截器
 * 
 * @author xuejt
 * 
 */
public class ExceptionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 6390831317255258653L;
	private transient final Log logger = LogFactory
			.getLog(ExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("ExceptionInterceptor enter");
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = "";
		try {
			/*
			 * String actionName = invocation.getInvocationContext().getName();
			 * String actionName1 = invocation.getProxy().getActionName();
			 * String nameSpace = invocation.getProxy().getNamespace();
			 * logger.info("actionName:"+actionName);
			 * logger.info("actionName1:"+actionName1);
			 * logger.info("nameSpace:"+nameSpace);
			 */
			result = invocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			// 在struts配置文件中配置 <result
			// name="SysException">/public/jsp/error.jsp</result>
			result = "SysException";
			ReturnValue returnValue = new ReturnValue();
			returnValue.setResult(-1);
			// 系统异常,请与管理员联系!
			returnValue.addInfo(139904);
			// 将返回结果写入Attribute
			request.setAttribute(Global.RETURNVALUE, returnValue);
			logger.error(returnValue.getInfo() + ";详细信息：" + e);
			//在日志拦截器中将operateLog写入attribute
			OperateLog operateLog =(OperateLog)request.getAttribute("operateLog");
			if(request.getAttribute("operateLog")!=null){
				operateLog.setOperateResult(0);
				operateLog.setLogType(1);
				operateLog.setMark("失败原因:" + e.getMessage());
				// action对象
				StandardAction action = (StandardAction) invocation.getAction();
				// action对应的service
				StandardService service = action.getService();
				service.add(operateLog);
			}
		}

		return result;
	}
}
