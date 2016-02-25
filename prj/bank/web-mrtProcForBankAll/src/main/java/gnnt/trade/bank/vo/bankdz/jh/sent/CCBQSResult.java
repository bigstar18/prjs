package gnnt.trade.bank.vo.bankdz.jh.sent;

import java.io.Serializable;
import java.util.Date;

public class CCBQSResult
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public String bankID;
  public String firmID;
  public double fee;
  public double changeMoney;
  public int state;
  public Date qsDate;
  public String note;
  public Date createDate;
}
