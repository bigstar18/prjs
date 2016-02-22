package gnnt.MEBS.audit.flowService.common;

import gnnt.MEBS.audit.flowService.Handle;
import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.ProcessContext;
import gnnt.MEBS.audit.flowService.StatusOperate;
import gnnt.MEBS.audit.flowService.Step;
import gnnt.MEBS.audit.model.Apply;
import gnnt.MEBS.config.constant.AuditConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessContextImpl
  implements ProcessContext
{
  private final transient Log logger = LogFactory.getLog(ProcessContextImpl.class);
  private List<Step> steps;
  private int finalStatus;
  private StatusOperate statusOperate;
  
  public void setSteps(List<Step> steps)
  {
    this.steps = steps;
  }
  
  public void setFinalStatus(int finalStatus)
  {
    this.finalStatus = finalStatus;
  }
  
  public void setStatusOperate(StatusOperate statusOperate)
  {
    this.statusOperate = statusOperate;
  }
  
  public int doActivities(OriginalModel originalModel)
  {
    int result = -102;
    if (this.steps != null)
    {
      boolean b = false;
      for (Step step : this.steps)
      {
        Apply auditObject = originalModel.getAuditObject();
        if (step.getCurrentStatus() == auditObject.getStatus().intValue())
        {
          b = true;
          this.logger.debug("auditObject.getStatus():" + auditObject.getStatus());
          result = step.deal(originalModel);
          this.logger.debug("result:" + result);
          if (result > 0) {
            this.logger.debug("originalModel:" + originalModel.getAuditObject().getStatus());
          }
          this.statusOperate.updateStatus(originalModel);
          break;
        }
      }
      if (!b) {
        result = -103;
      }
    }
    return result;
  }
  
  public List<Map<String, String>> getOperateMsg(int currentStatus)
  {
    List<Map<String, String>> operateMsgList = null;
    if (this.steps != null) {
      for (Step step : this.steps) {
        if (step.getCurrentStatus() == currentStatus)
        {
          List<Handle> handleList = step.getHandleList();
          if ((handleList != null) && (handleList.size() > 0))
          {
            operateMsgList = new ArrayList();
            for (Handle handle : handleList)
            {
              Map<String, String> operateMsg = new HashMap();
              operateMsg.put(AuditConstant.OPERATENAME, handle.getBeanName());
              operateMsg.put(AuditConstant.OPERATEKEY, handle.getKey());
              operateMsgList.add(operateMsg);
            }
          }
        }
      }
    }
    return operateMsgList;
  }
}
