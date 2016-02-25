package gnnt.MEBS.delivery.workflow;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;

public abstract interface StatusOperate
{
  public abstract void updateStatus(WorkFlowClone paramWorkFlowClone, int paramInt);
}
