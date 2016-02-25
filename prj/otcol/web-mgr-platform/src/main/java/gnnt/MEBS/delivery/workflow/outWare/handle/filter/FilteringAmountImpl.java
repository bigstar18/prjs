package gnnt.MEBS.delivery.workflow.outWare.handle.filter;

import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.OutWare;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FilteringAmountImpl
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(FilteringAmountImpl.class);
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    int i = 1;
    OutWare localOutWare = (OutWare)paramOriginalModel.getObject();
    EnterWare localEnterWare = this.enterWareService.getEnterWareById(localOutWare.getEnterWareId());
    this.logger.debug("enterWeight::" + localEnterWare.getWeight());
    this.logger.debug("unitWeight::" + localEnterWare.getUnitWeight());
    this.logger.debug("existAmount::" + localEnterWare.getExistAmount());
    this.logger.debug("outWeight::" + localOutWare.getWeight());
    boolean bool = localEnterWare.getExistAmount() - localEnterWare.getFrozenAmount() < localOutWare.getWeight();
    this.logger.debug("flag::" + bool);
    if (bool) {
      i = -8;
    }
    return i;
  }
}
