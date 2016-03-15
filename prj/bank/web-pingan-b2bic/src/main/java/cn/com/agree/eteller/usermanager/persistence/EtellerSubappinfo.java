package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EtellerSubappinfo
  implements Serializable, Comparable<EtellerSubappinfo>
{
  private EtellerSubappinfoPK comp_id;
  private String appname;
  private String appadress;
  private String subappdesc;
  private Appinfo app;
  
  public EtellerSubappinfo(EtellerSubappinfoPK comp_id, String appname, String appadress, String subappdesc)
  {
    this.comp_id = comp_id;
    this.appname = appname;
    this.appadress = appadress;
    this.subappdesc = subappdesc;
  }
  
  public EtellerSubappinfo() {}
  
  public EtellerSubappinfo(EtellerSubappinfoPK comp_id, String appname)
  {
    this.comp_id = comp_id;
    this.appname = appname;
  }
  
  public EtellerSubappinfoPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(EtellerSubappinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getAppname()
  {
    return this.appname;
  }
  
  public void setAppname(String appname)
  {
    this.appname = appname;
  }
  
  public String getAppadress()
  {
    return this.appadress;
  }
  
  public void setAppadress(String appadress)
  {
    this.appadress = appadress;
  }
  
  public String getSubappdesc()
  {
    return this.subappdesc;
  }
  
  public void setSubappdesc(String subappdesc)
  {
    this.subappdesc = subappdesc;
  }
  
  public void setApp(Appinfo app)
  {
    this.app = app;
  }
  
  public Appinfo getApp()
  {
    return this.app;
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
    if (!(other instanceof EtellerSubappinfo)) {
      return false;
    }
    EtellerSubappinfo castOther = (EtellerSubappinfo)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public int compareTo(EtellerSubappinfo o)
  {
    return this.comp_id.getSubappid().compareTo(o.comp_id.getSubappid());
  }
}
