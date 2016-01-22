package gnnt.trade.bank.vo;

import java.io.Serializable;

public class TransMnyObjValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int id;
  public String className;
  public String note;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("Id:" + this.id + sep);
    sb.append("className:" + this.className + sep);
    sb.append("note:" + this.note + sep);
    sb.append(sep);
    return sb.toString();
  }
}