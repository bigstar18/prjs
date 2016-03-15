package cn.com.agree.eteller.generic.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

import cn.com.agree.eteller.generic.utils.Pagination;
import cn.com.agree.eteller.generic.vo.DwzResponse;
import cn.com.agree.eteller.generic.vo.LoginUser;

@Controller
@Scope("prototype")
@ParentPackage("eteller-default")
@Namespace("/")
public class GenericAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	protected static Logger logger = null;
	public static final String DWZ = "dwz";
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	protected HttpSession session;
	private ActionProxy proxy;
	private String navTabId;
	protected DwzResponse dwzResp = new DwzResponse();
	protected Pagination page = new Pagination();
	protected Boolean rollPage = Boolean.valueOf(false);

	public GenericAction() {
		this.proxy = ActionContext.getContext().getActionInvocation().getProxy();
	}

	public ActionProxy getProxy() {
		return this.proxy;
	}

	public String getNamespace() {
		String namespace = this.proxy.getNamespace();
		if ("/".equals(namespace)) {
			namespace = "";
		}
		return namespace;
	}

	public boolean hasPermission(String func) {
		LoginUser user = (LoginUser) this.session.getAttribute("user");
		return user.getFuncs().contains(func);
	}

	public boolean addShow() {
		String actionName = this.proxy.getActionName();
		if (!actionName.startsWith("List")) {
			return true;
		}
		return hasPermission("Create" + actionName.substring(4));
	}

	public boolean modifyShow() {
		String actionName = this.proxy.getActionName();
		if (!actionName.startsWith("List")) {
			return true;
		}
		return hasPermission("Modify" + actionName.substring(4));
	}

	public boolean deleteShow() {
		String actionName = this.proxy.getActionName();
		if (!actionName.startsWith("List")) {
			return true;
		}
		return hasPermission("Delete" + actionName.substring(4));
	}

	public void setServletResponse(HttpServletResponse resp) {
		this.resp = resp;
	}

	public void setServletRequest(HttpServletRequest req) {
		this.req = req;
		this.session = req.getSession();
	}

	public Pagination getPage() {
		return this.page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public DwzResponse getDwzResp() {
		return this.dwzResp;
	}

	public void setDwzResp(DwzResponse dwzResp) {
		this.dwzResp = dwzResp;
	}

	public String getNavTabId() {
		return this.navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public Boolean getRollPage() {
		return this.rollPage;
	}

	public void setRollPage(Boolean rollPage) {
		this.rollPage = rollPage;
	}
}
