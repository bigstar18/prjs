package gnnt.MEBS.common.broker.webframe.strutsinterceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.common.broker.action.StandardAction;
import gnnt.MEBS.common.broker.common.Global;
import gnnt.MEBS.common.broker.common.ReturnValue;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.OperateLog;
import gnnt.MEBS.common.broker.model.Right;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.service.RightService;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.common.broker.statictools.ApplicationContextInit;
import gnnt.MEBS.common.broker.statictools.GnntBeanFactory;

public class WriteLogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 8669997795536485487L;
	private final transient Log logger = LogFactory.getLog(WriteLogInterceptor.class);

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		ActionContext localActionContext = paramActionInvocation.getInvocationContext();
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) localActionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String str1 = localHttpServletRequest.getServletPath();
		this.logger.debug("url=" + str1);
		Right localRight = getRightByUrl(Global.getRightTree(), str1);
		StandardAction localStandardAction = (StandardAction) paramActionInvocation.getAction();
		StandardModel localStandardModel = localStandardAction.getEntity();
		StandardService localStandardService = localStandardAction.getService();
		OperateLog localOperateLog = null;
		Object localObject2;
		if ((localRight != null) && ("Y".equalsIgnoreCase(localRight.getIsWriteLog()))
				&& (localRight.getLogCatalog().getCatalogID().intValue() != 0)) {
			localOperateLog = new OperateLog();
			Broker localObject1 = (Broker) localHttpServletRequest.getSession().getAttribute("CurrentUser");
			localOperateLog.setOperator(((Broker) localObject1).getBrokerId());
			localOperateLog.setOperateIp(((Broker) localObject1).getIpAddress());
			localOperateLog.setOperateTime(new Date());
			localOperateLog.setLogCatalog(localRight.getLogCatalog());
			localObject2 = "";
			String str2 = paramActionInvocation.getProxy().getMethod();
			if (str2.contains("add"))
				localObject2 = getAddLogDescription(localStandardModel);
			else if (str2.contains("delete"))
				localObject2 = getDeleteLogDescription(localStandardModel, localHttpServletRequest, localStandardService);
			else if (str2.contains("update"))
				localObject2 = getUpdateLogDescription(localStandardModel, localStandardService);
			else if (str2.contains("viewById"))
				localObject2 = getForUpdateLogDescription(localStandardModel);
			else if (str2.contains("forwardAdd"))
				localObject2 = getForAddLogDescription(localStandardModel, localRight);
			else if (str2.contains("list"))
				localObject2 = getListLogDescription(localRight);
			else
				localObject2 = "点击菜单" + localRight.getName();
			localOperateLog.setOperateContent((String) localObject2);
			if (localStandardModel != null)
				localOperateLog.setCurrentValue(localStandardModel.serialize());
		}
		localHttpServletRequest.setAttribute("operateLog", localOperateLog);
		String result = paramActionInvocation.invoke();
		if ((localOperateLog != null) && (result != null)) {
			if (((String) result).equals("success")) {
				localOperateLog.setOperateResult(Integer.valueOf(1));
			} else {
				localObject2 = (ReturnValue) localHttpServletRequest.getAttribute("ReturnValue");
				if ((localObject2 != null) && (((ReturnValue) localObject2).getResult() == -1))
					localOperateLog.setMark("失败原因:" + ((ReturnValue) localObject2).getInfo());
			}
			localStandardService.add(localOperateLog);
		}
		return result;
	}

	private String getAddLogDescription(StandardModel paramStandardModel) {
		return "添加数据：" + paramStandardModel.translate();
	}

	private String getDeleteLogDescription(StandardModel paramStandardModel, HttpServletRequest paramHttpServletRequest,
			StandardService paramStandardService) throws SecurityException, NoSuchFieldException {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("ids");
		Class localClass = paramStandardModel.getClass().getDeclaredField(paramStandardModel.fetchPKey().getKey()).getType();
		Object[] localObject;
		int i;
		if (localClass.equals(Long.class)) {
			localObject = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject[i] = Long.valueOf(arrayOfString[i]);
		} else if (localClass.equals(Integer.class)) {
			localObject = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject[i] = Integer.valueOf(arrayOfString[i]);
		} else {
			localObject = arrayOfString;
		}
		if ((localObject != null) && (localObject.length > 0)) {
			List localList = paramStandardService.getListByBulk(paramStandardModel, (Object[]) localObject);
			String str = "";
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				StandardModel localStandardModel = (StandardModel) localIterator.next();
				str = str + "删除数据：" + localStandardModel.translate();
			}
			return str;
		}
		return "删除数据：" + paramStandardModel.translate();
	}

	private String getUpdateLogDescription(StandardModel paramStandardModel, StandardService paramStandardService) {
		if ((paramStandardModel != null) && (paramStandardModel.fetchPKey().getValue() != null)) {
			StandardModel localStandardModel = paramStandardService.get(paramStandardModel);
			return "更新数据," + localStandardModel.compareTranslate(paramStandardModel);
		}
		return "更新数据，未获取到实体对象";
	}

	private String getForUpdateLogDescription(StandardModel paramStandardModel) {
		return "为了更新查询数据：" + paramStandardModel.translate();
	}

	private String getForAddLogDescription(StandardModel paramStandardModel, Right paramRight) {
		return "打开(" + paramRight.getName() + ")的添加页面";
	}

	private String getListLogDescription(Right paramRight) {
		return "点击菜单" + paramRight.getName() + "获取列表";
	}

	private Right getRightByUrl(Right paramRight, String paramString) {
		if (paramRight == null)
			return null;
		int i = 0;
		Object localObject1 = Global.getModuleIDList().iterator();
		Object localObject2;
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = (Integer) ((Iterator) localObject1).next();
			if ((paramRight.getModuleId() != null) && (paramRight.getModuleId().equals(localObject2))) {
				i = 1;
				break;
			}
		}
		if (i == 0)
			return null;
		if (paramString.equals(paramRight.getUrl()))
			return paramRight;
		if ((paramRight.getUrl() != null) && (paramRight.getUrl().endsWith("*")))
			if (paramRight.getType().intValue() == 0) {
				if (paramRight.getVisiturl().equals(paramString))
					return paramRight;
			} else {
				localObject1 = paramRight.getUrl().substring(0, paramRight.getUrl().length() - 1);
				if (paramString.contains((CharSequence) localObject1))
					return paramRight;
			}
		if ((paramRight.getChildRightSet() != null) && (paramRight.getChildRightSet().size() > 0)) {
			localObject1 = paramRight.getChildRightSet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				localObject2 = (Right) ((Iterator) localObject1).next();
				Right localRight = getRightByUrl((Right) localObject2, paramString);
				if (localRight != null)
					return localRight;
			}
		}
		return null;
	}

	private Right getRightByUrl(List<Right> paramList, String paramString) {
		if (paramList == null)
			return null;
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Right localRight = (Right) localIterator.next();
			if (paramString.equals(localRight.getUrl()))
				return localRight;
			if ((localRight.getUrl() != null) && (localRight.getUrl().endsWith("*")))
				if (localRight.getType().intValue() == 0) {
					if (localRight.getVisiturl().equals(paramString))
						return localRight;
				} else {
					Pattern localPattern = Pattern.compile(localRight.getUrl().replaceAll("\\*", ".*"));
					Matcher localMatcher = localPattern.matcher(paramString);
					if (localMatcher.find())
						return localRight;
				}
		}
		return null;
	}

	private List<Right> getRightList(Right paramRight) {
		ArrayList localArrayList = new ArrayList();
		if (paramRight == null)
			return null;
		localArrayList.add(paramRight);
		if ((paramRight.getChildRightSet() != null) && (paramRight.getChildRightSet().size() > 0)) {
			Iterator localIterator = paramRight.getChildRightSet().iterator();
			while (localIterator.hasNext()) {
				Right localRight = (Right) localIterator.next();
				localArrayList.addAll(getRightList(localRight));
			}
		}
		return localArrayList;
	}

	public static void main(String[] paramArrayOfString) {
		GnntBeanFactory.getBeanFactory();
		RightService localRightService = (RightService) ApplicationContextInit.getBean("com_rightService");
		Right localRight = localRightService.loadRightTree();
		WriteLogInterceptor localWriteLogInterceptor = new WriteLogInterceptor();
		long l1 = System.currentTimeMillis();
		long l2 = 100000L;
		for (int i = 0; i < l2; i++)
			localWriteLogInterceptor.getRightByUrl(localRight, "/user/update.action");
		System.out.println("使用递归算法计算" + l2 + "次花费时间：" + (System.currentTimeMillis() - l1) + "毫秒");
		List localList = localWriteLogInterceptor.getRightList(localRight);
		l1 = System.currentTimeMillis();
		for (int j = 0; j < l2; j++)
			localWriteLogInterceptor.getRightByUrl(localList, "/role/delete.sss");
		System.out.println("使用迭代算法计算" + l2 + "次花费时间：" + (System.currentTimeMillis() - l1) + "毫秒");
	}
}