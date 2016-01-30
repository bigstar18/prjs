package gnnt.MEBS.integrated.mgr.action.usernamage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.LogCatalog;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.model.OperateLogHis;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;

@Controller("roleAction")
@Scope("request")
public class RoleAction extends EcsideAction {
	private static final long serialVersionUID = 6138762149239689538L;
	@Autowired
	@Qualifier("writeOperateLogService")
	private WriteOperateLogService writeOperateLogService;
	@Resource(name = "com_operatorTypeMap")
	Map<String, String> com_operatorTypeMap;

	public Map<String, String> getCom_operatorTypeMap() {
		return this.com_operatorTypeMap;
	}

	public void setCom_operatorTypeMap(Map<String, String> paramMap) {
		this.com_operatorTypeMap = paramMap;
	}

	public String forwardAddRole() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if ((localUser != null) && (localUser.getWarehouseID() != null)) {
			this.request.setAttribute("wareHouseID", localUser.getWarehouseID());
		}
		return "success";
	}

	public String listRole() throws Exception {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest = getPageRequest(this.request);
		QueryConditions localQueryConditions = (QueryConditions) localPageRequest.getFilters();
		localQueryConditions.addCondition("primary.warehouseID", "=", localUser.getWarehouseID());
		localPageRequest.setSortColumns("order by id ");
		listByLimit(localPageRequest);
		return "success";
	}

	public String addRole() {
		this.logger.debug("enter add");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		Role localRole = (Role) this.entity;
		getService().add(localRole);
		addReturnValue(1, 119901L);
		this.writeOperateLogService.writeOperateLog(1201, localUser, "添加角色" + localRole.getName() + "成功", 1, "");
		return "success";
	}

	public String updateForwardRole() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		if ((localUser != null) && (localUser.getWarehouseID() != null)) {
			this.request.setAttribute("wareHouseID", localUser.getWarehouseID());
		}
		try {
			super.viewById();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "success";
	}

	public String updateRole() {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		Role localRole = (Role) this.entity;
		getService().update(localRole);
		addReturnValue(1, 119902L);
		this.writeOperateLogService.writeOperateLog(1201, localUser, "修改角色" + localRole.getName() + "成功", 1, "");
		return "success";
	}

	public String deleteRole() throws NoSuchFieldException, Exception {
		try {
			Role localRole = new Role();
			String[] arrayOfString = this.request.getParameterValues("ids");
			String str1 = "";
			for (String str2 : arrayOfString) {
				if (!"".equals(str1)) {
					str1 = str1 + ",";
				}
				Long localLong = Long.valueOf(Tools.strToLong(str2));
				localRole.setId(localLong);
				localRole = (Role) getService().get(localRole);
				str1 = str1 + localRole.getName();
				getService().executeUpdateBySql("delete w_role_right t where t.roleid = " + localLong);
				getService().delete(localRole);
			}
			addReturnValue(1, 119903L);
			User user = (User) this.request.getSession().getAttribute("CurrentUser");
			this.writeOperateLogService.writeOperateLog(1201, (User) user, "删除角色" + str1 + "成功", 1, "");
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
		}
		return "success";
	}

	public String operateLogList() throws Exception {
		this.logger.debug("enter operateLogList");
		String str1 = this.request.getParameter("type");
		if ((str1 != null) && (str1.equals("H"))) {
			this.entity = new OperateLogHis();
			str1 = "H";
		} else {
			this.entity = new OperateLog();
			str1 = "D";
		}
		PageRequest localPageRequest1 = new PageRequest("");
		Page localPage = getService().getPage(localPageRequest1, new LogCatalog());
		List localList = localPage.getResult();
		PageRequest localPageRequest2 = getPageRequest(this.request);
		QueryConditions localQueryConditions = (QueryConditions) localPageRequest2.getFilters();
		Map localMap = getParametersStartingWith(this.request, "gnnt_");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		localQueryConditions.addCondition("primary.wareHouseID", "=", localUser.getWarehouseID());
		if ((localMap != null) && (localMap.size() == 0)) {
			String str2 = Tools.fmtDate(new Date());
			String str3 = Tools.combineDateTime(str2);
			String str4 = Tools.combineDateTime(str2, 1);
			localQueryConditions.addCondition("primary.operateTime", ">=", Tools.strToDateTime(str3));
			localQueryConditions.addCondition("primary.operateTime", "<=", Tools.strToDateTime(str4));
		}
		localPageRequest2.setSortColumns("order by operateTime desc");
		listByLimit(localPageRequest2);
		this.request.setAttribute("type", str1);
		this.request.setAttribute("logList", localList);
		return "success";
	}
}
