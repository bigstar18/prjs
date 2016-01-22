package gnnt.trade.bank.vo.bankdz.xy;

import java.io.Serializable;
import java.util.Date;

public class XYMarketMoney
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int id;
  public String bankID;
  public String bankNumber;
  public int type;
  public int addDelt;
  public double money;
  public String note;
  public Date createTime;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("资金表ID[id](" + this.id + ")" + str);
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("银行转账号[bankNumber](" + this.bankNumber + ")" + str);
    sb.append("资金类型[type](" + this.type + ")" + str);
    sb.append("资金改变方向[addDelt](" + this.addDelt + ")" + str);
    sb.append("金额额度[money](" + this.money + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    return sb.toString();
  }
}