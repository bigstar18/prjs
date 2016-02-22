package gnnt.MEBS.account.action;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.service.RightService;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CommonRightAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonRightAction.class);
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  @Qualifier("rightService")
  private InService rightService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
  private Right treeRight;
  
  public Right getTreeRight()
  {
    return this.treeRight;
  }
  
  public void setTreeRight(Right treeRight)
  {
    this.treeRight = treeRight;
  }
  
  public InService getService()
  {
    return this.rightService;
  }
  
  public String commonRightUserList()
    throws Exception
  {
    this.logger.debug("//--[CommonRightController]--enter commonRightList()---//");
    User user = this.userService.loadUserById(this.request.getParameter("userId"), true, true, true);
    this.treeRight = ((RightService)this.rightService).getTreeRight();
    this.request.setAttribute("user", user);
    return "success";
  }
  
  public String commonRightRoleList()
    throws Exception
  {
    this.logger.debug("//--[CommonRightController]--enter commonRightRoleList()---//");
    String roleId = this.request.getParameter("roleId");
    Role role = this.roleService.loadRoleById(Long.parseLong(roleId), true, false);
    Set<Right> rSet = role.getRightSet();
    for (Right r : rSet) {
      this.logger.debug("r.id:" + r.getId());
    }
    this.treeRight = ((RightService)this.rightService).getTreeRight();
    this.request.setAttribute("role", role);
    
    return "success";
  }
  
  public String commonRightSaveUserRights()
  {
    this.logger.debug("//--[CommonUserController]--enter commonRightSaveUserRights()---//");
    String userId = this.request.getParameter("userId");
    String[] rightIds = this.request.getParameterValues("ck");
    this.userService.saveUserRights(userId, rightIds);
    this.request.setAttribute("resultMsg", "保存管理员[" + userId + "]权限成功!");
    this.request.setAttribute("modSuccess", "modSuccess!");
    return "success";
  }
  
  public String commonRightSaveRoleRights()
  {
    this.logger.debug("//--[CommonUserController]--enter commonRightSaveRoleRights()---//");
    String roleId = this.request.getParameter("roleId");
    String[] rightIds = this.request.getParameterValues("ck");
    int result = -1;
    try
    {
      this.roleService.saveUserRights(roleId, rightIds);
      result = 3;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    addResultMsg(this.request, result);
    return "success";
  }
}
