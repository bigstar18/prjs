package gnnt.MEBS.bill.core.util;

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
    Locale localLocale = LocaleContextHolder.getLocale();
    try
    {
      defaultDatePattern = ResourceBundle.getBundle("RESOURCE", localLocale).getString("date.format");
    }
    catch (MissingResourceException localMissingResourceException)
    {
      defaultDatePattern = "yyyy-MM-dd";
    }
    return defaultDatePattern;
  }
  
  public static final String getDate(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = null;
    String str = "";
    if (paramDate != null)
    {
      localSimpleDateFormat = new SimpleDateFormat(getDatePattern());
      str = localSimpleDateFormat.format(paramDate);
    }
    return str;
  }
  
  public static final Date convertStringToDate(String paramString1, String paramString2)
    throws ParseException
  {
    SimpleDateFormat localSimpleDateFormat = null;
    Date localDate = null;
    localSimpleDateFormat = new SimpleDateFormat(paramString1);
    try
    {
      localDate = localSimpleDateFormat.parse(paramString2);
    }
    catch (ParseException localParseException)
    {
      throw new ParseException(localParseException.getMessage(), localParseException.getErrorOffset());
    }
    return localDate;
  }
  
  public static String getTimeNow(Date paramDate)
  {
    return getDateTime(timePattern, paramDate);
  }
  
  public static Calendar getToday()
    throws ParseException
  {
    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(getDatePattern());
    String str = localSimpleDateFormat.format(localDate);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.setTime(convertStringToDate(str));
    return localGregorianCalendar;
  }
  
  public static final String getDateTime(String paramString, Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = null;
    String str = "";
    if (paramDate == null)
    {
      log.error("aDate is null!");
    }
    else
    {
      localSimpleDateFormat = new SimpleDateFormat(paramString);
      str = localSimpleDateFormat.format(paramDate);
    }
    return str;
  }
  
  public static final String convertDateToString(Date paramDate)
  {
    return getDateTime(getDatePattern(), paramDate);
  }
  
  public static Date convertStringToDate(String paramString)
    throws ParseException
  {
    Date localDate = null;
    try
    {
      if (log.isDebugEnabled()) {
        log.debug("converting date with pattern: " + getDatePattern());
      }
      localDate = convertStringToDate(getDatePattern(), paramString);
    }
    catch (ParseException localParseException)
    {
      log.error("Could not convert '" + paramString + "' to a date, throwing exception");
      localParseException.printStackTrace();
      throw new ParseException(localParseException.getMessage(), localParseException.getErrorOffset());
    }
    return localDate;
  }
  
  public static Date Str2Date(String paramString1, String paramString2, String paramString3)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(Integer.parseInt(paramString1), Integer.parseInt(paramString2) - 1, Integer.parseInt(paramString3), 0, 0, 0);
    localCalendar.set(14, 0);
    return localCalendar.getTime();
  }
  
  public static Date Str2Date(String paramString)
  {
    if ((paramString != null) && (!paramString.trim().equals("")))
    {
      if (paramString.indexOf("-") != -1)
      {
        String[] arrayOfString = paramString.split("-");
        return Str2Date(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
      }
      return Str2Date(paramString.substring(0, 4), paramString.substring(4, 6), paramString.substring(6));
    }
    return new Date();
  }
  
  public static Date GoMonth(Date paramDate, int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.add(2, paramInt);
    return localCalendar.getTime();
  }
  
  public static Date GoDate(Date paramDate, int paramInt)
  {
    long l = paramDate.getTime();
    l += paramInt * 24L * 60L * 60L * 1000L;
    return new Date(l);
  }
  
  public static String getCurDate()
  {
    Calendar localCalendar = Calendar.getInstance();
    Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
    String str = String.valueOf(localTimestamp);
    str = str.substring(0, str.indexOf(" "));
    return str;
  }
  
  public static String getCurTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
    String str = String.valueOf(localTimestamp);
    str = str.substring(11, str.indexOf("."));
    return str;
  }
  
  public static String getCurDateTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
    String str = String.valueOf(localTimestamp);
    str = str.substring(0, str.indexOf("."));
    return str;
  }
  
  public static String formatDate(Date paramDate, String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(paramDate);
  }
  
  public static String formatDate(String paramString1, String paramString2)
    throws Exception
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    return localSimpleDateFormat.format(paramString1);
  }
  
  public static String formatTime(String paramString)
  {
    String str = paramString.substring(0, 2) + paramString.substring(3, 5) + paramString.substring(6, 8);
    return str;
  }
  
  public static int getYear()
  {
    String str = getCurDate();
    int i = Integer.parseInt(str.substring(0, 4));
    return i;
  }
  
  public static int getMon()
  {
    String str = getCurDate();
    int i = Integer.parseInt(str.substring(5, 7));
    return i;
  }
  
  public static int getDd()
  {
    String str = getCurDate();
    int i = Integer.parseInt(str.substring(8, 10));
    return i;
  }
  
  public static int getWeekDay(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(7);
  }
  
  public static Timestamp GoSecond(Date paramDate, int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.add(13, paramInt);
    return new Timestamp(localCalendar.getTime().getTime());
  }
  
  public static boolean isValidDate(String paramString1, String paramString2)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
      localSimpleDateFormat.parse(paramString1);
      return true;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public static boolean isValidTime(String paramString1, String paramString2)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
      localSimpleDateFormat.parse(paramString1);
      return true;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    try
    {
      System.out.println(isValidDate("2012-10-10", "yyyy-MM-dd"));
      System.out.println(isValidTime("00:30:00", "HH:mm:ss"));
      String str = "2010-03-11 14:26:20.999999999";
      Timestamp localTimestamp = Timestamp.valueOf(str);
      System.out.println("ts:" + localTimestamp);
      Date localDate = convertStringToDate("yyyy-MM-dd HH:mm:ss", "2010-03-11 14:26:59");
      System.out.println("ts:" + GoSecond(localDate, -60));
    }
    catch (Exception localException)
    {
      System.out.println(localException);
    }
  }
}
