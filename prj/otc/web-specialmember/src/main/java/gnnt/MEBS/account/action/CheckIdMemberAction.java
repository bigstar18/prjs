package gnnt.MEBS.account.action;

import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CheckIdMemberAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CheckIdMemberAction.class);
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
  
  public RoleService getRoleService()
  {
    return this.roleService;
  }
  
  public UserService getUserService()
  {
    return this.userService;
  }
  
  public SpecialMemberService getSpecialMemberService()
  {
    return this.specialMemberService;
  }
  
  public MemberInfoService getMemberInfoService()
  {
    return this.memberInfoService;
  }
  
  public CustomerService getCustomerService()
  {
    return this.customerService;
  }
  
  public boolean existUserId(String userId)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("userId", "=", userId);
    List list = this.userService.getUserList(qc, null, false, false, false);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existRoleId(String RoleId)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.id", "=", Long.valueOf(Long.parseLong(RoleId)));
    List list = this.roleService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existUserName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.userService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existRoleName(String id, String name, String memberNo)
  {
    boolean exist = false;
    try
    {
      QueryConditions qc = new QueryConditions();
      qc.addCondition("primary.name", "=", name);
      qc.addCondition("primary.memberNo", "=", memberNo);
      if ((id != null) && (!"".equals(id))) {
        qc.addCondition("primary.id", "!=", id);
      }
      List list = this.roleService.getList(qc, null);
      exist = false;
      if ((list != null) && (list.size() > 0)) {
        exist = true;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return exist;
  }
  
  public boolean existMemberName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.memberInfoService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existSpecialMemberName(String name)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.name", "=", name);
    List list = this.specialMemberService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public InService getService()
  {
    return null;
  }
}
