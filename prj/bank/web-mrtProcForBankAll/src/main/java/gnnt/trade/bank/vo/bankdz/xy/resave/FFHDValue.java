package gnnt.trade.bank.vo.bankdz.xy.resave;

import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class FFHDValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String fileName = "fenfenhedui";
  public String bankID;
  public String maketID;
  public Date tradeDate;
  private Vector<FirmDateValue> fdv;
  
  public void setFdv(Vector<FirmDateValue> fdv)
  {
    this.fdv = fdv;
  }
  
  public Vector<FirmDateValue> getFdv()
  {
    if (this.fdv == null) {
      this.fdv = new Vector();
    }
    return this.fdv;
  }
  
  public void putFdv(FirmDateValue fd)
  {
    if (this.fdv == null) {
      this.fdv = new Vector();
    }
    this.fdv.add(fd);
  }
}
