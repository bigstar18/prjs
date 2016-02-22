package gnnt.MEBS.account.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.query.hibernate.QueryHelper;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CommonRoleAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonRoleAction.class);
  @Autowired
  @Qualifier("roleService")
  private InService roleService;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  private List rolesList;
  private PageInfo pageInfo;
  
  public PageInfo getPageInfo()
  {
    return this.pageInfo;
  }
  
  public void setPageInfo(PageInfo pageInfo)
  {
    this.pageInfo = pageInfo;
  }
  
  public List getRolesList()
  {
    return this.rolesList;
  }
  
  public void setRolesList(List rolesList)
  {
    this.rolesList = rolesList;
  }
  
  public InService getService()
  {
    return this.roleService;
  }
  
  public String returnRolesList()
  {
    this.logger.debug("//--[CommonRoleController]--enter returnUsersList()---//");
    return "success";
  }
  
  public String commonRoleUserAssociate()
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleUserAssociate()---//");
    String roleId = this.request.getParameter("roleId");
    long id = 
      Long.parseLong((roleId == null) || ("".equals(roleId.trim())) ? "-1" : 
      roleId);
    Role role = (Role)this.roleService.getById(Long.valueOf(id));
    if ((role.getUserSet() != null) && (role.getUserSet().size() > 0))
    {
      this.request.setAttribute("userSet", role.getUserSet());
      EcsideUtil.setRowAttributes(this.request, role.getUserSet().size());
    }
    this.request.setAttribute("role", role);
    return "success";
  }
  
  public String commonRoleSaveAssociation()
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleSaveAssociation()---//");
    String roleId = this.request.getParameter("id");
    long id = 
      Long.parseLong((roleId == null) || ("".equals(roleId.trim())) ? "-1" : 
      roleId);
    String[] userIds = this.request.getParameterValues("ck");
    String description = "";
    String name = "";
    Role role = (Role)this.roleService.getById(Long.valueOf(id));
    for (String userId : userIds) {
      name = name + ((User)this.userService.getById(userId)).getName() + ",";
    }
    description = 
      "添加" + role.getName() + "的关联管理员:" + name.substring(0, name.length() - 1);
    saveLog(description, role);
    ((RoleService)this.roleService).saveAssociation(id, userIds);
    this.request.setAttribute("resultMsg", "保存角色与管理员关联关系成功!");
    this.request.setAttribute("resultValue", Integer.valueOf(1));
    return "success";
  }
  
  public String commonRoleAddAssociation()
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleAddAssociation()---//");
    String roleId = this.request.getParameter("id");
    long id = 
      Long.parseLong((roleId == null) || ("".equals(roleId.trim())) ? "-1" : 
      roleId);
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    List<User> userList = this.userService.getUserNoRoleById(id, qc);
    Role role = (Role)this.roleService.getById(Long.valueOf(id));
    if (userList.size() > 0) {
      this.request.setAttribute("userList", userList);
    }
    this.request.setAttribute("role", role);
    returnBaseMsg(null);
    return "success";
  }
  
  public String commonRoleDeleteAssociation()
    throws Exception
  {
    this.logger.debug("//--[CommonRoleController]--enter commonRoleDeleteAssociation()---//");
    String roleId = this.request.getParameter("roleId");
    long id = 
      Long.parseLong((roleId == null) || ("".equals(roleId.trim())) ? "-1" : 
      roleId);
    String[] ids = this.request.getParameterValues("ids");
    String description = "";
    String name = "";
    Role role = (Role)this.roleService.getById(Long.valueOf(id));
    for (String userId : ids) {
      name = name + ((User)this.userService.getById(userId)).getName() + ",";
    }
    description = 
      "删除" + role.getName() + "的关联管理员:" + name.substring(0, name.length() - 1);
    saveLog(description, role);
    ((RoleService)this.roleService).removeRoleAndUserRelated(id, ids);
    this.request.setAttribute("resultMsg", "更新角色与管理员关联关系成功!");
    this.request.setAttribute("updateSuccess", "updateSuccess");
    this.request.setAttribute("roleId", roleId);
    return "success";
  }
  
  public String copyRole()
  {
    int resultValue = ((RoleService)this.roleService).copy(this.obj, 
      this.request.getAttribute("original_roleId"));
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] idString = id.split(",");
        this.obj.setPrimary(idString[0]);
        Clone clone = getService().get(this.obj);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        
        resultValue = getService().delete(this.obj);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
}
