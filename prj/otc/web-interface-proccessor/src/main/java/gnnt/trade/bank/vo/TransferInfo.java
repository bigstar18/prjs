package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransferInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankid;
  public String funId;
  public String actionId;
  public double money;
  public int type;
  public Timestamp createTime;
  public Timestamp bankTime;
  public String outAccount;
  public String outAccountName;
  public String inAccount;
  public String inAccountName;
  public int status;
}
