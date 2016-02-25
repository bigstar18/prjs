package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckChangeRegStock
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckChangeRegStock.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    String str = paramSettleObject.getChangeRegStockId();
    if (str != null)
    {
      SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
      RegStock localRegStock = this.regStockService.getRegStockForUpdate(str);
      double d1 = localRegStock.getWeight();
      this.logger.debug("weight:" + d1);
      double d2 = localRegStock.getFrozenWeight();
      this.logger.debug("frozenWeight:" + d2);
      this.logger.debug("settleMatch.getWeight:" + localSettleMatch.getWeight());
      double d3 = Arith.sub(d1, d2);
      if (!localRegStock.getFirmId().equals(localSettleMatch.getFirmID_S()))
      {
        this.logger.debug("");
        i = -16;
      }
      else if (Arith.compareTo(d3, localSettleMatch.getWeight()) < 0)
      {
        i = -16;
      }
    }
    else
    {
      i = -17;
    }
    return i;
  }
}
