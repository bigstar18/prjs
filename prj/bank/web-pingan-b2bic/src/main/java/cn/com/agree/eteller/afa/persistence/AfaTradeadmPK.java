package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaTradeadmPK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String subunitno;
  private String trxcode;
  
  public AfaTradeadmPK(String sysid, String unitno, String subunitno, String trxcode)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.trxcode = trxcode;
  }
  
  public AfaTradeadmPK() {}
  
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
  
  public String getTrxcode()
  {
    return this.trxcode;
  }
  
  public void setTrxcode(String trxcode)
  {
    this.trxcode = trxcode;
  }
  
  public String toString()
  {
    return 
    



      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("trxcode", getTrxcode()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaTradeadmPK)) {
      return false;
    }
    AfaTradeadmPK castOther = (AfaTradeadmPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getTrxcode(), castOther.getTrxcode())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    



      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getTrxcode()).toHashCode();
  }
}
