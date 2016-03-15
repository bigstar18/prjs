package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaPininfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sysid;
  private String channel;
  private String zmkname;
  private String pvkname;
  private String zpkname;
  private String crtdate;
  private String uptdate;
  private String uptcycle;
  private String reserv1;
  private String reserv2;
  private String reserv3;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Channel", "渠道代码");
    hm.put("Zmkname", "zmk名称");
    hm.put("Pvkname", "pvk名称");
    hm.put("Zpkname", "zpk名称");
    hm.put("Crtdate", "创建日期");
    hm.put("Uptdate", "更新日期");
    hm.put("Uptcycle", "更新周期");
    hm.put("Reserv1", "预留字段1");
    hm.put("Reserv2", "应用编号");
    hm.put("Reserv3", "预留字段2");
  }
  
  public void nulltospace()
  {
    if (getSysid() == null) {
      setSysid("");
    }
    if (getChannel() == null) {
      setChannel("");
    }
    if (getZmkname() == null) {
      setZmkname("");
    }
    if (getZpkname() == null) {
      setZpkname("");
    }
    if (getPvkname() == null) {
      setPvkname("");
    }
    if (getCrtdate() == null) {
      setCrtdate("");
    }
    if (getUptdate() == null) {
      setUptdate("");
    }
    if (getUptcycle() == null) {
      setUptcycle("");
    }
    if (getReserv1() == null) {
      setReserv1("");
    }
    if (getReserv2() == null) {
      setReserv2("");
    }
    if (getReserv3() == null) {
      setReserv3("");
    }
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getSysid() + "|").append(
      "Channel=" + getChannel() + "|").append(
      "Zmkname=" + getZmkname() + "|").append(
      "Zpkname=" + getZpkname() + "|").append(
      "Pvkname=" + getPvkname() + "|").append(
      "Crtdate=" + getCrtdate() + "|").append(
      "Uptdate=" + getUptdate() + "|").append(
      "Uptcycle=" + getUptcycle() + "|").append(
      "Reserv1=" + getReserv1() + "|").append(
      "Reserv2=" + getReserv2() + "|").append(
      "Reserv3=" + getReserv3());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaPininfo t where ";
    sb.append(sql).append(" sysid='" + getSysid() + "'").append(
      " and channel='" + getChannel() + "'").append(
      " and zmkname='" + getZmkname() + "'").append(
      " and zpkname='" + getZpkname() + "'").append(
      " and pvkname='" + getPvkname() + "'").append(
      " and crtdate='" + getCrtdate() + "'").append(
      " and uptdate='" + getUptdate() + "'").append(
      " and uptcycle='" + getUptcycle() + "'").append(
      " and reserv1='" + getReserv1() + "'").append(
      " and reserv2='" + getReserv2() + "'").append(
      " and reserv3='" + getReserv3() + "'");
    return sb.toString();
  }
  
  public String toChinese(String value)
  {
    if (value == null) {
      return null;
    }
    if (value.equals("")) {
      return "";
    }
    String[] str1InArray = value.split("\\Q|\\E");
    String str = "";
    for (int i = 0; i < str1InArray.length; i++)
    {
      String[] str1InArray2 = str1InArray[i].split("\\Q=\\E");
      if (hm.containsKey(str1InArray2[0])) {
        str1InArray2[0] = ((String)hm.get(str1InArray2[0]));
      }
      if (str1InArray2.length == 1) {
        str = str + str1InArray2[0] + "=|";
      } else {
        str = str + str1InArray2[0] + "=" + str1InArray2[1] + "|";
      }
    }
    return str;
  }
  
  public AfaPininfo(String sysid, String channel, String zmkname, String pvkname, String zpkname, String crtdate, String uptdate, String uptcycle, String reserv1, String reserv2, String reserv3)
  {
    this.sysid = sysid;
    this.channel = channel;
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
  
  public AfaPininfo() {}
  
  public String getSysid()
  {
    return this.sysid;
  }
  
  public void setSysid(String sysid)
  {
    this.sysid = sysid;
  }
  
  public String getChannel()
  {
    return this.channel;
  }
  
  public void setChannel(String channel)
  {
    this.channel = channel;
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
    






      new ToStringBuilder(this).append("sysid", getSysid()).append("channel", getChannel()).append("zmkname", getZmkname()).append("pvkname", getPvkname()).append("zpkname", getZpkname()).append("crtdate", getCrtdate()).append("uptdate", getUptdate()).append("uptcycle", getUptcycle()).append("reserv1", getReserv1()).append("reserv2", getReserv2()).append("reserv3", getReserv3()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaPininfo)) {
      return false;
    }
    AfaPininfo castOther = (AfaPininfo)other;
    return new EqualsBuilder()
      .append(getSysid(), castOther.getSysid()).append(
      getChannel(), castOther.getChannel()).append(
      getZmkname(), castOther.getZmkname()).append(
      getPvkname(), castOther.getPvkname()).append(
      getZpkname(), castOther.getZpkname()).append(
      getCrtdate(), castOther.getCrtdate()).append(
      getUptdate(), castOther.getUptdate()).append(
      getUptcycle(), castOther.getUptcycle()).append(
      getReserv1(), castOther.getReserv1()).append(
      getReserv2(), castOther.getReserv2()).append(
      getReserv3(), castOther.getReserv3()).isEquals();
  }
  
  public int hashCode()
  {
    return 
    


      new HashCodeBuilder().append(getSysid()).append(getChannel()).append(getZmkname()).append(getPvkname()).append(getZpkname()).append(getCrtdate()).append(getUptdate()).append(getUptcycle()).append(getReserv1()).append(getReserv2()).append(getReserv3()).toHashCode();
  }
}
