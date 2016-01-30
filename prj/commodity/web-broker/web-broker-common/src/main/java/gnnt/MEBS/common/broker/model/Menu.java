package gnnt.MEBS.common.broker.model;

import java.util.Set;

public class Menu extends StandardModel
{
  private static final long serialVersionUID = 2430870130134342514L;
  private Long id;
  private String name;
  private String icon;
  private String url;
  private Integer moduleId;
  private Integer visible;
  private Integer seq;
  private Long parentID;
  private Menu parentMenu;
  private Set<Menu> childMenuSet;
  private String onlyMember;

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

  public Set<Menu> getChildMenuSet()
  {
    return this.childMenuSet;
  }

  public void setChildMenuSet(Set<Menu> paramSet)
  {
    this.childMenuSet = paramSet;
  }

  public Long getParentID()
  {
    return this.parentID;
  }

  public void setParentID(Long paramLong)
  {
    this.parentID = paramLong;
  }

  public Menu getParentMenu()
  {
    return this.parentMenu;
  }

  public void setParentMenu(Menu paramMenu)
  {
    this.parentMenu = paramMenu;
  }

  public String getOnlyMember()
  {
    return this.onlyMember;
  }

  public void setOnlyMember(String paramString)
  {
    this.onlyMember = paramString;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}