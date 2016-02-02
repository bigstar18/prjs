package gnnt.trade.bank.util;

import java.math.BigDecimal;

public class Arith
{
  private static BigDecimal result(BigDecimal b)
  {
    return b.setScale(10, 4);
  }
  
  public static double add(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(10, 4);
    return result(b1.add(b2)).doubleValue();
  }
  
  public static double div(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(10, 4);
    return result(b1.divide(b2, 10, 4))
      .doubleValue();
  }
}
