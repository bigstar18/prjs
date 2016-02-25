package gnnt.trade.bank.vo;

import java.io.Serializable;

public class ArtificialValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int inOutMoney;
  public double money;
  public String firmID;
  public String bankID;
  public String note;
  public String trader;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append(str + "---" + getClass().getName() + "---" + str);
    sb.append("inOutMoney:" + this.inOutMoney + str);
    sb.append("money:" + this.money + str);
    sb.append("firmID:" + this.firmID + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("note:" + this.note + str);
    sb.append("trader:" + this.trader + str);
    return sb.toString();
  }
}
