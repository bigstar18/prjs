package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CredSubbusisysinfoPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sysid;
  private String unitno;
  private String subunitno;
  private String tradetype;
  
  public CredSubbusisysinfoPK(String sysid, String unitno, String subunitno, String tradetype)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.tradetype = tradetype;
  }
  
  public CredSubbusisysinfoPK() {}
  
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
  
  public String getSubunitno()
  {
    return this.subunitno;
  }
  
  public void setSubunitno(String subunitno)
  {
    this.subunitno = subunitno;
  }
  
  public String getTradetype()
  {
    return this.tradetype;
  }
  
  public void setTradetype(String tradetype)
  {
    this.tradetype = tradetype;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("tradetype", getTradetype()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CredSubbusisysinfoPK)) {
      return false;
    }
    CredSubbusisysinfoPK castOther = (CredSubbusisysinfoPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getTradetype(), castOther.getTradetype())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getTradetype()).toHashCode();
  }
}
