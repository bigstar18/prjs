package gnnt.MEBS.delivery.command.settleBehavior.condition;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Condition;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import java.util.List;

public class ModuleCondition
  implements Condition
{
  private List<String> moduleList;
  
  public void setModuleList(List<String> paramList)
  {
    this.moduleList = paramList;
  }
  
  public boolean check(SettleObject paramSettleObject)
  {
    boolean bool = false;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    if (this.moduleList.contains(localSettleMatch.getModuleId() + "")) {
      bool = true;
    }
    return bool;
  }
}
