package gnnt.MEBS.member.firm.unit;

import gnnt.MEBS.base.model.Clone;

public class Configuration
  extends Clone
{
  private String key;
  private String describe;
  private String value;
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public String getDescribe()
  {
    return this.describe;
  }
  
  public void setDescribe(String paramString)
  {
    this.describe = paramString;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
}
