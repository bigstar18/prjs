package gnnt.trade.bank.vo;

import java.io.Serializable;

public class OutMoneyMarket
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String firmID;
  public String trader;
  public String contact;
  public String account;
  public String accountPwd;
  public double money;
  public int type = 0;
  public String remark;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("firmID:" + this.firmID + str);
    sb.append("trader:" + this.trader + str);
    sb.append("contact:" + this.contact + str);
    sb.append("account:" + this.account + str);
    sb.append("money:" + this.money + str);
    sb.append("type:" + this.type + str);
    sb.append("remark:" + this.remark + str);
    return sb.toString();
  }
}
