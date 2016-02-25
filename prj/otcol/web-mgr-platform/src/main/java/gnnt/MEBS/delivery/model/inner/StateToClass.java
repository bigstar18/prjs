package gnnt.MEBS.delivery.model.inner;

import gnnt.MEBS.base.model.Clone;

public class StateToClass
  extends Clone
{
  private int id;
  private String kind;
  private String state;
  private String url;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public String getKind()
  {
    return this.kind;
  }
  
  public void setKind(String paramString)
  {
    this.kind = paramString;
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String paramString)
  {
    this.state = paramString;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
}
