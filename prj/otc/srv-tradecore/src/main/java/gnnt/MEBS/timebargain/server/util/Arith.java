package gnnt.MEBS.timebargain.server.util;

import java.io.PrintStream;
import java.math.BigDecimal;

public class Arith
{
  private static final int DEF_DIV_SCALE = 10;
  private static final long DEF_MUL_SCALE = 10000000000L;
  private static final int RESULT_DIV_SCALE = 10;
  
  private static BigDecimal result(BigDecimal b)
  {
    return b.setScale(10, 4);
  }
  
  public static boolean divideExactly(double d1, double d2)
  {
    double v1 = format(d1, 10);
    double v2 = format(d2, 10);
    return div(v1, v2) == div(v1, v2);
  }
  
  public static double priceFormat(double d1)
  {
    return format(d1, 10);
  }
  
  public static double add(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.add(b2)).doubleValue();
  }
  
  public static double add(float v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.add(b2)).doubleValue();
  }
  
  public static double add(float v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.add(b2)).doubleValue();
  }
  
  public static double add(double v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.add(b2)).doubleValue();
  }
  
  public static double sub(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.subtract(b2)).doubleValue();
  }
  
  public static double sub(float v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.subtract(b2)).doubleValue();
  }
  
  public static double sub(float v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.subtract(b2)).doubleValue();
  }
  
  public static double sub(double v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.subtract(b2)).doubleValue();
  }
  
  public static double mul(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    
    return result(result(b1.multiply(b2))).doubleValue();
  }
  
  public static double mul(float v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    
    return result(result(b1.multiply(b2))).doubleValue();
  }
  
  public static double mul(float v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    
    return result(result(b1.multiply(b2))).doubleValue();
  }
  
  public static double mul(double v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    
    return result(result(b1.multiply(b2))).doubleValue();
  }
  
  public static double div(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.divide(b2, 10, 4))
      .doubleValue();
  }
  
  public static double div(float v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.divide(b2, 10, 4))
      .doubleValue();
  }
  
  public static double div(float v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.divide(b2, 10, 4))
      .doubleValue();
  }
  
  public static double div(double v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return result(b1.divide(b2, 10, 4))
      .doubleValue();
  }
  
  public static double div(double v1, double v2, int scale, int roundingMode)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return result(b1.divide(b2, scale, roundingMode)).doubleValue();
  }
  
  public static int compareTo(double v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return b1.compareTo(b2);
  }
  
  public static int compareTo(float v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return b1.compareTo(b2);
  }
  
  public static int compareTo(double v1, float v2)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
      10, 4);
    return b1.compareTo(b2);
  }
  
  public static int compareTo(float v1, double v2)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
      10, 4);
    BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
      10, 4);
    return b1.compareTo(b2);
  }
  
  public static double format(double v, int scale)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v)).setScale(scale, 
      4);
    return b1.doubleValue();
  }
  
  public static double formatD(double v, int scale)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v)).setScale(scale, 
      1);
    return b1.doubleValue();
  }
  
  public static double format(float v, int scale)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v)).setScale(scale, 
      4);
    return b1.doubleValue();
  }
  
  public static double formatD(float v, int scale)
  {
    BigDecimal b1 = new BigDecimal(Float.toString(v)).setScale(scale, 
      1);
    return b1.doubleValue();
  }
  
  public static int getDecimalDigits(double data)
  {
    int num = 0;
    String strData = String.valueOf(data);
    BigDecimal bd = new BigDecimal(strData);
    num = bd.scale();
    if (num == 1) {
      if (strData.indexOf(".0") != -1) {
        num = 0;
      }
    }
    return num;
  }
  
  public static void main(String[] args)
  {
    System.out.println(format(1200.0005524999999D, 3));
  }
}
