package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CredSubbusisysinfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private CredSubbusisysinfoPK comp_id;
  private String channelid;
  private String tradesource;
  private String filetype;
  private String filename;
  private String procode;
  private String posmode;
  private String acqinstid;
  private String fwdinstid;
  private String opercode;
  private String note1;
  private String note2;
  private String note3;
  private String note4;
  private String note5;
  private String busisysunitno;
  private String busisysunittype;
  private String busisysunitzoneno;
  private String busisyssubunitno;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位代码");
    hm.put("Subunitno", "子商户单位代码");
    hm.put("Tradetype", "交易类型");
    hm.put("Channelid", "交易渠道");
    hm.put("Tradesource", "交易来源");
    hm.put("Filetype", "入账文件类型");
    hm.put("Filename", "入账文件名称");
    hm.put("Procode", "操作类别代码");
    hm.put("Posmode", "服务点输入模式");
    hm.put("Acqinstid", "收单机构代码");
    hm.put("Fwdinstid", "中转机构代码");
    hm.put("Opercode", "操作员代号");
    hm.put("Note1", "信用卡入账文件对应交易来源");
    hm.put("Note2", "信用卡清算方式");
    hm.put("Note3", "信用卡交易描述");
    hm.put("Note4", "备注字段4");
    hm.put("Note5", "备注字段5");
    hm.put("Busisysunitno", "商户代号");
    hm.put("Busisysunittype", "商户类别码");
    hm.put("Busisysunitzoneno", "特店银行号");
    hm.put("Busisyssubunitno", "特店号");
  }
  
  public CredSubbusisysinfo(CredSubbusisysinfoPK comp_id, String channelid, String tradesource, String filetype, String filename, String procode, String posmode, String acqinstid, String fwdinstid, String opercode, String note1, String note2, String note3, String note4, String note5, String busisysunitno, String busisysunittype, String busisysunitzoneno, String busisyssubunitno)
  {
    this.comp_id = comp_id;
    this.channelid = channelid;
    this.tradesource = tradesource;
    this.filetype = filetype;
    this.filename = filename;
    this.procode = procode;
    this.posmode = posmode;
    this.acqinstid = acqinstid;
    this.fwdinstid = fwdinstid;
    this.opercode = opercode;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
    this.note4 = note4;
    this.note5 = note5;
    this.busisysunitno = busisysunitno;
    this.busisysunittype = busisysunittype;
    this.busisysunitzoneno = busisysunitzoneno;
    this.busisyssubunitno = busisyssubunitno;
  }
  
  public CredSubbusisysinfo() {}
  
  public CredSubbusisysinfo(CredSubbusisysinfoPK comp_id, String channelid, String tradesource, String filetype, String filename)
  {
    this.comp_id = comp_id;
    this.channelid = channelid;
    this.tradesource = tradesource;
    this.filetype = filetype;
    this.filename = filename;
  }
  
  public CredSubbusisysinfoPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(CredSubbusisysinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getChannelid()
  {
    return this.channelid;
  }
  
  public void setChannelid(String channelid)
  {
    this.channelid = channelid;
  }
  
  public String getTradesource()
  {
    return this.tradesource;
  }
  
  public void setTradesource(String tradesource)
  {
    this.tradesource = tradesource;
  }
  
  public String getFiletype()
  {
    return this.filetype;
  }
  
  public void setFiletype(String filetype)
  {
    this.filetype = filetype;
  }
  
  public String getFilename()
  {
    return this.filename;
  }
  
  public void setFilename(String filename)
  {
    this.filename = filename;
  }
  
  public String getProcode()
  {
    return this.procode;
  }
  
  public void setProcode(String procode)
  {
    this.procode = procode;
  }
  
  public String getPosmode()
  {
    return this.posmode;
  }
  
  public void setPosmode(String posmode)
  {
    this.posmode = posmode;
  }
  
  public String getAcqinstid()
  {
    return this.acqinstid;
  }
  
  public void setAcqinstid(String acqinstid)
  {
    this.acqinstid = acqinstid;
  }
  
  public String getFwdinstid()
  {
    return this.fwdinstid;
  }
  
  public void setFwdinstid(String fwdinstid)
  {
    this.fwdinstid = fwdinstid;
  }
  
  public String getOpercode()
  {
    return this.opercode;
  }
  
  public void setOpercode(String opercode)
  {
    this.opercode = opercode;
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
  
  public String getNote5()
  {
    return this.note5;
  }
  
  public void setNote5(String note5)
  {
    this.note5 = note5;
  }
  
  public String getBusisysunitno()
  {
    return this.busisysunitno;
  }
  
  public void setBusisysunitno(String busisysunitno)
  {
    this.busisysunitno = busisysunitno;
  }
  
  public String getBusisysunittype()
  {
    return this.busisysunittype;
  }
  
  public void setBusisysunittype(String busisysunittype)
  {
    this.busisysunittype = busisysunittype;
  }
  
  public String getBusisysunitzoneno()
  {
    return this.busisysunitzoneno;
  }
  
  public void setBusisysunitzoneno(String busisysunitzoneno)
  {
    this.busisysunitzoneno = busisysunitzoneno;
  }
  
  public String getBusisyssubunitno()
  {
    return this.busisyssubunitno;
  }
  
  public void setBusisyssubunitno(String busisyssubunitno)
  {
    this.busisyssubunitno = busisyssubunitno;
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
    if (!(other instanceof CredSubbusisysinfo)) {
      return false;
    }
    CredSubbusisysinfo castOther = (CredSubbusisysinfo)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from CredSubbusisysinfo t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and t.comp_id.tradetype='" + getComp_id().getTradetype() + "'");
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
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getComp_id().getSysid() + "|")
      .append("Unitno=" + getComp_id().getUnitno() + "|")
      .append("Subunitno=" + getComp_id().getSubunitno() + "|")
      .append("Tradetype=" + getComp_id().getTradetype() + "|")
      .append("Channelid=" + getChannelid() + "|")
      .append("Tradesource=" + getTradesource() + "|")
      .append("Filetype=" + getFiletype() + "|")
      .append("Filename=" + getFilename() + "|")
      .append("Procode=" + getProcode() + "|")
      .append("Posmode=" + getPosmode() + "|")
      .append("Acqinstid=" + getAcqinstid() + "|")
      .append("Fwdinstid=" + getFwdinstid() + "|")
      .append("Opercode=" + getOpercode() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2() + "|")
      .append("Note3=" + getNote3() + "|")
      .append("Note4=" + getNote4() + "|")
      .append("Note5=" + getNote5() + "|")
      .append("Busisysunitno=" + getBusisysunitno() + "|")
      .append("Busisysunittype=" + getBusisysunittype() + "|")
      .append("Busisysunitzoneno=" + getBusisysunitzoneno() + "|")
      .append("Busisyssubunitno=" + getBusisyssubunitno());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getChannelid() == null) {
      setChannelid("");
    }
    if (getTradesource() == null) {
      setTradesource("");
    }
    if (getFiletype() == null) {
      setFiletype("");
    }
    if (getFilename() == null) {
      setFilename("");
    }
    if (getProcode() == null) {
      setPosmode("");
    }
    if (getPosmode() == null) {
      setPosmode("");
    }
    if (getAcqinstid() == null) {
      setAcqinstid("");
    }
    if (getFwdinstid() == null) {
      setFwdinstid("");
    }
    if (getOpercode() == null) {
      setOpercode("");
    }
    if (getBusisysunitno() == null) {
      setBusisysunitno("");
    }
    if (getBusisysunittype() == null) {
      setBusisysunittype("");
    }
    if (getBusisysunitzoneno() == null) {
      setBusisysunitzoneno("");
    }
    if (getBusisyssubunitno() == null) {
      setBusisyssubunitno("");
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
    if (getNote4() == null) {
      setNote4("");
    }
    if (getNote5() == null) {
      setNote5("");
    }
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
  
  public String getTradetype()
  {
    return getComp_id().getTradetype();
  }
  
  public void setTradetype(String type)
  {
    getComp_id().setTradetype(type);
  }
}
