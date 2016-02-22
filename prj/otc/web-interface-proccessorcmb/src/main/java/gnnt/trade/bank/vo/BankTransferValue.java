package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class BankTransferValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public long actionId;
  public String funId;
  public String payBankId;
  public String payBankName;
  public String recBankId;
  public String recBankName;
  public String payAcc;
  public String payAccName;
  public String recAcc;
  public String recAccName;
  public String recFirmId;
  public String recFirmName;
  public double money;
  public Timestamp createTime;
  public Timestamp updateTime;
  public int status;
  public String note;
  public long capitalActionId;
  public int type;
  public String info;
  public String account;
  public String Name;
  public String VldDt;
  public String Pwd;
  public String RegDt;
  public int St;
  public int accountType;
  public String flow;
  public String bankNum;
  public String transType;
}
