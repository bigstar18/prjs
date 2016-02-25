package gnnt.MEBS.delivery.workflow;

import java.util.List;
import java.util.Map;

public abstract interface WorkflowFacade
{
  public abstract Map dealWorkflow(OriginalModel paramOriginalModel, String paramString);
  
  public abstract Map getLastMsg(int paramInt1, int paramInt2, String paramString);
  
  public abstract String getLogContent(String paramString);
  
  public abstract List<Integer> getFinalStatus(String paramString);
}
