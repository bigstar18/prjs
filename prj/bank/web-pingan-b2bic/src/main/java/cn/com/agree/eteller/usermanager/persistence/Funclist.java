package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Funclist
  implements Serializable, Comparable<Funclist>
{
  private static final long serialVersionUID = 1L;
  private Long funcId;
  private String funcName;
  private String funcAddress;
  private String appid;
  private String funcDesc;
  private String runflg;
  private String subappid;
  private Set roles;
  private String runflgStr;
  private String appname;
  private Appinfo app;
  private EtellerSubappinfo subApp;
  
  public Funclist(Long funcId, String funcName, String funcAddress, String appid, String funcDesc, String runflg, String subappid, Set roles)
  {
    this.funcId = funcId;
    this.funcName = funcName;
    this.funcAddress = funcAddress;
    this.appid = appid;
    this.funcDesc = funcDesc;
    this.runflg = runflg;
    this.subappid = subappid;
    this.roles = roles;
  }
  
  public Funclist() {}
  
  public Funclist(Long funcId, String funcName, String funcAddress, String appid, String runflg, String subappid, Set roles)
  {
    this.funcId = funcId;
    this.funcName = funcName;
    this.funcAddress = funcAddress;
    this.appid = appid;
    this.runflg = runflg;
    this.subappid = subappid;
    this.roles = roles;
  }
  
  public Long getFuncId()
  {
    return this.funcId;
  }
  
  public void setFuncId(Long funcId)
  {
    this.funcId = funcId;
  }
  
  public String getFuncName()
  {
    return this.funcName;
  }
  
  public void setFuncName(String funcName)
  {
    this.funcName = funcName;
  }
  
  public String getFuncAddress()
  {
    return this.funcAddress;
  }
  
  public void setFuncAddress(String funcAddress)
  {
    this.funcAddress = funcAddress;
  }
  
  public String getAppid()
  {
    return this.appid;
  }
  
  public void setAppid(String appid)
  {
    this.appid = appid;
  }
  
  public String getFuncDesc()
  {
    return this.funcDesc;
  }
  
  public void setFuncDesc(String funcDesc)
  {
    this.funcDesc = funcDesc;
  }
  
  public String getRunflg()
  {
    return this.runflg;
  }
  
  public void setRunflg(String runflg)
  {
    this.runflg = runflg;
  }
  
  public Set getRoles()
  {
    return this.roles;
  }
  
  public void setRoles(Set roles)
  {
    this.roles = roles;
  }
  
  public String getRunflgStr()
  {
    if (getRunflg().equals("0")) {
      this.runflgStr = "否";
    } else if (getRunflg().equals("1")) {
      this.runflgStr = "是";
    } else {
      this.runflgStr = "无此类型";
    }
    return this.runflgStr;
  }
  
  public void setRunflgStr(String runflgStr)
  {
    this.runflgStr = runflgStr;
  }
  
  public String toString()
  {
    return 
    
      "Funclist [funcId=" + this.funcId + ", funcName=" + this.funcName + ", funcAddress=" + this.funcAddress + ", appid=" + this.appid + ", funcDesc=" + this.funcDesc + "]";
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Funclist)) {
      return false;
    }
    Funclist castOther = (Funclist)other;
    return new EqualsBuilder().append(getFuncId(), 
      castOther.getFuncId()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getFuncId()).toHashCode();
  }
  
  public String getAppname()
  {
    return this.appname;
  }
  
  public void setAppname(String appname)
  {
    this.appname = appname;
  }
  
  public String getSubappid()
  {
    return this.subappid;
  }
  
  public void setSubappid(String subappid)
  {
    this.subappid = subappid;
  }
  
  public Appinfo getApp()
  {
    return this.app;
  }
  
  public void setApp(Appinfo app)
  {
    this.app = app;
  }
  
  public EtellerSubappinfo getSubApp()
  {
    return this.subApp;
  }
  
  public void setSubApp(EtellerSubappinfo subApp)
  {
    this.subApp = subApp;
  }
  
  public int compareTo(Funclist o)
  {
    return this.funcId.compareTo(o.getFuncId());
  }
}
