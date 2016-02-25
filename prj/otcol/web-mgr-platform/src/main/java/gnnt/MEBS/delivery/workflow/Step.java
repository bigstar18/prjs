package gnnt.MEBS.delivery.workflow;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import java.util.List;

public abstract interface Step
{
  public abstract int deal(OriginalModel paramOriginalModel);
  
  public abstract int getCurrentStatus();
  
  public abstract boolean checkAuthority(int paramInt);
  
  public abstract void updateStatus(Handle paramHandle, WorkFlowClone paramWorkFlowClone);
  
  public abstract int dealPrecondtion(OriginalModel paramOriginalModel);
  
  public abstract List getBeanMessageList();
  
  public abstract boolean isExistNote();
}
