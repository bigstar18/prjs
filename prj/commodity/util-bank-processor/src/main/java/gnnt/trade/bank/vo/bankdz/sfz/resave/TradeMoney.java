package gnnt.trade.bank.vo.bankdz.sfz.resave;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.TradeMoneyChild;
import java.io.Serializable;
import java.util.Vector;

public class TradeMoney extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 4;
  public int tradeCound;
  public double outMoney;
  public double inMoney;
  public double billMoney;
  public Vector<TradeMoneyChild> child = new Vector();

  public String toString() {
    StringBuffer sb = new StringBuffer();
    String str = "\n";
    sb.append("-------------文件首行信息-------------" + str);
    sb.append("流水总比数(tradeCound)[" + this.tradeCound + "]" + str);
    sb.append("出金总金额(outMoney)[" + this.outMoney + "]" + str);
    sb.append("入金总金额(inMoney)[" + this.inMoney + "]" + str);
    sb.append("挂账总金额(billMoney)[" + this.billMoney + "]" + str);
    sb.append(str);
    for (TradeMoneyChild tm : this.child) {
      sb.append("-------------文件具体信息-------------" + str);
      sb.append(tm.toString());
    }
    return sb.toString();
  }
}