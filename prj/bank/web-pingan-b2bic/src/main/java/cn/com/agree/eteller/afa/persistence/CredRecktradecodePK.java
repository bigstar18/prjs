package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CredRecktradecodePK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String subunitno;
  private String tradecode;
  
  public CredRecktradecodePK(String sysid, String unitno, String subunitno, String tradecode)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.tradecode = tradecode;
  }
  
  public CredRecktradecodePK() {}
  
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
  
  public String getTradecode()
  {
    return this.tradecode;
  }
  
  public void setTradecode(String tradecode)
  {
    this.tradecode = tradecode;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("tradecode", getTradecode()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CredRecktradecodePK)) {
      return false;
    }
    CredRecktradecodePK castOther = (CredRecktradecodePK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getTradecode(), castOther.getTradecode())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getTradecode()).toHashCode();
  }
}
