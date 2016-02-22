package gnnt.MEBS.base.util;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateUtil
{
  private static Log log = LogFactory.getLog(DateUtil.class);
  private static String defaultDatePattern = null;
  private static String timePattern = "HH:mm:ss";
  
  public static synchronized String getDatePattern()
  {
    Locale locale = LocaleContextHolder.getLocale();
    try
    {
      defaultDatePattern = 
        ResourceBundle.getBundle("RESOURCE", locale).getString("date.format");
    }
    catch (MissingResourceException mse)
    {
      defaultDatePattern = "yyyy-MM-dd";
    }
    return defaultDatePattern;
  }
  
  public static final String getDate(Date aDate)
  {
    SimpleDateFormat df = null;
    String returnValue = "";
    if (aDate != null)
    {
      df = new SimpleDateFormat(getDatePattern());
      returnValue = df.format(aDate);
    }
    return returnValue;
  }
  
  public static final Date convertStringToDate(String aMask, String strDate)
    throws ParseException
  {
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(aMask);
    if (log.isDebugEnabled()) {
      log.debug("converting '" + strDate + "' to date with mask '" + 
        aMask + "'");
    }
    try
    {
      date = df.parse(strDate);
    }
    catch (ParseException pe)
    {
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }
    return date;
  }
  
  public static String getTimeNow(Date theTime)
  {
    return getDateTime(timePattern, theTime);
  }
  
  public static Calendar getToday()
    throws ParseException
  {
    Date today = new Date();
    SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
    


    String todayAsString = df.format(today);
    Calendar cal = new GregorianCalendar();
    cal.setTime(convertStringToDate(todayAsString));
    
    return cal;
  }
  
  public static final String getDateTime(String aMask, Date aDate)
  {
    SimpleDateFormat df = null;
    String returnValue = "";
    if (aDate == null)
    {
      log.error("aDate is null!");
    }
    else
    {
      df = new SimpleDateFormat(aMask);
      returnValue = df.format(aDate);
    }
    return returnValue;
  }
  
  public static final String convertDateToString(Date aDate)
  {
    return getDateTime(getDatePattern(), aDate);
  }
  
  public static Date convertStringToDate(String strDate)
    throws ParseException
  {
    Date aDate = null;
    try
    {
      if (log.isDebugEnabled()) {
        log.debug("converting date with pattern: " + getDatePattern());
      }
      aDate = convertStringToDate(getDatePattern(), strDate);
    }
    catch (ParseException pe)
    {
      log.error("Could not convert '" + strDate + 
        "' to a date, throwing exception");
      pe.printStackTrace();
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }
    return aDate;
  }
  
  public static Date Str2Date(String year, String mon, String day)
  {
    Calendar cal = Calendar.getInstance();
    cal.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, 
      Integer.parseInt(day), 0, 0, 0);
    cal.set(14, 0);
    return cal.getTime();
  }
  
  public static Date Str2Date(String str)
  {
    if ((str != null) && (!str.trim().equals("")))
    {
      if (str.indexOf("-") != -1)
      {
        String[] arrDate = str.split("-");
        return Str2Date(arrDate[0], arrDate[1], arrDate[2]);
      }
      return Str2Date(str.substring(0, 4), str.substring(4, 6), str
        .substring(6));
    }
    return new Date();
  }
  
  public static Date GoMonth(Date date, int mon)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(2, mon);
    return cal.getTime();
  }
  
  public static Date GoDate(Date date, int day)
  {
    long time = date.getTime();
    time += day * 24L * 60L * 60L * 1000L;
    return new Date(time);
  }
  
  public static String getCurDate()
  {
    Calendar c = Calendar.getInstance();
    Timestamp ts = new Timestamp(c.getTime().getTime());
    String curDate = String.valueOf(ts);
    curDate = curDate.substring(0, curDate.indexOf(" "));
    
    return curDate;
  }
  
  public static String getCurTime()
  {
    Calendar c = Calendar.getInstance();
    
    Timestamp ts = new Timestamp(c.getTime().getTime());
    String curTime = String.valueOf(ts);
    curTime = curTime.substring(11, curTime.indexOf("."));
    
    return curTime;
  }
  
  public static String getCurDateTime()
  {
    Calendar c = Calendar.getInstance();
    Timestamp ts = new Timestamp(c.getTime().getTime());
    String curDateTime = String.valueOf(ts);
    curDateTime = curDateTime.substring(0, curDateTime.indexOf("."));
    return curDateTime;
  }
  
  public static String formatDate(Date date, String pattern)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(date);
  }
  
  public static String formatDate(String date, String pattern)
    throws Exception
  {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Date dt = sdf.parse(date);
    return sdf.format(date);
  }
  
  public static String formatTime(String time)
  {
    String ctime = time.substring(0, 2) + time.substring(3, 5) + 
      time.substring(6, 8);
    return ctime;
  }
  
  public static int getYear()
  {
    String curDate = getCurDate();
    int year = Integer.parseInt(curDate.substring(0, 4));
    return year;
  }
  
  public static int getMon()
  {
    String curDate = getCurDate();
    int mon = Integer.parseInt(curDate.substring(5, 7));
    
    return mon;
  }
  
  public static int getDd()
  {
    String curDate = getCurDate();
    int dd = Integer.parseInt(curDate.substring(8, 10));
    
    return dd;
  }
  
  public static int getWeekDay(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(7);
  }
  
  public static Timestamp GoSecond(Date date, int second)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(13, second);
    return new Timestamp(c.getTime().getTime());
  }
  
  public static void main(String[] args)
  {
    try
    {
      Date date = new Date();
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      System.out.println(cal.get(1));
      System.out.println(cal.get(2));
      System.out.println(cal.get(5));
      System.out.println(cal.get(10));
      System.out.println(cal.get(12));
      System.out.println(cal.get(13));
      System.out.println(cal.get(7));
      String s = "2010-03-11 14:26:20.999999999";
      Timestamp ts = Timestamp.valueOf(s);
      System.out.println("ts:" + ts);
      Date d = convertStringToDate("yyyy-MM-dd HH:mm:ss", 
        "2010-03-11 14:26:59");
      System.out.println("ts:" + GoSecond(d, -60));
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
}
