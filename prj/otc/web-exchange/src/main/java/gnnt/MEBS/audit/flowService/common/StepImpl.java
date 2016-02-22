package gnnt.MEBS.audit.flowService.common;

import gnnt.MEBS.audit.flowService.Handle;
import gnnt.MEBS.audit.flowService.OriginalModel;
import gnnt.MEBS.audit.flowService.Step;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StepImpl
  implements Step
{
  private final transient Log logger = LogFactory.getLog(StepImpl.class);
  private List<Handle> handleList;
  private int currentStatus;
  
  public void setCurrentStatus(int currentStatus)
  {
    this.currentStatus = currentStatus;
  }
  
  public void setHandleList(List<Handle> handleList)
  {
    this.handleList = handleList;
  }
  
  public int deal(OriginalModel originalModel)
  {
    int result = -104;
    if (this.handleList != null) {
      for (Handle handle : this.handleList)
      {
        this.logger.debug("originalModel.getKey():" + originalModel.getKey());
        this.logger.debug("handle.key:" + handle.getKey());
        if (handle.getKey().equals(originalModel.getKey()))
        {
          this.logger.debug("handle:" + handle.getKey());
          result = handle.dealBehaviour(originalModel.getAuditObject());
          this.logger.debug("result:" + result);
          break;
        }
      }
    }
    return result;
  }
  
  public int getCurrentStatus()
  {
    return this.currentStatus;
  }
  
  public List<Handle> getHandleList()
  {
    return this.handleList;
  }
}
