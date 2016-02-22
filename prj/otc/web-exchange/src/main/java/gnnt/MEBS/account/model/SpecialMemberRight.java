package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.util.HashSet;
import java.util.Set;

public class SpecialMemberRight
  extends Clone
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
  private Set<SpecialMemberRight> rightSet = new HashSet();
  private Set<SpecialMemberUser> userSet;
  private Set<SpecialMemberRole> roleSet;
  private SpecialMemberRight right;
  
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
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public Integer getIsLog()
  {
    return this.isLog;
  }
  
  public void setIsLog(Integer isLog)
  {
    this.isLog = isLog;
  }
  
  public Set<SpecialMemberRight> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<SpecialMemberRight> rightSet)
  {
    this.rightSet = rightSet;
  }
  
  public SpecialMemberRight getRight()
  {
    return this.right;
  }
  
  public void setRight(SpecialMemberRight right)
  {
    this.right = right;
  }
  
  public Set<SpecialMemberUser> getUserSet()
  {
    return this.userSet;
  }
  
  public void setUserSet(Set<SpecialMemberUser> userSet)
  {
    this.userSet = userSet;
  }
  
  public Set<SpecialMemberRole> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<SpecialMemberRole> roleSet)
  {
    this.roleSet = roleSet;
  }
  
  public boolean equals(Object o)
  {
    boolean sign = true;
    SpecialMemberRight r = this;
    if ((o instanceof SpecialMemberRight))
    {
      SpecialMemberRight r1 = (SpecialMemberRight)o;
      if ((r.getId() != r1.getId()) || (!r.getUrl().equals(r1.getUrl()))) {
        sign = false;
      }
    }
    else
    {
      sign = false;
    }
    return sign;
  }
  
  public void toRightSetIterator()
  {
    if (this.rightSet != null) {
      for (SpecialMemberRight r : this.rightSet) {
        r.toRightSetIterator();
      }
    }
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
