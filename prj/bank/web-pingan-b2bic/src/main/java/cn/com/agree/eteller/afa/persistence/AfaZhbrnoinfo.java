package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaZhbrnoinfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaZhbrnoinfoPK comp_id;
  private String brname;
  private String note1;
  private String note2;
  
  public AfaZhbrnoinfo(AfaZhbrnoinfoPK comp_id, String brname, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.brname = brname;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaZhbrnoinfo() {}
  
  public AfaZhbrnoinfo(AfaZhbrnoinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public AfaZhbrnoinfoPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaZhbrnoinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getBrname()
  {
    return this.brname;
  }
  
  public void setBrname(String brname)
  {
    this.brname = brname;
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
    

      new ToStringBuilder(this).append("comp_id", getComp_id()).append("brname", getBrname()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaZhbrnoinfo)) {
      return false;
    }
    AfaZhbrnoinfo castOther = (AfaZhbrnoinfo)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
}
