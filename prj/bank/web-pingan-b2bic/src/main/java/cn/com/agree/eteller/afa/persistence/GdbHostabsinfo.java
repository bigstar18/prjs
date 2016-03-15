package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GdbHostabsinfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private GdbHostabsinfoPK comp_id;
  private String accttabs;
  private String prtabs;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Hosttype", "主机类型");
    hm.put("Bankunitno", "商户号");
    hm.put("Saveflag", "对私/对公标志");
    hm.put("Agentflag", "业务方式");
    
    hm.put("Accttabs", "清算摘要");
    hm.put("Prtabs", "打印摘要");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from GdbHostabsinfo t where ";
    sb.append(sql).append(" t.comp_id.hosttype='" + getComp_id().getHosttype() + "'")
      .append("and t.comp_id.bankunitno='" + getComp_id().getBankunitno() + "'")
      .append("and t.comp_id.saveflag='" + getComp_id().getSaveflag() + "'")
      .append("and t.comp_id.agentflag='" + getComp_id().getAgentflag() + "'");
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
    sb.append("Hosttype=" + getComp_id().getHosttype() + "|")
      .append("Bankunitno=" + getComp_id().getBankunitno() + "|")
      .append("Saveflag=" + getComp_id().getSaveflag() + "|")
      .append("Agentflag=" + getComp_id().getAgentflag() + "|")
      
      .append("Accttabs=" + getAccttabs() + "|")
      .append("Prtabs=" + getPrtabs() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getHosttype() == null) {
      setHosttype("");
    }
    if (getBankunitno() == null) {
      setBankunitno("");
    }
    if (getSaveflag() == null) {
      setSaveflag("");
    }
    if (getAgentflag() == null) {
      setAgentflag("");
    }
    if (getAccttabs() == null) {
      setAccttabs("");
    }
    if (getPrtabs() == null) {
      setPrtabs("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
  }
  
  public GdbHostabsinfo(GdbHostabsinfoPK comp_id, String accttabs, String prtabs, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.accttabs = accttabs;
    this.prtabs = prtabs;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public GdbHostabsinfo() {}
  
  public GdbHostabsinfo(GdbHostabsinfoPK comp_id, String accttabs, String prtabs)
  {
    this.comp_id = comp_id;
    this.accttabs = accttabs;
    this.prtabs = prtabs;
  }
  
  public GdbHostabsinfoPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(GdbHostabsinfoPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getAccttabs()
  {
    return this.accttabs;
  }
  
  public void setAccttabs(String accttabs)
  {
    this.accttabs = accttabs;
  }
  
  public String getPrtabs()
  {
    return this.prtabs;
  }
  
  public void setPrtabs(String prtabs)
  {
    this.prtabs = prtabs;
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
  
  public String getHosttype()
  {
    return getComp_id().getHosttype();
  }
  
  public void setHosttype(String hosttype)
  {
    getComp_id().setHosttype(hosttype);
  }
  
  public String getBankunitno()
  {
    return getComp_id().getBankunitno();
  }
  
  public void setBankunitno(String bankunitno)
  {
    getComp_id().setBankunitno(bankunitno);
  }
  
  public String getSaveflag()
  {
    return getComp_id().getSaveflag();
  }
  
  public void setSaveflag(String saveflag)
  {
    getComp_id().setSaveflag(saveflag);
  }
  
  public String getAgentflag()
  {
    return getComp_id().getAgentflag();
  }
  
  public void setAgentflag(String agentflag)
  {
    getComp_id().setAgentflag(agentflag);
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
    if (!(other instanceof GdbHostabsinfo)) {
      return false;
    }
    GdbHostabsinfo castOther = (GdbHostabsinfo)other;
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
