package gnnt.MEBS.common.model;

import java.util.Iterator;
import java.util.Set;

public class Menu
{
  private Long id;
  private String name;
  private String icon;
  private String url;
  private Integer moduleId;
  private Integer visible;
  private Integer seq;
  private Menu menu;
  private Set<Menu> menuSet;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String paramString)
  {
    this.icon = paramString;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }
  
  public Integer getVisible()
  {
    return this.visible;
  }
  
  public void setVisible(Integer paramInteger)
  {
    this.visible = paramInteger;
  }
  
  public Integer getSeq()
  {
    return this.seq;
  }
  
  public void setSeq(Integer paramInteger)
  {
    this.seq = paramInteger;
  }
  
  public Set<Menu> getMenuSet()
  {
    return this.menuSet;
  }
  
  public void setMenuSet(Set<Menu> paramSet)
  {
    this.menuSet = paramSet;
  }
  
  public Menu getMenu()
  {
    return this.menu;
  }
  
  public void setMenu(Menu paramMenu)
  {
    this.menu = paramMenu;
  }
  
  public String toString()
  {
    String str = this.name;
    if (this.menuSet != null)
    {
      Iterator localIterator = this.menuSet.iterator();
      while (localIterator.hasNext())
      {
        Menu localMenu = (Menu)localIterator.next();
        str = str + "\n" + localMenu.toString();
      }
    }
    return str;
  }
}
