package gnnt.MEBS.common.broker.action;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionSupport;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.common.broker.common.ReturnValue;
import gnnt.MEBS.common.broker.model.LogCatalog;
import gnnt.MEBS.common.broker.model.OperateLog;
import gnnt.MEBS.common.broker.model.StandardModel;
import gnnt.MEBS.common.broker.model.User;
import gnnt.MEBS.common.broker.service.StandardService;
import gnnt.MEBS.common.broker.statictools.Tools;

@Controller("com_standardAction")
@Scope("request")
public class StandardAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -2324265780415999469L;
	protected final transient Log logger = LogFactory.getLog(getClass());
	public static final String PARAMETERPREFIX = "gnnt_";
	public static final String PAGESIZE = "pageSize";
	public static final String PAGENUMBER = "pageNo";
	public static final String SORTCOLUMNS = "sortColumns";
	public static final String PAGEINFO = "pageInfo";
	public static final String OLDPARAMETER = "oldParams";
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected StandardModel entity;

	@Autowired
	@Qualifier("com_standardService")
	private StandardService standardService;
	private String auditType;

	public void setEntityName(String paramString) {
		if ((paramString == null) || (paramString.length() == 0))
			throw new IllegalArgumentException("业务对象实体类名称不允许为null!");
		if (this.entity == null)
			try {
				this.entity = ((StandardModel) Class.forName(paramString).newInstance());
			} catch (Exception localException) {
				this.logger.error("实例化" + paramString + "发生错误，错误信息：" + localException.getMessage());
				this.logger.error(Tools.getExceptionTrace(localException));
				throw new IllegalArgumentException("无法实例化" + paramString + "!");
			}
	}

	public void setServletRequest(HttpServletRequest paramHttpServletRequest) {
		this.request = paramHttpServletRequest;
	}

	public void setServletResponse(HttpServletResponse paramHttpServletResponse) {
		this.response = paramHttpServletResponse;
	}

	public StandardService getService() {
		return this.standardService;
	}

	public void setStandardService(StandardService paramStandardService) {
		this.standardService = paramStandardService;
	}

	public void setEntity(StandardModel paramStandardModel) {
		this.entity = paramStandardModel;
	}

	public StandardModel getEntity() {
		return this.entity;
	}

	public void setAuditType(String paramString) {
		this.auditType = paramString;
	}

	public String getAuditType() {
		return this.auditType;
	}

	public String forwardAdd() {
		return "success";
	}

	public String add() {
		this.logger.debug("enter add");
		getService().add(this.entity);
		addReturnValue(1, 119901L);
		return "success";
	}

	public String viewById() {
		this.logger.debug("enter viewById");
		this.entity = getService().get(this.entity);
		return "success";
	}

	public String update() {
		this.logger.debug("enter update");
		getService().update(this.entity);
		addReturnValue(1, 119902L);
		return "success";
	}

	public String delete() throws SecurityException, NoSuchFieldException {
		this.logger.debug("enter delete");
		String[] arrayOfString = this.request.getParameterValues("ids");
		if ((arrayOfString == null) || (arrayOfString.length == 0))
			throw new IllegalArgumentException("删除主键数组不能为空！");
		if (this.entity == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0))
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
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
		getService().deleteBYBulk(this.entity, (Object[]) localObject);
		addReturnValue(1, 119903L);
		return "success";
	}

	public String deleteCollection() throws SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		this.logger.debug("enter delete");
		String[] arrayOfString = this.request.getParameterValues("ids");
		if ((arrayOfString == null) || (arrayOfString.length == 0))
			throw new IllegalArgumentException("删除主键数组不能为空！");
		if (this.entity == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许集合 批量删除数据！");
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0))
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过集合 批量删除数据！");
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
		Object localObject1[];
		int i;
		if (localClass.equals(Long.class)) {
			localObject1 = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject1[i] = Long.valueOf(arrayOfString[i]);
		} else if (localClass.equals(Integer.class)) {
			localObject1 = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject1[i] = Integer.valueOf(arrayOfString[i]);
		} else {
			localObject1 = arrayOfString;
		}
		LinkedList localLinkedList = new LinkedList();
		for (Object localObject3 : localObject1) {
			StandardModel localStandardModel = (StandardModel) this.entity.getClass().newInstance();
			String str = localStandardModel.fetchPKey().getKey();
			Field localField = localStandardModel.getClass().getDeclaredField(str);
			if (!localField.isAccessible())
				localField.setAccessible(true);
			localField.set(localStandardModel, localObject3);
			localStandardModel = this.standardService.get(localStandardModel);
			if (localStandardModel != null)
				localLinkedList.add(localStandardModel);
		}
		getService().deleteBYBulk(localLinkedList);
		addReturnValue(1, 119903L);
		return "success";
	}

	public String list() throws Exception {
		this.logger.debug("enter list");
		PageRequest localPageRequest = getPageRequest(this.request);
		Page localPage = getService().getPage(localPageRequest, this.entity);
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}

	public void addReturnValue(int paramInt, long paramLong) {
		addReturnValue(paramInt, paramLong, null);
	}

	public void addReturnValue(int paramInt, long paramLong, Object[] paramArrayOfObject) {
		ReturnValue localReturnValue = new ReturnValue();
		localReturnValue.setResult(paramInt);
		if (paramArrayOfObject == null)
			localReturnValue.addInfo(paramLong);
		else
			localReturnValue.addInfo(paramLong, paramArrayOfObject);
		this.request.setAttribute("ReturnValue", localReturnValue);
	}

	public void writeOperateLog(int paramInt1, String paramString1, int paramInt2, String paramString2) {
		OperateLog localOperateLog = new OperateLog();
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		localOperateLog.setOperator(localUser.getUserId());
		localOperateLog.setOperateIp(localUser.getIpAddress());
		try {
			localOperateLog.setOperateTime(getService().getSysDate());
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
		}
		LogCatalog localLogCatalog = new LogCatalog();
		localLogCatalog.setCatalogID(Integer.valueOf(paramInt1));
		localOperateLog.setLogCatalog(localLogCatalog);
		localOperateLog.setOperateContent(paramString1);
		localOperateLog.setOperateResult(Integer.valueOf(paramInt2));
		localOperateLog.setMark(paramString2);
		getService().add(localOperateLog);
	}

	protected PageRequest<QueryConditions> getPageRequest(HttpServletRequest paramHttpServletRequest) throws Exception {
		PageRequest localPageRequest = new PageRequest();
		localPageRequest.setPageNumber(Tools.strToInt(paramHttpServletRequest.getParameter("pageNo"), 1));
		localPageRequest.setPageSize(Tools.strToInt(paramHttpServletRequest.getParameter("pageSize"), 1000));
		localPageRequest.setSortColumns(paramHttpServletRequest.getParameter("sortColumns"));
		localPageRequest.setFilters(getQueryConditionsFromRequest(paramHttpServletRequest));
		return localPageRequest;
	}

	protected QueryConditions getQueryConditionsFromRequest(HttpServletRequest paramHttpServletRequest) throws Exception {
		QueryConditions localQueryConditions = new QueryConditions();
		this.logger.debug("enter getQueryConditionsFromReq...");
		Map localMap1 = getParametersStartingWith(paramHttpServletRequest, "gnnt_");
		Map localMap2 = getAttributeStartingWith(paramHttpServletRequest, "gnnt_");
		localMap1.putAll(localMap2);
		if ((localMap1 != null) && (localMap1.size() > 0)) {
			this.logger.debug("parameter size:" + localMap1.size());
			Pattern localPattern1 = Pattern.compile("(.+?)\\[(.+)]\\[(.+)]");
			Pattern localPattern2 = Pattern.compile("(.+?)\\[(.+)]");
			Iterator localIterator = localMap1.keySet().iterator();
			while (localIterator.hasNext()) {
				String str4 = (String) localIterator.next();
				Object localObject = localMap1.get(str4);
				this.logger.debug("parameter:" + str4);
				Matcher localMatcher = localPattern1.matcher(str4);
				String str1;
				String str2;
				String str3;
				if (localMatcher.matches()) {
					str1 = localMatcher.group(1);
					str2 = localMatcher.group(2);
					str3 = localMatcher.group(3);
				} else {
					localMatcher = localPattern2.matcher(str4);
					if (localMatcher.matches()) {
						str1 = localMatcher.group(1);
						str2 = localMatcher.group(2);
						str3 = null;
					} else {
						str1 = null;
						str2 = null;
						str3 = null;
					}
				}
				if ((str1 != null) && (localObject != null) && (((String) localObject).trim().length() > 0)) {
					if ((localObject instanceof String))
						localObject = localObject.toString().trim();
					if (localQueryConditions == null)
						localQueryConditions = new QueryConditions();
					if (str3 != null) {
						localObject = Tools.convert(localObject, str3);
						if ((str3.equals("simpledate")) && (str2.equals("=")))
							str1 = "trunc(" + str1 + ")";
					}
					this.logger.debug("field:" + str1);
					this.logger.debug("operator:" + str2);
					this.logger.debug("datatype:" + str3);
					this.logger.debug("value:" + localObject);
					localQueryConditions.addCondition(str1, str2, localObject);
				}
			}
		}
		return localQueryConditions;
	}

	protected Map<String, Object> getParametersStartingWith(ServletRequest paramServletRequest, String paramString) {
		Assert.notNull(paramServletRequest, "Request must not be null");
		Enumeration localEnumeration = paramServletRequest.getParameterNames();
		TreeMap localTreeMap = new TreeMap();
		if (paramString == null)
			paramString = "";
		while ((localEnumeration != null) && (localEnumeration.hasMoreElements())) {
			String str1 = (String) localEnumeration.nextElement();
			if (("".equals(paramString)) || (str1.startsWith(paramString))) {
				String str2 = str1.substring(paramString.length());
				String[] arrayOfString = paramServletRequest.getParameterValues(str1);
				if ((arrayOfString != null) && (arrayOfString.length != 0))
					if (arrayOfString.length > 1)
						localTreeMap.put(str2, arrayOfString);
					else
						localTreeMap.put(str2, arrayOfString[0]);
			}
		}
		return localTreeMap;
	}

	protected Map<String, String> getAttributeStartingWith(ServletRequest paramServletRequest, String paramString) {
		Enumeration localEnumeration = paramServletRequest.getAttributeNames();
		TreeMap localTreeMap = new TreeMap();
		if (paramString == null)
			paramString = "";
		while ((localEnumeration != null) && (localEnumeration.hasMoreElements())) {
			String str1 = (String) localEnumeration.nextElement();
			if (("".equals(paramString)) || (str1.startsWith(paramString))) {
				String str2 = str1.substring(paramString.length());
				String str3 = (String) paramServletRequest.getAttribute(str1);
				if (str3 != null)
					localTreeMap.put(str2, str3.trim());
			}
		}
		return localTreeMap;
	}
}