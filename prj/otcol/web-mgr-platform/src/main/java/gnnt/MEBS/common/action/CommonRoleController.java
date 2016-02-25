package gnnt.MEBS.common.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.services.RoleService;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysData;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import gnnt.MEBS.common.util.query.QueryHelper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

public class CommonRoleController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(CommonRoleController.class);
  
  public ModelAndView commonRoleDirect(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleDirect()---//");
    return commonRoleList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView commonRoleList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleList()---//");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, 20, "id", false);
    }
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    List localList = localRoleService.getRoleList(localQueryConditions, localPageInfo);
    ModelAndView localModelAndView = new ModelAndView("common/roles/rolesList");
    localModelAndView.addObject("rolesList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    Map localMap = WebUtils.getParametersStartingWith(paramHttpServletRequest, "_");
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView commonUserReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonUserReturn()---//");
    return returnRolesList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView returnRolesList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonRoleController]--enter returnUsersList()---//");
    return getModelAndView("rolesList");
  }
  
  public ModelAndView commonRoleView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleView()---//");
    String str = paramHttpServletRequest.getParameter("roleId");
    long l = Long.parseLong(str);
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    Role localRole = localRoleService.getRoleById(l);
    ModelAndView localModelAndView = new ModelAndView("common/roles/modRole");
    localModelAndView.addObject("role", localRole);
    return localModelAndView;
  }
  
  public ModelAndView commonRoleReturn(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleReturn()---//");
    return returnRolesList(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public ModelAndView commonRoleAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleAdd()---//");
    Role localRole = new Role();
    ParamUtil.bindData(paramHttpServletRequest, localRole);
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    boolean bool = localRoleService.addRole(localRole);
    ModelAndView localModelAndView = new ModelAndView("common/roles/addRole");
    localModelAndView.addObject("resultMsg", bool ? "添加新角色成功!" : "角色代码已存在!");
    localModelAndView.addObject("addSuccess", "addSuccess");
    return localModelAndView;
  }
  
  public ModelAndView commonRoleAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleAddForward()---//");
    return new ModelAndView("common/roles/addRole");
  }
  
  public ModelAndView commonRoleDelete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleDelete()---//");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    localRoleService.deleteRoles(arrayOfString);
    ModelAndView localModelAndView = commonRoleList(paramHttpServletRequest, paramHttpServletResponse);
    localModelAndView.addObject("resultMsg", "删除成功!");
    return localModelAndView;
  }
  
  public ModelAndView commonRoleModForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleModForward()---//");
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    long l = Long.parseLong(paramHttpServletRequest.getParameter("id"));
    Role localRole = localRoleService.loadRoleById(l, true, true);
    ParamUtil.bindData(paramHttpServletRequest, localRole);
    localRoleService.updateRole(localRole);
    ModelAndView localModelAndView = new ModelAndView("common/roles/modRole");
    localModelAndView.addObject("resultMsg", "修改角色信息成功!");
    localModelAndView.addObject("modSuccess", "modSuccess");
    return localModelAndView;
  }
  
  public ModelAndView commonRoleUserAssociate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleUserAssociate()---//");
    String str = paramHttpServletRequest.getParameter("roleId");
    long l = Long.parseLong((str == null) || ("".equals(str.trim())) ? "-1" : str);
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    Role localRole = localRoleService.loadRoleById(l, true, true);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo();
    }
    ModelAndView localModelAndView = new ModelAndView("common/roles/roleAssociateUsers");
    localModelAndView.addObject("role", localRole);
    if ((localRole.getUserSet() != null) && (localRole.getUserSet().size() > 0)) {
      localModelAndView.addObject("userSet", localRole.getUserSet());
    }
    return localModelAndView;
  }
  
  public ModelAndView commonRoleSaveAssociation(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleSaveAssociation()---//");
    String str = paramHttpServletRequest.getParameter("roleId");
    long l = Long.parseLong((str == null) || ("".equals(str.trim())) ? "-1" : str);
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("ck");
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    localRoleService.saveAssociation(l, arrayOfString);
    ModelAndView localModelAndView = new ModelAndView("common/roles/addAssociation");
    localModelAndView.addObject("resultMsg", "保存角色与管理员关联关系成功!");
    localModelAndView.addObject("saveAssociationSuccess", "saveAssociationSuccess");
    return localModelAndView;
  }
  
  public ModelAndView commonRoleAddAssociation(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleAddAssociation()---//");
    String str = paramHttpServletRequest.getParameter("roleId");
    long l = Long.parseLong((str == null) || ("".equals(str.trim())) ? "-1" : str);
    UserService localUserService = (UserService)SysData.getBean("userService");
    List localList = localUserService.getUserNoRoleById(l);
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    Role localRole = localRoleService.getRoleById(l);
    ModelAndView localModelAndView = new ModelAndView("common/roles/addAssociation");
    if (localList.size() > 0) {
      localModelAndView.addObject("userList", localList);
    }
    localModelAndView.addObject("role", localRole);
    return localModelAndView;
  }
  
  public ModelAndView commonRoleDeleteAssociation(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleDeleteAssociation()---//");
    String str = paramHttpServletRequest.getParameter("roleId");
    long l = Long.parseLong((str == null) || ("".equals(str.trim())) ? "-1" : str);
    RoleService localRoleService = (RoleService)SysData.getBean("roleService");
    localRoleService.removeRoleAndUserRelated(l, paramHttpServletRequest.getParameterValues("ck"));
    ModelAndView localModelAndView = new ModelAndView("common/roles/roleAssociateUsers");
    Role localRole = localRoleService.loadRoleById(l, true, true);
    localModelAndView.addObject("role", localRole);
    if ((localRole.getUserSet() != null) && (localRole.getUserSet().size() > 0)) {
      localModelAndView.addObject("userSet", localRole.getUserSet());
    }
    localModelAndView.addObject("resultMsg", "更新角色与管理员关联关系成功!");
    localModelAndView.addObject("roleId", str);
    return localModelAndView;
  }
}
