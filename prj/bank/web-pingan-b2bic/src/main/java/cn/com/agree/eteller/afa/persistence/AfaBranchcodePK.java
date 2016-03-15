package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaBranchcodePK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String zoneno;
  private String branchno;
  
  public AfaBranchcodePK(String sysid, String unitno, String zoneno, String branchno)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.zoneno = zoneno;
    this.branchno = branchno;
  }
  
  public AfaBranchcodePK() {}
  
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
  
  public String getZoneno()
  {
    return this.zoneno;
  }
  
  public void setZoneno(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getBranchno()
  {
    return this.branchno;
  }
  
  public void setBranchno(String branchno)
  {
    this.branchno = branchno;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("zoneno", getZoneno()).append("branchno", getBranchno()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaBranchcodePK)) {
      return false;
    }
    AfaBranchcodePK castOther = (AfaBranchcodePK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getZoneno(), castOther.getZoneno())
      .append(getBranchno(), castOther.getBranchno())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getZoneno()).append(getBranchno()).toHashCode();
  }
}
