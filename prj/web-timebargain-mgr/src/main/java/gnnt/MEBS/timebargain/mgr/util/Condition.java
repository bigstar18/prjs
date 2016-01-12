package gnnt.MEBS.timebargain.mgr.util;

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

  public String getSqlClause() {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null))
      return "";
    String clause = null;
    if ("is".equals(this.operator))
      clause = getField() + " is " + getValue();
    else if ("in".equals(this.operator))
      clause = getField() + " in " + getValue();
    else if ("like".equals(this.operator))
      clause = getField() + " like " + " '%' || ? || '%' ";
    else if ("or".equals(this.operator)) {
      clause = (String)getValue();
    }
    else if ("or".equals(this.operator))
      clause = (String)getValue();
    else
      clause = getField() + " " + getOperator() + " ?";
    return clause;
  }

  public Integer getSqlDataType() {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null))
      return null;
    if ("is".equals(this.operator))
      return null;
    if ("in".equals(this.operator))
      return null;
    if ("or".equals(this.operator))
      return null;
    if ("string".equals(this.dataType))
      return new Integer(12);
    if ("long".equals(this.dataType))
      return new Integer(-5);
    if ("date".equals(this.dataType))
      return new Integer(91);
    if ("datetime".equals(this.dataType)) {
      return new Integer(91);
    }
    return null;
  }

  public Object getSqlValue() {
    if ((getField() == null) || (getField().length() == 0) || 
      (this.operator == null) || (this.operator.length() == 0) || 
      (this.value == null))
      return null;
    if ("is".equals(this.operator))
      return null;
    if ("in".equals(this.operator))
      return null;
    if ("or".equals(this.operator))
      return null;
    return this.value;
  }
}