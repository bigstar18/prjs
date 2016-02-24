package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FirmBankMsg
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String contact;
  public String account;
  public String accountName;
  public String account1;
  public double frozenfuns;
  public String bankID;
  public String bankName;
  public String mainBank;
  public double transferFund;
  public double funds;
  public double canOutMoney;
}
