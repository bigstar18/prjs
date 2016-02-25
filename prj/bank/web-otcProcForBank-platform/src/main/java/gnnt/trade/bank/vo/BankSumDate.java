package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Date;

public class BankSumDate
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date tradeDate;
  public String bankCode;
  public String bankName;
  public double balacne;
  public double rightsfrozenfunds;
  public double rights;
  public double firmfee;
  public double fundIO;
}
