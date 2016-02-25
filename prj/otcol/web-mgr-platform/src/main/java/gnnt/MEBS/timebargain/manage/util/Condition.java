package gnnt.MEBS.timebargain.manage.util;

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
    this.field = paramString1;
    this.operator = paramString2;
    this.value = paramObject;
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
}
