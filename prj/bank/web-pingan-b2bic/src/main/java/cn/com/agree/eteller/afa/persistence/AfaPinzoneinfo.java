package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaPinzoneinfo
  implements Serializable
{
  private AfaPinzoneinfoPK comp_id;
  private String zmkname;
  private String pvkname;
  private String zpkname;
  private String crtdate;
  private String uptdate;
  private String uptcycle;
  private String reserv1;
  private String reserv2;
  private String reserv3;
  
  public AfaPinzoneinfo(AfaPinzoneinfoPK comp_id, String zmkname, String pvkname, String zpkname, String crtdate, String uptdate, String uptcycle, String reserv1, String reserv2, String reserv3)
  {
    this.comp_id = comp_id;
    this.zmkname = zmkname;
    this.pvkname = pvkname;
    this.zpkname = zpkname;
    this.crtdate = crtdate;
    this.uptdate = uptdate;
    this.uptcycle = uptcycle;
    this.reserv1 = reserv1;
    this.reserv2 = reserv2;
    this.reserv3 = reserv3;
  }
  
  public AfaPinzoneinfo() {}
  
  public AfaPinzoneinfo(AfaPinzoneinfoPK comp_id, String zmkname, String pvkname, String zpkname, String crtdate, String uptdate, String uptcycle)
  {
    this.comp_id = comp_id;
    this.zmkname = zmkname;
    this.pvkname = pvkname;
    this.zpkname = zpkname;
    this.crtdate = crtdate;
    this.uptdate = uptdate;
    this.uptcycle = uptcycle;
  }
  
  public AfaPinzoneinfoPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaPinzoneinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getZmkname()
  {
    return this.zmkname;
  }
  
  public void setZmkname(String zmkname)
  {
    this.zmkname = zmkname;
  }
  
  public String getPvkname()
  {
    return this.pvkname;
  }
  
  public void setPvkname(String pvkname)
  {
    this.pvkname = pvkname;
  }
  
  public String getZpkname()
  {
    return this.zpkname;
  }
  
  public void setZpkname(String zpkname)
  {
    this.zpkname = zpkname;
  }
  
  public String getCrtdate()
  {
    return this.crtdate;
  }
  
  public void setCrtdate(String crtdate)
  {
    this.crtdate = crtdate;
  }
  
  public String getUptdate()
  {
    return this.uptdate;
  }
  
  public void setUptdate(String uptdate)
  {
    this.uptdate = uptdate;
  }
  
  public String getUptcycle()
  {
    return this.uptcycle;
  }
  
  public void setUptcycle(String uptcycle)
  {
    this.uptcycle = uptcycle;
  }
  
  public String getReserv1()
  {
    return this.reserv1;
  }
  
  public void setReserv1(String reserv1)
  {
    this.reserv1 = reserv1;
  }
  
  public String getReserv2()
  {
    return this.reserv2;
  }
  
  public void setReserv2(String reserv2)
  {
    this.reserv2 = reserv2;
  }
  
  public String getReserv3()
  {
    return this.reserv3;
  }
  
  public void setReserv3(String reserv3)
  {
    this.reserv3 = reserv3;
  }
  
  public String toString()
  {
    return 
    
      new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaPinzoneinfo)) {
      return false;
    }
    AfaPinzoneinfo castOther = (AfaPinzoneinfo)other;
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
