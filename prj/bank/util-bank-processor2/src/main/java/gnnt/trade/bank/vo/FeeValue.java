package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class FeeValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public Date tradeDate;
  public String bankID;
  public String name;
  public double funds;
  public double fee;
  public double marketdelayfeebalance;
  public double fundsall;
}
