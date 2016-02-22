package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;

public class Configruration
{
  private String key;
  private String describe;
  private String value;
  
  @ClassDiscription(name="关键字", key=true, keyWord=true)
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  @ClassDiscription(name="描述")
  public String getDescribe()
  {
    return this.describe;
  }
  
  public void setDescribe(String describe)
  {
    this.describe = describe;
  }
  
  @ClassDiscription(name="值")
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}
