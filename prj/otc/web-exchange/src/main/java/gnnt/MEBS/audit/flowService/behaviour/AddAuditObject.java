package gnnt.MEBS.audit.flowService.behaviour;

import gnnt.MEBS.audit.flowService.Behaviour;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.audit.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddAuditObject
  implements Behaviour
{
  @Autowired
  @Qualifier("applyService")
  private ApplyService applyService;
  
  public int deal(Apply auditObject)
  {
    this.applyService.add(auditObject);
    return 1;
  }
}
