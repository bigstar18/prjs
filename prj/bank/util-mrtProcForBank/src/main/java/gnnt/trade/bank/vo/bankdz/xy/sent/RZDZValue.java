package gnnt.trade.bank.vo.bankdz.xy.sent;

import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class RZDZValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String fileName = "rizhongduizhang";
  public String bankID;
  public String maketID;
  public Date tradeDate;
  private Vector<FirmDZValue> fdv;

  public void setFdv(Vector<FirmDZValue> fdv)
  {
    this.fdv = fdv;
  }

  public Vector<FirmDZValue> getFdv() {
    if (this.fdv == null) {
      this.fdv = new Vector();
    }
    return this.fdv;
  }

  public void putFdv(FirmDZValue fd) {
    if (this.fdv == null) {
      this.fdv = new Vector();
    }
    this.fdv.add(fd);
  }
}