package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class FirmBalance_CEB
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String memNum;
  public String firmID;
  public String bankID;
  public String account;
  public String accountName;
  public String cardType;
  public String card;
  public double FeeMoney;
  public double QYChangeMoney;
  public double QYMoney;
  public Date date;
  public double CRJSum;
  public double yesQYMoney;
  public double useableBalance;
  public String account1;
}
