package gnnt.MEBS.common.mgr.beanforajax;

import gnnt.MEBS.common.mgr.common.ActiveUserManager;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.service.UserService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 为多个系统信息统一化服务的 Ajax 类
 * 
 * @author liuzx
 */
@Controller("com_ajaxForCommunications")
@Scope("request")
public class AjaxForCommunications extends BaseAjax{

	/**
	 * 注入标准service实例
	 */
	@Autowired
	@Qualifier("com_userService")
	private UserService userService;

	/**
	 * 获取Action使用的service实例
	 * 
	 * @return service对象
	 */
	public StandardService getService() {
		return this.userService;
	}

	/**
	 * 通过 Ajax 修改本系统的风格<br/>
	 * 参数：<font color="#FF0000">skin:风格名称</font>
	 * 
	 * @return String
	 */
	public String changeStyle() {
		HttpServletRequest request = this.getRequest();

		User user = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);
		// 如果用户不为空，则本用户修改风格
		if (user != null) {
			user.setSkin(((User) userService.getUserByID(user.getUserId()))
					.getSkin());
			request.getSession().setAttribute(Global.CURRENTUSERSTR, user);
		}

		jsonValidateReturn = createJSONArray(true);

		return result();
	}

	/**
	 * 通过 Ajax 执行本系统的推出
	 * 
	 * @return String
	 */
	public String logout() {
		try {
			ActiveUserManager.logoff(this.getRequest());
		} catch (Exception e) {
		}
		jsonValidateReturn = createJSONArray(true);
		return result();
	}

	/**
	 * 通过 Ajax 调用本方法，如果登录用户已经过期，则会在 URL 检查时，通过 sessionID 重新获取 登录信息
	 */
	public String sessionGetUser() {
		HttpServletRequest request = this.getRequest();

		User user = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);

		if (user != null) {
			jsonValidateReturn = createJSONArray(true);
		} else {
			jsonValidateReturn = createJSONArray(false);
		}

		return result();
	}
}
