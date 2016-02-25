package gnnt.MEBS.base.query;

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
  
  public Condition(String paramString1, String paramString2, Object paramObject)
  {
    this(paramString1, paramString2, paramObject, "string");
  }
  
  public Condition(String paramString1, String paramString2, Object paramObject, String paramString3)
  {
    this.field = paramString1;
    this.operator = paramString2;
    this.value = paramObject;
    this.dataType = paramString3;
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
  
  public String getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(String paramString)
  {
    this.dataType = paramString;
  }
  
  public String getSqlClause()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null)) {
      return "";
    }
    String str = null;
    if ("is".equals(this.operator)) {
      str = getField() + " is " + getValue();
    } else if ("in".equals(this.operator)) {
      str = getField() + " in " + getValue();
    } else if ("like".equals(this.operator)) {
      str = getField() + " like " + " '%' || ? || '%' ";
    } else if ("or".equals(this.operator)) {
      str = (String)getValue();
    } else {
      str = getField() + " " + getOperator() + " ?";
    }
    return str;
  }
  
  public Integer getSqlDataType()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null)) {
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
    return null;
  }
  
  public Object getSqlValue()
  {
    if ((getField() == null) || (getField().length() == 0) || (this.operator == null) || (this.operator.length() == 0) || (this.value == null)) {
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
    return this.value;
  }
}
