package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class QueryTradeData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String name;
  public String tradeID;
  public String systemID;
  public String systemName;
  public double lastBalance;
  public double balance;
  public double lastRights;
  public double rights;
  public double fundIO;
  public double fee;
  public Date tradeDate;
}