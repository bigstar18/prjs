package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaBranchcode
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfaBranchcodePK comp_id;
  private String settlebrno;
  private String branchcode;
  private String branchnames;
  private String branchname;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位代码");
    hm.put("Zoneno", "分行号");
    hm.put("Branchno", "网点号");
    hm.put("Settlebrno", "清算行所号");
    hm.put("Branchcode", "人行金融代码");
    hm.put("Branchnames", "行所名称简称");
    hm.put("Branchname", "行所名称全称");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public AfaBranchcode(AfaBranchcodePK comp_id, String settlebrno, String branchcode, String branchnames, String branchname, String note1, String note2)
  {
    this.comp_id = comp_id;
    this.settlebrno = settlebrno;
    this.branchcode = branchcode;
    this.branchnames = branchnames;
    this.branchname = branchname;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaBranchcode() {}
  
  public AfaBranchcode(AfaBranchcodePK comp_id, String branchcode)
  {
    this.comp_id = comp_id;
    this.branchcode = branchcode;
  }
  
  public AfaBranchcodePK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfaBranchcodePK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getSettlebrno()
  {
    return this.settlebrno;
  }
  
  public void setSettlebrno(String settlebrno)
  {
    this.settlebrno = settlebrno;
  }
  
  public String getBranchcode()
  {
    return this.branchcode;
  }
  
  public void setBranchcode(String branchcode)
  {
    this.branchcode = branchcode;
  }
  
  public String getBranchnames()
  {
    return this.branchnames;
  }
  
  public void setBranchnames(String branchnames)
  {
    this.branchnames = branchnames;
  }
  
  public String getBranchname()
  {
    return this.branchname;
  }
  
  public void setBranchname(String branchname)
  {
    this.branchname = branchname;
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
    if (!(other instanceof AfaBranchcode)) {
      return false;
    }
    AfaBranchcode castOther = (AfaBranchcode)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Sysid=" + getComp_id().getSysid() + "|")
      .append("Unitno=" + getComp_id().getUnitno() + "|")
      .append("Zoneno=" + getComp_id().getZoneno() + "|")
      .append("Branchno=" + getComp_id().getBranchno() + "|")
      .append("Settlebrno=" + getSettlebrno() + "|")
      .append("Branchcode=" + getBranchcode() + "|")
      .append("Branchnames=" + getBranchnames() + "|")
      .append("Branchname=" + getBranchname() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaBranchcode t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.zoneno='" + getComp_id().getZoneno() + "'")
      .append(" and t.comp_id.branchno='" + getComp_id().getBranchno() + "'");
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
    if (getSettlebrno() == null) {
      setSettlebrno("");
    }
    if (getBranchcode() == null) {
      setBranchcode("");
    }
    if (getBranchnames() == null) {
      setBranchnames("");
    }
    if (getBranchname() == null) {
      setBranchname("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
  }
  
  public String getBranchno()
  {
    return getComp_id().getBranchno();
  }
  
  public void setBranchno(String branchno)
  {
    getComp_id().setBranchno(branchno);
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
  
  public String getZoneno()
  {
    return getComp_id().getZoneno();
  }
  
  public void setZoneno(String zoneno)
  {
    getComp_id().setZoneno(zoneno);
  }
}
