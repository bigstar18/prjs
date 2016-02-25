package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class QSChangeResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String firmID;
  public Date tradeDate;
  public double money;
  public int type;
  public int tradeType;
  public String note;
  public Date createDate;
  public int status;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("银行编号[bankID](" + this.bankID + ")" + str);
    sb.append("交易商代码[firmID](" + this.firmID + ")" + str);
    sb.append("清算日期[tradeDate](" + this.tradeDate + ")" + str);
    sb.append("发生额[money](" + this.money + ")" + str);
    sb.append("借贷标示[type](" + this.type + ")" + str);
    sb.append("资金类型[tradeType](" + this.tradeType + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    sb.append("写入日期[createDate](" + this.createDate + ")" + str);
    sb.append("操作状态[status](" + this.status + ")" + str);
    return sb.toString();
  }
}
