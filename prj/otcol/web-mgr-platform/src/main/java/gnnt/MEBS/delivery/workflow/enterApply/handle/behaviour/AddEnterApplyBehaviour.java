package gnnt.MEBS.delivery.workflow.enterApply.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddEnterApplyBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddEnterApplyBehaviour.class);
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    this.enterApplyService.addEnterApply((EnterApply)paramWorkFlowClone);
    this.logger.debug("添加仓库申请表AddEnterApplyBehaviour");
  }
}
