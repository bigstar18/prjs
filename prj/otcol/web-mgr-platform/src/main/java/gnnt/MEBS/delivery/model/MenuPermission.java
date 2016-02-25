package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;
import java.util.List;

public class MenuPermission
  extends Clone
{
  public String id;
  public String menuName;
  public List menuList;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public List getMenuList()
  {
    return this.menuList;
  }
  
  public void setMenuList(List paramList)
  {
    this.menuList = paramList;
  }
  
  public String getMenuName()
  {
    return this.menuName;
  }
  
  public void setMenuName(String paramString)
  {
    this.menuName = paramString;
  }
}
