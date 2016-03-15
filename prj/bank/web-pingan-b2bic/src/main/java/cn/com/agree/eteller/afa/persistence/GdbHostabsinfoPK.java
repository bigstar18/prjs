package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GdbHostabsinfoPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String hosttype;
  private String bankunitno;
  private String saveflag;
  private String agentflag;
  
  public GdbHostabsinfoPK(String hosttype, String bankunitno, String saveflag, String agentflag)
  {
    this.hosttype = hosttype;
    this.bankunitno = bankunitno;
    this.saveflag = saveflag;
    this.agentflag = agentflag;
  }
  
  public GdbHostabsinfoPK() {}
  
  public String getHosttype()
  {
    return this.hosttype;
  }
  
  public void setHosttype(String hosttype)
  {
    this.hosttype = hosttype;
  }
  
  public String getBankunitno()
  {
    return this.bankunitno;
  }
  
  public void setBankunitno(String bankunitno)
  {
    this.bankunitno = bankunitno;
  }
  
  public String getSaveflag()
  {
    return this.saveflag;
  }
  
  public void setSaveflag(String saveflag)
  {
    this.saveflag = saveflag;
  }
  
  public String getAgentflag()
  {
    return this.agentflag;
  }
  
  public void setAgentflag(String agentflag)
  {
    this.agentflag = agentflag;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("hosttype", getHosttype()).append("bankunitno", getBankunitno()).append("saveflag", getSaveflag()).append("agentflag", getAgentflag()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof GdbHostabsinfoPK)) {
      return false;
    }
    GdbHostabsinfoPK castOther = (GdbHostabsinfoPK)other;
    return new EqualsBuilder()
      .append(getHosttype(), castOther.getHosttype())
      .append(getBankunitno(), castOther.getBankunitno())
      .append(getSaveflag(), castOther.getSaveflag())
      .append(getAgentflag(), castOther.getAgentflag())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getHosttype()).append(getBankunitno()).append(getSaveflag()).append(getAgentflag()).toHashCode();
  }
}
