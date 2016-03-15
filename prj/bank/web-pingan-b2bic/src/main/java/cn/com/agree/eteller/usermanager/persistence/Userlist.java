package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Userlist
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String userId;
  private String tellerName;
  private String tellerState;
  private String tellerPasswd;
  private String error = "0";
  private String remark;
  private Long roleId;
  private Rolelist role;
  private String departmentId;
  private Department dept;
  private String pwdModDate;
  private String enterTime;
  private String exitTime;
  private String ipAddress;
  private String pwdInit;
  private String loginStatus;
  private String encryptMethod;
  private String resetLogin;
  private String level;
  private String comp;
  private String mail;
  
  public String getEnterTime()
  {
    return this.enterTime;
  }
  
  public void setEnterTime(String enterTime)
  {
    this.enterTime = enterTime;
  }
  
  public String getExitTime()
  {
    return this.exitTime;
  }
  
  public void setExitTime(String exitTime)
  {
    this.exitTime = exitTime;
  }
  
  public String getIpAddress()
  {
    return this.ipAddress;
  }
  
  public void setIpAddress(String ipAddress)
  {
    this.ipAddress = ipAddress;
  }
  
  public String getPwdInit()
  {
    return this.pwdInit;
  }
  
  public void setPwdInit(String pwdInit)
  {
    this.pwdInit = pwdInit;
  }
  
  public String getLoginStatus()
  {
    return this.loginStatus;
  }
  
  public void setLoginStatus(String loginStatus)
  {
    this.loginStatus = loginStatus;
  }
  
  public String getEncryptMethod()
  {
    return this.encryptMethod;
  }
  
  public void setEncryptMethod(String encryptMethod)
  {
    this.encryptMethod = encryptMethod;
  }
  
  public String getResetLogin()
  {
    return this.resetLogin;
  }
  
  public void setResetLogin(String resetLogin)
  {
    this.resetLogin = resetLogin;
  }
  
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("UserId", "标识");
    hm.put("TellerName", "姓名");
    hm.put("RoleId", "级别");
    hm.put("DepartmentId", "所属机构");
    hm.put("TellerState", "状态");
    hm.put("TellerPasswd", "密码");
    hm.put("Error", "出错次数");
    hm.put("Remark", "用户类型");
    hm.put("PwdModDate", "上次修改密码时间");
    hm.put("EncryptMethod", "密码校验方式");
    hm.put("PwdInit", "密码初始化状态");
    hm.put("LoginStatus", "登录状态");
    hm.put("ResetLogin", "重新登录状态");
    hm.put("PwdModDate", "密码修改时间");
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("UserId=" + getUserId() + "|")
      .append("TellerName=" + getTellerName() + "|")
      .append("RoleId=" + getRoleId() + "|")
      .append("DepartmentId=" + getDepartmentId() + "|")
      .append("TellerState=" + getTellerState() + "|")
      .append("TellerPasswd=" + getTellerPasswd() + "|")
      .append("Remark=" + getRemark() + "|")
      .append("PwdModDate=" + getPwdModDate() + "|")
      .append("EncryptMethod=" + getEncryptMethod() + "|")
      .append("PwdInit=" + getPwdInit() + "|")
      .append("LoginStatus=" + getLoginStatus() + "|")
      .append("ResetLogin=" + getResetLogin() + "|")
      .append("Error=" + getError());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from Userlist t where ";
    sb.append(sql)
      .append(" t.userId='" + getUserId() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getUserId() == null) {
      setUserId("");
    }
    if (getTellerName() == null) {
      setTellerName("");
    }
    if (getRoleId() == null) {
      setRoleId(new Long("0"));
    }
    if (getDepartmentId() == null) {
      setDepartmentId("");
    }
    if (getTellerState() == null) {
      setTellerState("");
    }
    if (getRemark() == null) {
      setRemark("");
    }
    if (getPwdInit() == null) {
      setPwdInit("");
    }
    if (getEncryptMethod() == null) {
      setEncryptMethod("");
    }
    if (getPwdModDate() == null) {
      setPwdModDate("");
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
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getTellerName()
  {
    return this.tellerName;
  }
  
  public void setTellerName(String tellerName)
  {
    this.tellerName = tellerName;
  }
  
  public String getTellerState()
  {
    return this.tellerState;
  }
  
  public void setTellerState(String tellerStat)
  {
    this.tellerState = tellerStat;
  }
  
  public String getTellerPasswd()
  {
    return this.tellerPasswd;
  }
  
  public void setTellerPasswd(String tellerPasswd)
  {
    this.tellerPasswd = tellerPasswd;
  }
  
  public String getError()
  {
    return this.error;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
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
  
  public String toString()
  {
    return new ToStringBuilder(this).append("userId", getUserId()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Userlist)) {
      return false;
    }
    Userlist castOther = (Userlist)other;
    return new EqualsBuilder().append(getUserId(), castOther.getUserId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getUserId()).toHashCode();
  }
  
  public String getPwdModDate()
  {
    return this.pwdModDate;
  }
  
  public void setPwdModDate(String pwdModDate)
  {
    this.pwdModDate = pwdModDate;
  }
  
  public void setRole(Rolelist role)
  {
    this.role = role;
  }
  
  public Rolelist getRole()
  {
    return this.role;
  }
  
  public void setDept(Department dept)
  {
    this.dept = dept;
  }
  
  public Department getDept()
  {
    return this.dept;
  }
  
  public String getLevel()
  {
    return this.level;
  }
  
  public void setLevel(String level)
  {
    this.level = level;
  }
  
  public String getComp()
  {
    return this.comp;
  }
  
  public void setComp(String comp)
  {
    this.comp = comp;
  }
  
  public String getMail()
  {
    return this.mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
}
