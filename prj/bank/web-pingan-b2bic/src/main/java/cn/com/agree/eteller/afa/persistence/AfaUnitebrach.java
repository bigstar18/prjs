package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfaUnitebrach
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String branchno;
  private String newbranchno;
  private String brdate;
  private String note1;
  private String note2;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Branchno", "机构号");
    hm.put("Newbranchno", "并入机构");
    hm.put("Brdate", "并入日期");
    hm.put("Note1", "备注1");
    hm.put("Note2", "备注2");
    hm.put("Note3", "备注3");
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
    sb.append("Branchno=" + getBranchno() + "|")
      .append("Newbranchno=" + getNewbranchno() + "|")
      .append("Brdate=" + getBrdate() + "|")
      .append("Note1=" + getNote1() + "|")
      .append("Note2=" + getNote2());
    return sb.toString();
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfaUnitebrach t where ";
    sb.append(sql).append(" t.branchno='" + getBranchno() + "'");
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getBranchno() == null) {
      setBranchno("");
    }
    if (getNewbranchno() == null) {
      setNewbranchno("");
    }
    if (getBrdate() == null) {
      setBrdate("");
    }
    if (getNote1() == null) {
      setNote1("");
    }
    if (getNote2() == null) {
      setNote2("");
    }
  }
  
  public AfaUnitebrach(String branchno, String newbranchno, String brdate, String note1, String note2)
  {
    this.branchno = branchno;
    this.newbranchno = newbranchno;
    this.brdate = brdate;
    this.note1 = note1;
    this.note2 = note2;
  }
  
  public AfaUnitebrach() {}
  
  public AfaUnitebrach(String branchno)
  {
    this.branchno = branchno;
  }
  
  public String getBranchno()
  {
    return this.branchno;
  }
  
  public void setBranchno(String branchno)
  {
    this.branchno = branchno;
  }
  
  public String getNewbranchno()
  {
    return this.newbranchno;
  }
  
  public void setNewbranchno(String newbranchno)
  {
    this.newbranchno = newbranchno;
  }
  
  public String getBrdate()
  {
    return this.brdate;
  }
  
  public void setBrdate(String brdate)
  {
    this.brdate = brdate;
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
    
      new ToStringBuilder(this).append("branchno", getBranchno()).toString();
  }
}
