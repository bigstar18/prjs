package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CredCardbinPK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String subunitno;
  private String cardbin;
  
  public CredCardbinPK(String sysid, String unitno, String subunitno, String cardbin)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.cardbin = cardbin;
  }
  
  public CredCardbinPK() {}
  
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
  
  public String getCardbin()
  {
    return this.cardbin;
  }
  
  public void setCardbin(String cardbin)
  {
    this.cardbin = cardbin;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("cardbin", getCardbin()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CredCardbinPK)) {
      return false;
    }
    CredCardbinPK castOther = (CredCardbinPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getCardbin(), castOther.getCardbin())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getCardbin()).toHashCode();
  }
}
