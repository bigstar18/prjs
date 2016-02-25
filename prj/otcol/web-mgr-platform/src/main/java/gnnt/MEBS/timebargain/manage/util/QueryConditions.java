package gnnt.MEBS.timebargain.manage.util;

import java.util.ArrayList;
import java.util.List;

public class QueryConditions
{
  private List conditionList = new ArrayList();
  
  public QueryConditions(List paramList)
  {
    this.conditionList = paramList;
  }
  
  public QueryConditions() {}
  
  public QueryConditions(String paramString1, String paramString2, Object paramObject)
  {
    addCondition(paramString1, paramString2, paramObject);
  }
  
  public QueryConditions(String paramString1, String paramString2, Object paramObject, String paramString3)
  {
    addCondition(paramString1, paramString2, paramObject, paramString3);
  }
  
  public void addCondition(String paramString1, String paramString2, Object paramObject)
  {
    this.conditionList.add(new Condition(paramString1, paramString2, paramObject));
  }
  
  public void addCondition(String paramString1, String paramString2, Object paramObject, String paramString3)
  {
    this.conditionList.add(new Condition(paramString1, paramString2, paramObject, paramString3));
  }
  
  public void removeCondition(String paramString)
  {
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition localCondition = (Condition)this.conditionList.get(i);
      if (localCondition.getField().equals(paramString)) {
        this.conditionList.remove(i);
      }
    }
  }
  
  public void removeCondition(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition localCondition = (Condition)this.conditionList.get(i);
      if ((localCondition.getField().equals(paramString1)) && (localCondition.getOperator().equals(paramString2))) {
        this.conditionList.remove(i);
      }
    }
  }
  
  public String getFieldsSqlClause()
  {
    String str = null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition localCondition = null;
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        localCondition = (Condition)this.conditionList.get(i);
        if (str != null) {
          str = str + " and " + localCondition.getField() + " " + localCondition.getOperator() + " ?";
        } else {
          str = localCondition.getField() + " " + localCondition.getOperator() + " ?";
        }
      }
    }
    return str;
  }
  
  public Object[] getValueArray()
  {
    Object[] arrayOfObject = null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition localCondition = null;
      arrayOfObject = new Object[this.conditionList.size()];
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        localCondition = (Condition)this.conditionList.get(i);
        arrayOfObject[i] = localCondition.getValue();
      }
    }
    return arrayOfObject;
  }
  
  public int[] getSqlDataTypes()
  {
    int[] arrayOfInt = null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition localCondition = null;
      arrayOfInt = new int[this.conditionList.size()];
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        localCondition = (Condition)this.conditionList.get(i);
        String str = localCondition.getDataType();
        if ("string".equals(str)) {
          arrayOfInt[i] = 12;
        } else if ("long".equals(str)) {
          arrayOfInt[i] = -5;
        } else if (("date".equals(str)) || ("datetime".equals(str))) {
          arrayOfInt[i] = 91;
        } else {
          arrayOfInt[i] = 12;
        }
      }
    }
    return arrayOfInt;
  }
  
  public Object getConditionValue(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition localCondition = (Condition)this.conditionList.get(i);
      if (paramString.equals(localCondition.getField())) {
        return localCondition.getValue();
      }
    }
    return null;
  }
  
  public Object getConditionValue(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      return null;
    }
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition localCondition = (Condition)this.conditionList.get(i);
      if ((paramString1.equals(localCondition.getField())) && (paramString2.equals(localCondition.getOperator()))) {
        return localCondition.getValue();
      }
    }
    return null;
  }
  
  public List getConditionList()
  {
    return this.conditionList;
  }
  
  public void setConditionList(List paramList)
  {
    this.conditionList = paramList;
  }
}
