package gnnt.MEBS.timebargain.tradeweb.util;

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
    this.field = field;
    this.operator = operator;
    this.value = value;
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
}
