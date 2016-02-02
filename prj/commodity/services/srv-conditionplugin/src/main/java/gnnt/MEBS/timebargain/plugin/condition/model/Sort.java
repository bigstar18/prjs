package gnnt.MEBS.timebargain.plugin.condition.model;

import java.util.Comparator;

public class Sort
  implements Comparator
{
  public static final int SORT_BY_PRICE = 1;
  public static final int SORT_BY_BUY1 = 2;
  public static final int SORT_BY_SELL1 = 3;
  private int sortby;
  
  public Sort() {}
  
  public Sort(int paramInt)
  {
    this.sortby = paramInt;
  }
  
  public int compare(Object paramObject1, Object paramObject2)
  {
    ConditionOrder localConditionOrder1 = (ConditionOrder)paramObject1;
    ConditionOrder localConditionOrder2 = (ConditionOrder)paramObject2;
    return localConditionOrder1.getConditionPrice().compareTo(localConditionOrder2.getConditionPrice());
  }
}
