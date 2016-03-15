package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaSystem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sysid;
  private String sysename;
  private String syscname;
  private String workdate;
  private String preworkdate;
  private String status;
  private String type;
  private String maxamount;
  private String totalamount;
  private String channelmode;
  private String actnomode;
  private String note1;
  private String note2;
  private String note3;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Sysename", "系统简称");
    hm.put("Syscname", "中文名称");
    hm.put("Workdate", "业务日期");
    hm.put("Preworkdate", "业务上一日日期");
    hm.put("Status", "系统状态");
    hm.put("Type", "系统类型");
    hm.put("Maxamount", "单笔额度");
    hm.put("Totalamount", "日累计额度");
    hm.put("Channelmode", "渠道管理模式");
    hm.put("Actnomode", "缴费介质管理模式");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
    hm.put("Note3", "备注3");
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getSysid() + "|").append(
      "Sysename=" + getSysename() + "|").append(
      "Syscname=" + getSyscname() + "|").append(
      "Status=" + getStatus() + "|").append(
      "Workdate=" + getWorkdate() + "|").append(
      "Preworkdate=" + getPreworkdate() + "|").append(
      "Type=" + getType() + "|").append(
      "Maxamount=" + getMaxamount() + "|").append(
      "Totalamount=" + getTotalamount() + "|").append(
      "Channelmode=" + getChannelmode() + "|").append(
      "Actnomode=" + getActnomode() + "|").append(
      "Note1=" + getNote1() + "|").append(
      "Note2=" + getNote2() + "|").append(
      "Note3=" + getNote3());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaSystem t where ";
    sb.append(sql).append(" t.sysid='" + getSysid() + "'");
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
  
  public void nulltospace()
  {
    if (getSysid() == null) {
      setSysid("");
    }
    if (getStatus() == null) {
      setStatus("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
    if (getNote3() == null) {
      setNote3("");
    }
    if (getSysename() == null) {
      setSysename("");
    }
    if (getSyscname() == null) {
      setSyscname("");
    }
    if (getWorkdate() == null) {
      setWorkdate("");
    }
    if (getPreworkdate() == null) {
      setPreworkdate("");
    }
    if (getType() == null) {
      setType("");
    }
    if (getMaxamount() == null) {
      setMaxamount("");
    }
    if (getTotalamount() == null) {
      setTotalamount("");
    }
    if (getChannelmode() == null) {
      setChannelmode("");
    }
    if (getActnomode() == null) {
      setActnomode("");
    }
  }
  
  public AfaSystem(String sysid, String sysename, String syscname, String workdate, String preworkdate, String status, String type, String maxamount, String totalamount, String channelmode, String actnomode, String note1, String note2, String note3)
  {
    this.sysid = sysid;
    this.sysename = sysename;
    this.syscname = syscname;
    this.workdate = workdate;
    this.preworkdate = preworkdate;
    this.status = status;
    this.type = type;
    this.maxamount = maxamount;
    this.totalamount = totalamount;
    this.channelmode = channelmode;
    this.actnomode = actnomode;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
  }
  
  public AfaSystem() {}
  
  public AfaSystem(String sysid, String status, String type, String maxamount, String totalamount, String channelmode, String actnomode)
  {
    this.sysid = sysid;
    this.status = status;
    this.type = type;
    this.maxamount = maxamount;
    this.totalamount = totalamount;
    this.channelmode = channelmode;
    this.actnomode = actnomode;
  }
  
  public String getSysid()
  {
    return this.sysid;
  }
  
  public void setSysid(String sysid)
  {
    this.sysid = sysid;
  }
  
  public String getSysename()
  {
    return this.sysename;
  }
  
  public void setSysename(String sysename)
  {
    this.sysename = sysename;
  }
  
  public String getSyscname()
  {
    return this.syscname;
  }
  
  public void setSyscname(String syscname)
  {
    this.syscname = syscname;
  }
  
  public String getWorkdate()
  {
    return this.workdate;
  }
  
  public void setWorkdate(String workdate)
  {
    this.workdate = workdate;
  }
  
  public String getPreworkdate()
  {
    return this.preworkdate;
  }
  
  public void setPreworkdate(String preworkdate)
  {
    this.preworkdate = preworkdate;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getMaxamount()
  {
    return this.maxamount;
  }
  
  public void setMaxamount(String maxamount)
  {
    this.maxamount = maxamount;
  }
  
  public String getTotalamount()
  {
    return this.totalamount;
  }
  
  public void setTotalamount(String totalamount)
  {
    this.totalamount = totalamount;
  }
  
  public String getChannelmode()
  {
    return this.channelmode;
  }
  
  public void setChannelmode(String channelmode)
  {
    this.channelmode = channelmode;
  }
  
  public String getActnomode()
  {
    return this.actnomode;
  }
  
  public void setActnomode(String actnomode)
  {
    this.actnomode = actnomode;
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
  
  public String toString()
  {
    return 
    





      new ToStringBuilder(this).append("sysid", getSysid()).append("sysename", getSysename()).append("syscname", getSyscname()).append("workdate", getWorkdate()).append("preworkdate", getPreworkdate()).append("status", getStatus()).append("type", getType()).append("maxamount", getMaxamount()).append("totalamount", getTotalamount()).append("channelmode", getChannelmode()).append("actnomode", getActnomode()).toString();
  }
}
