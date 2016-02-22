package gnnt.MEBS.base.query.hibernate;

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
  
  public void addCondition(Condition condition)
  {
    this.conditionList.add(condition);
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
      if ((cond.getField().equals(field)) && 
        (cond.getOperator().equals(operator))) {
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
          sqlclause = sqlclause + " and " + cond.getSqlClause();
        } else {
          sqlclause = cond.getSqlClause();
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
      List lst = new ArrayList();
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        Object obj = cond.getSqlValue();
        if (obj != null) {
          if ((obj instanceof List)) {
            lst.addAll((List)obj);
          } else {
            lst.add(obj);
          }
        }
      }
      params = lst.toArray();
    }
    return params;
  }
  
  public String[] getNameArray()
  {
    String[] names = (String[])null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition cond = null;
      List lst = new ArrayList();
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        String obj = cond.getSqlName();
        if (obj != null) {
          if ("date".equals(cond.getDataType()))
          {
            if (">".equals(cond.getOperator()))
            {
              obj = 
              

                obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
            }
            else if (">=".equals(cond.getOperator()))
            {
              obj = 
              

                obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
            }
            else if ("<".equals(cond.getOperator()))
            {
              obj = 
              

                obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + Math.abs(" < ".hashCode()) + Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
            }
            else if ("<=".equals(cond.getOperator()))
            {
              obj = 
              

                obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + Math.abs(" < ".hashCode()) + Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
            }
            else if ("=".equals(cond.getOperator()))
            {
              obj = 
              

                obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
              obj = cond.getSqlName();
              obj = obj.replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + 
              
                Math.abs(" < ".hashCode()) + 
                Math.abs(cond.getValue().toString()
                .hashCode());
              lst.add(obj);
            }
          }
          else
          {
            obj = 
            

              obj.replace(".", new StringBuilder(String.valueOf(Math.abs(cond.getOperator().hashCode()))).toString()) + Math.abs(cond.getOperator().hashCode()) + Math.abs(cond.getValue().toString()
              .hashCode());
            lst.add(obj);
          }
        }
      }
      int size = lst.size();
      if (size > 0)
      {
        names = new String[size];
        for (int i = 0; i < lst.size(); i++) {
          names[i] = ((String)lst.get(i));
        }
      }
    }
    return names;
  }
  
  public int[] getSqlDataTypes()
  {
    int[] dataTypes = (int[])null;
    if ((this.conditionList != null) && (this.conditionList.size() > 0))
    {
      Condition cond = null;
      List lst = new ArrayList();
      for (int i = 0; i < this.conditionList.size(); i++)
      {
        cond = (Condition)this.conditionList.get(i);
        Integer type = cond.getSqlDataType();
        if (type != null) {
          lst.add(type);
        }
      }
      int size = lst.size();
      if (size > 0)
      {
        dataTypes = new int[size];
        for (int i = 0; i < lst.size(); i++) {
          dataTypes[i] = ((Integer)lst.get(i)).intValue();
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
      if ((field.equals(cond.getField())) && 
        (operator.equals(cond.getOperator()))) {
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
