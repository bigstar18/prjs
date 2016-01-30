package gnnt.MEBS.integrated.mgr.action.usernamage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.MyMenu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.integrated.mgr.service.user.RoleService;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;

@Controller("userAction")
@Scope("request")
public class UserAction<T> extends EcsideAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	@Autowired
	@Qualifier("writeOperateLogService")
	private WriteOperateLogService writeOperateLogService;
	@Resource(name = "com_isForbidMap")
	Map<String, String> com_isForbidMap;

	public UserAction() {
		super.setEntityName(User.class.getName());
	}

	public void setEntityName(String paramString) {
	}

	public Map<String, String> getCom_isForbidMap() {
		return this.com_isForbidMap;
	}

	public String userList() throws Exception {
		this.logger.debug("enter userList");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest = getPageRequest(this.request);
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions = (QueryConditions) localPageRequest.getFilters();
		localQueryConditions.addCondition("warehouseID", "=", localUser.getWarehouseID());
		localPageRequest.setSortColumns("order by userId desc");
		listByLimit(localPageRequest);
		return "success";
	}

	public String addUser() {
		User localUser1 = (User) this.request.getSession().getAttribute("CurrentUser");
		User localUser2 = (User) this.entity;
		localUser2.setPassword(MD5.getMD5(localUser2.getUserId(), localUser2.getPassword()));
		localUser2.setWarehouseID(localUser1.getWarehouseID());
		getService().add(localUser2);
		addReturnValue(1, 121001L, new Object[] { localUser2.getUserId() });
		this.writeOperateLogService.writeOperateLog(1202, localUser1, "用户" + localUser2.getUserId() + "添加成功", 1, "");
		return "success";
	}

	public String updateUser() {
		this.logger.debug("enter updateUser");
		User localUser1 = (User) this.entity;
		if ("DEFAULT_ADMIN".equals(localUser1.getType())) {
			localUser1.setRoleSet(null);
		}
		User localUser2 = (User) this.request.getSession().getAttribute("CurrentUser");
		if ("DEFAULT_SUPER_ADMIN".equals(localUser2.getType())) {
			User localUser3 = (User) getService().get(localUser1);
			if ((!localUser1.getType().equals(localUser3.getType())) && ("ADMIN".equals(localUser1.getType()))) {
				PageRequest localPageRequest = new PageRequest(" and primary.user.userId='" + localUser1.getUserId() + "'");
				Page localPage = getService().getPage(localPageRequest, new MyMenu());
				if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
					getService().deleteBYBulk(localPage.getResult());
				}
			}
			if ((!localUser1.getType().equals(localUser3.getType())) && ("ADMIN".equals(localUser3.getType()))) {
				getService().executeUpdateBySql("delete w_user_role where userid='" + localUser1.getUserId() + "'");
			}
		}
		getService().update(localUser1);
		addReturnValue(1, 121002L, new Object[] { localUser1.getUserId() });
		this.writeOperateLogService.writeOperateLog(1202, localUser2, "修改管理员" + localUser1.getUserId(), 1, "");
		return "success";
	}

	public String relatedRightForward() {
		User localUser1 = (User) getService().get(this.entity);
		User localUser2 = (User) this.request.getSession().getAttribute("CurrentUser");
		Boolean localBoolean = (Boolean) this.request.getSession().getAttribute("IsSuperAdmin");
		if (localBoolean.booleanValue()) {
			localUser2.setRoleSet(this.roleService.getRoleByWarehouseID(localUser2.getWarehouseID()));
		}
		this.request.setAttribute("roleList", localUser2.getRoleSet());
		this.request.setAttribute("user", localUser1);
		return "success";
	}

	public String updateRelatedRight() {
		String[] arrayOfString = this.request.getParameterValues("ck");
		User localUser1 = (User) getService().get(this.entity);
		User localUser2 = (User) this.request.getSession().getAttribute("CurrentUser");
		ArrayList localArrayList1 = new ArrayList();
		Set localSet = localUser2.getRoleSet();
		Object localObject1 = localUser1.getRoleSet().iterator();
		int j;
		while (((Iterator) localObject1).hasNext()) {
			Role localRole1 = (Role) ((Iterator) localObject1).next();
			j = 0;
			Iterator localIterator1 = localSet.iterator();
			while (localIterator1.hasNext()) {
				Role localObject2 = (Role) localIterator1.next();
				if (localRole1.getId().longValue() == ((Role) localObject2).getId().longValue()) {
					j = 1;
				}
			}
			if (j == 0) {
				localArrayList1.add(localRole1.getId() + "");
			}
		}
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String localIterator1 : arrayOfString) {
				localArrayList1.add(localIterator1);
			}
		}
		localObject1 = new PageRequest(" and primary.user.userId='" + localUser1.getUserId() + "'");
		Page localPage = getService().getPage((PageRequest) localObject1, new MyMenu());
		HashSet localHashSet = new HashSet();
		Iterator localIterator1 = localArrayList1.iterator();
		while (localIterator1.hasNext()) {
			String localObject2 = (String) localIterator1.next();
			Role localRole2 = new Role();
			localRole2.setId(Long.valueOf(Long.parseLong((String) localObject2)));
			localHashSet.add(localRole2);
			if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
				ArrayList localArrayList2 = new ArrayList();
				localRole2 = (Role) getService().get(localRole2);
				if ((localRole2.getRightSet() != null) && (localRole2.getRightSet().size() > 0)) {
					Iterator localIterator2 = localPage.getResult().iterator();
					while (localIterator2.hasNext()) {
						StandardModel localStandardModel = (StandardModel) localIterator2.next();
						MyMenu localMyMenu = (MyMenu) localStandardModel;
						int k = 0;
						Iterator localIterator3 = localRole2.getRightSet().iterator();
						while (localIterator3.hasNext()) {
							Right localRight = (Right) localIterator3.next();
							if (localMyMenu.getRight().getId() == localRight.getId()) {
								k = 1;
							}
						}
						if (k == 0) {
							localArrayList2.add(localMyMenu);
						}
					}
				}
				getService().deleteBYBulk(localArrayList2);
			}
		}
		localUser1.setRoleSet(localHashSet);
		getService().update(localUser1);
		addReturnValue(1, 121004L, new Object[] { localUser1.getUserId() });
		this.writeOperateLogService.writeOperateLog(1202, localUser2, "修改管理员" + localUser1.getUserId() + "的角色", 1, "");
		return "success";
	}

	public String updatePassword() {
		this.logger.debug("enter password");
		User localUser1 = (User) this.request.getSession().getAttribute("CurrentUser");
		User localUser2 = (User) getService().get(this.entity);
		localUser2.setPassword(MD5.getMD5(localUser2.getUserId(), ((User) this.entity).getPassword()));
		getService().update(localUser2);
		addReturnValue(1, 121003L, new Object[] { localUser2.getUserId() });
		this.writeOperateLogService.writeOperateLog(1202, localUser1, "修改管理员" + localUser2.getUserId() + "的密码", 1, "");
		return "success";
	}
}
