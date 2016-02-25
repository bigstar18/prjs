package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmid;
  public String bankid;
  public String key;
  public String value;
  
  public String toString()
  {
    String sep = "\n";
    StringBuilder sb = new StringBuilder();
    sb.append("**" + getClass().getName() + "**");
    sb.append("firmid:" + this.firmid + sep);
    sb.append("bankid:" + this.bankid + sep);
    sb.append("key:" + this.key + sep);
    sb.append("value:" + this.value + sep);
    
    return sb.toString();
  }
}
