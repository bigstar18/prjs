package gnnt.MEBS.vendue.util;

import java.io.PrintStream;
import java.math.BigDecimal;

public class Arith
{
  private static final int DEF_DIV_SCALE = 10;
  private static final int RESULT_DIV_SCALE = 10;
  
  private static BigDecimal result(BigDecimal paramBigDecimal)
  {
    return paramBigDecimal.setScale(10, 4);
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
  
  public static double format(float paramFloat, int paramInt)
  {
    BigDecimal localBigDecimal = new BigDecimal(Float.toString(paramFloat)).setScale(paramInt, 4);
    return localBigDecimal.doubleValue();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    float f1 = 1800.01F;
    double d = 153846.0D;
    float f2 = 153846.0F;
    f1 = (float)d;
    System.out.println(f1 + " " + d + " " + f2);
    System.out.println("3.4028235E38 1.4E-45");
  }
}
