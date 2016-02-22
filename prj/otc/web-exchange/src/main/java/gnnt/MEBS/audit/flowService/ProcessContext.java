package gnnt.MEBS.audit.flowService;

import java.util.List;
import java.util.Map;

public abstract interface ProcessContext
{
  public abstract int doActivities(OriginalModel paramOriginalModel);
  
  public abstract List<Map<String, String>> getOperateMsg(int paramInt);
}
