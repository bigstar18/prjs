package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaFeeinfo
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String subunitno;
  private String feeflag;
  private String acctype;
  private String amount;
  private String note1;
  private String note2;
  
  public AfaFeeinfo(String sysid, String unitno, String subunitno, String feeflag, String acctype, String amount, String note1, String note2)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.subunitno = subunitno;
    this.feeflag = feeflag;
    this.acctype = acctype;
    this.amount = amount;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaFeeinfo() {}
  
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
  
  public String getFeeflag()
  {
    return this.feeflag;
  }
  
  public void setFeeflag(String feeflag)
  {
    this.feeflag = feeflag;
  }
  
  public String getAcctype()
  {
    return this.acctype;
  }
  
  public void setAcctype(String acctype)
  {
    this.acctype = acctype;
  }
  
  public String getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(String amount)
  {
    this.amount = amount;
  }
  
  public String getNote1()
  {
    return this.note1;
  }
  
  public void setNote1(String note1)
  {
    this.note1 = note1;
  }
  
  public String getNote2()
  {
    return this.note2;
  }
  
  public void setNote2(String note2)
  {
    this.note2 = note2;
  }
  
  public String toString()
  {
    return 
    







      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("subunitno", getSubunitno()).append("feeflag", getFeeflag()).append("acctype", getAcctype()).append("amount", getAmount()).append("note1", getNote1()).append("note2", getNote2()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaFeeinfo)) {
      return false;
    }
    AfaFeeinfo castOther = (AfaFeeinfo)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getSubunitno(), castOther.getSubunitno())
      .append(getFeeflag(), castOther.getFeeflag())
      .append(getAcctype(), castOther.getAcctype())
      .append(getAmount(), castOther.getAmount())
      .append(getNote1(), castOther.getNote1())
      .append(getNote2(), castOther.getNote2())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    







      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getSubunitno()).append(getFeeflag()).append(getAcctype()).append(getAmount()).append(getNote1()).append(getNote2()).toHashCode();
  }
}
