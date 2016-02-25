package gnnt.MEBS.common.model;

import java.util.Iterator;
import java.util.Set;

public class Right
{
  private Long id;
  private String name;
  private String icon;
  private String url;
  private Integer moduleId;
  private Integer visible;
  private Integer seq;
  private Integer type;
  private Integer isLog;
  private Set<Right> rightSet;
  private Set<User> userSet;
  private Set<Role> roleSet;
  private Right right;
  
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
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer paramInteger)
  {
    this.type = paramInteger;
  }
  
  public Integer getIsLog()
  {
    return this.isLog;
  }
  
  public void setIsLog(Integer paramInteger)
  {
    this.isLog = paramInteger;
  }
  
  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<Right> paramSet)
  {
    this.rightSet = paramSet;
  }
  
  public Right getRight()
  {
    return this.right;
  }
  
  public void setRight(Right paramRight)
  {
    this.right = paramRight;
  }
  
  public Set<User> getUserSet()
  {
    return this.userSet;
  }
  
  public void setUserSet(Set<User> paramSet)
  {
    this.userSet = paramSet;
  }
  
  public Set<Role> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<Role> paramSet)
  {
    this.roleSet = paramSet;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    Right localRight1 = this;
    if ((paramObject instanceof Right))
    {
      Right localRight2 = (Right)paramObject;
      if ((localRight1.getId() != localRight2.getId()) || (!localRight1.getUrl().equals(localRight2.getUrl()))) {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public void toRightSetIterator()
  {
    if (this.rightSet != null)
    {
      Iterator localIterator = this.rightSet.iterator();
      while (localIterator.hasNext())
      {
        Right localRight = (Right)localIterator.next();
        localRight.toRightSetIterator();
      }
    }
  }
}
