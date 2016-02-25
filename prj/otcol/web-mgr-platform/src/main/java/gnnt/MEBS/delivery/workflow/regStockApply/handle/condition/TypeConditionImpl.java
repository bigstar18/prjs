package gnnt.MEBS.delivery.workflow.regStockApply.handle.condition;

import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import gnnt.MEBS.delivery.workflow.Condition;
import gnnt.MEBS.delivery.workflow.Handle;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import java.util.List;

public class TypeConditionImpl
  implements Condition
{
  private List<String> type;
  
  public List<String> getType()
  {
    return this.type;
  }
  
  public void setType(List<String> paramList)
  {
    this.type = paramList;
  }
  
  public boolean check(OriginalModel paramOriginalModel, Handle paramHandle)
  {
    boolean bool = false;
    String str = ((RegStockApply)paramOriginalModel.getObject()).getType() + "";
    if (this.type.contains(str)) {
      bool = true;
    }
    return bool;
  }
}
