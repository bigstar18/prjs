package gnnt.MEBS.account.dwr;

import gnnt.MEBS.base.query.hibernate.PageInfo;
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
public class CustomerAdd
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
  
  public Map<String, List> getBrokerageAndManagerList(String organizationNO, String memberNo)
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
  
  public List<Organization> getOrganizationList(String memberNo)
  {
    PageInfo pageInfo = new PageInfo(1, 100000000, "primary.name", true);
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.memberNo", "=", memberNo);
    
    List<Organization> organizationList = this.organizationService.getList(qc, 
      pageInfo);
    
    return organizationList;
  }
  
  public List<Brokerage> getBrokerageListByMember(String memberNo)
  {
    List<Brokerage> returnList = new ArrayList();
    try
    {
      if (!"".equals(memberNo))
      {
        QueryConditions qc = new QueryConditions();
        qc.addCondition("primary.brokerageNo", "not in", 
          "(select bo.brokerageNO from BrokerageAndOrganization bo)");
        qc.addCondition("primary.memberNo", "=", memberNo);
        
        PageInfo pageInfo = new PageInfo(1, 100000000, "primary.name", 
          true);
        returnList = this.brokerageService.getList(qc, pageInfo);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return returnList;
  }
}
