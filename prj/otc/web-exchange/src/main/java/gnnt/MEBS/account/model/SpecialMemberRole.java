package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Iterator;
import java.util.Set;

public class SpecialMemberRole
  extends Clone
{
  private Long id;
  private String name;
  private String description;
  private Set<SpecialMemberRight> rightSet;
  private Set<SpecialMemberUser> userSet;
  
  public Set<SpecialMemberUser> getUserSet()
  {
    return this.userSet;
  }
  
  public void setUserSet(Set<SpecialMemberUser> userSet)
  {
    this.userSet = userSet;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(long id)
  {
    this.id = Long.valueOf(id);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Set<SpecialMemberRight> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<SpecialMemberRight> rightSet)
  {
    this.rightSet = rightSet;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public void toRightSetIterator()
  {
    if (this.rightSet != null)
    {
      SpecialMemberRight localSpecialMemberRight;
      for (Iterator localIterator = this.rightSet.iterator(); localIterator.hasNext(); localSpecialMemberRight = (SpecialMemberRight)localIterator.next()) {}
    }
  }
  
  public void toUserSetIterator()
  {
    if (this.userSet != null)
    {
      SpecialMemberUser localSpecialMemberUser;
      for (Iterator localIterator = this.userSet.iterator(); localIterator.hasNext(); localSpecialMemberUser = (SpecialMemberUser)localIterator.next()) {}
    }
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
