package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.workflow.Condition;
import gnnt.MEBS.delivery.workflow.Handle;
import gnnt.MEBS.delivery.workflow.OriginalModel;

public class ConditionImpl
  implements Condition
{
  public boolean check(OriginalModel paramOriginalModel, Handle paramHandle)
  {
    boolean bool = false;
    if (paramHandle.getBeanName().equals(paramOriginalModel.getOperate())) {
      bool = true;
    }
    return bool;
  }
}
