package gnnt.MEBS.common.model;

import java.util.Iterator;
import java.util.Set;

public class Role
{
  private long id;
  private String name;
  private String description;
  private Set<Right> rightSet;
  private Set<User> userSet;
  
  public Set<User> getUserSet()
  {
    return this.userSet;
  }
  
  public void setUserSet(Set<User> paramSet)
  {
    this.userSet = paramSet;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
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
  
  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<Right> paramSet)
  {
    this.rightSet = paramSet;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void toRightSetIterator()
  {
    if (this.rightSet != null)
    {
      Iterator localIterator = this.rightSet.iterator();
      while (localIterator.hasNext()) {
        Right localRight = (Right)localIterator.next();
      }
    }
  }
  
  public void toUserSetIterator()
  {
    if (this.userSet != null)
    {
      Iterator localIterator = this.userSet.iterator();
      while (localIterator.hasNext()) {
        User localUser = (User)localIterator.next();
      }
    }
  }
}
