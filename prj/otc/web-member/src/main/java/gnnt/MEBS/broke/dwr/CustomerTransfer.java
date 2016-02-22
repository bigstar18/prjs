package gnnt.MEBS.broke.dwr;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.BrokerageAndOrganization;
import gnnt.MEBS.broke.model.Manager;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.BrokerageAndOrganizationService;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.ManagerService;
import gnnt.MEBS.broke.service.OrganizationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CustomerTransfer
{
  @Autowired
  @Qualifier("brokerageAndOrganizationService")
  private BrokerageAndOrganizationService brokerageAndOrganizationService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("managerService")
  private ManagerService managerService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public Map<String, List> getBrokerageAndManagerList(String organizationNO)
  {
    Map<String, List> map = new HashMap();
    List<BrokerageAndOrganization> brokerageAndOrganizationList = new ArrayList();
    List<Brokerage> brokerageList = new ArrayList();
    List<Manager> managerList = new ArrayList();
    brokerageAndOrganizationList = this.brokerageAndOrganizationService.getList(new QueryConditions("primary.organizationNO", "=", organizationNO), null);
    for (BrokerageAndOrganization brokerageAndOrganization : brokerageAndOrganizationList)
    {
      Brokerage brokerage = (Brokerage)this.brokerageService.getById(brokerageAndOrganization.getId());
      if (brokerage.getMemberNo().equals(((Organization)this.organizationService.getById(organizationNO)).getMemberNo())) {
        brokerageList.add(brokerage);
      }
    }
    QueryConditions queryConditions = new QueryConditions("primary.parentOrganizationNO", "=", organizationNO);
    queryConditions.addCondition("primary.memberNo", "=", ((Organization)this.organizationService.getById(organizationNO)).getMemberNo());
    managerList = this.managerService.getList(queryConditions, null);
    map.put("brokerageList", brokerageList);
    map.put("managerList", managerList);
    return map;
  }
}
