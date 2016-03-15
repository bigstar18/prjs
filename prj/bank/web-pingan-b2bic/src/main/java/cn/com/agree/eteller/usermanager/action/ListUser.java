package cn.com.agree.eteller.usermanager.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import cn.com.agree.eteller.afa.persistence.EtellerCheckapp;
import cn.com.agree.eteller.afa.spring.IAfaManager;
import cn.com.agree.eteller.generic.action.GenericAction;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.ConfigUtil;
import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.utils.TellerUtil;
import cn.com.agree.eteller.generic.utils.Tools;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.persistence.Department;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.persistence.Userlist;
import cn.com.agree.eteller.usermanager.spring.IUserManager;

public class ListUser extends GenericAction {
	private static final long serialVersionUID = 841204797722544155L;
	@Resource(name = "userManagerTarget")
	private IUserManager userMg;
	@Resource(name = "afaManagerTarget")
	private IAfaManager afaMg;
	private String returnMsg;
	private String tablename = "Userlist";
	private String oldRecord;
	private EtellerCheckapp checkapp;
	private List<EtellerCheckapp> checkappsList;
	private Map<String, String> condition;
	private String msg;
	private Userlist u;
	private List<Userlist> uList;
	private List<Rolelist> roleList;
	private List<Department> deptList;

