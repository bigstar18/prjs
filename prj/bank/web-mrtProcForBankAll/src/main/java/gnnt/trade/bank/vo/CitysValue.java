package gnnt.trade.bank.vo;

import java.io.Serializable;

public class CitysValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String ID;
  public String cityName;
  public String parentID;
  
  public String toString()
  {
    String sep = "\n";
    StringBuilder sb = new StringBuilder();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("ID:" + this.ID + sep);
    sb.append("cityName:" + this.cityName + sep);
    sb.append("parentID:" + this.parentID + sep);
    return sb.toString();
  }
}
