package gnnt.trade.bank.vo;

import java.io.Serializable;

public class DicValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public int type;
  public String bankID;
  public String name;
  public String value;
  public String note;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("Id:" + this.id + sep);
    sb.append("type:" + this.type + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("name:" + this.name + sep);
    sb.append("value:" + this.value + sep);
    sb.append("note:" + this.note + sep);
    sb.append(sep);
    return sb.toString();
  }
}
