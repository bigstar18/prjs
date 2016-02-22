package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SpecialMemberUser
  extends Clone
{
  private String userId;
  private String name;
  private String password;
  private String description;
  private String skin = "default";
  private String keyCode;
  private Set<SpecialMemberRight> rightSet;
  private Set<SpecialMemberRole> roleSet;
  private Map<Long, SpecialMemberRight> rightMap;
  private Long sessionId;
  private String memberId;
  
  public String getMemberId()
  {
    return this.memberId;
  }
  
  public void setMemberId(String memberId)
  {
    this.memberId = memberId;
  }
  
  public Long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long sessionId)
  {
    this.sessionId = Long.valueOf(sessionId);
  }
  
  public Set<SpecialMemberRole> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<SpecialMemberRole> roleSet)
  {
    this.roleSet = roleSet;
  }
  
  @ClassDiscription(name="管理员Id", key=true, keyWord=true)
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public Set<SpecialMemberRight> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<SpecialMemberRight> rightSet)
  {
    this.rightSet = rightSet;
  }
  
  @ClassDiscription(name="名称", keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="描述")
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public String getSkin()
  {
    return this.skin;
  }
  
  public void setSkin(String skin)
  {
    this.skin = skin;
  }
  
  public String getKeyCode()
  {
    return this.keyCode;
  }
  
  public void setKeyCode(String keyCode)
  {
    this.keyCode = keyCode;
  }
  
  public Map<Long, SpecialMemberRight> getRightMap()
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
      SpecialMemberRight localSpecialMemberRight;
      for (Iterator localIterator = this.rightSet.iterator(); localIterator.hasNext(); localSpecialMemberRight = (SpecialMemberRight)localIterator.next()) {}
    }
  }
  
  public void toRoleSetIterator(boolean rightSign)
  {
    if (this.roleSet != null) {
      for (SpecialMemberRole r : this.roleSet) {
        if (rightSign) {
          r.toRightSetIterator();
        }
      }
    }
  }
  
  private Map<Long, SpecialMemberRight> toRightMap()
  {
    Map<Long, SpecialMemberRight> newMap = new HashMap();
    
    Set<SpecialMemberRight> rightSet = this.rightSet;
    Set<SpecialMemberRole> roleSet = this.roleSet;
    if (rightSet != null)
    {
      Iterator<SpecialMemberRight> rightIterator = rightSet.iterator();
      newMap = putKeyAndValue(rightIterator, newMap);
    }
    if (roleSet != null)
    {
      Iterator roleIterator = roleSet.iterator();
      if (roleIterator != null) {
        while (roleIterator.hasNext())
        {
          SpecialMemberRole role = (SpecialMemberRole)roleIterator.next();
          Set<SpecialMemberRight> roleRightSet = role.getRightSet();
          if (roleRightSet != null) {
            newMap = putKeyAndValue(roleRightSet.iterator(), newMap);
          }
        }
      }
    }
    return newMap;
  }
  
  private Map<Long, SpecialMemberRight> putKeyAndValue(Iterator<SpecialMemberRight> iterator, Map<Long, SpecialMemberRight> newMap)
  {
    if (iterator != null) {
      while (iterator.hasNext())
      {
        SpecialMemberRight right = (SpecialMemberRight)iterator.next();
        Long rightId = new Long(right.getId().longValue());
        if (containsObj(rightId, newMap)) {
          newMap.put(right.getId(), right);
        }
      }
    }
    return newMap;
  }
  
  private boolean containsObj(Long rightId, Map<Long, SpecialMemberRight> map)
  {
    boolean ceckResult = true;
    if (map.containsKey(rightId)) {
      ceckResult = false;
    }
    return ceckResult;
  }
  
  public boolean equals(Object o)
  {
    boolean sign = true;
    SpecialMemberUser u = this;
    if ((o instanceof SpecialMemberUser))
    {
      SpecialMemberUser u1 = (SpecialMemberUser)o;
      if (!u1.getUserId().equals(u.getUserId())) {
        sign = false;
      }
    }
    else
    {
      sign = false;
    }
    return sign;
  }
  
  public int hashCode()
  {
    return this.userId != null ? this.userId.hashCode() : 0;
  }
  
  public String getId()
  {
    return this.userId;
  }
  
  public void setPrimary(String primary)
  {
    this.userId = primary;
  }
}
