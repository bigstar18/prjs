package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.workflow.Behaviour;
import gnnt.MEBS.delivery.workflow.Condition;
import gnnt.MEBS.delivery.workflow.Filtering;
import gnnt.MEBS.delivery.workflow.Handle;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HandleImpl
  implements Handle
{
  private final transient Log logger = LogFactory.getLog(HandleImpl.class);
  private int finalStatus;
  private List<Behaviour> behaviourList;
  private List<Condition> conditionList;
  private List<Filtering> filteringList;
  private String beanName;
  private boolean isWriteNote;
  
  public boolean checkCondition(OriginalModel paramOriginalModel)
  {
    boolean bool = false;
    if (this.conditionList != null)
    {
      Iterator localIterator = this.conditionList.iterator();
      while (localIterator.hasNext())
      {
        Condition localCondition = (Condition)localIterator.next();
        bool = localCondition.check(paramOriginalModel, this);
        if (!bool) {
          break;
        }
      }
    }
    return bool;
  }
  
  public void dealBehaviour(WorkFlowClone paramWorkFlowClone)
  {
    if (this.behaviourList != null)
    {
      Iterator localIterator = this.behaviourList.iterator();
      while (localIterator.hasNext())
      {
        Behaviour localBehaviour = (Behaviour)localIterator.next();
        this.logger.debug("HandleImpl+dealBehaviour");
        localBehaviour.deal(paramWorkFlowClone);
      }
    }
  }
  
  public int getFinalStatus()
  {
    return this.finalStatus;
  }
  
  public void setBehaviourList(List<Behaviour> paramList)
  {
    this.behaviourList = paramList;
  }
  
  public void setConditionList(List<Condition> paramList)
  {
    this.conditionList = paramList;
  }
  
  public void setFinalStatus(int paramInt)
  {
    this.finalStatus = paramInt;
  }
  
  public void setFilteringList(List<Filtering> paramList)
  {
    this.filteringList = paramList;
  }
  
  public int checkFilterList(OriginalModel paramOriginalModel)
  {
    int i = 1;
    if (this.filteringList != null)
    {
      Iterator localIterator = this.filteringList.iterator();
      while (localIterator.hasNext())
      {
        Filtering localFiltering = (Filtering)localIterator.next();
        i = localFiltering.checkFiler(paramOriginalModel);
        this.logger.debug("result:::::" + i);
        if (i != 1) {
          break;
        }
      }
    }
    return i;
  }
  
  public String getBeanName()
  {
    return this.beanName;
  }
  
  public void setBeanName(String paramString)
  {
    this.beanName = paramString;
  }
  
  public void setWriteNote(boolean paramBoolean)
  {
    this.isWriteNote = paramBoolean;
  }
  
  public boolean isWriteNote()
  {
    return this.isWriteNote;
  }
}
