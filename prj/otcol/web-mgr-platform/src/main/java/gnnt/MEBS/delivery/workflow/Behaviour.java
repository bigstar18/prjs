package gnnt.MEBS.delivery.workflow;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;

public abstract interface Behaviour
{
  public abstract void deal(WorkFlowClone paramWorkFlowClone);
}
