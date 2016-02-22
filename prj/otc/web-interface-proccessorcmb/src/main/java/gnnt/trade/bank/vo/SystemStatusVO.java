package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class SystemStatusVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date tradeDate;
  public Date endDate;
  public int status;
  public int sectionID;
  public String note;
  public String recoverTime;
  public String pauseType;
  public Date lastTradeDate;
  public Date nextTradeDate;
}
