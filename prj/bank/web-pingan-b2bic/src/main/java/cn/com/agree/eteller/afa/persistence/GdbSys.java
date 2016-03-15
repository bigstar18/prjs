package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GdbSys
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private GdbSysPK comp_id;
  private String dataqueue;
  private String note1;
  private String note2;
  private String note3;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Bankunitno", "银行商户代码");
    hm.put("Onlineflag", "联机处理标志");
    hm.put("Dataqueue", "主机DATAQ名");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
    hm.put("Note3", "备注3");
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from GdbSys t where ";
    sb.append(sql)
      .append(" t.comp_id.bankunitno='" + getComp_id().getBankunitno() + "'")
      .append("and  t.comp_id.onlineflag='" + getComp_id().getOnlineflag() + "'");
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
    sb.append("Bankunitno=" + getComp_id().getBankunitno() + "|")
      .append("Onlineflag=" + getComp_id().getOnlineflag() + "|")
      .append("Dataqueue=" + getDataqueue() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2() + "|")
      .append("Note3=" + getNote3());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBankunitno() == null) {
      setBankunitno("");
    }
    if (getOnlineflag() == null) {
      setOnlineflag("");
    }
    if (getDataqueue() == null) {
      setDataqueue("");
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
  
  public GdbSys(GdbSysPK comp_id, String dataqueue, String note1, String note2, String note3)
  {
    this.comp_id = comp_id;
    this.dataqueue = dataqueue;
    this.note1 = note1;
    this.note2 = note2;
    this.note3 = note3;
  }
  
  public GdbSys() {}
  
  public GdbSys(GdbSysPK comp_id, String dataqueue)
  {
    this.comp_id = comp_id;
    this.dataqueue = dataqueue;
  }
  
  public GdbSysPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(GdbSysPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getDataqueue()
  {
    return this.dataqueue;
  }
  
  public void setDataqueue(String dataqueue)
  {
    this.dataqueue = dataqueue;
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
  
  public String getBankunitno()
  {
    return getComp_id().getBankunitno();
  }
  
  public void setBankunitno(String bankunitno)
  {
    getComp_id().setBankunitno(bankunitno);
  }
  
  public String getOnlineflag()
  {
    return getComp_id().getOnlineflag();
  }
  
  public void setOnlineflag(String onlineflag)
  {
    getComp_id().setOnlineflag(onlineflag);
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
    if (!(other instanceof GdbSys)) {
      return false;
    }
    GdbSys castOther = (GdbSys)other;
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
