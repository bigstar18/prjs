package gnnt.trade.bank.data.hxb;

import java.io.Serializable;

public class QS
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String account;
  public int direction;
  public double amt;
  public String type;
  public String abstct;
}
