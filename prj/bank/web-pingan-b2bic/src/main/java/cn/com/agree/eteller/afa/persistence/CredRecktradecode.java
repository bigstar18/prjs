package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CredRecktradecode
  implements Serializable
{
  private CredRecktradecodePK comp_id;
  private String recktradecode;
  private String desc;
  private String note1;
  private String note2;
  private String note3;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Sysid", "系统标识");
    hm.put("Unitno", "商户单位代码");
    hm.put("Subunitno", "子商户单位代码");
    hm.put("Tradecode", "实时交易代码");
    hm.put("Recktradecode", "清算代码");
    hm.put("Desc", "说明");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
    hm.put("Note3", "备注3");
  }
  
  public CredRecktradecode(CredRecktradecodePK comp_id, String recktradecode, String desc, String note1, String note2, String note3)
  {
    this.comp_id = comp_id;
    this.recktradecode = recktradecode;
    this.desc = desc;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
  }
  
  public CredRecktradecode() {}
  
  public CredRecktradecode(CredRecktradecodePK comp_id, String recktradecode, String desc)
  {
    this.comp_id = comp_id;
    this.recktradecode = recktradecode;
    this.desc = desc;
  }
  
  public CredRecktradecodePK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(CredRecktradecodePK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getRecktradecode()
  {
    return this.recktradecode;
  }
  
  public void setRecktradecode(String recktradecode)
  {
    this.recktradecode = recktradecode;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public void setDesc(String desc)
  {
    this.desc = desc;
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
    
      new ToStringBuilder(this).append("comp_id", getComp_id()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof CredRecktradecode)) {
      return false;
    }
    CredRecktradecode castOther = (CredRecktradecode)other;
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
    sql = "from CredRecktradecode t where ";
    sb.append(sql)
      .append(" t.comp_id.sysid='" + getComp_id().getSysid() + "'")
      .append(" and t.comp_id.unitno='" + getComp_id().getUnitno() + "'")
      .append(" and t.comp_id.subunitno='" + getComp_id().getSubunitno() + "'")
      .append(" and t.comp_id.tradecode='" + getComp_id().getTradecode() + "'");
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
      .append("Tradecode=" + getComp_id().getTradecode() + "|")
      .append("Recktradecode=" + getRecktradecode() + "|")
      .append("Desc=" + getDesc() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2() + "|")
      .append("Note3=" + getNote3());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getRecktradecode() == null) {
      setRecktradecode("");
    }
    if (getDesc() == null) {
      setDesc("");
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
  
  public String getTradecode()
  {
    return getComp_id().getTradecode();
  }
  
  public void setTradecode(String code)
  {
    getComp_id().setTradecode(code);
  }
}
