package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ReleaseOldRegStock
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(ReleaseOldRegStock.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public void deal(SettleObject settleObject)
  {
    SettleMatch settleMatch = settleObject.getSettleMatch();
    
    String regStockId = settleMatch.getRegStockId();
    if (regStockId != null)
    {
      RegStock regStock = this.regStockService.getRegStockForUpdate(regStockId);
      this.regStockService.releaseAmount(regStock, settleMatch.getWeight(), 2);
    }
  }
}
