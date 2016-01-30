package gnnt.trade.bank.vo;

import java.io.Serializable;

public class MarketFirmMsg
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String bankID;
  public String account;
  public String accountName;
  public String cardType;
  public String card;
  public double balance;
  public double frozen;
  public double right;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append(str + "---" + getClass().getName() + "---" + str);
    sb.append("交易商代码[firmID](" + this.firmID + ")" + str);
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("银行账号[account](" + this.account + ")" + str);
    sb.append("账户名[accountName](" + this.accountName + ")" + str);
    sb.append("证件类型[cardType](" + this.cardType + ")" + str);
    sb.append("证件号码[card](" + this.card + ")" + str);
    sb.append("可用资金[balance](" + this.balance + ")" + str);
    sb.append("冻结资金[frozen](" + this.frozen + ")" + str);
    sb.append("权益[right](" + this.right + ")" + str);
    return sb.toString();
  }
}