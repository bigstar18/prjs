package gnnt.MEBS.delivery.workflow;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;

public abstract interface Handle
{
  public abstract boolean checkCondition(OriginalModel paramOriginalModel);
  
  public abstract void dealBehaviour(WorkFlowClone paramWorkFlowClone);
  
  public abstract int getFinalStatus();
  
  public abstract int checkFilterList(OriginalModel paramOriginalModel);
  
  public abstract String getBeanName();
  
  public abstract boolean isWriteNote();
}
