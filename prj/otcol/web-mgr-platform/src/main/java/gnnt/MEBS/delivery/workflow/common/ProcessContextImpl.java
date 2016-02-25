package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.ProcessContext;
import gnnt.MEBS.delivery.workflow.Step;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessContextImpl
  implements ProcessContext
{
  private final transient Log logger = LogFactory.getLog(ProcessContextImpl.class);
  private List<Step> steps;
  private List<Integer> finalStatus;
  
  public int doActivities(OriginalModel paramOriginalModel)
  {
    int i = -1;
    this.logger.debug("执行到此");
    if (this.steps != null)
    {
      Iterator localIterator = this.steps.iterator();
      while (localIterator.hasNext())
      {
        Step localStep = (Step)localIterator.next();
        WorkFlowClone localWorkFlowClone1 = paramOriginalModel.getObject();
        if ((localStep.getCurrentStatus() == localWorkFlowClone1.getCurrentStatus()) && (localStep.checkAuthority(paramOriginalModel.getHoldAuthority())))
        {
          i = localStep.dealPrecondtion(paramOriginalModel);
          if (i != 1) {
            break;
          }
          this.logger.debug("currentStatus:" + localStep.getCurrentStatus());
          i = localStep.deal(paramOriginalModel);
          WorkFlowClone localWorkFlowClone2 = paramOriginalModel.getObject();
          localWorkFlowClone2.addActionStartStatus(localStep.getCurrentStatus());
          break;
        }
        if ((localStep.getCurrentStatus() == localWorkFlowClone1.getCurrentStatus()) && (!localStep.checkAuthority(paramOriginalModel.getHoldAuthority()))) {
          i = -2;
        }
      }
    }
    return i;
  }
  
  public void setSteps(List<Step> paramList)
  {
    this.steps = paramList;
  }
  
  public Map getNewest(int paramInt1, int paramInt2)
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    this.logger.debug("getNewest");
    if (this.steps != null)
    {
      this.logger.debug("steps is not null");
      Iterator localIterator = this.steps.iterator();
      while (localIterator.hasNext())
      {
        Step localStep = (Step)localIterator.next();
        if (localStep.getCurrentStatus() == paramInt1)
        {
          this.logger.debug("CurrentStatus is right");
          if (localStep.checkAuthority(paramInt2))
          {
            localHashMap.put("result", Integer.valueOf(1));
            localHashMap.put("handleNameList", localStep.getBeanMessageList());
            localHashMap.put("isExistNote", Boolean.valueOf(localStep.isExistNote()));
            break;
          }
          localHashMap.put("result", Integer.valueOf(-1));
        }
      }
      if (!localHashMap.containsKey("result"))
      {
        this.logger.debug("CurrentStatus is wrong");
        localHashMap.put("result", Integer.valueOf(-1));
      }
    }
    return localHashMap;
  }
  
  public List<Integer> getFinalStatus()
  {
    return this.finalStatus;
  }
  
  public void setFinalStatus(List<Integer> paramList)
  {
    this.finalStatus = paramList;
  }
}
