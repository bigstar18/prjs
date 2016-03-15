package cn.com.agree.eteller.usermanager.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Appinfo
  implements Serializable, Cloneable, Comparable<Appinfo>
{
  private static final long serialVersionUID = 1L;
  private String appid;
  private String appname;
  private String appadress;
  private String starttime;
  private String stoptime;
  private String depdate;
  private String version;
  private String finalupdate;
  
  public Appinfo(String appid, String appname, String appadress, String starttime, String stoptime, String depdate, String version, String finalupdate)
  {
    this.appid = appid;
    this.appname = appname;
    this.appadress = appadress;
    this.starttime = starttime;
    this.stoptime = stoptime;
    this.depdate = depdate;
    this.version = version;
    this.finalupdate = finalupdate;
  }
  
  public Appinfo() {}
  
  public Appinfo(String appid, String appname)
  {
    this.appid = appid;
    this.appname = appname;
  }
  
  public String getAppid()
  {
    return this.appid;
  }
  
  public void setAppid(String appid)
  {
    this.appid = appid;
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
  
  public String getStarttime()
  {
    return this.starttime;
  }
  
  public void setStarttime(String starttime)
  {
    this.starttime = starttime;
  }
  
  public String getStoptime()
  {
    return this.stoptime;
  }
  
  public void setStoptime(String stoptime)
  {
    this.stoptime = stoptime;
  }
  
  public String getDepdate()
  {
    return this.depdate;
  }
  
  public void setDepdate(String depdate)
  {
    this.depdate = depdate;
  }
  
  public String getVersion()
  {
    return this.version;
  }
  
  public void setVersion(String version)
  {
    this.version = version;
  }
  
  public String getFinalupdate()
  {
    return this.finalupdate;
  }
  
  public void setFinalupdate(String finalupdate)
  {
    this.finalupdate = finalupdate;
  }
  
  public String toString()
  {
    return new ToStringBuilder(this).append("appid", getAppid()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Appinfo)) {
      return false;
    }
    Appinfo castOther = (Appinfo)other;
    return new EqualsBuilder()
      .append(getAppid(), castOther.getAppid()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getAppid()).toHashCode();
  }
  
  public Object clone()
  {
    Cloneable theClone = new Appinfo();
    return theClone;
  }
  
  public int compareTo(Appinfo o)
  {
    return this.appid.compareTo(o.getAppid());
  }
}
