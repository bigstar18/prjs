package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaAgentadmPK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String subunitno;
  private String agentflag;
  private String zoneno;
  private String zhno;
  
  public AfaAgentadmPK(String sysid, String unitno, String subunitno, String agentflag, String zoneno, String zhno)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.agentflag = agentflag;
    this.zoneno = zoneno;
    this.zhno = zhno;
  }
  
  public AfaAgentadmPK() {}
  
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
  
  public String getAgentflag()
  {
    return this.agentflag;
  }
  
  public void setAgentflag(String agentflag)
  {
    this.agentflag = agentflag;
  }
  
  public String getZoneno()
  {
    return this.zoneno;
  }
  
  public void setZoneno(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getZhno()
  {
    return this.zhno;
  }
  
  public void setZhno(String zhno)
  {
    this.zhno = zhno;
  }
  
  public String toString()
  {
    return 
    





      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("agentflag", getAgentflag()).append("zoneno", getZoneno()).append("zhno", getZhno()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaAgentadmPK)) {
      return false;
    }
    AfaAgentadmPK castOther = (AfaAgentadmPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getAgentflag(), castOther.getAgentflag())
      .append(getZoneno(), castOther.getZoneno())
      .append(getZhno(), castOther.getZhno())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    





      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getAgentflag()).append(getZoneno()).append(getZhno()).toHashCode();
  }
}
