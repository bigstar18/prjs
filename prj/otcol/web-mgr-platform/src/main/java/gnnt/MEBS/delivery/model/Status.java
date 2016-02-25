package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;

public class Status
  extends Clone
{
  private String kind;
  private int value;
  private String name;
  
  public String getKind()
  {
    return this.kind;
  }
  
  public void setKind(String paramString)
  {
    this.kind = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public int getValue()
  {
    return this.value;
  }
  
  public void setValue(int paramInt)
  {
    this.value = paramInt;
  }
}
