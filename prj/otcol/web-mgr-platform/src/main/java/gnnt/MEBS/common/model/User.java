package gnnt.MEBS.common.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class User
{
  private String userId;
  private String name;
  private String password;
  private String description;
  private String skin;
  private String keyCode;
  private Set<Right> rightSet;
  private Set<Role> roleSet;
  private Map<Long, Right> rightMap;
  private long sessionId;
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long paramLong)
  {
    this.sessionId = paramLong;
  }
  
  public Set<Role> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<Role> paramSet)
  {
    this.roleSet = paramSet;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<Right> paramSet)
  {
    this.rightSet = paramSet;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getSkin()
  {
    return this.skin;
  }
  
  public void setSkin(String paramString)
  {
    this.skin = paramString;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String paramString)
  {
    this.keyCode = paramString;
  }
  
  public Map<Long, Right> getRightMap()
  {
    if (this.rightMap == null) {
      this.rightMap = toRightMap();
    }
    return this.rightMap;
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
  
  public void toRoleSetIterator(boolean paramBoolean)
  {
    if (this.roleSet != null)
    {
      Iterator localIterator = this.roleSet.iterator();
      while (localIterator.hasNext())
      {
        Role localRole = (Role)localIterator.next();
        if (paramBoolean) {
          localRole.toRightSetIterator();
        }
      }
    }
  }
  
  private Map<Long, Right> toRightMap()
  {
    Object localObject = new HashMap();
    Set localSet1 = this.rightSet;
    Set localSet2 = this.roleSet;
    Iterator localIterator;
    if (localSet1 != null)
    {
      localIterator = localSet1.iterator();
      localObject = putKeyAndValue(localIterator, (Map)localObject);
    }
    if (localSet2 != null)
    {
      localIterator = localSet2.iterator();
      if (localIterator != null) {
        while (localIterator.hasNext())
        {
          Role localRole = (Role)localIterator.next();
          Set localSet3 = localRole.getRightSet();
          if (localSet3 != null) {
            localObject = putKeyAndValue(localSet3.iterator(), (Map)localObject);
          }
        }
      }
    }
    return localObject;
  }
  
  private Map<Long, Right> putKeyAndValue(Iterator<Right> paramIterator, Map<Long, Right> paramMap)
  {
    if (paramIterator != null) {
      while (paramIterator.hasNext())
      {
        Right localRight = (Right)paramIterator.next();
        Long localLong = new Long(localRight.getId().longValue());
        if (containsObj(localLong, paramMap)) {
          paramMap.put(localRight.getId(), localRight);
        }
      }
    }
    return paramMap;
  }
  
  private boolean containsObj(Long paramLong, Map<Long, Right> paramMap)
  {
    boolean bool = true;
    if (paramMap.containsKey(paramLong)) {
      bool = false;
    }
    return bool;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    User localUser1 = this;
    if ((paramObject instanceof User))
    {
      User localUser2 = (User)paramObject;
      if (!localUser2.getUserId().equals(localUser1.getUserId())) {
        bool = false;
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.userId != null ? this.userId.hashCode() : 0;
  }
}
