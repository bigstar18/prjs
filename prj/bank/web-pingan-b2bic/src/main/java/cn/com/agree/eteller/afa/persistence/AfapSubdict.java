package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfapSubdict
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AfapSubdictPK comp_id;
  private String codename;
  private String itemcname;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Item", "类别代码");
    hm.put("Code", "代码");
    hm.put("Codename", "中文名称");
  }
  
  public AfapSubdict(AfapSubdictPK comp_id, String codename)
  {
    this.comp_id = comp_id;
    this.codename = codename;
  }
  
  public AfapSubdict() {}
  
  public AfapSubdictPK getComp_id()
  {
    return this.comp_id;
  }
  
  public void setComp_id(AfapSubdictPK comp_id)
  {
    this.comp_id = comp_id;
  }
  
  public String getCodename()
  {
    return this.codename;
  }
  
  public void setCodename(String codename)
  {
    this.codename = codename;
  }
  
  public String getItem()
  {
    return getComp_id().getItem();
  }
  
  public void setItem(String item)
  {
    getComp_id().setItem(item);
  }
  
  public String getCode()
  {
    return getComp_id().getCode();
  }
  
  public void setCode(String code)
  {
    getComp_id().setCode(code);
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Item=" + getComp_id().getItem() + "|")
      .append("Code=" + getComp_id().getCode() + "|")
      .append("Codename=" + getCodename());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getCodename() == null) {
      setCodename("");
    }
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfapSubdict t where ";
    sb.append(sql)
      .append(" t.comp_id.item='" + getComp_id().getItem() + "'")
      .append(" and t.comp_id.code='" + getComp_id().getCode() + "'");
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
  
  public String toString()
  {
    return 
    

      new ToStringBuilder(this).append("comp_id", getComp_id()).append("codename", getCodename()).toString();
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof AfapSubdict)) {
      return false;
    }
    AfapSubdict castOther = (AfapSubdict)other;
    return new EqualsBuilder()
      .append(getComp_id(), castOther.getComp_id())
      .isEquals();
  }
  
  public int hashCode()
  {
    return 
    
      new HashCodeBuilder().append(getComp_id()).toHashCode();
  }
  
  public String getItemcname()
  {
    return this.itemcname;
  }
  
  public void setItemcname(String itemcname)
  {
    this.itemcname = itemcname;
  }
}
