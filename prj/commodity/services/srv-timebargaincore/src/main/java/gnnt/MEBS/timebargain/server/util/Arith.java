package gnnt.MEBS.timebargain.server.util;

import java.io.PrintStream;
import java.math.BigDecimal;

public class Arith
{
  private static final int DEF_DIV_SCALE = 10;
  private static final long DEF_MUL_SCALE = 10000000000L;
  private static final int RESULT_DIV_SCALE = 10;
  
  private static BigDecimal result(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.setScale(10, 4);
  }
  
  public static boolean divideExactly(double paramDouble1, double paramDouble2)
  {
    double d1 = format(paramDouble1, 10);
    double d2 = format(paramDouble2, 10);
    return div(d1, d2) == div(d1, d2);
  }
  
  public static double priceFormat(double paramDouble)
  {
    return format(paramDouble, 10);
  }
  
  public static double add(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return result(localBigDecimal1.add(localBigDecimal2)).doubleValue();
  }
  
  public static double add(float paramFloat1, float paramFloat2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat2)).setScale(10, 4);
    return result(localBigDecimal1.add(localBigDecimal2)).doubleValue();
  }
  
  public static double add(float paramFloat, double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    return result(localBigDecimal1.add(localBigDecimal2)).doubleValue();
  }
  
  public static double add(double paramDouble, float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    return result(localBigDecimal1.add(localBigDecimal2)).doubleValue();
  }
  
  public static double sub(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return result(localBigDecimal1.subtract(localBigDecimal2)).doubleValue();
  }
  
  public static double sub(float paramFloat1, float paramFloat2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat2)).setScale(10, 4);
    return result(localBigDecimal1.subtract(localBigDecimal2)).doubleValue();
  }
  
  public static double sub(float paramFloat, double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    return result(localBigDecimal1.subtract(localBigDecimal2)).doubleValue();
  }
  
  public static double sub(double paramDouble, float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    return result(localBigDecimal1.subtract(localBigDecimal2)).doubleValue();
  }
  
  public static double mul(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return result(result(localBigDecimal1.multiply(localBigDecimal2))).doubleValue();
  }
  
  public static double mul(float paramFloat1, float paramFloat2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat2)).setScale(10, 4);
    return result(result(localBigDecimal1.multiply(localBigDecimal2))).doubleValue();
  }
  
  public static double mul(float paramFloat, double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    return result(result(localBigDecimal1.multiply(localBigDecimal2))).doubleValue();
  }
  
  public static double mul(double paramDouble, float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    return result(result(localBigDecimal1.multiply(localBigDecimal2))).doubleValue();
  }
  
  public static double div(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return result(localBigDecimal1.divide(localBigDecimal2, 10, 4)).doubleValue();
  }
  
  public static double div(float paramFloat1, float paramFloat2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat2)).setScale(10, 4);
    return result(localBigDecimal1.divide(localBigDecimal2, 10, 4)).doubleValue();
  }
  
  public static double div(float paramFloat, double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    return result(localBigDecimal1.divide(localBigDecimal2, 10, 4)).doubleValue();
  }
  
  public static double div(double paramDouble, float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    return result(localBigDecimal1.divide(localBigDecimal2, 10, 4)).doubleValue();
  }
  
  public static double div(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return result(localBigDecimal1.divide(localBigDecimal2, paramInt1, paramInt2)).doubleValue();
  }
  
  public static int compareTo(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2)).setScale(10, 4);
    return localBigDecimal1.compareTo(localBigDecimal2);
  }
  
  public static int compareTo(float paramFloat1, float paramFloat2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat1)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat2)).setScale(10, 4);
    return localBigDecimal1.compareTo(localBigDecimal2);
  }
  
  public static int compareTo(double paramDouble, float paramFloat)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    return localBigDecimal1.compareTo(localBigDecimal2);
  }
  
  public static int compareTo(float paramFloat, double paramDouble)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Float.toString(paramFloat)).setScale(10, 4);
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble)).setScale(10, 4);
    return localBigDecimal1.compareTo(localBigDecimal2);
  }
  
  public static double format(double paramDouble, int paramInt)
  {
    BigDecimal localBigDecimal = new BigDecimal(Double.toString(paramDouble)).setScale(paramInt, 4);
    return localBigDecimal.doubleValue();
  }
  
  public static double formatD(double paramDouble, int paramInt)
  {
    BigDecimal localBigDecimal = new BigDecimal(Double.toString(paramDouble)).setScale(paramInt, 1);
    return localBigDecimal.doubleValue();
  }
  
  public static double format(float paramFloat, int paramInt)
  {
    BigDecimal localBigDecimal = new BigDecimal(Float.toString(paramFloat)).setScale(paramInt, 4);
    return localBigDecimal.doubleValue();
  }
  
  public static double formatD(float paramFloat, int paramInt)
  {
    BigDecimal localBigDecimal = new BigDecimal(Float.toString(paramFloat)).setScale(paramInt, 1);
    return localBigDecimal.doubleValue();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println(formatD(-1973.5989999999999D, 1));
    System.out.println(formatD(-1973.45D, 1));
    System.out.println(formatD(-1973.549D, 1));
    System.out.println(format(1973.5D, 0));
    System.out.println(format(1973.4000000000001D, 0));
    System.out.println(format(1973.5999999999999D, 0));
    System.out.println(Math.ceil(1973.01D));
    System.out.println(Math.floor(1973.99D));
    System.out.println(5L);
  }
}
