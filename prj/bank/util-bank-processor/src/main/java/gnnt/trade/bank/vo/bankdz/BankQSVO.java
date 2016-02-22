package gnnt.trade.bank.vo.bankdz;

import java.io.Serializable;
import java.util.Date;

public class BankQSVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public Date tradeDate;
  public int tradeType;
  public int tradeStatus;
  public String note;
  public Date createDate;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("清算日期[tradeDate](" + this.tradeDate + ")" + str);
    sb.append("清算类型[tradeType](" + this.tradeType + ")" + str);
    sb.append("清算状态[tradeStatus](" + this.tradeStatus + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    sb.append("记录创建时间[createDate](" + this.createDate + ")" + str);
    return sb.toString();
  }
}