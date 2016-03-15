package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaHolidayadm
  implements Serializable
{
  private AfaHolidayadmPK comp_id;
  private String starttim;
  private String stoptime;
  private String week;
  private String holidayname;
  private String note1;
  private String note2;
  private String note3;
  private String note4;
  
  public AfaHolidayadm(AfaHolidayadmPK comp_id, String starttim, String stoptime, String week, String holidayname, String note1, String note2, String note3, String note4)
  {
    this.comp_id = comp_id;
    this.starttim = starttim;
    this.stoptime = stoptime;
    this.week = week;
    this.holidayname = holidayname;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
    this.note4 = note4;
  }
  
  public AfaHolidayadm() {}
  
  public AfaHolidayadm(AfaHolidayadmPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public AfaHolidayadmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaHolidayadmPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getStarttim()
  {
    return this.starttim;
  }
  
  public void setStarttim(String starttim)
  {
    this.starttim = starttim;
  }
  
  public String getStoptime()
  {
    return this.stoptime;
  }
  
  public void setStoptime(String stoptime)
  {
    this.stoptime = stoptime;
  }
  
  public String getWeek()
  {
    return this.week;
  }
  
  public void setWeek(String week)
  {
    this.week = week;
  }
  
  public String getHolidayname()
  {
    return this.holidayname;
  }
  
  public void setHolidayname(String holidayname)
  {
    this.holidayname = holidayname;
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
  
  public String getNote3()
  {
    return this.note3;
  }
  
  public void setNote3(String note3)
  {
    this.note3 = note3;
  }
  
  public String getNote4()
  {
    return this.note4;
  }
  
  public void setNote4(String note4)
  {
    this.note4 = note4;
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
    if (!(other instanceof AfaHolidayadm)) {
      return false;
    }
    AfaHolidayadm castOther = (AfaHolidayadm)other;
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
