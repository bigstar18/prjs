package gnnt.MEBS.delivery.command.settleBehavior.condition;

import gnnt.MEBS.delivery.command.model.SettleObject;
import gnnt.MEBS.delivery.command.settleBehavior.Condition;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import java.util.List;

public class TypeCondition
  implements Condition
{
  private List<String> typeList;
  
  public void setTypeList(List<String> paramList)
  {
    this.typeList = paramList;
  }
  
  public boolean check(SettleObject paramSettleObject)
  {
    boolean bool = false;
    SettleMatch localSettleMatch = paramSettleObject.getSettleMatch();
    int i = paramSettleObject.getType();
    if (i == 0) {
      i = localSettleMatch.getResult();
    }
    if (this.typeList.contains(i + "")) {
      bool = true;
    }
    return bool;
  }
}
