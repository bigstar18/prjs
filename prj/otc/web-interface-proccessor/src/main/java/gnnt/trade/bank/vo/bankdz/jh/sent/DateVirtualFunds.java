package gnnt.trade.bank.vo.bankdz.jh.sent;

import java.io.Serializable;
import java.util.Date;

public class DateVirtualFunds
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String firmid;
  public String contact;
  public double virtualFunds;
  public Date createDate;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("银行id[bankID](" + this.bankID + ")" + str);
    sb.append("交易商id[firmid](" + this.firmid + ")" + str);
    sb.append("交易商账号[contact](" + this.contact + ")" + str);
    
    sb.append("虚拟资金[virtualFunds](" + this.virtualFunds + ")" + str);
    sb.append("记录创建时间[createDate](" + this.createDate + ")" + str);
    return sb.toString();
  }
}
