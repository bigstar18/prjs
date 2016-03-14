package gnnt.MEBS.common.action;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.services.RoleService;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysData;

public class CommonRightController extends BaseController {
	private final transient Log logger = LogFactory.getLog(CommonRightController.class);

	public ModelAndView commonRightUserList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//--[CommonRightController]--enter commonRightList()---//");
		UserService localUserService = (UserService) SysData.getBean("userService");
		User localUser = localUserService.loadUserById(paramHttpServletRequest.getParameter("userId"), true, true, true);
		RightService localRightService = (RightService) SysData.getBean("rightService");
		Right localRight = localRightService.getTreeRight();
		ModelAndView localModelAndView = new ModelAndView("common/users/assignUserRights");
		localModelAndView.addObject("treeRight", localRight);
		localModelAndView.addObject("user", localUser);
		return localModelAndView;
	}

	public ModelAndView commonRightRoleList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		this.logger.debug("//--[CommonRightController]--enter commonRightRoleList()---//");
		String str = paramHttpServletRequest.getParameter("roleId");
		RoleService localRoleService = (RoleService) SysData.getBean("roleService");
		Role localRole = localRoleService.loadRoleById(Long.parseLong(str), true, false);
		Set localSet = localRole.getRightSet();
		Object localObject = localSet.iterator();
		while (((Iterator) localObject).hasNext()) {
			Right localRight = (Right) ((Iterator) localObject).next();
			this.logger.debug("r.id:" + localRight.getId());
		}
		localObject = (RightService) SysData.getBean("rightService");
		Right localRight = ((RightService) localObject).getTreeRight();
		ModelAndView localModelAndView = new ModelAndView("common/roles/assignRoleRights");
		localModelAndView.addObject("treeRight", localRight);
		localModelAndView.addObject("role", localRole);
		return localModelAndView;
	}

	public ModelAndView commonRightSaveUserRights(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		this.logger.debug("//--[CommonUserController]--enter commonRightSaveUserRights()---//");
		String str = paramHttpServletRequest.getParameter("userId");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
		UserService localUserService = (UserService) SysData.getBean("userService");
		localUserService.saveUserRights(str, arrayOfString);
		ModelAndView localModelAndView = new ModelAndView("common/users/assignUserRights");
		localModelAndView.addObject("resultMsg", "保存管理员[" + str + "]权限成功!");
		localModelAndView.addObject("modSuccess", "modSuccess!");
		return localModelAndView;
	}

	public ModelAndView commonRightSaveRoleRights(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		this.logger.debug("//--[CommonUserController]--enter commonRightSaveRoleRights()---//");
		String str = paramHttpServletRequest.getParameter("roleId");
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
		RoleService localRoleService = (RoleService) SysData.getBean("roleService");
		localRoleService.saveUserRights(str, arrayOfString);
		ModelAndView localModelAndView = new ModelAndView("common/roles/assignRoleRights");
		localModelAndView.addObject("resultMsg", "保存角色[" + str + "]权限成功!");
		localModelAndView.addObject("modSuccess", "modSuccess!");
		return localModelAndView;
	}
}
