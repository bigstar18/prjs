package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Rolelist
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long roleId;
  private String roleName;
  private Long superiorRoleId;
  private Rolelist superiorRole;
  private char finalFlag;
  private Set functions = new HashSet(0);
  private Set departments = new HashSet(0);
  
  public Long getRoleId()
  {
    return this.roleId;
  }
  
  public void setRoleId(Long roleId)
  {
    this.roleId = roleId;
  }
  
  public String getRoleName()
  {
    return this.roleName;
  }
  
  public void setRoleName(String roleName)
  {
    this.roleName = roleName;
  }
  
  public Set getFunctions()
  {
    return this.functions;
  }
  
  public void setFunctions(Set functions)
  {
    this.functions = functions;
  }
  
  public Set getDepartments()
  {
    return this.departments;
  }
  
  public void setDepartments(Set departments)
  {
    this.departments = departments;
  }
  
  public String toString()
  {
    return 
      new ToStringBuilder(this).append("roleId", getRoleId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Rolelist)) {
      return false;
    }
    Rolelist castOther = (Rolelist)other;
    return new EqualsBuilder().append(getRoleId(), 
      castOther.getRoleId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getRoleId()).toHashCode();
  }
  
  public Long getSuperiorRoleId()
  {
    return this.superiorRoleId;
  }
  
  public void setSuperiorRoleId(Long superiorRoleId)
  {
    this.superiorRoleId = superiorRoleId;
  }
  
  public char getFinalFlag()
  {
    return this.finalFlag;
  }
  
  public void setFinalFlag(char finalFlag)
  {
    this.finalFlag = finalFlag;
  }
  
  public void setSuperiorRole(Rolelist superiorRole)
  {
    this.superiorRole = superiorRole;
  }
  
  public Rolelist getSuperiorRole()
  {
    return this.superiorRole;
  }
}
