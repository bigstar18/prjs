package gnnt.MEBS.timebargain.tradeweb.util;

import java.util.ArrayList;
import java.util.List;

public class QueryConditions
{
  private List conditionList = new ArrayList();
  
  public QueryConditions(List conditionList)
  {
    this.conditionList = conditionList;
  }
  
  public QueryConditions() {}
  
  public QueryConditions(String field, String operator, Object value)
  {
    addCondition(field, operator, value);
  }
  
  public QueryConditions(String field, String operator, Object value, String dataType)
  {
    addCondition(field, operator, value, dataType);
  }
  
  public void addCondition(String field, String operator, Object value)
  {
    this.conditionList.add(new Condition(field, operator, value));
  }
  
  public void addCondition(String field, String operator, Object value, String dataType)
  {
    this.conditionList.add(new Condition(field, operator, value, dataType));
  }
  
  public void removeCondition(String field)
  {
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition cond = (Condition)this.conditionList.get(i);
      if (cond.getField().equals(field)) {
        this.conditionList.remove(i);
      }
    }
  }
  
  public void removeCondition(String field, String operator)
  {
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition cond = (Condition)this.conditionList.get(i);
      if ((cond.getField().equals(field)) && (cond.getOperator().equals(operator))) {
        this.conditionList.remove(i);
      }
    }
  }
  
  public String getFieldsSqlClause()
  {
    String sqlclause = null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition cond = null;
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        if (sqlclause != null) {
          sqlclause = 
            sqlclause + " and " + cond.getField() + " " + cond.getOperator() + " ?";
        } else {
          sqlclause = 
            cond.getField() + " " + cond.getOperator() + " ?";
        }
      }
    }
    return sqlclause;
  }
  
  public Object[] getValueArray()
  {
    Object[] params = (Object[])null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition cond = null;
      params = new Object[this.conditionList.size()];
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        params[i] = cond.getValue();
      }
    }
    return params;
  }
  
  public int[] getSqlDataTypes()
  {
    int[] dataTypes = (int[])null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition cond = null;
      dataTypes = new int[this.conditionList.size()];
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        String dataType = cond.getDataType();
        if ("string".equals(dataType)) {
          dataTypes[i] = 12;
        } else if ("long".equals(dataType)) {
          dataTypes[i] = -5;
        } else if (("date".equals(dataType)) || ("datetime".equals(dataType))) {
          dataTypes[i] = 91;
        } else {
          dataTypes[i] = 12;
        }
      }
    }
    return dataTypes;
  }
  
  public Object getConditionValue(String field)
  {
    if (field == null) {
      return null;
    }
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition cond = (Condition)this.conditionList.get(i);
      if (field.equals(cond.getField())) {
        return cond.getValue();
      }
    }
    return null;
  }
  
  public Object getConditionValue(String field, String operator)
  {
    if ((field == null) || (operator == null)) {
      return null;
    }
    for (int i = 0; i < this.conditionList.size(); i++)
    {
      Condition cond = (Condition)this.conditionList.get(i);
      if ((field.equals(cond.getField())) && (operator.equals(cond.getOperator()))) {
        return cond.getValue();
      }
    }
    return null;
  }
  
  public List getConditionList()
  {
    return this.conditionList;
  }
  
  public void setConditionList(List conditionList)
  {
    this.conditionList = conditionList;
  }
}
