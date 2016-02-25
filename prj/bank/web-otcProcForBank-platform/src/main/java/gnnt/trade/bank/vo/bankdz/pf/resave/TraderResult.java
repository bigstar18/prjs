package gnnt.trade.bank.vo.bankdz.pf.resave;

import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import java.util.Vector;

public class TraderResult
{
  public Vector<Margins> mVe = new Vector();
  public Vector<TradeList> tVe = new Vector();
  public int timeOutCount = 0;
  public int faileCount = 0;
  public int flag = 0;
}
