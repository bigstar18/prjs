package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Iterator;
import java.util.Set;

public class MemberRole
  extends Clone
{
  private Long id;
  private String name;
  private String description;
  private Set<MemberRight> rightSet;
  private Set<MemberUser> userSet;
  
  public Set<MemberUser> getUserSet()
  {
    return this.userSet;
  }
  
  public void setUserSet(Set<MemberUser> userSet)
  {
    this.userSet = userSet;
  }
  
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
  
  public Set<MemberRight> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<MemberRight> rightSet)
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
      MemberRight localMemberRight;
      for (Iterator localIterator = this.rightSet.iterator(); localIterator.hasNext(); localMemberRight = (MemberRight)localIterator.next()) {}
    }
  }
  
  public void toUserSetIterator()
  {
    if (this.userSet != null)
    {
      MemberUser localMemberUser;
      for (Iterator localIterator = this.userSet.iterator(); localIterator.hasNext(); localMemberUser = (MemberUser)localIterator.next()) {}
    }
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
