package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EtellerSubappinfoPK
  implements Serializable
{
  private String appid;
  private String subappid;
  
  public EtellerSubappinfoPK(String appid, String subappid)
  {
    this.appid = appid;
    this.subappid = subappid;
  }
  
  public EtellerSubappinfoPK() {}
  
  public String getAppid()
  {
    return this.appid;
  }
  
  public void setAppid(String appid)
  {
    this.appid = appid;
  }
  
  public String getSubappid()
  {
    return this.subappid;
  }
  
  public void setSubappid(String subappid)
  {
    this.subappid = subappid;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("appid", getAppid()).append("subappid", getSubappid()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof EtellerSubappinfoPK)) {
      return false;
    }
    EtellerSubappinfoPK castOther = (EtellerSubappinfoPK)other;
    return new EqualsBuilder()
      .append(getAppid(), castOther.getAppid())
      .append(getSubappid(), castOther.getSubappid())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    

      new HashCodeBuilder().append(getAppid()).append(getSubappid()).toHashCode();
  }
}
