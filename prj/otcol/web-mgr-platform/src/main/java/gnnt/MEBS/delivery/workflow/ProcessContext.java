package gnnt.MEBS.delivery.workflow;

import java.util.List;
import java.util.Map;

public abstract interface ProcessContext
{
  public abstract int doActivities(OriginalModel paramOriginalModel);
  
  public abstract Map getNewest(int paramInt1, int paramInt2);
  
  public abstract List<Integer> getFinalStatus();
}
