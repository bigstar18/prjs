package gnnt.MEBS.audit.flowService.behaviour;

import gnnt.MEBS.audit.businessService.BusinessService;
import gnnt.MEBS.audit.flowService.Behaviour;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.base.copy.XmlToMap;
import java.util.Map;

public class BusinessDeal
  implements Behaviour
{
  private BusinessService businessService;
  
  public void setBusinessService(BusinessService businessService)
  {
    this.businessService = businessService;
  }
  
  public int deal(Apply auditObject)
  {
    int result = -1;
    String xml = auditObject.getContent();
    Map<String, String> businessObjectMap = XmlToMap.xmlToMap(xml);
    result = this.businessService.business(businessObjectMap);
    return result;
  }
}
