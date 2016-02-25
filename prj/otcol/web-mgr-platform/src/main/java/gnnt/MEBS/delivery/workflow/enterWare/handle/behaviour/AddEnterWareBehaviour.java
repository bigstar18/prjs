package gnnt.MEBS.delivery.workflow.enterWare.handle.behaviour;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.Behaviour;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AddEnterWareBehaviour
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(AddEnterWareBehaviour.class);
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  private int informAbility;
  
  public void setInformAbility(int paramInt)
  {
    this.informAbility = paramInt;
  }
  
  public void deal(WorkFlowClone paramWorkFlowClone)
  {
    EnterWare localEnterWare = (EnterWare)paramWorkFlowClone;
    this.enterWareService.addEnterWare(localEnterWare);
    String str = localEnterWare.getEnterInformId();
    EnterApply localEnterApply = this.enterApplyService.getEnterApplyById(str);
    localEnterApply.setInformAbility(this.informAbility);
    this.enterApplyService.updateEnterApply(localEnterApply);
    this.logger.debug("添加入库单申请表AddEnterWareBehaviour");
  }
}
