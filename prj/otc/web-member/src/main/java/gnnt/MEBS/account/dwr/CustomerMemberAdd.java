package gnnt.MEBS.account.dwr;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.BrokerageAndOrganization;
import gnnt.MEBS.broke.model.BrokerageVO;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageAndOrganizationService;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.BrokerageVOService;
import gnnt.MEBS.broke.service.ManagerService;
import gnnt.MEBS.broke.service.OrganizationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerMemberAdd
{
  @Autowired
  @Qualifier("brokerageAndOrganizationService")
  private BrokerageAndOrganizationService brokerageAndOrganizationService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("brokerageVOService")
  private BrokerageVOService brokerageVOService;
  @Autowired
  @Qualifier("managerService")
  private ManagerService managerService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  private final transient Log logger = LogFactory.getLog(CustomerAdd.class);
  
  public Map<String, List> getBrokerageAndManagerList(String organizationNO, String sessionOrgNo, String memberNo)
  {
    Map<String, List> map = new HashMap();
    try
    {
      List<BrokerageAndOrganization> brokerageAndOrganizationList = new ArrayList();
      List<BrokerageVO> brokerageList = new ArrayList();
      List<Manager> managerList = new ArrayList();
      
      QueryConditions qc = new QueryConditions();
      qc.addCondition("primary.memberNo", "=", memberNo);
      if ((organizationNO != null) && (!"".equals(organizationNO))) {
        qc.addCondition("primary.orgNo", "=", organizationNO);
      } else {
        qc.addCondition("primary.orgNo", "is", "null");
      }
      brokerageList = this.brokerageVOService.getListForSimple(qc, null);
      map.put("brokerageList", brokerageList);
      map.put("managerList", managerList);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return map;
  }
  
  public List<Organization> getOrganizationList(String memberNo, String sessionOrgNo)
  {
    QueryConditions qc = new QueryConditions();
    if ((sessionOrgNo != null) && (!"".equals(sessionOrgNo)))
    {
      String organizationNos = "(";
      organizationNos = organizationNos + "select view.actualOrganizationNo from gnnt.MEBS.common.model.OrganizationRelatedSelf  view where view.organizationNo='" + 
        sessionOrgNo + "')";
      qc.addCondition("primary.organizationNO", "in", organizationNos);
    }
    List<Organization> organizationList = this.organizationService.getByMemberNoList(qc, 
      memberNo);
    
    return organizationList;
  }
  
  public List<Brokerage> getBrokerageListByMember(String memberNo, String sessionOrgNo)
  {
    List<Brokerage> returnList = new ArrayList();
    if (!"".equals(memberNo))
    {
      QueryConditions qc = new QueryConditions();
      qc.addCondition("primary.memberNo", "=", memberNo);
      qc.addCondition("primary.brokerageNo", "not in", 
        "(select bo.brokerageNO from BrokerageAndOrganization bo)");
      if ((sessionOrgNo != null) && (!"".equals(sessionOrgNo)))
      {
        String brokerageNos = "(";
        brokerageNos = brokerageNos + "select view.brokerageNo from gnnt.MEBS.common.model.BrokerageRelateOrganization  view where view.organizationNo='" + 
          sessionOrgNo + "')";
      }
      returnList = this.brokerageService.getList(qc, null);
    }
    return returnList;
  }
}
