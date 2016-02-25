package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.SettleReceive;
import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleExtra.SettleBalanceCheck;
import gnnt.MEBS.delivery.command.settleExtra.SettleLegalExamine;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseReceive
  implements SettleReceive
{
  private final transient Log logger = LogFactory.getLog(BaseReceive.class);
  private List<Handle> handleList;
  private SettleBalanceCheck settleBalanceCheck;
  private SettleLegalExamine settleLegalExamine;
  
  public SettleBalanceCheck getSettleBalanceCheck()
  {
    return this.settleBalanceCheck;
  }
  
  public void setSettleBalanceCheck(SettleBalanceCheck paramSettleBalanceCheck)
  {
    this.settleBalanceCheck = paramSettleBalanceCheck;
  }
  
  public List<Handle> getHandleList()
  {
    return this.handleList;
  }
  
  public void setHandleList(List<Handle> paramList)
  {
    this.handleList = paramList;
  }
  
  public void setSettleLegalExamine(SettleLegalExamine paramSettleLegalExamine)
  {
    this.settleLegalExamine = paramSettleLegalExamine;
  }
  
  public SettleLegalExamine getSettleLegalExamine()
  {
    return this.settleLegalExamine;
  }
  
  public int deal(Information paramInformation)
  {
    int i = 0;
    SettleObject localSettleObject = (SettleObject)paramInformation.getObject();
    if (this.handleList != null)
    {
      Iterator localIterator = this.handleList.iterator();
      while (localIterator.hasNext())
      {
        Handle localHandle = (Handle)localIterator.next();
        if (localHandle.checkCondition(localSettleObject))
        {
          i = localHandle.checkFilterList(localSettleObject);
          if (i == 1)
          {
            localHandle.doDeal(localSettleObject);
            break;
          }
        }
      }
    }
    return i;
  }
}
