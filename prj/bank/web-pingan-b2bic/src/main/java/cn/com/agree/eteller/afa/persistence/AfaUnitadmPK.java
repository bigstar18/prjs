package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaUnitadmPK
  implements Serializable
{
  private String sysid;
  private String unitno;
  
  public AfaUnitadmPK(String sysid, String unitno)
  {
    this.sysid = sysid;
    this.unitno = unitno;
  }
  
  public AfaUnitadmPK() {}
  
  public String getSysid()
  {
    return this.sysid;
  }
  
  public void setSysid(String sysid)
  {
    this.sysid = sysid;
  }
  
  public String getUnitno()
  {
    return this.unitno;
  }
  
  public void setUnitno(String unitno)
  {
    this.unitno = unitno;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaUnitadmPK)) {
      return false;
    }
    AfaUnitadmPK castOther = (AfaUnitadmPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    

      new HashCodeBuilder().append(getSysid()).append(getUnitno()).toHashCode();
  }
}
