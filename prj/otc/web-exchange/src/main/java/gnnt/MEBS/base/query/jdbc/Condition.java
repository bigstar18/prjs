package gnnt.MEBS.base.query.jdbc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Condition
{
  public static final String STRING = "string";
  public static final String LONG = "long";
  public static final String DATE = "date";
  public static final String DATETIME = "datetime";
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
  
  public Condition() {}
  
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
    if ("is".equals(this.operator)) {
      clause = getField() + " is " + getValue();
    } else if ("in".equals(this.operator)) {
      clause = getField() + " in " + getValue();
    } else if ("or".equals(this.operator)) {
      clause = (String)getValue();
    } else if ("composite".equals(this.operator)) {
      clause = getField().replaceAll("%composite%", (String)getValue());
    } else if ("date".equals(this.dataType))
    {
      if (">".equals(getOperator())) {
        clause = getField() + " >= " + " to_date('" + addDate((Date)getValue(), 1) + "','yyyy-MM-dd')";
      } else if (">=".equals(getOperator())) {
        clause = getField() + " >= " + " to_date('" + addDate((Date)getValue(), 0) + "','yyyy-MM-dd')";
      } else if ("<".equals(getOperator())) {
        clause = getField() + " < " + " to_date('" + addDate((Date)getValue(), 0) + "','yyyy-MM-dd')";
      } else if ("<=".equals(getOperator())) {
        clause = getField() + " < " + " to_date('" + addDate((Date)getValue(), 1) + "','yyyy-MM-dd')";
      } else if ("=".equals(getOperator())) {
        clause = 
          getField() + " >= " + " to_date('" + addDate((Date)getValue(), 0) + "','yyyy-MM-dd')" + " and " + getField() + " <" + " to_date('" + addDate((Date)getValue(), 1) + "','yyyy-MM-dd')";
      }
    }
    else {
      clause = getField() + " " + getOperator() + " ?";
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
    if ("or".equals(this.operator)) {
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
      return null;
    }
    if ("datetime".equals(this.dataType)) {
      return new Integer(91);
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
    if ("or".equals(this.operator)) {
      return null;
    }
    if ("like".equals(this.operator)) {
      return "%" + getValue() + "%";
    }
    if ("composite".equals(this.operator)) {
      return null;
    }
    if ("date".equals(this.dataType)) {
      return null;
    }
    return this.value;
  }
  
  public static String addDate(Date day, int x)
  {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(day);
    cal.add(5, x);
    day = cal.getTime();
    cal = null;
    String strDate = format.format(day);
    return strDate;
  }
}
