package gnnt.MEBS.delivery.workflow.enterWare.handle.filter;

import gnnt.MEBS.delivery.model.workflow.EnterApply;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.services.EnterApplyService;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.common.ProcessContextImpl;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FilteringAbilityImpl
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(FilteringAbilityImpl.class);
  @Autowired
  @Qualifier("w_enterApplyService")
  private EnterApplyService enterApplyService;
  @Autowired
  @Qualifier("enterApplyProcessContext")
  private ProcessContextImpl enterApplyProcessContext;
  
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    int i = 1;
    EnterWare localEnterWare = (EnterWare)paramOriginalModel.getObject();
    EnterApply localEnterApply = this.enterApplyService.getEnterApplyById(localEnterWare.getEnterInformId());
    int j = ((Integer)this.enterApplyProcessContext.getFinalStatus().get(0)).intValue();
    if (localEnterApply.getAbility() != j) {
      i = -8;
    }
    return i;
  }
}
