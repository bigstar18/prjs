package cn.com.agree.eteller.usermanager.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.exception.ServiceException;
import cn.com.agree.eteller.generic.utils.CommonType;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfoPK;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;

public class ListRole extends GenericAction {
	private static final long serialVersionUID = 7632330310303981137L;
	@Resource(name = "userManagerTarget")
	private IUserManager userMg;
	private Rolelist role;
	private List<Rolelist> roleList;
	private String appid;
	private List<CommonType> apps;
	private List<Funclist> toSelectFuncList;
	private List<Funclist> selectedFuncList;
	private Map functionsInSubApp;
	private Rolelist roleToCh;

	@Action(value = "ListRole", results = { @org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/ListRole.jsp") })
	public String list() throws Exception {
		LoginUser user = (LoginUser) this.session.getAttribute("user");
		if ("root".equals(user.getUserId())) {
			this.roleList = this.userMg.getAllEntity(Rolelist.class, this.page);
		} else {
			if (this.rollPage.booleanValue()) {
				this.roleList = ((List) this.session.getAttribute("list"));
			} else {
				this.roleList = this.userMg.getRoleUnderlingList(user.getRole().getRoleId());
				this.session.setAttribute("list", this.roleList);
			}
			this.roleList = Pagination.splitPageWithAllRecords(this.roleList, this.page);
		}
		return "success";
	}

	@Action(value = "CreateRole", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String add() throws Exception {
		try {
			this.userMg.checkCycleSuperior(this.role);
			this.role.setFunctions(new HashSet(this.selectedFuncList));
			this.userMg.add(this.role);
		} catch (ServiceException e) {
			this.dwzResp.exceptionForward(e);
			return "success";
		}
		this.dwzResp.successForward("添加级别成功");
		return "success";
	}

	@Action(value = "ModifyRole", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String modify() throws Exception {
		if ((this.role.getFinalFlag() == '1') && (this.userMg.hasRoleUnderling(this.role.getRoleId()))) {
			this.dwzResp.errorForward("该级别有下属级别");
			return "success";
		}
		try {
			this.userMg.checkCycleSuperior(this.role);
			this.role.setFunctions(new HashSet(this.selectedFuncList));
			this.userMg.updateEntity(this.role);
		} catch (ServiceException e) {
			this.dwzResp.exceptionForward(e);
			return "success";
		}
		this.dwzResp.successForward("修改级别成功");
		return "success";
	}

	@Action(value = "CreateInfoRole", results = {
			@org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/CreateRole.jsp") })
	public String addInfo() throws Exception {
		this.toSelectFuncList = getToSelectList();

		this.roleList = Arrays.asList(this.userMg.getAllRole());

		this.apps = getSubappIdList();

		return "success";
	}

	@Action(value = "ModifyInfoRole", results = {
			@org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/ModifyRole.jsp") })
	public String modifyInfo() throws Exception {
		if ((this.role != null) && (this.role.getRoleId() != null)) {
			this.role = this.userMg.getRole(this.role.getRoleId());
			this.selectedFuncList = new ArrayList(this.userMg.getRoleFunctions(this.role.getRoleId()));
		}
		return addInfo();
	}

	@Action(value = "SelectListInfoRole", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "excludeProperties",
			"role,roleList,appid,apps,selectedFuncList" }) })
	public String toSelectListInfo() throws Exception {
		this.toSelectFuncList = getToSelectList();
		for (int i = 0; i < this.toSelectFuncList.size(); i++) {
			((Funclist) this.toSelectFuncList.get(i)).setRoles(null);
		}
		return "success";
	}

	@Action(value = "RemoveRole", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String remove() throws Exception {
		String errMsg = "删除失败";
		if ((this.roleList == null) || (this.roleList.size() == 0)) {
			errMsg = "请选择待删除的记录";
			this.dwzResp.errorForward(errMsg);
			return "success";
		}
		for (int i = 0; i < this.roleList.size(); i++) {
			try {
				this.userMg.deleteRole(((Rolelist) this.roleList.get(i)).getRoleId());
			} catch (RuntimeException e) {
				errMsg = "选中的第" + (i + 1) + "条记录删除失败：" + e.getMessage();
				e.printStackTrace();
				this.dwzResp.exceptionForward(e);
				return "success";
			}
		}
		errMsg = "本次共成功删除" + this.roleList.size() + "条记录！";
		this.dwzResp.ajaxSuccessForward(errMsg);

		return "success";
	}

	private List<Funclist> getToSelectList() {
		String[] appids = (String[]) getFunctionsInsubApp().keySet().toArray(new String[0]);
		if (this.appid == null) {
			HashMap subapp = (HashMap) getFunctionsInsubApp().get(appids[0]);
			String[] subapps = (String[]) subapp.keySet().toArray(new String[0]);
			return (ArrayList) subapp.get(subapps[0]);
		}
		int count = 0;
		for (int i = 0; i < appids.length; i++) {
			HashMap subapp = (HashMap) getFunctionsInsubApp().get(appids[i]);
			String[] subapps = (String[]) subapp.keySet().toArray(new String[0]);
			for (int t = 0; t < subapps.length; t++) {
				if (count++ == Integer.parseInt(this.appid)) {
					return (ArrayList) subapp.get(subapps[t]);
				}
			}
		}
		return null;
	}

	private List<CommonType> getSubappIdList() {
		this.functionsInSubApp = null;
		String[] appids = (String[]) getFunctionsInsubApp().keySet().toArray(new String[0]);
		Appinfo[] apps = new Appinfo[appids.length];
		ArrayList<CommonType> list = new ArrayList();
		int count = 0;
		for (int i = 0; i < appids.length; i++) {
			HashMap subapp = (HashMap) getFunctionsInsubApp().get(appids[i]);
			String[] subappinfo = (String[]) subapp.keySet().toArray(new String[0]);
			EtellerSubappinfo[] subapps = new EtellerSubappinfo[subappinfo.length];
			for (int t = 0; t < subappinfo.length; t++) {
				if (subappinfo[t].equals("00000")) {
					Appinfo app1 = this.userMg.getAppinfo(appids[i]);
					EtellerSubappinfo subapps1 = new EtellerSubappinfo();
					EtellerSubappinfoPK subapps1pk = new EtellerSubappinfoPK();
					subapps1pk.setAppid("10000");
					subapps1pk.setSubappid("10000");
					subapps1.setComp_id(subapps1pk);

					subapps1.setAppname(app1.getAppname());
					list.add(new CommonType(String.valueOf(count++), subapps1.getAppname()));
				} else {
					subapps[t] = this.userMg.getSubAppinfo(subappinfo[t]);
					Appinfo app1 = this.userMg.getAppinfo(appids[i]);
					if (app1 != null) {
						subapps[t].setAppname(app1.getAppname() + "-->" + subapps[t].getAppname());
					}
					list.add(new CommonType(String.valueOf(count++), subapps[t].getAppname()));
				}
			}
		}
		return list;
	}

	private Map getFunctionsInsubApp() {
		if (this.functionsInSubApp == null) {
			this.functionsInSubApp = new HashMap();

			LoginUser user = (LoginUser) this.session.getAttribute("user");
			Funclist[] funcs;
			if ("root".equals(user.getUserId())) {
				funcs = this.userMg.getAllFunction();
			} else {
				funcs = this.userMg.getFunctionByRoleId(this.userMg.getUser(user.getUserId()).getRoleId());
			}
			for (int i = 0; i < funcs.length; i++) {
				String subappid = funcs[i].getSubappid();
				String appid = funcs[i].getAppid();
				HashMap SubAppmap = null;
				SubAppmap = (HashMap) this.functionsInSubApp.get(appid);
				if (SubAppmap == null) {
					SubAppmap = new HashMap();
					this.functionsInSubApp.put(appid, SubAppmap);
				}
				SubAppmap.put(subappid, new ArrayList());
			}
			String[] appids = (String[]) getFunctionsInsubApp().keySet().toArray(new String[0]);
			for (int i = 0; i < appids.length; i++) {
				HashMap tt = (HashMap) this.functionsInSubApp.get(appids[i]);
				String[] subappids = (String[]) tt.keySet().toArray(new String[0]);
				for (int k = 0; k < subappids.length; k++) {
					ArrayList funcListsub = null;
					funcListsub = (ArrayList) tt.get(subappids[k]);
					for (int n = 0; n < funcs.length; n++) {
						String subappid = funcs[n].getSubappid();
						if ((subappid.equals(subappids[k])) && (funcs[n].getAppid().equals(appids[i]))) {
							funcListsub.add(funcs[n]);
						}
					}
				}
			}
		}
		return this.functionsInSubApp;
	}

	public String superiorRoleName(Long superiorRoleId) {
		if ((this.roleToCh != null) && (this.roleToCh.getRoleId().equals(superiorRoleId))) {
			return this.roleToCh.getRoleName();
		}
		this.roleToCh = ((Rolelist) this.userMg.getEntity(Rolelist.class, superiorRoleId));
		return this.roleToCh.getRoleName();
	}

	public void setRole(Rolelist role) {
		this.role = role;
	}

	public Rolelist getRole() {
		return this.role;
	}

	public List<Rolelist> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(List<Rolelist> roleList) {
		this.roleList = roleList;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public List<Funclist> getToSelectFuncList() {
		return this.toSelectFuncList;
	}

	public void setToSelectFuncList(List<Funclist> toSelectFuncList) {
		this.toSelectFuncList = toSelectFuncList;
	}

	public List<Funclist> getSelectedFuncList() {
		return this.selectedFuncList;
	}

	public void setSelectedFuncList(List<Funclist> selectedFuncList) {
		this.selectedFuncList = selectedFuncList;
	}

	public List<CommonType> getApps() {
		return this.apps;
	}

	public void setApps(List<CommonType> apps) {
		this.apps = apps;
	}
}
