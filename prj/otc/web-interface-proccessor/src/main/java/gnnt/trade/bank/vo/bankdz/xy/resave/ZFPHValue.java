package gnnt.trade.bank.vo.bankdz.xy.resave;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZFPHValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String fileName = "zongfenpingheng";
  public String bankID;
  public String maketID;
  public Date tradeDate;
  public String currency = "001";
  public int type;
  public BigDecimal lastAccountBalance;
  public BigDecimal accountBalance;
  public int result;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("fileName:" + this.fileName + str);
    sb.append("bankID:" + this.bankID + str);
    sb.append("maketID:" + this.maketID + str);
    sb.append("tradeDate:" + this.tradeDate + str);
    sb.append("currency:" + this.currency + str);
    sb.append("type:" + this.type + str);
    sb.append("lastAccountBalance:" + this.lastAccountBalance + str);
    sb.append("accountBalance:" + this.accountBalance + str);
    sb.append("result:" + this.result + str);
    sb.append(str);
    return sb.toString();
  }
}
