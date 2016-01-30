package gnnt.MEBS.common.front.action;

import java.lang.reflect.Field;
import java.util.LinkedList;

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

import com.opensymphony.xwork2.ActionSupport;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.ReturnValue;
import gnnt.MEBS.common.front.model.LogCatalog;
import gnnt.MEBS.common.front.model.OperateLog;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.AbstractService;
import gnnt.MEBS.common.front.service.QueryService;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ActionUtil;

@Controller("com_standardAction")
@Scope("request")
public class StandardAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -2324265780415999469L;
	protected final transient Log logger = LogFactory.getLog(getClass());
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected StandardModel entity;
	@Autowired
	@Qualifier("com_standardService")
	private StandardService com_standardService;
	@Autowired
	@Qualifier("com_queryService")
	private QueryService queryService;
	private String auditType;

	public void setEntityName(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			throw new IllegalArgumentException("业务对象实体类名称不允许为null!");
		}
		if (this.entity == null) {
			try {
				this.entity = ((StandardModel) Class.forName(paramString).newInstance());
			} catch (Exception localException) {
				this.logger.error("实例化" + paramString + "发生错误，错误信息：" + localException.getMessage());
				localException.printStackTrace();
				throw new IllegalArgumentException("无法实例化" + paramString + "!");
			}
		}
	}

	public void setServletRequest(HttpServletRequest paramHttpServletRequest) {
		this.request = paramHttpServletRequest;
	}

	public void setServletResponse(HttpServletResponse paramHttpServletResponse) {
		this.response = paramHttpServletResponse;
	}

	public StandardService getService() {
		return this.com_standardService;
	}

	public AbstractService getAbstractService(PageRequest<?> paramPageRequest) {
		AbstractService localStandardService = paramPageRequest.isQueryDB() ? getQueryService() : getService();
		return localStandardService;
	}

	public void setStandardService(StandardService paramStandardService) {
		this.com_standardService = paramStandardService;
	}

	public QueryService getQueryService() {
		return this.queryService;
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
		addReturnValue(1, 9910002L);
		return "success";
	}

	public String viewById() throws Exception {
		this.logger.debug("enter viewById");
		PageRequest localPageRequest = ActionUtil.getPageRequest(this.request);
		AbstractService localStandardService = localPageRequest.isQueryDB() ? getQueryService() : getService();
		this.entity = localStandardService.get(this.entity);
		return "success";
	}

	public String update() {
		this.logger.debug("enter update");
		getService().update(this.entity);
		addReturnValue(1, 9910003L);
		return "success";
	}

	public String delete() throws SecurityException, NoSuchFieldException {
		this.logger.debug("enter delete");
		String[] arrayOfString = this.request.getParameterValues("ids");
		if ((arrayOfString == null) || (arrayOfString.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}
		if (this.entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		}
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		}
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
		Object[] localObject;
		int i;
		if (localClass.equals(Long.class)) {
			localObject = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++) {
				localObject[i] = Long.valueOf(arrayOfString[i]);
			}
		} else if (localClass.equals(Integer.class)) {
			localObject = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++) {
				localObject[i] = Integer.valueOf(arrayOfString[i]);
			}
		} else {
			localObject = arrayOfString;
		}
		getService().deleteBYBulk(this.entity, (Object[]) localObject);
		addReturnValue(1, 9910004L);
		return "success";
	}

	public String deleteCollection() throws SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		this.logger.debug("enter delete");
		String[] arrayOfString = this.request.getParameterValues("ids");
		if ((arrayOfString == null) || (arrayOfString.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}
		if (this.entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许集合 批量删除数据！");
		}
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过集合 批量删除数据！");
		}
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
		Object[] localObject1;
		int i;
		if (localClass.equals(Long.class)) {
			localObject1 = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++) {
				localObject1[i] = Long.valueOf(arrayOfString[i]);
			}
		} else if (localClass.equals(Integer.class)) {
			localObject1 = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++) {
				localObject1[i] = Integer.valueOf(arrayOfString[i]);
			}
		} else {
			localObject1 = arrayOfString;
		}
		LinkedList localLinkedList = new LinkedList();
		for (Object localObject3 : localObject1) {
			StandardModel localStandardModel = (StandardModel) this.entity.getClass().newInstance();
			String str = localStandardModel.fetchPKey().getKey();
			Field localField = localStandardModel.getClass().getDeclaredField(str);
			if (!localField.isAccessible()) {
				localField.setAccessible(true);
			}
			localField.set(localStandardModel, localObject3);
			localStandardModel = this.com_standardService.get(localStandardModel);
			if (localStandardModel != null) {
				localLinkedList.add(localStandardModel);
			}
		}
		getService().deleteBYBulk(localLinkedList);
		addReturnValue(1, 9910004L);
		return "success";
	}

	public String list() throws Exception {
		this.logger.debug("enter list");
		PageRequest localPageRequest = ActionUtil.getPageRequest(this.request);
		AbstractService localStandardService = localPageRequest.isQueryDB() ? getQueryService() : getService();
		Page localPage = localStandardService.getPage(localPageRequest, this.entity);
		this.request.setAttribute("pageInfo", localPage);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}

	public void addReturnValue(int paramInt, long paramLong) {
		addReturnValue(paramInt, paramLong, null);
	}

	public void addReturnValue(int paramInt, long paramLong, Object[] paramArrayOfObject) {
		addReturnValue(paramInt, paramLong, paramArrayOfObject, -1);
	}

	public void addReturnValue(int paramInt1, long paramLong, Object[] paramArrayOfObject, int paramInt2) {
		ReturnValue localReturnValue = new ReturnValue();
		localReturnValue.setResult(paramInt1);
		localReturnValue.setResultType(paramInt2);
		if (paramArrayOfObject == null) {
			localReturnValue.addInfo(paramLong);
		} else {
			localReturnValue.addInfo(paramLong, paramArrayOfObject);
		}
		this.request.setAttribute("ReturnValue", localReturnValue);
	}

	public void writeOperateLog(int paramInt1, String paramString1, int paramInt2, String paramString2) {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		OperateLog localOperateLog = new OperateLog();
		localOperateLog.setLogType(Integer.valueOf(2));
		LogCatalog localLogCatalog = new LogCatalog();
		localLogCatalog.setCatalogID(Integer.valueOf(paramInt1));
		localOperateLog.setLogCatalog(localLogCatalog);
		localOperateLog.setOperateContent(paramString1);
		localOperateLog.setOperateResult(Integer.valueOf(paramInt2));
		localOperateLog.setMark(paramString2);
		localOperateLog.setOperator(localUser.getBelongtoFirm().getFirmID());
		localOperateLog.setOperateIP(localUser.getIpAddress());
		getService().add(localOperateLog);
	}
}
