package gnnt.MEBS.common.front.webFrame.strutsInterceptor;

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

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Global;
import gnnt.MEBS.common.front.common.ReturnValue;
import gnnt.MEBS.common.front.model.OperateLog;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.RightService;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ApplicationContextInit;
import gnnt.MEBS.common.front.statictools.GnntBeanFactory;
import gnnt.MEBS.common.front.statictools.Tools;

public class WriteLogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 8669997795536485487L;
	private final transient Log logger = LogFactory.getLog(WriteLogInterceptor.class);

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		ActionContext localActionContext = paramActionInvocation.getInvocationContext();
		HttpServletRequest localHttpServletRequest = (HttpServletRequest) localActionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		String str1 = localHttpServletRequest.getServletPath();
		Right localRight = getRightByUrl(Global.getRightTree(), str1);
		StandardAction localStandardAction = (StandardAction) paramActionInvocation.getAction();
		StandardModel localStandardModel = localStandardAction.getEntity();
		StandardService localStandardService = localStandardAction.getService();
		OperateLog localOperateLog = null;
		Object localObject2;
		if ((localRight != null) && ("Y".equalsIgnoreCase(localRight.getIsWriteLog()))
				&& (localRight.getLogCatalog().getCatalogID().intValue() != 0)) {
			localOperateLog = new OperateLog();
			localOperateLog.setLogType(Integer.valueOf(2));
			User localObject1 = (User) localHttpServletRequest.getSession().getAttribute("CurrentUser");
			localOperateLog.setOperator(((User) localObject1).getTraderID());
			localOperateLog.setOperateIP(((User) localObject1).getIpAddress());
			localOperateLog.setOperateTime(new Date());
			localOperateLog.setLogCatalog(localRight.getLogCatalog());
			localOperateLog.setOperatorType(((User) localObject1).getType());
			localObject2 = "";
			String str2 = paramActionInvocation.getProxy().getMethod();
			if (str2.contains("add")) {
				localObject2 = getAddLogDescription(localStandardModel);
			} else if (str2.contains("delete")) {
				localObject2 = getDeleteLogDescription(localStandardModel, localHttpServletRequest, localStandardService);
			} else if (str2.contains("update")) {
				localObject2 = getUpdateLogDescription(localStandardModel, localStandardService);
			} else if (str2.contains("viewById")) {
				localObject2 = getForUpdateLogDescription(localStandardModel);
			} else if (str2.contains("forwardAdd")) {
				localObject2 = getForAddLogDescription(localStandardModel, localRight);
			} else if (str2.contains("list")) {
				localObject2 = getListLogDescription(localRight);
			} else {
				localObject2 = "点击菜单" + localRight.getName();
			}
			localOperateLog.setOperateContent((String) localObject2);
			if (localStandardModel != null) {
				localOperateLog.setCurrentValue(localStandardModel.serialize());
			}
		}
		localHttpServletRequest.setAttribute("operateLog", localOperateLog);
		Object localObject1 = paramActionInvocation.invoke();
		if (localOperateLog != null) {
			if (((String) localObject1).equals("success")) {
				localOperateLog.setOperateResult(Integer.valueOf(1));
			} else {
				localObject2 = (ReturnValue) localHttpServletRequest.getAttribute("ReturnValue");
				if ((localObject2 != null) && (((ReturnValue) localObject2).getResult() == -1)) {
					localOperateLog.setMark("失败原因:" + ((ReturnValue) localObject2).getInfo());
				}
			}
			localStandardService.add(localOperateLog);
		}
		return (String) localObject1;
	}

	private String getAddLogDescription(StandardModel paramStandardModel) {
		return "添加数据：" + paramStandardModel.translate();
	}

	private String getDeleteLogDescription(StandardModel paramStandardModel, HttpServletRequest paramHttpServletRequest,
			StandardService paramStandardService) {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("ids");
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			Object localObject1 = String.class.getGenericSuperclass();
			int i;
			try {
				if ((paramStandardModel != null) && (paramStandardModel.fetchPKey() != null) && (paramStandardModel.fetchPKey().getKey() != null)) {
					localObject1 = paramStandardModel.getClass().getDeclaredField(paramStandardModel.fetchPKey().getKey()).getType();
				} else {
					String str1 = "删除数据：";
					if (arrayOfString != null) {
						str1 = str1 + "[";
						for (i = 0; i < arrayOfString.length; i++) {
							if (i > 0) {
								str1 = str1 + ",";
							}
							str1 = str1 + arrayOfString[i];
						}
						str1 = str1 + "]";
					}
					return str1;
				}
			} catch (SecurityException localSecurityException) {
				this.logger.error(Tools.getExceptionTrace(localSecurityException));
			} catch (NoSuchFieldException localNoSuchFieldException) {
				this.logger.error(Tools.getExceptionTrace(localNoSuchFieldException));
			}
			Object[] localObject2;
			if (localObject1.equals(Long.class)) {
				localObject2 = new Long[arrayOfString.length];
				for (i = 0; i < arrayOfString.length; i++) {
					localObject2[i] = Long.valueOf(arrayOfString[i]);
				}
			} else if (localObject1.equals(Integer.class)) {
				localObject2 = new Integer[arrayOfString.length];
				for (i = 0; i < arrayOfString.length; i++) {
					localObject2[i] = Integer.valueOf(arrayOfString[i]);
				}
			} else {
				localObject2 = arrayOfString;
			}
			List localList = paramStandardService.getListByBulk(paramStandardModel, (Object[]) localObject2);
			String str2 = "";
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				StandardModel localStandardModel = (StandardModel) localIterator.next();
				str2 = str2 + "删除数据：" + localStandardModel.translate();
			}
			return str2;
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
		if (paramRight == null) {
			return null;
		}
		if (paramString.equals(paramRight.getUrl())) {
			return paramRight;
		}
		Object localObject;
		if ((paramRight.getUrl() != null) && (paramRight.getUrl().endsWith("*"))) {
			if (paramRight.getType().intValue() == 0) {
				if (paramRight.getVisiturl().equals(paramString)) {
					return paramRight;
				}
			} else {
				localObject = paramRight.getUrl().substring(0, paramRight.getUrl().length() - 1);
				if (paramString.contains((CharSequence) localObject)) {
					return paramRight;
				}
			}
		}
		if ((paramRight.getChildRightSet() != null) && (paramRight.getChildRightSet().size() > 0)) {
			localObject = paramRight.getChildRightSet().iterator();
			while (((Iterator) localObject).hasNext()) {
				Right localRight1 = (Right) ((Iterator) localObject).next();
				Right localRight2 = getRightByUrl(localRight1, paramString);
				if (localRight2 != null) {
					return localRight2;
				}
			}
		}
		return null;
	}

	private Right getRightByUrl(List<Right> paramList, String paramString) {
		if (paramList == null) {
			return null;
		}
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Right localRight = (Right) localIterator.next();
			if (paramString.equals(localRight.getUrl())) {
				return localRight;
			}
			if ((localRight.getUrl() != null) && (localRight.getUrl().endsWith("*"))) {
				if (localRight.getType().intValue() == 0) {
					if (localRight.getVisiturl().equals(paramString)) {
						return localRight;
					}
				} else {
					Pattern localPattern = Pattern.compile(localRight.getUrl().replaceAll("\\*", ".*"));
					Matcher localMatcher = localPattern.matcher(paramString);
					if (localMatcher.find()) {
						return localRight;
					}
				}
			}
		}
		return null;
	}

	private List<Right> getRightList(Right paramRight) {
		ArrayList localArrayList = new ArrayList();
		if (paramRight == null) {
			return null;
		}
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
		RightService localRightService = (RightService) ApplicationContextInit.getBean("rightService");
		Right localRight = localRightService.loadRightTree();
		WriteLogInterceptor localWriteLogInterceptor = new WriteLogInterceptor();
		long l1 = System.currentTimeMillis();
		long l2 = 100000L;
		for (int i = 0; i < l2; i++) {
			localWriteLogInterceptor.getRightByUrl(localRight, "/user/update.action");
		}
		System.out.println("使用递归算法计算" + l2 + "次花费时间：" + (System.currentTimeMillis() - l1) + "毫秒");
		List localList = localWriteLogInterceptor.getRightList(localRight);
		l1 = System.currentTimeMillis();
		for (int j = 0; j < l2; j++) {
			localWriteLogInterceptor.getRightByUrl(localList, "/role/delete.sss");
		}
		System.out.println("使用迭代算法计算" + l2 + "次花费时间：" + (System.currentTimeMillis() - l1) + "毫秒");
	}
}
