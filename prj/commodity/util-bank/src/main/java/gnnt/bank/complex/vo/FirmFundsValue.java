package gnnt.bank.complex.vo;

import java.io.Serializable;

public class FirmFundsValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String contact;
  public double firmFunds;
  public double canOutFunds;
  public double lastBalance;
  public double inOutFunds;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("firmID[" + this.firmID + "]" + str);
    sb.append("contact[" + this.contact + "]" + str);
    sb.append("firmFunds[" + this.firmFunds + "]" + str);
    sb.append("canOutFunds[" + this.canOutFunds + "]" + str);
    sb.append("lastBalance[" + this.lastBalance + "]" + str);
    sb.append("inOutFunds[" + this.inOutFunds + "]" + str);
    return sb.toString();
  }
}
