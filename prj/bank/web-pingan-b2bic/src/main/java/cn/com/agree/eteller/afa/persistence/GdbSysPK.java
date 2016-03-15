package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GdbSysPK
  implements Serializable
{
  private String bankunitno;
  private String onlineflag;
  
  public GdbSysPK(String bankunitno, String onlineflag)
  {
    this.bankunitno = bankunitno;
    this.onlineflag = onlineflag;
  }
  
  public GdbSysPK() {}
  
  public String getBankunitno()
  {
    return this.bankunitno;
  }
  
  public void setBankunitno(String bankunitno)
  {
    this.bankunitno = bankunitno;
  }
  
  public String getOnlineflag()
  {
    return this.onlineflag;
  }
  
  public void setOnlineflag(String onlineflag)
  {
    this.onlineflag = onlineflag;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("bankunitno", getBankunitno()).append("onlineflag", getOnlineflag()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof GdbSysPK)) {
      return false;
    }
    GdbSysPK castOther = (GdbSysPK)other;
    return new EqualsBuilder()
      .append(getBankunitno(), castOther.getBankunitno())
      .append(getOnlineflag(), castOther.getOnlineflag())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    

      new HashCodeBuilder().append(getBankunitno()).append(getOnlineflag()).toHashCode();
  }
}
