package gnnt.MEBS.delivery.command.settleBehavior;

import gnnt.MEBS.delivery.command.model.SettleObject;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseHandle
  implements Handle
{
  private final transient Log logger = LogFactory.getLog(BaseHandle.class);
  private List<Condition> conditionList;
  private List<Behaviour> behaviourList;
  private List<Filtering> filteringList;
  
  public void setConditionList(List<Condition> paramList)
  {
    this.conditionList = paramList;
  }
  
  public void setBehaviourList(List<Behaviour> paramList)
  {
    this.behaviourList = paramList;
  }
  
  public void setFilteringList(List<Filtering> paramList)
  {
    this.filteringList = paramList;
  }
  
  public boolean checkCondition(SettleObject paramSettleObject)
  {
    boolean bool = false;
    if (this.conditionList != null)
    {
      Iterator localIterator = this.conditionList.iterator();
      while (localIterator.hasNext())
      {
        Condition localCondition = (Condition)localIterator.next();
        bool = localCondition.check(paramSettleObject);
        if (!bool) {
          break;
        }
      }
    }
    return bool;
  }
  
  public int checkFilterList(SettleObject paramSettleObject)
  {
    int i = 1;
    if (this.filteringList != null)
    {
      Iterator localIterator = this.filteringList.iterator();
      while (localIterator.hasNext())
      {
        Filtering localFiltering = (Filtering)localIterator.next();
        i = localFiltering.checkFilter(paramSettleObject);
        this.logger.debug("result:::::" + i);
        if (i != 1) {
          break;
        }
      }
    }
    return i;
  }
  
  public void doDeal(SettleObject paramSettleObject)
  {
    if (this.behaviourList != null)
    {
      Iterator localIterator = this.behaviourList.iterator();
      while (localIterator.hasNext())
      {
        Behaviour localBehaviour = (Behaviour)localIterator.next();
        localBehaviour.deal(paramSettleObject);
      }
    }
  }
}
