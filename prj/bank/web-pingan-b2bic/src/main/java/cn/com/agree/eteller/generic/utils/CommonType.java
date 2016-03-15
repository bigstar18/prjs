package cn.com.agree.eteller.generic.utils;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;

public class CommonType
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id;
  private String value;
  
  public CommonType() {}
  
  public CommonType(String id, String value)
  {
    this.id = id;
    this.value = value;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CommonType)) {
      return false;
    }
    CommonType castOther = (CommonType)other;
    return new EqualsBuilder().append(getId(), castOther.getId())
      .isEquals();
  }
}
