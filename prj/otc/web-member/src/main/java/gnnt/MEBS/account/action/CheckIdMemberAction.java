package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.FirmService;
import gnnt.MEBS.trade.service.TradeTimeService;
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
  @Qualifier("tradeTimeService")
  private TradeTimeService tradeTimeService;
  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  @Qualifier("roleService")
  private RoleService roleService;
  @Autowired
  @Qualifier("firmService")
  private FirmService firmService;
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
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
  
  public boolean existCustomerPapers(String papersName, String papersType, String customerNo)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersName", "=", papersName);
    qc.addCondition("primary.papersType", "=", Integer.valueOf(Integer.parseInt(papersType)));
    if (!"".equals(customerNo)) {
      qc.addCondition("primary.customerNo", "!=", customerNo);
    }
    List list = null;
    list = this.customerService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existMemberPapers(String papersName, String papersType, String memberNo)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.papersName", "=", papersName);
    qc.addCondition("primary.papersType", "=", Integer.valueOf(Integer.parseInt(papersType)));
    if (!"".equals(memberNo)) {
      qc.addCondition("primary.id", "!=", memberNo);
    }
    List list = null;
    list = this.memberInfoService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existId(String id)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", id);
    List list = this.firmService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
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
  
  public boolean existSectionid(Long sectionid)
  {
    boolean exist = false;
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.sectionId", "=", sectionid);
    List list = this.tradeTimeService.getList(qc, null);
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public InService getService()
  {
    return this.memberInfoService;
  }
  
  public String existCustomerIds(String customerId, String userId)
  {
    customerId = customerId.replaceAll(" ", "");
    String wrongCustomerId = "";
    QueryConditions qc = null;
    List<Customer> customerList = null;
    String[] customerArr = customerId.replaceAll("，", ",").split(",");
    for (String string : customerArr)
    {
      qc = new QueryConditions();
      qc.addCondition("primary.memberNo", "=", ((User)this.userService.getById(userId)).getMemberNo());
      qc.addCondition("primary.customerNo", "=", string);
      qc.addCondition("primary.customerStatus.status", "!=", "D");
      customerList = this.customerService.getList(qc, null);
      if (customerList.size() == 0)
      {
        wrongCustomerId = string;
        break;
      }
    }
    return wrongCustomerId;
  }
}
