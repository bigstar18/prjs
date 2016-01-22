package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BankTime
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String bankName;
  public String login;
  public String transferon;
  public String transferoff;
  public String quit;
  public String compareTime;
  public int compareMode;
  public int openType;
  public int transferType;
}