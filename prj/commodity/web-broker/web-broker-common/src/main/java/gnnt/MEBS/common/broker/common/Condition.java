package gnnt.MEBS.common.broker.common;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Condition
{
  private String field;
  private String operator;
  private Object value;

  public Condition()
  {
  }

  public Condition(String paramString1, String paramString2, Object paramObject)
  {
    this.field = paramString1;
    this.operator = paramString2;
    this.value = paramObject;
  }

  public String getField()
  {
    return this.field;
  }

  public void setField(String paramString)
  {
    this.field = paramString;
  }

  public String getOperator()
  {
    if ("allLike".equals(this.operator))
      return "like";
    return this.operator;
  }

  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }

  public Object getValue()
  {
    return this.value;
  }

  public void setValue(Object paramObject)
  {
    this.value = paramObject;
  }

  public String getSqlClause()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null))
      return "";
    String str = null;
    if (isPlaceholder())
      str = getField() + " " + getOperator() + " ?";
    else
      str = getField() + " " + this.operator + " " + getValue();
    return str;
  }

  public Integer getSqlDataType()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null))
      return null;
    if (isPlaceholder())
    {
      if ((this.value instanceof String))
        return new Integer(12);
      if ((this.value instanceof Long))
        return new Integer(-5);
      if ((this.value instanceof java.util.Date))
        return new Integer(91);
      if ((this.value instanceof java.sql.Date))
        return new Integer(91);
      if ((this.value instanceof Timestamp))
        return new Integer(93);
      if ((this.value instanceof BigDecimal))
        return new Integer(2);
      return null;
    }
    return null;
  }

  public Object getSqlValue()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null))
      return null;
    if (isPlaceholder())
    {
      if ("like".equals(this.operator))
        return ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
      if ("allLike".equals(this.operator))
        return "%" + ((String)getValue()).replaceAll("%", "/%").replaceAll("_", "/_") + "%";
      return this.value;
    }
    return null;
  }

  private boolean isPlaceholder()
  {
    if ("is".equals(this.operator))
      return false;
    if ("in".equals(this.operator))
      return false;
    if ("not in".equals(this.operator))
      return false;
    return this.operator.trim().length() != 0;
  }
}