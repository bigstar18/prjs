package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class MarketCashBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public double lastCash;
  public double cash;
  public double cashAMT;
  public double cashIO;
  public double lastFee;
  public double fee;
  public double feeAMT;
  public double feeIO;
  public Date tradeDate;
}
