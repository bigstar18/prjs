package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class FrozenNewRegStock
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(FrozenNewRegStock.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public void deal(SettleObject paramSettleObject)
  {
    String str = paramSettleObject.getChangeRegStockId();
    if (str != null)
    {
      SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
      RegStock localRegStock = this.regStockService.getRegStockForUpdate(str);
      double d = localRegStock.getFrozenWeight();
      d = Arith.add(d, localSettleMatch.getWeight());
      localRegStock.setFrozenWeight(d);
      this.regStockService.updateRegStock(localRegStock);
    }
  }
}
