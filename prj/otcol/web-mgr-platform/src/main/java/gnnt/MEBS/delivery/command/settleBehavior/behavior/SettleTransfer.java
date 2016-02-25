package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.EnterWareService;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleTransfer
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(SettleTransfer.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  @Autowired
  @Qualifier("w_enterWareService")
  private EnterWareService enterWareService;
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject settleObject)
  {
    SettleMatch settleMatch = settleObject.getSettleMatch();
    String regStockId = settleMatch.getRegStockId();
    
    RegStock regStock = this.regStockService.getRegStockForUpdate(regStockId);
    String stockId = regStock.getStockId();
    String enter_wareId = null;
    if ((stockId != null) && (regStock.getType() == 0))
    {
      EnterWare enter_Ware = this.enterWareService.getEnterWareLock(stockId);
      this.enterWareService.releaseAmount(enter_Ware, settleMatch.getWeight(), 3);
      enter_wareId = this.enterWareService.copyEnterWare(enter_Ware, settleMatch.getWeight(), settleMatch.getFirmID_B(), false);
    }
    else
    {
      enter_wareId = stockId;
    }
    this.regStockService.releaseAmount(regStock, settleMatch.getWeight(), 3);
    String newRegStockId = this.regStockService.copyRegStock(regStock, settleMatch.getWeight(), settleMatch.getFirmID_B(), enter_wareId);
    
    settleMatch.setIsChangeOwner(1);
    this.settleMatchService.updateSettleMatch(settleMatch);
  }
}
