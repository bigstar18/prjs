package gnnt.MEBS.bill.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Tool
{
  public static String convertTS(Timestamp paramTimestamp)
  {
    return paramTimestamp.toString().substring(0, paramTimestamp.toString().indexOf(" "));
  }
  
  public static String fmtDouble2(double paramDouble)
  {
    String str = "0.00";
    try
    {
      DecimalFormat localDecimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
      localDecimalFormat.applyPattern("###0.00");
      str = localDecimalFormat.format(paramDouble);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtDouble(double paramDouble)
  {
    String str = "0";
    try
    {
      str = String.valueOf(BigDecimal.valueOf(paramDouble));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }
  
  public static String fmtTime(Timestamp paramTimestamp)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      str = localSimpleDateFormat.format(paramTimestamp);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtDate(Timestamp paramTimestamp)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      str = localSimpleDateFormat.format(paramTimestamp);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtOnlyTime(Timestamp paramTimestamp)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
      str = localSimpleDateFormat.format(paramTimestamp);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtTime(java.sql.Date paramDate)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      str = localSimpleDateFormat.format(paramDate);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtDate(java.sql.Date paramDate)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      str = localSimpleDateFormat.format(paramDate);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtTime(java.util.Date paramDate)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      str = localSimpleDateFormat.format(paramDate);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static String fmtDate(java.util.Date paramDate)
  {
    String str = "";
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      str = localSimpleDateFormat.format(paramDate);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static int strToInt(String paramString)
  {
    return strToInt(paramString, 64536);
  }
  
  public static int strToInt(String paramString, int paramInt)
  {
    int i = paramInt;
    try
    {
      if ((paramString != null) && (paramString.length() != 0)) {
        i = Integer.parseInt(paramString);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }
  
  public static double strToDouble(String paramString, double paramDouble)
  {
    double d = paramDouble;
    try
    {
      if ((paramString != null) && (paramString.length() != 0)) {
        d = Double.parseDouble(paramString);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return d;
  }
  
  public static java.util.Date strToDate(String paramString)
  {
    java.util.Date localDate = null;
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      localDate = localSimpleDateFormat.parse(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localDate;
  }
  
  public static Time convertTime(String paramString)
  {
    Time localTime = null;
    try
    {
      localTime = Time.valueOf(paramString);
    }
    catch (Exception localException) {}
    return localTime;
  }
  
  public static String delNull(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
  }
  
  public static String getExceptionTrace(Throwable paramThrowable)
  {
    if (paramThrowable != null)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      paramThrowable.printStackTrace(localPrintWriter);
      return localStringWriter.toString();
    }
    return "No Exception";
  }
}
