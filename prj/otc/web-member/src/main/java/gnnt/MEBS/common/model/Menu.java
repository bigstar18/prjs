package gnnt.MEBS.common.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Set;

public class Menu
  extends Clone
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
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public void setIcon(String icon)
  {
    this.icon = icon;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer moduleId)
  {
    this.moduleId = moduleId;
  }
  
  public Integer getVisible()
  {
    return this.visible;
  }
  
  public void setVisible(Integer visible)
  {
    this.visible = visible;
  }
  
  public Integer getSeq()
  {
    return this.seq;
  }
  
  public void setSeq(Integer seq)
  {
    this.seq = seq;
  }
  
  public Set<Menu> getMenuSet()
  {
    return this.menuSet;
  }
  
  public void setMenuSet(Set<Menu> menuSet)
  {
    this.menuSet = menuSet;
  }
  
  public Menu getMenu()
  {
    return this.menu;
  }
  
  public void setMenu(Menu menu)
  {
    this.menu = menu;
  }
  
  public String toString()
  {
    String name = this.name;
    if (this.menuSet != null) {
      for (Menu m : this.menuSet) {
        name = name + "\n" + m.toString();
      }
    }
    return name;
  }
  
  public void setPrimary(String arg0) {}
}
