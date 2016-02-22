package gnnt.MEBS.broke.action;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.broke.service.ManagerService;
import gnnt.MEBS.broke.service.OrganizationService;
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
public class CheckAddIdAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CheckAddIdAction.class);
  @Autowired
  @Qualifier("managerService")
  private ManagerService managerService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public BrokerageService getBrokerageService()
  {
    return this.brokerageService;
  }
  
  public OrganizationService getOrganizationService()
  {
    return this.organizationService;
  }
  
  public InService getService()
  {
    return this.managerService;
  }
  
  public boolean existOrganizationId(String id)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.organizationNO", "=", id);
    List list = this.organizationService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existBokerageId(String id)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.brokerageNo", "=", id);
    List list = this.brokerageService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
  
  public boolean existManagerId(String id)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.managerNo", "=", id);
    List list = this.managerService.getList(qc, null);
    boolean exist = false;
    if ((list != null) && (list.size() > 0)) {
      exist = true;
    }
    return exist;
  }
}
