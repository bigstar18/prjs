package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.util.Vector;

public class ZHBalaceValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankId;
  public String tradeDate;
  private Vector<BalaceErrorValue> v_bev;

  public Vector<BalaceErrorValue> getV_bev()
  {
    if (this.v_bev == null) {
      this.v_bev = new Vector();
    }
    return this.v_bev;
  }

  public void setV_bev(Vector<BalaceErrorValue> vBev)
  {
    this.v_bev = vBev;
  }

  public void pubBev(BalaceErrorValue bev)
  {
    if (bev == null)
      this.v_bev = new Vector();
    this.v_bev.add(bev);
  }
}