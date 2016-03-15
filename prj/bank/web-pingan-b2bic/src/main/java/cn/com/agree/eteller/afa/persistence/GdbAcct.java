package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GdbAcct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String acctype;
  private String mapname;
  private String hosttype;
  private BigDecimal afstart;
  private BigDecimal ffhstart;
  private BigDecimal len;
  private String fhhflag;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Acctype", "帐号类型");
    hm.put("Mapname", "MAP文件名");
    hm.put("Hosttype", "主机类型");
    hm.put("Afstart", "帐号中分行号起始位置");
    hm.put("Ffhstart", "分行号起始位置");
    hm.put("Len", "分行号长度");
    hm.put("Fhhflag", "分行号校验标志");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Acctype=" + getAcctype() + "|").append("Mapname=" + getMapname() + "|").append("Hosttype=" + getHosttype() + "|")
      .append("Afstart=" + getAfstart() + "|").append("Ffhstart=" + getFfhstart() + "|").append("Len=" + getLen() + "|")
      .append("Fhhflag=" + getFhhflag() + "|").append("Note1=" + getNote1() + "|").append("Note2=" + getNote2());
    
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from GdbAcct t where ";
    sb.append(sql).append(" t.acctype='" + getAcctype() + "'");
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
    if (getAcctype() == null) {
      setAcctype("");
    }
    if (getMapname() == null) {
      setMapname("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
    if (getHosttype() == null) {
      setHosttype("");
    }
    if (getAfstart() == null) {
      setAfstart(new BigDecimal("0"));
    }
    if (getFfhstart() == null) {
      setFfhstart(new BigDecimal("0"));
    }
    if (getLen() == null) {
      setLen(new BigDecimal("0"));
    }
    if (getFhhflag() == null) {
      setFhhflag("");
    }
  }
  
  public GdbAcct(String acctype, String mapname, String hosttype, BigDecimal afstart, BigDecimal ffhstart, BigDecimal len, String fhhflag, String note1, String note2)
  {
    this.acctype = acctype;
    this.mapname = mapname;
    this.hosttype = hosttype;
    this.afstart = afstart;
    this.ffhstart = ffhstart;
    this.len = len;
    this.fhhflag = fhhflag;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public GdbAcct() {}
  
  public GdbAcct(String acctype, String mapname, String hosttype, String fhhflag)
  {
    this.acctype = acctype;
    this.mapname = mapname;
    this.hosttype = hosttype;
    this.fhhflag = fhhflag;
  }
  
  public String getAcctype()
  {
    return this.acctype;
  }
  
  public void setAcctype(String acctype)
  {
    this.acctype = acctype;
  }
  
  public String getMapname()
  {
    return this.mapname;
  }
  
  public void setMapname(String mapname)
  {
    this.mapname = mapname;
  }
  
  public String getHosttype()
  {
    return this.hosttype;
  }
  
  public void setHosttype(String hosttype)
  {
    this.hosttype = hosttype;
  }
  
  public BigDecimal getAfstart()
  {
    return this.afstart;
  }
  
  public void setAfstart(BigDecimal afstart)
  {
    this.afstart = afstart;
  }
  
  public BigDecimal getFfhstart()
  {
    return this.ffhstart;
  }
  
  public void setFfhstart(BigDecimal ffhstart)
  {
    this.ffhstart = ffhstart;
  }
  
  public BigDecimal getLen()
  {
    return this.len;
  }
  
  public void setLen(BigDecimal len)
  {
    this.len = len;
  }
  
  public String getFhhflag()
  {
    return this.fhhflag;
  }
  
  public void setFhhflag(String fhhflag)
  {
    this.fhhflag = fhhflag;
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
    return new ToStringBuilder(this).append("acctype", getAcctype()).toString();
  }
}
