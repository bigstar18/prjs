package gnnt.MEBS.common.model;

import gnnt.MEBS.account.model.SpecialMember;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class User
  extends Clone
{
  private String userId;
  private String name;
  private String password;
  private String description;
  private String skin = "default";
  private String keyCode;
  private Set<Right> rightSet;
  private Set<Role> roleSet;
  private Set<Role> operateRoleSet;
  private String type;
  private Map<Long, Right> rightMap;
  private SpecialMember memberInfo;
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public Set<Role> getOperateRoleSet()
  {
    return this.operateRoleSet;
  }
  
  public void setOperateRoleSet(Set<Role> operateRoleSet)
  {
    this.operateRoleSet = operateRoleSet;
  }
  
  private String isForbid = "N";
  private long sessionId;
  
  @ClassDiscription(name="状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Y", value="禁用"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="N", value="不禁用")})
  public String getIsForbid()
  {
    return this.isForbid;
  }
  
  public void setIsForbid(String isForbid)
  {
    this.isForbid = isForbid;
  }
  
  public String getMemberNo()
  {
    if (this.memberInfo == null) {
      return null;
    }
    return this.memberInfo.getId();
  }
  
  public void setMemberNo(String memberNo)
  {
    if (this.memberInfo == null) {
      this.memberInfo = new SpecialMember();
    }
    this.memberInfo.setId(memberNo);
  }
  
  public SpecialMember getMemberInfo()
  {
    return this.memberInfo;
  }
  
  public void setMemberInfo(SpecialMember memberInfo)
  {
    this.memberInfo = memberInfo;
  }
  
  public void setRightMap(Map<Long, Right> rightMap)
  {
    this.rightMap = rightMap;
  }
  
  public long getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(long sessionId)
  {
    this.sessionId = sessionId;
  }
  
  public Set<Role> getRoleSet()
  {
    return this.roleSet;
  }
  
  public void setRoleSet(Set<Role> roleSet)
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
  
  public Set<Right> getRightSet()
  {
    return this.rightSet;
  }
  
  public void setRightSet(Set<Right> rightSet)
  {
    this.rightSet = rightSet;
  }
  
  @ClassDiscription(name="管理员名称", keyWord=true)
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
      Right localRight;
      for (Iterator localIterator = this.rightSet.iterator(); localIterator.hasNext(); localRight = (Right)localIterator.next()) {}
    }
  }
  
  public void toRoleSetIterator(boolean rightSign)
  {
    if (this.roleSet != null) {
      for (Role r : this.roleSet) {
        if (rightSign) {
          r.toRightSetIterator();
        }
      }
    }
  }
  
  public void toOperateRoleSetIterator()
  {
    if (this.operateRoleSet != null)
    {
      Role localRole;
      for (Iterator localIterator = this.operateRoleSet.iterator(); localIterator.hasNext(); localRole = (Role)localIterator.next()) {}
    }
  }
  
  private Map<Long, Right> toRightMap()
  {
    Map<Long, Right> newMap = new HashMap();
    
    Set<Right> rightSet = this.rightSet;
    Set<Role> roleSet = this.roleSet;
    if (rightSet != null)
    {
      Iterator<Right> rightIterator = rightSet.iterator();
      newMap = putKeyAndValue(rightIterator, newMap);
    }
    if (roleSet != null)
    {
      Iterator roleIterator = roleSet.iterator();
      if (roleIterator != null) {
        while (roleIterator.hasNext())
        {
          Role role = (Role)roleIterator.next();
          Set<Right> roleRightSet = role.getRightSet();
          if (roleRightSet != null) {
            newMap = putKeyAndValue(roleRightSet.iterator(), newMap);
          }
        }
      }
    }
    return newMap;
  }
  
  private Map<Long, Right> putKeyAndValue(Iterator<Right> iterator, Map<Long, Right> newMap)
  {
    if (iterator != null) {
      while (iterator.hasNext())
      {
        Right right = (Right)iterator.next();
        Long rightId = new Long(right.getId().longValue());
        if (containsObj(rightId, newMap)) {
          newMap.put(right.getId(), right);
        }
      }
    }
    return newMap;
  }
  
  private boolean containsObj(Long rightId, Map<Long, Right> map)
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
    User u = this;
    if ((o instanceof User))
    {
      User u1 = (User)o;
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
  
  public void setPrimary(String arg0)
  {
    this.userId = arg0;
  }
}
