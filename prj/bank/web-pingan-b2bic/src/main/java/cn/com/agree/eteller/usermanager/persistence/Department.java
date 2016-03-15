package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Department
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String depnotype;
  private String name;
  private String superiorDepartmentId;
  private Department superiorDept;
  private String newSystemNo;
  private char finalFlag;
  private Set roles = new HashSet(0);
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Id", "机构号");
    hm.put("Depnotype", "机构类型");
    hm.put("Name", "机构名称");
    hm.put("SuperiorDepartmentId", "上级机构");
    hm.put("FinalFlag", "下属机构");
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Id=" + getId() + "|")
      .append("Depnotype=" + getDepnotype() + "|")
      .append("Name=" + getName() + "|")
      .append("SuperiorDepartmentId=" + getSuperiorDepartmentId() + "|")
      .append("FinalFlag=" + getFinalFlag());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from Department t where ";
    sb.append(sql)
      .append(" t.Id='" + getId() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getId() == null) {
      setId("");
    }
    if (getName() == null) {
      setName("");
    }
    if (getSuperiorDepartmentId() == null) {
      setSuperiorDepartmentId("");
    }
  }
  
  public String toChinese(String value)
  {
    if (value == null) {
      return null;
    }
    if (value.equals("")) {
      return "";
    }
    String[] str1InArray = value.split("\\Q|\\E");
    String str = "";
    for (int i = 0; i < str1InArray.length; i++)
    {
      String[] str1InArray2 = str1InArray[i].split("\\Q=\\E");
      if (hm.containsKey(str1InArray2[0])) {
        str1InArray2[0] = ((String)hm.get(str1InArray2[0]));
      }
      if (str1InArray2.length == 1) {
        str = str + str1InArray2[0] + "=|";
      } else {
        str = str + str1InArray2[0] + "=" + str1InArray2[1] + "|";
      }
    }
    return str;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
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
  
  public String getSuperiorDepartmentId()
  {
    return this.superiorDepartmentId;
  }
  
  public void setSuperiorDepartmentId(String superiorDepartmentId)
  {
    this.superiorDepartmentId = superiorDepartmentId;
  }
  
  public Set getRoles()
  {
    return this.roles;
  }
  
  public void setRoles(Set roles)
  {
    this.roles = roles;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("departmentId", getId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Department)) {
      return false;
    }
    Department castOther = (Department)other;
    return new EqualsBuilder().append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }
  
  public String getNewSystemNo()
  {
    return this.newSystemNo;
  }
  
  public void setNewSystemNo(String newSystemNo)
  {
    this.newSystemNo = newSystemNo;
  }
  
  public char getFinalFlag()
  {
    return this.finalFlag;
  }
  
  public void setFinalFlag(char finalFlag)
  {
    this.finalFlag = finalFlag;
  }
  
  public String getDepnotype()
  {
    return this.depnotype;
  }
  
  public void setDepnotype(String depnotype)
  {
    this.depnotype = depnotype;
  }
  
  public void setSuperiorDept(Department superiorDept)
  {
    this.superiorDept = superiorDept;
  }
  
  public Department getSuperiorDept()
  {
    return this.superiorDept;
  }
}
