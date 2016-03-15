package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaSubunitadm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaSubunitadmPK comp_id;
  private String subunitname;
  private String subunitsname;
  private String status;
  private String workdate;
  private String preworkdate;
  private String starttime;
  private String stoptime;
  private String feeflag;
  private String bankcode;
  private String zoneno;
  private String brno;
  private String busimode;
  private String accmode;
  private String bankunitno;
  private String accno1;
  private String accno2;
  private String accno3;
  private String accno4;
  private String accno5;
  private String accno6;
  private String name;
  private String telphone;
  private String address;
  private String agenteigen;
  private String loginstatus;
  private String dayendstatus;
  private String dayendtime;
  private String trxchkstatus;
  private String trxchktime;
  private String note1;
  private String note2;
  
  public AfaSubunitadm(AfaSubunitadmPK comp_id, String subunitname, String subunitsname, String status, String workdate, String preworkdate, String starttime, String stoptime, String feeflag, String bankcode, String zoneno, String brno, String busimode, String accmode, String bankunitno, String accno1, String accno2, String accno3, String accno4, String accno5, String accno6, String name, String telphone, String address, String agenteigen, String loginstatus, String dayendstatus, String dayendtime, String trxchkstatus, String trxchktime, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.subunitname = subunitname;
    this.subunitsname = subunitsname;
    this.status = status;
    this.workdate = workdate;
    this.preworkdate = preworkdate;
    this.starttime = starttime;
    this.stoptime = stoptime;
    this.feeflag = feeflag;
    this.bankcode = bankcode;
    this.zoneno = zoneno;
    this.brno = brno;
    this.busimode = busimode;
    this.accmode = accmode;
    this.bankunitno = bankunitno;
    this.accno1 = accno1;
    this.accno2 = accno2;
    this.accno3 = accno3;
    this.accno4 = accno4;
    this.accno5 = accno5;
    this.accno6 = accno6;
    this.name = name;
    this.telphone = telphone;
    this.address = address;
    this.agenteigen = agenteigen;
    this.loginstatus = loginstatus;
    this.dayendstatus = dayendstatus;
    this.dayendtime = dayendtime;
    this.trxchkstatus = trxchkstatus;
    this.trxchktime = trxchktime;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位");
    hm.put("Subunitno", "商户分支单位代码");
    
    hm.put("Subunitname", "商户分支名称");
    hm.put("Subunitsname", "商户分支简称");
    hm.put("Status", "商户分支单位状态");
    hm.put("Workdate", "业务日期");
    hm.put("Preworkdate", "业务上一日日期");
    hm.put("Starttime", "开始时间");
    hm.put("Stoptime", "结束时间");
    hm.put("Feeflag", "手续费标识");
    hm.put("Bankcode", "银行编码");
    hm.put("Zoneno", "主办分行号");
    hm.put("Brno", "主办行所号");
    
    hm.put("Busimode", "业务模式");
    hm.put("Accmode", "帐户模式");
    hm.put("Bankunitno", "银行商户代码");
    hm.put("Accno1", "帐号1");
    hm.put("Accno2", "帐号2");
    hm.put("Accno3", "帐号3");
    hm.put("Accno4", "帐号4");
    hm.put("Accno5", "帐号5");
    hm.put("Accno6", "帐号6");
    hm.put("Name", "联系人");
    hm.put("Telphone", "联系电话");
    
    hm.put("Address", "联系地址");
    hm.put("Agenteigen", "业务特征码");
    hm.put("Loginstatus", "签到校验");
    hm.put("Dayendstatus", "日终校验");
    hm.put("Dayendtime", "日终时间");
    hm.put("Trxchkstatus", "对帐校验");
    hm.put("Trxchktime", "对帐时间");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public AfaSubunitadm(AfaSubunitadmPK comp_id, String status, String busimode, String accmode, String bankunitno)
  {
    this.comp_id = comp_id;
    this.status = status;
    this.busimode = busimode;
    this.accmode = accmode;
    this.bankunitno = bankunitno;
  }
  
  public AfaSubunitadmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaSubunitadmPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getSubunitname()
  {
    return this.subunitname;
  }
  
  public void setSubunitname(String subunitname)
  {
    this.subunitname = subunitname;
  }
  
  public String getSubunitsname()
  {
    return this.subunitsname;
  }
  
  public void setSubunitsname(String subunitsname)
  {
    this.subunitsname = subunitsname;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getFeeflag()
  {
    return this.feeflag;
  }
  
  public void setFeeflag(String feeflag)
  {
    this.feeflag = feeflag;
  }
  
  public String getBankcode()
  {
    return this.bankcode;
  }
  
  public void setBankcode(String bankcode)
  {
    this.bankcode = bankcode;
  }
  
  public String getZoneno()
  {
    return this.zoneno;
  }
  
  public void setZoneno(String zoneno)
  {
    this.zoneno = zoneno;
  }
  
  public String getBrno()
  {
    return this.brno;
  }
  
  public void setBrno(String brno)
  {
    this.brno = brno;
  }
  
  public String getBusimode()
  {
    return this.busimode;
  }
  
  public void setBusimode(String busimode)
  {
    this.busimode = busimode;
  }
  
  public String getAccmode()
  {
    return this.accmode;
  }
  
  public void setAccmode(String accmode)
  {
    this.accmode = accmode;
  }
  
  public String getBankunitno()
  {
    return this.bankunitno;
  }
  
  public void setBankunitno(String bankunitno)
  {
    this.bankunitno = bankunitno;
  }
  
  public String getAccno1()
  {
    return this.accno1;
  }
  
  public void setAccno1(String accno1)
  {
    this.accno1 = accno1;
  }
  
  public String getAccno2()
  {
    return this.accno2;
  }
  
  public void setAccno2(String accno2)
  {
    this.accno2 = accno2;
  }
  
  public String getAccno3()
  {
    return this.accno3;
  }
  
  public void setAccno3(String accno3)
  {
    this.accno3 = accno3;
  }
  
  public String getAccno4()
  {
    return this.accno4;
  }
  
  public void setAccno4(String accno4)
  {
    this.accno4 = accno4;
  }
  
  public String getAccno5()
  {
    return this.accno5;
  }
  
  public void setAccno5(String accno5)
  {
    this.accno5 = accno5;
  }
  
  public String getAccno6()
  {
    return this.accno6;
  }
  
  public void setAccno6(String accno6)
  {
    this.accno6 = accno6;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getTelphone()
  {
    return this.telphone;
  }
  
  public void setTelphone(String telphone)
  {
    this.telphone = telphone;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getAgenteigen()
  {
    return this.agenteigen;
  }
  
  public void setAgenteigen(String agenteigen)
  {
    this.agenteigen = agenteigen;
  }
  
  public String getLoginstatus()
  {
    return this.loginstatus;
  }
  
  public void setLoginstatus(String loginstatus)
  {
    this.loginstatus = loginstatus;
  }
  
  public String getDayendstatus()
  {
    return this.dayendstatus;
  }
  
  public void setDayendstatus(String dayendstatus)
  {
    this.dayendstatus = dayendstatus;
  }
  
  public String getDayendtime()
  {
    return this.dayendtime;
  }
  
  public void setDayendtime(String dayendtime)
  {
    this.dayendtime = dayendtime;
  }
  
  public String getTrxchkstatus()
  {
    return this.trxchkstatus;
  }
  
  public void setTrxchkstatus(String trxchkstatus)
  {
    this.trxchkstatus = trxchkstatus;
  }
  
  public String getTrxchktime()
  {
    return this.trxchktime;
  }
  
  public void setTrxchktime(String trxchktime)
  {
    this.trxchktime = trxchktime;
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
  
  public String getSysid()
  {
    return getComp_id().getSysid();
  }
  
  public void setSysid(String sysid)
  {
    getComp_id().setSysid(sysid);
  }
  
  public String getUnitno()
  {
    return getComp_id().getUnitno();
  }
  
  public void setUnitno(String unitno)
  {
    getComp_id().setUnitno(unitno);
  }
  
  public String getSubunitno()
  {
    return getComp_id().getSubunitno();
  }
  
  public void setSubunitno(String subunitno)
  {
    getComp_id().setSubunitno(subunitno);
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getComp_id().getSysid() + "|").append(
      "Unitno=" + getComp_id().getUnitno() + "|").append(
      "Subunitno=" + getComp_id().getSubunitno() + "|").append(
      "Subunitname=" + getSubunitname() + "|").append(
      "Subunitsname=" + getSubunitsname() + "|").append(
      "Status=" + getStatus() + "|").append(
      "Workdate=" + getWorkdate() + "|").append(
      "Preworkdate=" + getPreworkdate() + "|").append(
      "Starttime=" + getStarttime() + "|").append(
      "Stoptime=" + getStoptime() + "|").append(
      "Feeflag=" + getFeeflag() + "|").append(
      "Bankcode=" + getBankcode() + "|").append(
      "Zoneno=" + getZoneno() + "|").append(
      "Brno=" + getBrno() + "|").append(
      "Busimode=" + getBusimode() + "|").append(
      "Accmode=" + getAccmode() + "|").append(
      "Bankunitno=" + getBankunitno() + "|").append(
      "Accno1=" + getAccno1() + "|").append(
      "Accno2=" + getAccno2() + "|").append(
      "Accno3=" + getAccno3() + "|").append(
      "Accno4=" + getAccno4() + "|").append(
      "Accno5=" + getAccno5() + "|").append(
      "Accno6=" + getAccno6() + "|").append(
      "Name=" + getName() + "|").append(
      "Telphone=" + getTelphone() + "|").append(
      "Address=" + getAddress() + "|").append(
      "Agenteigen=" + getAgenteigen() + "|").append(
      "Loginstatus=" + getLoginstatus() + "|").append(
      "Dayendstatus=" + getDayendstatus() + "|").append(
      "Dayendtime=" + getDayendtime() + "|").append(
      "Trxchkstatus=" + getTrxchkstatus() + "|").append(
      "Trxchktime=" + getTrxchktime() + "|").append(
      "Note1=" + getNote1() + "|").append(
      "Note2=" + getNote2());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaSubunitadm t where ";
    sb.append(sql).append(
      " t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(
      " and t.comp_id.unitno='" + 
      getComp_id().getUnitno() + "'").append(
      " and t.comp_id.subunitno='" + 
      getComp_id().getSubunitno() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getSubunitname() == null) {
      setSubunitname("");
    }
    if (getSubunitsname() == null) {
      setSubunitsname("");
    }
    if (getStatus() == null) {
      setStatus("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
    if (getWorkdate() == null) {
      setWorkdate("");
    }
    if (getPreworkdate() == null) {
      setPreworkdate("");
    }
    if (getStarttime() == null) {
      setStarttime("");
    }
    if (getStoptime() == null) {
      setStoptime("");
    }
    if (getFeeflag() == null) {
      setFeeflag("");
    }
    if (getBankcode() == null) {
      setBankcode("");
    }
    if (getZoneno() == null) {
      setZoneno("");
    }
    if (getBrno() == null) {
      setBrno("");
    }
    if (getBusimode() == null) {
      setBusimode("");
    }
    if (getAccmode() == null) {
      setAccmode("");
    }
    if (getBankunitno() == null) {
      setBankunitno("");
    }
    if (getAccno1() == null) {
      setAccno1("");
    }
    if (getAccno2() == null) {
      setAccno2("");
    }
    if (getAccno3() == null) {
      setAccno3("");
    }
    if (getAccno4() == null) {
      setAccno4("");
    }
    if (getAccno5() == null) {
      setAccno5("");
    }
    if (getAccno6() == null) {
      setAccno6("");
    }
    if (getName() == null) {
      setName("");
    }
    if (getTelphone() == null) {
      setTelphone("");
    }
    if (getAddress() == null) {
      setAddress("");
    }
    if (getAgenteigen() == null) {
      setAgenteigen("0000000000000000");
    }
    if (getLoginstatus() == null) {
      setLoginstatus("");
    }
    if (getDayendstatus() == null) {
      setDayendstatus("");
    }
    if (getDayendtime() == null) {
      setDayendtime("");
    }
    if (getTrxchkstatus() == null) {
      setTrxchkstatus("");
    }
    if (getTrxchktime() == null) {
      setTrxchktime("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
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
    StringBuffer ssoldValue = new StringBuffer();
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
  
  public String toString()
  {
    if ((getAgenteigen() == null) || (getAgenteigen().equals(""))) {
      setAgenteigen("0000000000000000");
    }
    return 
    














      new ToStringBuilder(this).append("comp_id", getComp_id()).append("subunitname ", getSubunitname()).append("subunitsname", getSubunitsname()).append("status", getStatus()).append("workdate", getWorkdate()).append("preworkdate", getPreworkdate()).append("starttime", getStarttime()).append("stoptime", getStoptime()).append("feeflag", getFeeflag()).append("bankcode", getBankcode()).append("zoneno", getZoneno()).append("brno", getBrno()).append("busimode", getBusimode()).append("accmode", getAccmode()).append("bankunitno", getBankunitno()).append("accno1", getAccno1()).append("accno2", getAccno2()).append("accno3", getAccno3()).append("accno4", getAccno4()).append("accno5", getAccno5()).append("accno6", getAccno6()).append("name", getName()).append("telphone", getTelphone()).append("address", getAddress()).append("agenteigen", getAgenteigen()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaSubunitadm)) {
      return false;
    }
    AfaSubunitadm castOther = (AfaSubunitadm)other;
    return new EqualsBuilder().append(getComp_id(), 
      castOther.getComp_id()).isEquals();
  }
  
  public int hashCode()
  {
    return new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public AfaSubunitadm() {}
}
