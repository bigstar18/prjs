package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaZhbrnoinfoPK
  implements Serializable
{
  private String zhno;
  private String brno;
  
  public AfaZhbrnoinfoPK(String zhno, String brno)
  {
    this.zhno = zhno;
    this.brno = brno;
  }
  
  public AfaZhbrnoinfoPK() {}
  
  public String getZhno()
  {
    return this.zhno;
  }
  
  public void setZhno(String zhno)
  {
    this.zhno = zhno;
  }
  
  public String getBrno()
  {
    return this.brno;
  }
  
  public void setBrno(String brno)
  {
    this.brno = brno;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("zhno", getZhno()).append("brno", getBrno()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaZhbrnoinfoPK)) {
      return false;
    }
    AfaZhbrnoinfoPK castOther = (AfaZhbrnoinfoPK)other;
    return new EqualsBuilder()
      .append(getZhno(), castOther.getZhno())
      .append(getBrno(), castOther.getBrno())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    

      new HashCodeBuilder().append(getZhno()).append(getBrno()).toHashCode();
  }
}
