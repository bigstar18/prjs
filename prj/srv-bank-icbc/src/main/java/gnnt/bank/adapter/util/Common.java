package gnnt.bank.adapter.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common
{
  public static DateFormat df1 = new SimpleDateFormat("yyyyMMdd HHmmss");
  public static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
  public static DateFormat df3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static DateFormat df4 = new SimpleDateFormat("HH:mm:ss");
  public static DateFormat df5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static DateFormat df6 = new SimpleDateFormat("yyyyMMddHHmmss");
  public static DateFormat df7 = new SimpleDateFormat("yyyyMMdd");
  public static DateFormat df8 = new SimpleDateFormat("HHmmss");

  public static Date getDate()
  {
    Calendar ca = Calendar.getInstance();
    Date date = ca.getTime();
    return date;
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

  public static String toLowerCaseStart(String str)
  {
    String startStr = str.substring(0, 1);
    String _str = str.replaceFirst(startStr, startStr.toLowerCase());
    return _str;
  }

  public static String delNull(String str)
  {
    String _str = "";
    if (str != null) {
      _str = str;
    }
    return _str;
  }

  public static String fmtStrField(String str, int len, String c, int side)
  {
    String _str = str;
    while (_str.getBytes().length < len) {
      if (side == 0)
        _str = c + _str;
      else {
        _str = _str + c;
      }
    }
    return _str;
  }
  public static String fmtStrField(int str, int len, String c, int side) {
    return fmtStrField(String.valueOf(str), len, c, side);
  }
  public static String fmtStrField(long str, int len, String c, int side) {
    return fmtStrField(String.valueOf(str), len, c, side);
  }
  public static String fmtStrField(float str, int len, String c, int side) {
    return fmtStrField(String.valueOf(str), len, c, side);
  }
  public static String fmtStrField(double str, int len, String c, int side) {
    return fmtStrField(String.valueOf(str), len, c, side);
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

  public static Date getNextDay(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, 1);
    date = calendar.getTime();
    return date;
  }
}