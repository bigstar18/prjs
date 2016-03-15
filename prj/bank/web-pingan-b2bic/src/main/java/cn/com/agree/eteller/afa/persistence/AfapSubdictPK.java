package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfapSubdictPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String item;
  private String code;
  
  public AfapSubdictPK(String item, String code)
  {
    this.item = item;
    this.code = code;
  }
  
  public AfapSubdictPK() {}
  
  public String getItem()
  {
    return this.item;
  }
  
  public void setItem(String item)
  {
    this.item = item;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("item", getItem()).append("code", getCode()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfapSubdictPK)) {
      return false;
    }
    AfapSubdictPK castOther = (AfapSubdictPK)other;
    return new EqualsBuilder()
      .append(getItem(), castOther.getItem())
      .append(getCode(), castOther.getCode())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    

      new HashCodeBuilder().append(getItem()).append(getCode()).toHashCode();
  }
}
