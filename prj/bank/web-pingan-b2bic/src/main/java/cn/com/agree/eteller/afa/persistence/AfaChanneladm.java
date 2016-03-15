package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaChanneladm
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaChanneladmPK comp_id;
  private String busimode;
  private String agentbrno;
  private String agentteller;
  private String maxamount;
  private String totalamount;
  private String billsavectl;
  private String autorevtranctl;
  private String errchkctl;
  private String channelstatus;
  private String autochkacct;
  private String flag1;
  private String flag2;
  private String flag3;
  private String flag4;
  private String note1;
  private String note2;
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
    hm.put("Busimode", "业务模式");
    hm.put("Agentbrno", "外围系统行所号");
    hm.put("Agentteller", "外围系统出纳员号");
    hm.put("Maxamount", "单笔交易额");
    hm.put("Totalamount", "日累计交易额度");
    hm.put("Billsavectl", "发票保存标志");
    hm.put("Autorevtranctl", "自动冲帐标志");
    hm.put("Errchkctl", "异常交易检测标志");
    hm.put("Channelstatus", "渠道状态");
    hm.put("Autochkacct", "自动检查帐户类型");
    hm.put("Flag1", "标志1");
    hm.put("Flag2", "标志2");
    hm.put("Flag3", "标志3");
    hm.put("Flag4", "标志4");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public AfaChanneladm(AfaChanneladmPK comp_id, String busimode, String agentbrno, String agentteller, String maxamount, String totalamount, String billsavectl, String autorevtranctl, String errchkctl, String channelstatus, String autochkacct, String flag1, String flag2, String flag3, String flag4, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
    this.agentbrno = agentbrno;
    this.agentteller = agentteller;
    this.maxamount = maxamount;
    this.totalamount = totalamount;
    this.billsavectl = billsavectl;
    this.autorevtranctl = autorevtranctl;
    this.errchkctl = errchkctl;
    this.channelstatus = channelstatus;
    this.autochkacct = autochkacct;
    this.flag1 = flag1;
    this.flag2 = flag2;
    this.flag3 = flag3;
    this.flag4 = flag4;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaChanneladm() {}
  
  public AfaChanneladm(AfaChanneladmPK comp_id, String busimode)
  {
    this.comp_id = comp_id;
    this.busimode = busimode;
  }
  
  public AfaChanneladmPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaChanneladmPK comp_id)
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
  
  public String getAgentbrno()
  {
    return this.agentbrno;
  }
  
  public void setAgentbrno(String agentbrno)
  {
    this.agentbrno = agentbrno;
  }
  
  public String getAgentteller()
  {
    return this.agentteller;
  }
  
  public void setAgentteller(String agentteller)
  {
    this.agentteller = agentteller;
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
  
  public String getBillsavectl()
  {
    return this.billsavectl;
  }
  
  public void setBillsavectl(String billsavectl)
  {
    this.billsavectl = billsavectl;
  }
  
  public String getAutorevtranctl()
  {
    return this.autorevtranctl;
  }
  
  public void setAutorevtranctl(String autorevtranctl)
  {
    this.autorevtranctl = autorevtranctl;
  }
  
  public String getErrchkctl()
  {
    return this.errchkctl;
  }
  
  public void setErrchkctl(String errchkctl)
  {
    this.errchkctl = errchkctl;
  }
  
  public String getChannelstatus()
  {
    return this.channelstatus;
  }
  
  public void setChannelstatus(String channelstatus)
  {
    this.channelstatus = channelstatus;
  }
  
  public String getAutochkacct()
  {
    return this.autochkacct;
  }
  
  public void setAutochkacct(String autochkacct)
  {
    this.autochkacct = autochkacct;
  }
  
  public String getFlag1()
  {
    return this.flag1;
  }
  
  public void setFlag1(String flag1)
  {
    this.flag1 = flag1;
  }
  
  public String getFlag2()
  {
    return this.flag2;
  }
  
  public void setFlag2(String flag2)
  {
    this.flag2 = flag2;
  }
  
  public String getFlag3()
  {
    return this.flag3;
  }
  
  public void setFlag3(String flag3)
  {
    this.flag3 = flag3;
  }
  
  public String getFlag4()
  {
    return this.flag4;
  }
  
  public void setFlag4(String flag4)
  {
    this.flag4 = flag4;
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
  
  public String toString()
  {
    return 
    










      new ToStringBuilder(this).append("comp_id", getComp_id()).append("busimode", getBusimode()).append("agentbrno", getAgentbrno()).append("agentteller", getAgentteller()).append("maxamount", getMaxamount()).append("totalamount", getTotalamount()).append("billsavectl", getBillsavectl()).append("autorevtranctl", getAutorevtranctl()).append("errchkctl", getErrchkctl()).append("channelstatus ", getChannelstatus()).append("autochkacct", getAutochkacct()).toString();
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
      .append("Busimode=" + getBusimode() + "|")
      .append("Agentbrno=" + getAgentbrno() + "|")
      .append("Agentteller=" + getAgentteller() + "|")
      .append("Maxamount=" + getMaxamount() + "|")
      .append("Totalamount=" + getTotalamount() + "|")
      .append("Billsavectl=" + getBillsavectl() + "|")
      .append("Autorevtranctl=" + getAutorevtranctl() + "|")
      .append("Errchkctl=" + getErrchkctl() + "|")
      .append("Channelstatus=" + getChannelstatus() + "|")
      .append("Autochkacct=" + getAutochkacct() + "|")
      .append("Flag1=" + getFlag1() + "|")
      .append("Flag2=" + getFlag2() + "|")
      .append("Flag3=" + getFlag3() + "|")
      .append("Flag4=" + getFlag4() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaChanneladm t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.zoneno='" + getComp_id().getZoneno() + "'")
      .append(" and t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and t.comp_id.agentflag='" + getComp_id().getAgentflag() + "'")
      .append(" and t.comp_id.zhno='" + getComp_id().getZhno() + "'")
      .append(" and t.comp_id.channelcode='" + getComp_id().getChannelcode() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBusimode() == null) {
      setBusimode("");
    }
    if (getAgentbrno() == null) {
      setAgentbrno("");
    }
    if (getAgentteller() == null) {
      setAgentteller("");
    }
    if (getMaxamount() == null) {
      setMaxamount("");
    }
    if (getTotalamount() == null) {
      setTotalamount("");
    }
    if (getBillsavectl() == null) {
      setBillsavectl("");
    }
    if (getAutorevtranctl() == null) {
      setAutorevtranctl("");
    }
    if (getErrchkctl() == null) {
      setErrchkctl("");
    }
    if (getChannelstatus() == null) {
      setChannelstatus("");
    }
    if (getAutochkacct() == null) {
      setAutochkacct("");
    }
    if (getFlag1() == null) {
      setFlag1("");
    }
    if (getFlag2() == null) {
      setFlag2("");
    }
    if (getFlag3() == null) {
      setFlag3("");
    }
    if (getFlag4() == null) {
      setFlag4("");
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
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfaChanneladm)) {
      return false;
    }
    AfaChanneladm castOther = (AfaChanneladm)other;
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
