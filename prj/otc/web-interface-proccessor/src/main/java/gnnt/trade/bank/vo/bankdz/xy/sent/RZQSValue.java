package gnnt.trade.bank.vo.bankdz.xy.sent;

import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class RZQSValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String fileName = "rizhongqingsuan";
  public String bankID;
  public String maketID;
  public Date tradeDate;
  private MarketRightValue mr;
  private Vector<FirmRightValue> frv = new Vector();
  
  public void setMarketRight(MarketRightValue mr)
  {
    this.mr = mr;
  }
  
  public MarketRightValue getMarketRight()
  {
    if (this.mr == null) {
      this.mr = new MarketRightValue();
    }
    return this.mr;
  }
  
  public void setFrv(Vector<FirmRightValue> frv)
  {
    this.frv = frv;
  }
  
  public Vector<FirmRightValue> getFrv()
  {
    if (this.frv == null) {
      this.frv = new Vector();
    }
    return this.frv;
  }
  
  public void putFrv(FirmRightValue fr)
  {
    if (this.frv == null) {
      this.frv = new Vector();
    }
    this.frv.add(fr);
  }
}
