package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaBranch
  implements Serializable
{
  private String branchno;
  private String branchcode;
  private String type;
  private String upbranchno;
  private String branchnames;
  private String branchname;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Branchno", "机构号");
    hm.put("Branchcode", "机构代码");
    hm.put("Type", "机构类型");
    hm.put("Upbranchno", "管辖机构号");
    hm.put("Branchnames", "机构简称");
    hm.put("Branchname", "机构名称");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
  }
  
  public AfaBranch(String branchno, String branchcode, String type, String upbranchno, String branchnames, String branchname, String note1, String note2)
  {
    this.branchno = branchno;
    this.branchcode = branchcode;
    this.type = type;
    this.upbranchno = upbranchno;
    this.branchnames = branchnames;
    this.branchname = branchname;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaBranch() {}
  
  public AfaBranch(String branchno, String type, String upbranchno, String branchnames, String branchname)
  {
    this.branchno = branchno;
    this.type = type;
    this.upbranchno = upbranchno;
    this.branchnames = branchnames;
    this.branchname = branchname;
  }
  
  public String getBranchno()
  {
    return this.branchno;
  }
  
  public void setBranchno(String branchno)
  {
    this.branchno = branchno;
  }
  
  public String getBranchcode()
  {
    return this.branchcode;
  }
  
  public void setBranchcode(String branchcode)
  {
    this.branchcode = branchcode;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getUpbranchno()
  {
    return this.upbranchno;
  }
  
  public void setUpbranchno(String upbranchno)
  {
    this.upbranchno = upbranchno;
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
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Branchno=" + getBranchno() + "|")
      .append("Branchcode=" + getBranchcode() + "|")
      .append("Type=" + getType() + "|")
      .append("Upbranchno=" + getUpbranchno() + "|")
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
    sql = "from AfaBranch t where ";
    sb.append(sql)
      .append(" t.branchno='" + getBranchno() + "'");
    
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBranchno() == null) {
      setBranchno("");
    }
    if (getBranchcode() == null) {
      setBranchcode("");
    }
    if (getType() == null) {
      setType("");
    }
    if (getUpbranchno() == null) {
      setUpbranchno("");
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
    
      new ToStringBuilder(this).append("branchno", getBranchno()).toString();
  }
}
