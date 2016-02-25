package gnnt.MEBS.delivery.command.settleBehavior.behavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Behaviour;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UpdateResultForSettleMatch
  implements Behaviour
{
  private final transient Log logger = LogFactory.getLog(UpdateResultForSettleMatch.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  
  public void deal(SettleObject paramSettleObject)
  {
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    this.settleMatchService.settleChangeStatus(localSettleMatch, paramSettleObject.getType());
  }
}
