package gnnt.MEBS.audit.flowService;

import java.util.List;
import java.util.Map;

public abstract interface WorkflowFacade
{
  public abstract int dealWorkflow(OriginalModel paramOriginalModel, String paramString);
  
  public abstract List<Map<String, String>> getOperateMsg(String paramString, int paramInt1, int paramInt2);
}
