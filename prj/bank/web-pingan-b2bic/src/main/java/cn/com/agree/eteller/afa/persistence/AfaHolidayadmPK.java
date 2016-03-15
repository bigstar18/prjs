package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaHolidayadmPK
  implements Serializable
{
  private String sysid;
  private String unitno;
  private String holiday;
  
  public AfaHolidayadmPK(String sysid, String unitno, String holiday)
  {
    this.sysid = sysid;
    this.unitno = unitno;
    this.holiday = holiday;
  }
  
  public AfaHolidayadmPK() {}
  
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
  
  public String getHoliday()
  {
    return this.holiday;
  }
  
  public void setHoliday(String holiday)
  {
    this.holiday = holiday;
  }
  
  public String toString()
  {
    return 
    


      new ToStringBuilder(this).append("sysid", getSysid()).append("unitno", getUnitno()).append("holiday", getHoliday()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaHolidayadmPK)) {
      return false;
    }
    AfaHolidayadmPK castOther = (AfaHolidayadmPK)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid())
      .append(getUnitno(), castOther.getUnitno())
      .append(getHoliday(), castOther.getHoliday())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    


      new HashCodeBuilder().append(getSysid()).append(getUnitno()).append(getHoliday()).toHashCode();
  }
}
