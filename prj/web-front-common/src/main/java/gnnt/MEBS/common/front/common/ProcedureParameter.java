package gnnt.MEBS.common.front.common;

public class ProcedureParameter
{
  public static final int OUTPARAMETER = 1;
  public static final int INPARAMETER = 2;
  private int parameterType;
  private int sqlType;
  private String name;
  private Object value;
  
  public int getParameterType()
  {
    return this.parameterType;
  }
  
  public void setParameterType(int paramInt)
  {
    this.parameterType = paramInt;
  }
  
  public int getSqlType()
  {
    return this.sqlType;
  }
  
  public void setSqlType(int paramInt)
  {
    this.sqlType = paramInt;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public Object getValue()
  {
    return this.value;
  }
  
  public void setValue(Object paramObject)
  {
    this.value = paramObject;
  }
}
