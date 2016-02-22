package gnnt.MEBS.audit.flowService;

import gnnt.MEBS.audit.model.Apply;

public abstract interface Handle
{
  public abstract int dealBehaviour(Apply paramApply);
  
  public abstract String getKey();
  
  public abstract String getBeanName();
}
