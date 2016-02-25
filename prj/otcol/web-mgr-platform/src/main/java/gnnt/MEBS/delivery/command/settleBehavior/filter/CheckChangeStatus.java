package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckChangeStatus
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckChangeStatus.class);
  @Autowired
  @Qualifier("settleMatchService")
  private SettleMatchService settleMatchService;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    String str1 = paramSettleObject.getMatchId();
    SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(str1);
    int j = paramSettleObject.getType();
    String str2 = paramSettleObject.getChangeRegStockId();
    if ((j == 1) && (str2 == null)) {
      i = -17;
    } else if ((",2,3,4,".indexOf("," + j + ",") >= 0) && (str2 != null)) {
      i = -17;
    }
    return i;
  }
}
