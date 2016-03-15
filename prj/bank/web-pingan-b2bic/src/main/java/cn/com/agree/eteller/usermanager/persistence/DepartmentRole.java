package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;

public class DepartmentRole
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String departmentId;
  private Long roleId;
  
  public String getDepartmentId()
  {
    return this.departmentId;
  }
  
  public void setDepartmentId(String departmentId)
  {
    this.departmentId = departmentId;
  }
  
  public Long getRoleId()
  {
    return this.roleId;
  }
  
  public void setRoleId(Long roleId)
  {
    this.roleId = roleId;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof DepartmentRole))
    {
      DepartmentRole other = (DepartmentRole)obj;
      return (this.departmentId.equals(other.departmentId)) && (this.roleId.equals(other.roleId));
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.departmentId.hashCode() + this.roleId.hashCode();
  }
}
