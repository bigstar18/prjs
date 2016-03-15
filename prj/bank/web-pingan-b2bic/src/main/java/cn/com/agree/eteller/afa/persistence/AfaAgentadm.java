package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaAgentadm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaAgentadmPK comp_id;
  private String busimode;
  private String status;
  private String note1;
  private String note2;
  private String note3;
  private String note4;
  private String note5;
  private String note6;
  
  public AfaAgentadm(AfaAgentadmPK comp_id, String busimode, String status, String note1, String note2, String note3, String note4, String note5, String note6)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.status = status;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
    this.note4 = note4;
    this.note5 = note5;
    this.note6 = note6;
  }
  
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位");
    hm.put("Subunitno", "商户分支");
    hm.put("Agentflag", "业务方式");
    hm.put("Zoneno", "业务地区号");
    hm.put("Zhno", "业务支行号");
    hm.put("Busimode", "业务模式");
    hm.put("Status", "业务状态");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
    hm.put("Note3", "备注3");
    hm.put("Note4", "备注4");
    hm.put("Note5", "备注5");
    hm.put("Note6", "备注6");
  }
  
  public AfaAgentadm(AfaAgentadmPK comp_id, String busimode, String status)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.status = status;
  }
  
  public AfaAgentadmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaAgentadmPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getBusimode()
  {
    return this.busimode;
  }
  
  public void setBusimode(String busimode)
  {
    this.busimode = busimode;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getNote6()
  {
    return this.note6;
  }
  
  public void setNote6(String note6)
  {
    this.note6 = note6;
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
  
  public String getAgentflag()
  {
    return getComp_id().getAgentflag();
  }
  
  public void setAgentflag(String agentflag)
  {
    getComp_id().setAgentflag(agentflag);
  }
  
  public String getZoneno()
  {
    return getComp_id().getZoneno();
  }
  
  public void setZoneno(String zoneno)
  {
    getComp_id().setZoneno(zoneno);
  }
  
  public String getZhno()
  {
    return getComp_id().getZhno();
  }
  
  public void setZhno(String zhno)
  {
    getComp_id().setZhno(zhno);
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getComp_id().getSysid() + "|")
      .append("Unitno=" + getComp_id().getUnitno() + "|")
      .append("Zoneno=" + getComp_id().getZoneno() + "|")
      .append("Subunitno=" + getComp_id().getSubunitno() + "|")
      .append("Agentflag=" + getComp_id().getAgentflag() + "|")
      .append("Zhno=" + getComp_id().getZhno() + "|")
      .append("Busimode=" + getBusimode() + "|")
      .append("Status=" + getStatus() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2() + "|")
      .append("Note3=" + getNote3() + "|")
      .append("Note4=" + getNote4() + "|")
      .append("Note5=" + getNote5() + "|")
      .append("Note6=" + getNote6());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaAgentadm t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.zoneno='" + getComp_id().getZoneno() + "'")
      .append(" and t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and t.comp_id.agentflag='" + getComp_id().getAgentflag() + "'")
      .append(" and t.comp_id.zhno='" + getComp_id().getZhno() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBusimode() == null) {
      setBusimode("");
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
    if (getNote4() == null) {
      setNote4("");
    }
    if (getNote5() == null) {
      setNote5("");
    }
    if (getNote6() == null) {
      setNote6("");
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
    return 
    


      new ToStringBuilder(this).append("comp_id", getComp_id()).append("busimode", getBusimode()).append("status", getStatus()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaAgentadm)) {
      return false;
    }
    AfaAgentadm castOther = (AfaAgentadm)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public AfaAgentadm() {}
}
