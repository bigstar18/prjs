package gnnt.MEBS.delivery.command.settleBehavior.filter;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Filtering;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.services.SettleMatchService;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CheckRestoreRegStock
  implements Filtering
{
  private final transient Log logger = LogFactory.getLog(CheckRestoreRegStock.class);
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  @Autowired
  @Qualifier("w_changeRegStockFiltering")
  private Filtering filtering;
  
  public int checkFilter(SettleObject paramSettleObject)
  {
    int i = 1;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    Map localMap = this.settleMatchService.getSettleMatchInit(localSettleMatch);
    String str1 = (String)localMap.get("REGSTOCKID");
    String str2 = localSettleMatch.getRegStockId();
    if ((str1 != null) && (!"null".equals(str1)) && (!"".equals(str1)))
    {
      paramSettleObject.setChangeRegStockId(str1);
      if (!str1.equals(str2)) {
        i = this.filtering.checkFilter(paramSettleObject);
      }
    }
    return i;
  }
}
