package gnnt.MEBS.vendue.util;

import java.io.PrintStream;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import oracle.sql.CLOB;

public class Tool
{
  public static String fmtHZNumber(String paramString)
  {
    switch (Integer.parseInt(paramString))
    {
    case 0: 
      return "零";
    case 1: 
      return "壹";
    case 2: 
      return "贰";
    case 3: 
      return "叁";
    case 4: 
      return "肆";
    case 5: 
      return "伍";
    case 6: 
      return "陆";
    case 7: 
      return "柒";
    case 8: 
      return "捌";
    case 9: 
      return "玖";
    }
    return "";
  }
  
  public static String fmtCapitalization(double paramDouble)
  {
    String str1 = fmtDouble2(paramDouble);
    str1 = str1.substring(0, str1.length() - 3);
    int i = str1.length();
    String str2 = "";
    if (i > 8) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 9, i - 8)) + " 亿 ";
    }
    if (i > 7) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 8, i - 7)) + " 仟 ";
    }
    if (i > 6) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 7, i - 6)) + " 佰 ";
    }
    if (i > 5) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 6, i - 5)) + " 拾 ";
    }
    if (i > 4) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 5, i - 4)) + " 万 ";
    }
    if (i > 3) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 4, i - 3)) + " 仟 ";
    }
    if (i > 2) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 3, i - 2)) + " 佰 ";
    }
    if (i > 1) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 2, i - 1)) + " 拾 ";
    }
    if (i > 0) {
      str2 = str2 + fmtHZNumber(str1.substring(i - 1, i)) + " 元 ";
    }
    return str2;
  }
  
  public static long getTime(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    String[] arrayOfString = paramString.split(":");
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(11, Integer.parseInt(arrayOfString[0]));
    localCalendar.set(12, Integer.parseInt(arrayOfString[1]));
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    return localCalendar.getTimeInMillis();
  }
  
  public static String delNull(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
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
  
  public static String fmtDouble4(double paramDouble)
  {
    String str = "0.0000";
    try
    {
      DecimalFormat localDecimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
      localDecimalFormat.applyPattern("###0.0000");
      str = localDecimalFormat.format(paramDouble);
    }
    catch (Exception localException) {}
    return str;
  }
  
  public static long strToLong(String paramString)
  {
    long l = -10000000L;
    try
    {
      l = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return l;
  }
  
  public static double strToDouble(String paramString)
  {
    double d = -10000000.0D;
    try
    {
      d = Double.parseDouble(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return d;
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
  
  public static String CLOBToString(CLOB paramCLOB)
  {
    String str = "";
    try
    {
      if (paramCLOB != null)
      {
        Reader localReader = paramCLOB.getCharacterStream();
        int i = 0;
        char[] arrayOfChar = new char[1000000];
        StringBuffer localStringBuffer = null;
        while ((i = localReader.read(arrayOfChar)) != -1)
        {
          localStringBuffer = new StringBuffer();
          localStringBuffer.append(arrayOfChar, 0, i);
          str = str + localStringBuffer.toString();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(11, Integer.parseInt(paramArrayOfString[0]));
    localCalendar.set(12, Integer.parseInt(paramArrayOfString[1]));
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    long l = localCalendar.getTimeInMillis();
    System.out.println((l - System.currentTimeMillis()) / 1000L);
  }
}
