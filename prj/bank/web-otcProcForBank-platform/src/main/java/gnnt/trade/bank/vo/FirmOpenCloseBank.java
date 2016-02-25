package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class FirmOpenCloseBank
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public int type;
  public String note;
  public Date tradeDate;
}
