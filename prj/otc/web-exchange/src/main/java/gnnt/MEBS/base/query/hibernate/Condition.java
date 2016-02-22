package gnnt.MEBS.base.query.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Condition
{
  private final transient Log logger = LogFactory.getLog(Condition.class);
  public static final String STRING = "string";
  public static final String LONG = "long";
  public static final String INT = "int";
  public static final String DATE = "date";
  public static final String DATETIME = "datetime";
  public static final String TIMESTEMP = "timestemp";
  public static final String BIGDECIMAL = "bigdecimal";
  public static final String DOUBLE = "double";
  private String field;
  private String operator;
  private Object value;
  private String dataType;
  
  public Condition(String field, String operator, Object value)
  {
    this(field, operator, value, "string");
  }
  
  public Condition(String field, String operator, Object value, String dataType)
  {
    this.field = field;
    this.operator = operator;
    this.value = value;
    this.dataType = dataType;
  }
  
  public String getField()
  {
    return this.field;
  }
  
  public void setField(String field)
  {
    this.field = field;
  }
  
  public String getOperator()
  {
    if ("notLike".equals(this.operator)) {
      return "not like";
    }
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
  
  public String getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(String dataType)
  {
    this.dataType = dataType;
  }
  
  public String getSqlClause()
  {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null)) {
      return "";
    }
    String clause = null;
    if (this.operator.indexOf("is") >= 0)
    {
      clause = getField() + " is " + getValue();
    }
    else if (this.operator.indexOf("not in") >= 0)
    {
      clause = getField() + " not in " + getValue();
      this.logger.debug("clause:" + clause);
    }
    else if (this.operator.indexOf("in") >= 0)
    {
      clause = getField() + " in " + getValue();
    }
    else if ("allLike".equals(this.operator))
    {
      clause = "(" + getField() + " like " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(getOperator().hashCode()))).toString()) + Math.abs(getOperator().hashCode()) + Math.abs(getValue().toString().hashCode()) + "  escape '/' )";
    }
    else if ("like".equals(this.operator))
    {
      clause = "(" + getField() + " like :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(getOperator().hashCode()))).toString()) + Math.abs(getOperator().hashCode()) + Math.abs(getValue().toString().hashCode()) + " escape '/' )";
    }
    else if ("notLike".equals(this.operator))
    {
      clause = "(" + getField() + " not like :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(getOperator().hashCode()))).toString()) + Math.abs(getOperator().hashCode()) + Math.abs(getValue().toString().hashCode()) + " escape '/' )";
    }
    else if ("notAllLike".equals(this.operator))
    {
      clause = "(" + getField() + " not like " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(getOperator().hashCode()))).toString()) + Math.abs(getOperator().hashCode()) + Math.abs(getValue().toString().hashCode()) + "  escape '/' )";
    }
    else if ("filter".equals(this.operator))
    {
      clause = (String)getValue();
      this.logger.debug("clause:" + clause);
    }
    else if ("date".equals(this.dataType))
    {
      if (">".equals(getOperator())) {
        clause = getField() + " >= " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(getValue().toString().hashCode());
      } else if (">=".equals(getOperator())) {
        clause = getField() + " >= " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(getValue().toString().hashCode());
      } else if ("<".equals(getOperator())) {
        clause = getField() + " < " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + Math.abs(" < ".hashCode()) + Math.abs(getValue().toString().hashCode());
      } else if ("<=".equals(getOperator())) {
        clause = getField() + " < " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + Math.abs(" < ".hashCode()) + Math.abs(getValue().toString().hashCode());
      } else if ("=".equals(getOperator())) {
        clause = 
          getField() + " >= " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" >= ".hashCode()))).toString()) + Math.abs(" >= ".hashCode()) + Math.abs(getValue().toString().hashCode()) + " and " + getField() + " < " + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(" < ".hashCode()))).toString()) + Math.abs(" < ".hashCode()) + Math.abs(getValue().toString().hashCode());
      }
    }
    else if ("composite".equals(this.operator))
    {
      clause = getField().replaceAll("%composite%", (String)getValue());
    }
    else
    {
      clause = "(" + getField() + " " + getOperator() + " :" + getField().replace(".", new StringBuilder(String.valueOf(Math.abs(getOperator().hashCode()))).toString()) + Math.abs(getOperator().hashCode()) + Math.abs(getValue().toString().hashCode()) + ")";
    }
    return clause;
  }
  
  public Integer getSqlDataType()
  {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null)) {
      return null;
    }
    if ("is".equals(this.operator)) {
      return null;
    }
    if ("in".equals(this.operator)) {
      return null;
    }
    if ("not in".equals(this.operator)) {
      return null;
    }
    if ("filter".equals(this.operator)) {
      return null;
    }
    if ("composite".equals(this.operator)) {
      return null;
    }
    if ("string".equals(this.dataType)) {
      return new Integer(12);
    }
    if ("long".equals(this.dataType)) {
      return new Integer(-5);
    }
    if ("date".equals(this.dataType)) {
      return new Integer(91);
    }
    if ("datetime".equals(this.dataType)) {
      return new Integer(91);
    }
    if ("bigdecimal".equals(this.dataType)) {
      return new Integer(2);
    }
    return null;
  }
  
  public Object getSqlValue()
  {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null)) {
      return null;
    }
    if ("is".equals(this.operator)) {
      return null;
    }
    if ("in".equals(this.operator)) {
      return null;
    }
    if ("not in".equals(this.operator)) {
      return null;
    }
    if ("like".equals(this.operator)) {
      return ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
    }
    if ("notLike".equals(this.operator)) {
      return ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
    }
    if ("allLike".equals(this.operator)) {
      return "%" + ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
    }
    if ("notLike".equals(this.operator)) {
      return getValue() + "%";
    }
    if ("notAllLike".equals(this.operator)) {
      return "%" + ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
    }
    if ("composite".equals(this.operator)) {
      return null;
    }
    if ("filter".equals(this.operator)) {
      return null;
    }
    if ("date".equals(this.dataType))
    {
      Date value = (Date)getValue();
      if (">".equals(getOperator())) {
        return addDate(value, 1);
      }
      if (">=".equals(getOperator())) {
        return value;
      }
      if ("<".equals(getOperator())) {
        return value;
      }
      if ("<=".equals(getOperator())) {
        return addDate(value, 1);
      }
      if ("=".equals(getOperator()))
      {
        List list = new ArrayList();
        list.add(value);
        list.add(addDate(value, 1));
        return list;
      }
    }
    return this.value;
  }
  
  public String getSqlName()
  {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null)) {
      return null;
    }
    if ("is".equals(this.operator)) {
      return null;
    }
    if ("in".equals(this.operator)) {
      return null;
    }
    if ("not in".equals(this.operator)) {
      return null;
    }
    if ("composite".equals(this.operator)) {
      return null;
    }
    if ("filter".equals(this.operator)) {
      return null;
    }
    return getField();
  }
  
  public static Date addDate(Date day, int x)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(day);
    cal.add(5, x);
    day = cal.getTime();
    day = new Date(day.getTime());
    cal = null;
    return day;
  }
}
