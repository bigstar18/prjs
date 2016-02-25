package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import gnnt.MEBS.delivery.services.ToolService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UpdateMatchQuantityBehavior
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(UpdateMatchQuantityBehavior.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  private static String KEY = "MATCHID";
  @Autowired
  @Qualifier("w_toolService")
  private ToolService toolService;
  
  public void deal(SettleObject paramSettleObject)
  {
    this.logger.debug("UpdateMatchQuantityBehavior");
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    String str = this.toolService.getXmlNode(localSettleMatch.getXml(), KEY);
    this.settleMatchService.restoreMatchQuantity(str);
  }
}