	@Action(value = "ListUser", results = { @org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/ListUser.jsp") })
	public String list() throws Exception {
		LoginUser loginUser = (LoginUser) session.getAttribute("user");
		long time1 = System.currentTimeMillis();
		if (rollPage.booleanValue()) {
			uList = (List) session.getAttribute("list");
			roleList = (List) session.getAttribute("roleList");
			deptList = (List) session.getAttribute("deptList");
		} else {
			List dataList = userMg.getUnderlingList(loginUser.getUserId(), condition);
			uList = (List) dataList.get(0);
			long time2 = System.currentTimeMillis();
			logger.debug((new StringBuilder("获取用户列表用时：")).append((time2 - time1) / 1000L).append("秒").toString());
			if ("root".equals(loginUser.getUserId())) {
				roleList = Arrays.asList(userMg.getAllRole());
				deptList = Arrays.asList(userMg.getAllDepartment());
			} else {
				roleList = (List) dataList.get(1);
				deptList = (List) dataList.get(2);
			}
			int count = roleList.size() <= deptList.size() ? deptList.size() : roleList.size();
			for (Iterator iterator = uList.iterator(); iterator.hasNext();) {
				Userlist ul = (Userlist) iterator.next();
				for (int i = 0; i < count; i++) {
					if (ul.getRole() != null && ul.getDept() != null)
						break;
					Rolelist r = i >= roleList.size() ? null : (Rolelist) roleList.get(i);
					Department d = i >= deptList.size() ? null : (Department) deptList.get(i);
					if (ul.getRole() == null && r != null && r.getRoleId().equals(ul.getRoleId()))
						ul.setRole(r);
					if (ul.getDept() == null && d != null && d.getId().equals(ul.getDepartmentId()))
						ul.setDept(d);
				}

			}

			session.setAttribute("list", uList);
			session.setAttribute("roleList", roleList);
			session.setAttribute("deptList", deptList);
		}
		uList = Pagination.splitPageWithAllRecords(uList, page);
		return "success";
	}

	@Action(value = "CreateInfoUser", results = {
			@org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/CreateUser.jsp") })
	public String addInfo() throws Exception {
		if ("root".equals(TellerUtil.getLoginTeller().getUserId())) {
			this.roleList = this.userMg.getRootRoleList();
		} else {
			this.roleList = this.userMg.getRoleUnderlingList(TellerUtil.getLoginTeller().getRole().getRoleId());
		}
		if (!ComFunction.isEmpty(this.roleList)) {
			this.deptList = getRoleDetpList(((Rolelist) this.roleList.get(0)).getRoleId().longValue());
		} else {
			this.deptList = new ArrayList();
		}
		return "success";
	}

	@Action(value = "ModifyInfoUser", results = {
			@org.apache.struts2.convention.annotation.Result(location = "/WEB-INF/jsp/usermanager/ModifyUser.jsp") })
	public String modifyInfo() throws Exception {
		if ((this.u != null) && (this.u.getUserId() != null)) {
			this.u = this.userMg.getUser(this.u.getUserId());
		} else if ((this.uList != null) && (this.uList.size() == 1)) {
			this.u = this.userMg.getUser(((Userlist) this.uList.get(0)).getUserId());
		}
		if ("root".equals(TellerUtil.getLoginTeller().getUserId())) {
			this.roleList = this.userMg.getRoleList();
		} else {
			this.roleList = this.userMg.getRoleUnderlingList(TellerUtil.getLoginTeller().getRole().getRoleId());
		}
		return "success";
	}

	@Action(value = "CreateUser", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String add() throws Exception {
		if (this.userMg.getUser(this.u.getTellerName()) != null) {
			this.dwzResp.errorForward("用户已存在");
			return "dwz";
		}
		this.returnMsg = "error";
		LoginUser user = (LoginUser) this.session.getAttribute("user");

		this.u.setUserId(this.u.getTellerName());
		this.u.setTellerPasswd(Tools.INITIAL_PASSWORD);
		this.u.setTellerState("0");
		this.u.setEncryptMethod("1");

		ConfigUtil conf = new ConfigUtil("config/init.properties");
		if (conf.getString("addUserChangePassword").equals("0")) {
			this.u.setPwdInit("1");
		} else {
			this.u.setPwdInit("0");
		}
		this.u.setPwdModDate(ComFunction.getCurrentDate());
		this.u.setLoginStatus("0");
		this.u.setResetLogin("0");

		this.u.setDepartmentId("999999");
		try {
			this.userMg.addUser(this.u);
		} catch (RuntimeException e) {
			e.printStackTrace();
			this.dwzResp.exceptionForward(e);
			return "success";
		}
		this.returnMsg = ("创建用户[" + this.u.getUserId() + "]成功！");

		this.dwzResp.successForward(this.returnMsg);
		this.dwzResp.setAlertClose(Boolean.valueOf(false));
		return "success";
	}

	@Action(value = "ModifyUser", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String modify() throws Exception {
		this.returnMsg = "error";

		this.userMg.update(this.u);
		this.returnMsg = "修改成功";

		this.dwzResp.successForward(this.returnMsg);
		return "success";
	}

	@Action(value = "RemoveUser", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = { "root", "dwzResp" }) })
	public String remove() throws Exception {
		LoginUser user = (LoginUser) this.session.getAttribute("user");
		this.returnMsg = "删除失败！";
		if ((this.uList == null) || (this.uList.size() == 0)) {
			this.returnMsg = "请选择待删除的记录";
			this.dwzResp.ajaxErrorForward(this.returnMsg);
			return "success";
		}
		for (int i = 0; i < this.uList.size(); i++) {
			this.u = this.userMg.getUser(((Userlist) this.uList.get(i)).getUserId());
			try {
				this.userMg.delete(this.u);
			} catch (RuntimeException e) {
				this.returnMsg = ("选中的第" + (i + 1) + "笔记录删除失败！");
				e.printStackTrace();
				this.dwzResp.ajaxSuccessForward(this.returnMsg);
				return "success";
			}
		}
		this.returnMsg = ("本次成功删除" + this.uList.size() + "条记录！");
		this.dwzResp.ajaxSuccessForward(this.returnMsg);
		return "success";
	}

	@Action(value = "ListUser_DeptSelectList", results = { @org.apache.struts2.convention.annotation.Result(type = "json", params = {
			"excludeProperties", "returnMsg,checkapp,checkappsList,condition,u,roleList,uList" }) })
	public String deptSelectList() throws Exception {
		this.deptList = getRoleDetpList(this.u.getRoleId().longValue());
		for (Department d : this.deptList) {
			d.setRoles(null);
			d.setSuperiorDept(null);
		}
		return "success";
	}

	private List<Department> getRoleDetpList(long roleId) {
		String departmentId = this.userMg.getUser(TellerUtil.getLoginTeller().getUserId()).getDepartmentId();
		List depList = this.userMg.getSubDepartmentWithCurrentList(departmentId);
		return this.userMg.getRoleDepartmentList(Long.valueOf(roleId), depList);
	}

	public Userlist getU() {
		return this.u;
	}

	public void setU(Userlist u) {
		this.u = u;
	}

	public List<Userlist> getUList() {
		return this.uList;
	}

	public void setUList(List<Userlist> uList) {
		this.uList = uList;
	}

	public List<Rolelist> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(List<Rolelist> roleList) {
		this.roleList = roleList;
	}

	public List<Department> getDeptList() {
		return this.deptList;
	}

	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}

	public void setCondition(Map<String, String> condition) {
		this.condition = condition;
	}

	public Map<String, String> getCondition() {
		return this.condition;
	}

	public List<Userlist> getuList() {
		return this.uList;
	}

	public void setuList(List<Userlist> uList) {
		this.uList = uList;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReturnMsg() {
		return this.returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getOldRecord() {
		return this.oldRecord;
	}

	public void setOldRecord(String oldRecord) {
		this.oldRecord = oldRecord;
	}

	public EtellerCheckapp getCheckapp() {
		return this.checkapp;
	}

	public void setCheckapp(EtellerCheckapp checkapp) {
		this.checkapp = checkapp;
	}

	public List<EtellerCheckapp> getCheckappsList() {
		return this.checkappsList;
	}

	public void setCheckappsList(List<EtellerCheckapp> checkappsList) {
		this.checkappsList = checkappsList;
	}
}
