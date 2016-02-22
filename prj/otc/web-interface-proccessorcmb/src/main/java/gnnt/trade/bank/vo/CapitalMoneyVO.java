package gnnt.trade.bank.vo;

import java.io.Serializable;

public class CapitalMoneyVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int type;
  public int status;
  public double money;
  public int rowcount;
  
  public CapitalMoneyVO(int type, int status)
  {
    this.type = type;
    this.status = status;
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("type[" + this.type + "]" + str);
    sb.append("status[" + this.status + "]" + str);
    sb.append("money[" + this.money + "]" + str);
    sb.append("rowcount[" + this.rowcount + "]" + str);
    return sb.toString();
  }
}
