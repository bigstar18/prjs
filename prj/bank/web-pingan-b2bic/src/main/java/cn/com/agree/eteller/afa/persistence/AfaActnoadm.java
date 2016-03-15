package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaActnoadm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaActnoadmPK comp_id;
  private String busimode;
  private String chkaccpwdctl;
  private String enpaccpwdctl;
  private String hosttype;
  private String status;
  private String chkacclocalctl;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位");
    hm.put("Subunitno", "商户分支");
    hm.put("Agentflag", "业务方式");
    hm.put("Zoneno", "业务地区号");
    hm.put("Zhno", "业务支行号");
    hm.put("Channelcode", "渠道代码");
    hm.put("Acttypecode", "缴费介质");
    hm.put("Busimode", "业务模式");
    hm.put("Chkaccpwdctl", "校验帐户密码标志");
    hm.put("Enpaccpwdctl", "帐户密码加密标志");
    hm.put("Hosttype", "主机类型");
    hm.put("Status", "缴费介质状态");
    hm.put("Chkacclocalctl", "账户本异地检查标志");
  }
  
  public AfaActnoadm(AfaActnoadmPK comp_id, String busimode, String chkaccpwdctl, String enpaccpwdctl, String hosttype, String status, String chkacclocalctl)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.chkaccpwdctl = chkaccpwdctl;
    this.enpaccpwdctl = enpaccpwdctl;
    this.hosttype = hosttype;
    this.status = status;
    this.chkacclocalctl = chkacclocalctl;
  }
  
  public AfaActnoadm() {}
  
  public AfaActnoadm(AfaActnoadmPK comp_id, String busimode)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
  }
  
  public AfaActnoadmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaActnoadmPK comp_id)
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
  
  public String getChkaccpwdctl()
  {
    return this.chkaccpwdctl;
  }
  
  public void setChkaccpwdctl(String chkaccpwdctl)
  {
    this.chkaccpwdctl = chkaccpwdctl;
  }
  
  public String getEnpaccpwdctl()
  {
    return this.enpaccpwdctl;
  }
  
  public void setEnpaccpwdctl(String enpaccpwdctl)
  {
    this.enpaccpwdctl = enpaccpwdctl;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
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
  
  public String getChannelcode()
  {
    return getComp_id().getChannelcode();
  }
  
  public void setChannelcode(String channelcode)
  {
    getComp_id().setChannelcode(channelcode);
  }
  
  public String getActtypecode()
  {
    return getComp_id().getActtypecode();
  }
  
  public void setActtypecode(String acttypecode)
  {
    getComp_id().setActtypecode(acttypecode);
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
      .append("Channelcode=" + getComp_id().getChannelcode() + "|")
      .append("Acttypecode=" + getComp_id().getActtypecode() + "|")
      .append("Busimode=" + getBusimode() + "|")
      .append("Chkaccpwdctl=" + getChkaccpwdctl() + "|")
      .append("Enpaccpwdctl=" + getEnpaccpwdctl() + "|")
      .append("Hosttype=" + getHosttype() + "|")
      .append("Chkacclocalctl=" + getChkacclocalctl() + "|")
      .append("Status=" + getStatus());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaActnoadm t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.zoneno='" + getComp_id().getZoneno() + "'")
      .append(" and t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and t.comp_id.agentflag='" + getComp_id().getAgentflag() + "'")
      .append(" and t.comp_id.zhno='" + getComp_id().getZhno() + "'")
      .append(" and t.comp_id.channelcode='" + getComp_id().getChannelcode() + "'")
      .append(" and t.comp_id.acttypecode='" + getComp_id().getActtypecode() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBusimode() == null) {
      setBusimode("");
    }
    if (getChkaccpwdctl() == null) {
      setChkaccpwdctl("");
    }
    if (getEnpaccpwdctl() == null) {
      setEnpaccpwdctl("");
    }
    if (getStatus() == null) {
      setStatus("");
    }
    if (getHosttype() == null) {
      setHosttype("");
    }
    if (getChkacclocalctl() == null) {
      setChkacclocalctl("");
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
    






      new ToStringBuilder(this).append("comp_id", getComp_id()).append("busimode", getBusimode()).append("chkaccpwdctl", getChkaccpwdctl()).append("enpaccpwdctl", getEnpaccpwdctl()).append("hosttype", getHosttype()).append("status", getStatus()).append("chkacclocalctl", getChkacclocalctl()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaActnoadm)) {
      return false;
    }
    AfaActnoadm castOther = (AfaActnoadm)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public String getHosttype()
  {
    return this.hosttype;
  }
  
  public void setHosttype(String hosttype)
  {
    this.hosttype = hosttype;
  }
  
  public String getChkacclocalctl()
  {
    return this.chkacclocalctl;
  }
  
  public void setChkacclocalctl(String chkacclocalctl)
  {
    this.chkacclocalctl = chkacclocalctl;
  }
}
