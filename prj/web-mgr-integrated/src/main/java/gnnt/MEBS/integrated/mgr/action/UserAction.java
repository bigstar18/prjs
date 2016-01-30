package gnnt.MEBS.integrated.mgr.action;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Condition;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.MyMenu;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.RoleService;
import gnnt.MEBS.integrated.mgr.integrated.IntegratedGlobal;
import gnnt.MEBS.integrated.mgr.model.usermanage.OnlineUser;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;

@Controller("userAction")
@Scope("request")
public class UserAction extends EcsideAction {
	private static final long serialVersionUID = -5214089717619011935L;
	@Autowired
	@Qualifier("com_roleService")
	private RoleService roleService;
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

	public String updatePassword() {
		this.logger.debug("enter password");
		User localUser = (User) getService().get(this.entity);
		localUser.setPassword(MD5.getMD5(localUser.getUserId(), ((User) this.entity).getPassword()));
		getService().update(localUser);
		addReturnValue(1, 111302L, new Object[] { localUser.getUserId() });
		writeOperateLog(1011, "修改管理员" + localUser.getUserId() + "的密码", 1, "");
		return "success";
	}

	public String addUser() {
		this.logger.debug("enter addUser");
		User localUser = (User) this.entity;
		localUser.setPassword(MD5.getMD5(localUser.getUserId(), ((User) this.entity).getPassword()));
		getService().add(localUser);
		addReturnValue(1, 111401L, new Object[] { localUser.getUserId() });
		writeOperateLog(1011, "添加管理员" + localUser.getUserId(), 1, "");
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
				getService().executeUpdateBySql("delete c_user_role where userid='" + localUser1.getUserId() + "'");
			}
		}
		getService().update(localUser1);
		addReturnValue(1, 111402L, new Object[] { localUser1.getUserId() });
		writeOperateLog(1011, "修改管理员" + localUser1.getUserId(), 1, "");
		return "success";
	}

	public String relatedRightForward() {
		User localUser1 = (User) getService().get(this.entity);
		User localUser2 = (User) this.request.getSession().getAttribute("CurrentUser");
		Boolean localBoolean = (Boolean) this.request.getSession().getAttribute("IsSuperAdmin");
		if (localBoolean.booleanValue()) {
			localUser2.setRoleSet(this.roleService.getAllRole());
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
		Object localObject2;
		while (((Iterator) localObject1).hasNext()) {
			Role localRole1 = (Role) ((Iterator) localObject1).next();
			j = 0;
			Iterator localIterator1 = localSet.iterator();
			while (localIterator1.hasNext()) {
				localObject2 = (Role) localIterator1.next();
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
			localObject2 = (String) localIterator1.next();
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
		addReturnValue(1, 111403L, new Object[] { localUser1.getUserId() });
		writeOperateLog(1011, "修改管理员" + localUser1.getUserId() + "的角色", 1, "");
		return "success";
	}

	public String onlineUserList() {
		this.logger.debug("enter OnlineUserList");
		PageRequest localPageRequest = new PageRequest();
		try {
			localPageRequest = getPageRequest(this.request);
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		QueryConditions localQueryConditions = new QueryConditions();
		try {
			localQueryConditions = getQueryConditionsFromRequest(this.request);
		} catch (Exception localException2) {
			localException2.printStackTrace();
		}
		List localList = localQueryConditions.getConditionList();
		int i = 0;
		if ((localList != null) && (localList.size() > 0)) {
			i = 1;
		}
		ArrayList localArrayList = new ArrayList();
		Object localObject1 = new ArrayList();
		try {
			localObject1 = IntegratedGlobal.getLogonService().getOnLineUserList("mgr", null, "", "");
		} catch (RemoteException localRemoteException1) {
			try {
				IntegratedGlobal.clearLogonService();
				localObject1 = IntegratedGlobal.getLogonService().getOnLineUserList("mgr", null, "", "");
			} catch (MalformedURLException localMalformedURLException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用协议错误" });
			} catch (NotBoundException localNotBoundException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用无服务" });
			} catch (RemoteException localRemoteException2) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "退出用户时连接RMI失败" });
			} catch (Exception localException4) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "调用远程服务异常" });
			}
		} catch (Exception localException3) {
			this.logger.error(localException3.getMessage());
			addReturnValue(-1, 139904L);
		}
		if ((localObject1 != null) && (((List) localObject1).size() > 0)) {
			Iterator localObject2 = ((List) localObject1).iterator();
			while (((Iterator) localObject2).hasNext()) {
				String str = (String) ((Iterator) localObject2).next();
				String[] arrayOfString = str.split(",");
				OnlineUser localOnlineUser = new OnlineUser();
				localOnlineUser.setUserId(arrayOfString[0]);
				localOnlineUser.setLoginIp(arrayOfString[1]);
				localOnlineUser.setLogonTime(arrayOfString[2]);
				if (i != 0) {
					Iterator localIterator = localList.iterator();
					while (localIterator.hasNext()) {
						Condition localCondition = (Condition) localIterator.next();
						if (localOnlineUser.getUserId().contains(localCondition.getValue().toString())) {
							localArrayList.add(localOnlineUser);
						}
					}
				} else {
					localArrayList.add(localOnlineUser);
				}
			}
		}
		Object localObject2 = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localArrayList.size(), localArrayList);
		this.request.setAttribute("pageInfo", localObject2);
		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		return "success";
	}

	public String updateDownOnlineUsers() {
		String[] arrayOfString = this.request.getParameterValues("ids");
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		ArrayList localArrayList = new ArrayList();
		for (Object localObject2 : arrayOfString) {
			localArrayList.add(localObject2);
		}
		CompulsoryLogoffVO vo = new CompulsoryLogoffVO();
		((CompulsoryLogoffVO) vo).setOperator(localUser.getUserId());
		((CompulsoryLogoffVO) vo).setLogonIP(localUser.getIpAddress());
		((CompulsoryLogoffVO) vo).setUserIDList(localArrayList);
		int result = -3;
		try {
			result = IntegratedGlobal.getLogonService().compulsoryLogoff("mgr", (CompulsoryLogoffVO) vo);
		} catch (RemoteException localRemoteException1) {
			try {
				IntegratedGlobal.clearLogonService();
				result = IntegratedGlobal.getLogonService().compulsoryLogoff("mgr", (CompulsoryLogoffVO) vo);
			} catch (MalformedURLException localMalformedURLException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用协议错误" });
			} catch (NotBoundException localNotBoundException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "远程调用无服务" });
			} catch (RemoteException localRemoteException2) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "退出用户时连接RMI失败" });
			} catch (Exception localException) {
				this.logger.error(localRemoteException1.getMessage());
				addReturnValue(-1, 999999L, new Object[] { "调用远程服务异常" });
			}
		}
		if (result == 1) {
			addReturnValue(1, 111404L, new Object[] { ((CompulsoryLogoffVO) vo).getUserIDList() });
			writeOperateLog(1011, "管理员[" + ((CompulsoryLogoffVO) vo).getOperator() + "]在[" + ((CompulsoryLogoffVO) vo).getLogonIP() + "]地址强制退出用户["
					+ ((CompulsoryLogoffVO) vo).getUserIDList() + "]成功", 1, "");
			this.logger.info("管理员[" + ((CompulsoryLogoffVO) vo).getOperator() + "]在[" + ((CompulsoryLogoffVO) vo).getLogonIP() + "]地址强制退出用户["
					+ ((CompulsoryLogoffVO) vo).getUserIDList() + "]成功");
		} else {
			addReturnValue(-1, 131401L, new Object[] { ((CompulsoryLogoffVO) vo).getUserIDList() });
			writeOperateLog(1011, "管理员[" + ((CompulsoryLogoffVO) vo).getOperator() + "]在[" + ((CompulsoryLogoffVO) vo).getLogonIP() + "]地址强制退出用户["
					+ ((CompulsoryLogoffVO) vo).getUserIDList() + "]失败", 0, " 部分AU连接失败或部分AU 处理异常！");
			this.logger.info("管理员[" + ((CompulsoryLogoffVO) vo).getOperator() + "]在[" + ((CompulsoryLogoffVO) vo).getLogonIP() + "]地址强制退出用户["
					+ ((CompulsoryLogoffVO) vo).getUserIDList() + "]失败");
		}
		return "success";
	}
}
