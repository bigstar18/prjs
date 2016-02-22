package gnnt.MEBS.audit.flowService.common;

import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.ProcessContext;
import gnnt.MEBS.audit.flowService.WorkflowFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkflowFacadeImpl
  implements WorkflowFacade
{
  public final transient Log logger = LogFactory.getLog(WorkflowFacadeImpl.class);
  private Map<String, ProcessContext> workflowMap;
  
  public void setWorkflowMap(Map<String, ProcessContext> workflowMap)
  {
    this.workflowMap = workflowMap;
  }
  
  public int dealWorkflow(OriginalModel originalModel, String workflowName)
  {
    Map<String, Object> map = new HashMap();
    ProcessContext processContext = (ProcessContext)this.workflowMap.get(workflowName);
    int result = -101;
    if (processContext != null) {
      try
      {
        result = processContext.doActivities(originalModel);
        if (result <= 0) {
          break label71;
        }
        result = 3;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        result = -101;
      }
    } else {
      result = -100;
    }
    label71:
    return result;
  }
  
  public List<Map<String, String>> getOperateMsg(String workflowName, int currentStatus, int operateStatus)
  {
    Map<String, Object> map = new HashMap();
    ProcessContext processContext = (ProcessContext)this.workflowMap.get(workflowName);
    List<Map<String, String>> operateMsgList = null;
    if (currentStatus == operateStatus) {
      operateMsgList = processContext.getOperateMsg(currentStatus);
    }
    return operateMsgList;
  }
}
