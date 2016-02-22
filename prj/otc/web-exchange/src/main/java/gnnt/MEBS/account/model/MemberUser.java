package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MemberUser
  extends Clone
{
  private String userId;
  private String name;
  private String password;
  private String description;
  private String skin = "default";
  private String keyCode;
  private Set<MemberRight> rightSet;
  private Set<MemberRole> roleSet;
  private Map<Long, MemberRight> rightMap;
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
  
  public Set<MemberRole> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<MemberRole> roleSet)
  {
    this.roleSet = roleSet;
  }
  
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
  
  public Set<MemberRight> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<MemberRight> rightSet)
  {
    this.rightSet = rightSet;
  }
  
  @ClassDiscription(keyWord=true, name="会员管理员名称")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @ClassDiscription(name="会员管理员描述")
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
  
  public Map<Long, MemberRight> getRightMap()
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
      MemberRight localMemberRight;
      for (Iterator localIterator = this.rightSet.iterator(); localIterator.hasNext(); localMemberRight = (MemberRight)localIterator.next()) {}
    }
  }
  
  public void toRoleSetIterator(boolean rightSign)
  {
    if (this.roleSet != null) {
      for (MemberRole r : this.roleSet) {
        if (rightSign) {
          r.toRightSetIterator();
        }
      }
    }
  }
  
  private Map<Long, MemberRight> toRightMap()
  {
    Map<Long, MemberRight> newMap = new HashMap();
    
    Set<MemberRight> rightSet = this.rightSet;
    Set<MemberRole> roleSet = this.roleSet;
    if (rightSet != null)
    {
      Iterator<MemberRight> rightIterator = rightSet.iterator();
      newMap = putKeyAndValue(rightIterator, newMap);
    }
    if (roleSet != null)
    {
      Iterator roleIterator = roleSet.iterator();
      if (roleIterator != null) {
        while (roleIterator.hasNext())
        {
          MemberRole role = (MemberRole)roleIterator.next();
          Set<MemberRight> roleRightSet = role.getRightSet();
          if (roleRightSet != null) {
            newMap = putKeyAndValue(roleRightSet.iterator(), newMap);
          }
        }
      }
    }
    return newMap;
  }
  
  private Map<Long, MemberRight> putKeyAndValue(Iterator<MemberRight> iterator, Map<Long, MemberRight> newMap)
  {
    if (iterator != null) {
      while (iterator.hasNext())
      {
        MemberRight right = (MemberRight)iterator.next();
        Long rightId = new Long(right.getId().longValue());
        if (containsObj(rightId, newMap)) {
          newMap.put(right.getId(), right);
        }
      }
    }
    return newMap;
  }
  
  private boolean containsObj(Long rightId, Map<Long, MemberRight> map)
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
    MemberUser u = this;
    if ((o instanceof MemberUser))
    {
      MemberUser u1 = (MemberUser)o;
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
