package gnnt.trade.bank.vo.bankdz.hx.sent;

import gnnt.trade.bank.vo.bankdz.hx.sent.child.DZMessage;
import gnnt.trade.bank.vo.bankdz.hx.sent.child.QSMessage;
import java.io.Serializable;

public class HXSentQSMsgValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String bankID;
  public String bankName;
  public String account;
  public String accountName;
  public String account1;
  public String accountName1;
  public String card;
  public String cardType;
  public int isopen;
  public int status;
  public double rsm;
  public double rm;
  public double margin;
  public double rfl;
  public double fl;
  public double todaybalance;
  public double lastbalance;
  public double inmoney;
  public double outmoney;
  public QSMessage qs;
  public DZMessage dz;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("交易商代码[firmID](" + this.firmID + ")" + str);
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("银行名称[bankName](" + this.bankName + ")" + str);
    sb.append("银行账号[account](" + this.account + ")" + str);
    sb.append("账户名[accountName](" + this.accountName + ")" + str);
    sb.append("交易商子账号[account1](" + this.account1 + ")" + str);
    sb.append("交易商子账户名[accountName1](" + this.accountName1 + ")" + str);
    sb.append("证件号码[card](" + this.card + ")" + str);
    sb.append("证件类型[cardType](" + this.cardType + ")" + str);
    sb.append("签约状态[isopen](" + this.isopen + ")" + str);
    sb.append("用户状态[status](" + this.status + ")" + str);
    sb.append("当日交收保证金[rsm](" + this.rsm + ")" + str);
    sb.append("当日远期保证金[rm](" + this.rm + ")" + str);
    sb.append("现货竞价保证金[margin](" + this.margin + ")" + str);
    sb.append("当日浮亏[rfl](" + this.rfl + ")" + str);
    sb.append("浮动盈亏[fl](" + this.fl + ")" + str);
    sb.append("当前可用资金[todaybalance](" + this.todaybalance + ")" + str);
    sb.append("上日可用资金[lastbalance](" + this.lastbalance + ")" + str);
    sb.append("当天入金金额[inmoney](" + this.inmoney + ")" + str);
    sb.append("当天出金[outmoney](" + this.outmoney + ")" + str);
    sb.append((this.qs == null ? "清算类为null" : this.qs.toString()) + str);
    sb.append((this.dz == null ? "对账类为null" : this.dz.toString()) + str);
    return sb.toString();
  }
}