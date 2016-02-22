package gnnt.trade.bank.vo;

import java.io.Serializable;

public class InMoneyMarket
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String trader;
  public String contact;
  public String bankID;
  public String account;
  public String accountPwd;
  public double money;
  public String remark;
  public String bankFlag;
  public String inOutStart;
  public String personName;
  public String amoutDate;
  public String bankName;
  public String outAccount;
  public String payChannel;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("firmID:" + this.firmID + str);
    sb.append("trader:" + this.trader + str);
    sb.append("contact:" + this.contact + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("account:" + this.account + str);
    sb.append("accountPwd:" + this.accountPwd + str);
    sb.append("money:" + this.money + str);
    sb.append("remark:" + this.remark + str);
    sb.append("inOutStart:" + this.inOutStart + str);
    sb.append("personName:" + this.personName + str);
    sb.append("amoutDate:" + this.amoutDate + str);
    sb.append("bankName:" + this.bankName + str);
    sb.append("outAccount:" + this.outAccount + str);
    return sb.toString();
  }
}
