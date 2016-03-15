package cn.com.agree.eteller.afa.page.selectmodel;

import java.io.Serializable;

public class PubType
  implements Serializable, Comparable
{
  private String id;
  private String value;
  
  public PubType() {}
  
  public PubType(String id, String value)
  {
    this.id = id;
    this.value = value;
  }
  
  public int compareTo(Object o)
  {
    return this.id.compareTo(((PubType)o).id);
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}
