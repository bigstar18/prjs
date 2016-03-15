package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AfapMaindict
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String item;
  private String itemename;
  private String itemcname;
  private static HashMap hm = new HashMap();
  
  static
  {
    hm.put("Item", "类别代码");
    hm.put("Itemename", "英文名称");
    hm.put("Itemcname", "中文名称");
  }
  
  public AfapMaindict(String item, String itemename, String itemcname)
  {
    this.item = item;
    this.itemename = itemename;
    this.itemcname = itemcname;
  }
  
  public AfapMaindict() {}
  
  public String getItem()
  {
    return this.item;
  }
  
  public void setItem(String item)
  {
    this.item = item;
  }
  
  public String getItemename()
  {
    return this.itemename;
  }
  
  public void setItemename(String itemename)
  {
    this.itemename = itemename;
  }
  
  public String getItemcname()
  {
    return this.itemcname;
  }
  
  public void setItemcname(String itemcname)
  {
    this.itemcname = itemcname;
  }
  
  public String toString2()
  {
    nulltospace();
    StringBuffer sb = new StringBuffer();
    sb.append("Item=" + getItem() + "|")
      .append("Itemename=" + getItemename() + "|")
      .append("Itemcname=" + getItemcname());
    return sb.toString();
  }
  
  public void nulltospace()
  {
    if (getItem() == null) {
      setItem("");
    }
    if (getItemename() == null) {
      setItemename("");
    }
    if (getItemcname() == null) {
      setItemcname("");
    }
  }
  
  public String toSql()
  {
    StringBuffer sb = new StringBuffer();
    String sql = "";
    sql = "from AfapMaindict t where ";
    sb.append(sql)
      .append(" t.item='" + getItem() + "'")
      .append(" and t.itemename='" + getItemename() + "'")
      .append(" and t.itemcname='" + getItemcname() + "'");
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
    


      new ToStringBuilder(this).append("item", getItem()).append("itemename", getItemename()).append("itemcname", getItemcname()).toString();
  }
}
