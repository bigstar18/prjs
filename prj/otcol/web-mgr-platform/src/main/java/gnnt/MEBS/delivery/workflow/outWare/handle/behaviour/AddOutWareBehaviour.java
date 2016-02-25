package gnnt.MEBS.delivery.workflow.outWare.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.OutWareService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddOutWareBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddOutWareBehaviour.class);
  @Autowired
  @Qualifier("w_outWareService")
  private OutWareService outWareService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    this.outWareService.addOutEnter((OutWare)paramWorkFlowClone);
    this.logger.debug("添加仓库申请表addOutWareBehaviour");
  }
}
