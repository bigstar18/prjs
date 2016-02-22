package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.util.Arith;

public class Function
{
  public static final short ALGR_PERCENT = 1;
  public static final short ALGR_ABSOLUTE = 2;
  
  public static double computeMargin(short marginAlgr, long quantity, double marginRate, double price, double contractFactor)
  {
    double v_margin = 0.0D;
    if (marginAlgr == 1) {
      v_margin = quantity * Arith.mul(contractFactor, Arith.mul(price, marginRate));
    } else if (marginAlgr == 2) {
      v_margin = quantity * marginRate;
    }
    return v_margin;
  }
  
  public static double computeFee(short feeAlgr, long quantity, double feeRate, double price, double contractFactor)
  {
    double v_fee = 0.0D;
    if (feeAlgr == 1) {
      v_fee = quantity * Arith.mul(contractFactor, Arith.mul(price, feeRate));
    } else if (feeAlgr == 2) {
      v_fee = quantity * feeRate;
    }
    return v_fee;
  }
}
