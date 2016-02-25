package gnnt.MEBS.delivery.workflow.regStockApply.handle.filter;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.services.DealerService;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FilteringFirmImpl
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(FilteringFirmImpl.class);
  @Autowired
  @Qualifier("w_dealerService")
  private DealerService dealService;
  
  public int checkFiler(OriginalModel paramOriginalModel)
  {
    int i = 1;
    RegStockApply localRegStockApply = (RegStockApply)paramOriginalModel.getObject();
    String str = localRegStockApply.getFirmId();
    Dealer localDealer = this.dealService.getDealerById(str);
    if (localDealer == null) {
      i = -10;
    }
    return i;
  }
}
