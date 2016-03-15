package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaPinzoneinfoPK
  implements Serializable
{
  private String sysid;
  private String zoneno;
  private String tremid;
  
  public AfaPinzoneinfoPK(String sysid, String zoneno, String tremid)
  {
    this.sysid = sysid;
    this.zoneno = zoneno;
    this.tremid = tremid;
  }
  
  public AfaPinzoneinfoPK() {}
  
  public String getSysid()
  {
    return this.sysid;
  }
  
  public void setSysid(String sysid)
  {
    this.sysid = sysid;
  }
  
  public String getZoneno()
  {
    return this.zoneno;
  }
  
  public void setZoneno(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getTremid()
  {
    return this.tremid;
  }
  
  public void setTremid(String tremid)
  {
    this.tremid = tremid;
  }
  
  public String toString()
  {
    return 
    


      new ToStringBuilder(this).append("sysid", getSysid()).append("zoneno", getZoneno()).append("tremid", getTremid()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaPinzoneinfoPK)) {
      return false;
    }
    AfaPinzoneinfoPK castOther = (AfaPinzoneinfoPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getZoneno(), castOther.getZoneno())
      .append(getTremid(), castOther.getTremid())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    


      new HashCodeBuilder().append(getSysid()).append(getZoneno()).append(getTremid()).toHashCode();
  }
}
