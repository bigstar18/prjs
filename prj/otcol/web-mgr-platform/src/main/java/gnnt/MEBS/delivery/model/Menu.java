package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;

public class Menu
  extends Clone
{
  public String id;
  public String name;
  public String url;
  public String image;
  public int popedom;
  public int reservedInt1;
  public String reservedChar1;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getImage()
  {
    return this.image;
  }
  
  public void setImage(String paramString)
  {
    this.image = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public int getPopedom()
  {
    return this.popedom;
  }
  
  public void setPopedom(int paramInt)
  {
    this.popedom = paramInt;
  }
  
  public String getReservedChar1()
  {
    return this.reservedChar1;
  }
  
  public void setReservedChar1(String paramString)
  {
    this.reservedChar1 = paramString;
  }
  
  public int getReservedInt1()
  {
    return this.reservedInt1;
  }
  
  public void setReservedInt1(int paramInt)
  {
    this.reservedInt1 = paramInt;
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
