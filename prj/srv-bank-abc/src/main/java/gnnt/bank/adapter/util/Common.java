package gnnt.bank.adapter.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common
{
  public static DateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
  public static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
  public static DateFormat df3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static DateFormat df4 = new SimpleDateFormat("HH:mm:ss");
  public static DateFormat df5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static DateFormat df6 = new SimpleDateFormat("yyyyMMddHHmmss");
  public static DateFormat df7 = new SimpleDateFormat("yyyyMMdd");
  public static final String endSign = ".";
  private static final int mLen = 8;

  public static Date getDate()
  {
    Calendar ca = Calendar.getInstance();
    Date date = ca.getTime();
    return date;
  }

  public static String strTime(String time)
  {
    String line = "-";
    String s = ":";
    String strTime = time.substring(0, 4) + line + time.substring(4, 6) + line + time.substring(6, 8) + 
      " " + time.substring(8, 10) + s + time.substring(10, 12) + s + time.substring(12, 14);
    return strTime;
  }

  public static String timeToStr(Timestamp time)
  {
    String str = "";
    String strTime = time.toString();
    str = strTime.substring(0, 4) + strTime.substring(5, 7) + strTime.substring(8, 10) + 
      strTime.substring(11, 13) + strTime.substring(14, 16) + strTime.substring(17, 19);
    return str;
  }

  public static String timeToStr()
  {
    Date date = getDate();
    Timestamp t = new Timestamp(date.getTime());
    String str = "";
    String strTime = t.toString();
    str = strTime.substring(0, 4) + strTime.substring(5, 7) + strTime.substring(8, 10) + 
      strTime.substring(11, 13) + strTime.substring(14, 16) + strTime.substring(17, 19);
    return str;
  }

  public static String MakeMarketSer(long marketWater, int num)
  {
    String marketSer = String.valueOf(marketWater);
    int strNum = marketSer.length();
    for (int i = 0; i < num - strNum; i++) {
      marketSer = "0" + marketSer;
    }
    return marketSer;
  }

  public static double TrDouble(String Amt)
  {
    if (Amt == null)
      Amt = "0";
    else if (Amt.trim().equals(""))
      Amt = "0";
    double amtInt = 0.0D;
    amtInt = Double.parseDouble(Amt);
    return amtInt;
  }

  public static String Spell(int len)
  {
    String str = "";
    for (int i = 1; i <= len; i++) {
      str = str + " ";
    }
    return str;
  }

  public static String Spell(String str, int len)
  {
    if (str == null) {
      str = "";
      for (int i = 1; i <= len; i++)
        str = str + " ";
    }
    else {
      int slen = str.length();
      for (int i = 1; i <= len - slen; i++) {
        str = str + " ";
      }
    }
    return str;
  }

  public static String setFill(int len)
  {
    String str = "";
    int len1 = String.valueOf(len).length();
    if (len1 < 8) {
      for (int i = 1; i <= 8 - len1; i++) {
        str = str + "0";
      }
    }
    str = str + len;
    return str;
  }
  public static String MakeMarketSer(int key) {
    String marketSer = String.valueOf(key);
    int strNum = marketSer.length();
    int Num = 6;
    for (int i = 0; i < Num - strNum; i++) {
      marketSer = "0" + marketSer;
    }
    return marketSer;
  }
  public static boolean isNum(String msg) {
    if (Character.isDigit(msg.charAt(0))) {
      return true;
    }
    return false;
  }

  public static String disDouble2(double f) {
    String result = "0.00";
    try
    {
      DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
      nf.applyPattern("0.00");
      result = nf.format(f);
    } catch (Exception localException) {
    }
    return result;
  }

  public static String getExceptionTrace(Throwable e)
  {
    if (e != null) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return sw.toString();
    }
    return "No Exception";
  }

  public static String delNull(String str)
  {
    if (str == null)
    {
      return "";
    }

    return str;
  }

  public static Date getLastDay(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, -1);
    date = calendar.getTime();
    return date;
  }
}