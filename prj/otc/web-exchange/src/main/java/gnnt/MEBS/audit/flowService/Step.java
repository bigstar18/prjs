package gnnt.MEBS.audit.flowService;

import java.util.List;

public abstract interface Step
{
  public abstract int deal(OriginalModel paramOriginalModel);
  
  public abstract int getCurrentStatus();
  
  public abstract List<Handle> getHandleList();
}
