package cn.com.agree.eteller.generic.pub;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
  public static int getCurrentWeek(String date, int xh)
  {
    int weekxh = 0;
    String[] firstzone = getWeek(date.substring(0, 4), 0, xh);
    String[] lastzone = getWeek(date.substring(0, 4), 52, xh);
    if ((Integer.parseInt(firstzone[0]) <= Integer.parseInt(date)) && (Integer.parseInt(date) <= Integer.parseInt(firstzone[(firstzone.length - 1)])))
    {
      weekxh = 0;
    }
    else if ((Integer.parseInt(lastzone[0]) <= Integer.parseInt(date)) && (Integer.parseInt(date) <= Integer.parseInt(lastzone[(lastzone.length - 1)])))
    {
      weekxh = 52;
    }
    else
    {
      weekxh = 1;
      firstzone = getWeek(date.substring(0, 4), 1, xh);
      String[] datezone = getDateByinterval(firstzone[(firstzone.length - 1)], date);
      if ((datezone != null) && (datezone.length > 0))
      {
        weekxh = 1 + datezone.length / 7;
        if (datezone.length % 7 > 0) {
          weekxh++;
        }
      }
    }
    return weekxh;
  }
  
  public static String getNextDay(String startdate, int n)
  {
    String day = "";
    if (startdate == null) {
      return day;
    }
    startdate = startdate.replaceAll(" ", "");
    if (startdate.length() < 8) {
      return day;
    }
    try
    {
      GregorianCalendar gc = new GregorianCalendar();
      gc.set(1, Integer.parseInt(startdate.substring(0, 4)));
      gc.set(2, Integer.parseInt(startdate.substring(4, 6)) - 1);
      gc.set(5, Integer.parseInt(startdate.substring(6)));
      long d = 86400000L;
      long gclong = gc.getTime().getTime();
      long daylong = gclong + d * n;
      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
      day = sf.format(new Date(daylong));
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return day;
  }
  
  public static String getNextWeekDay(String startdate, int xh)
  {
    String weekday = "";
    if (startdate == null) {
      return weekday;
    }
    startdate = startdate.replaceAll(" ", "");
    if (startdate.length() < 8) {
      return weekday;
    }
    try
    {
      for (int i = 1; i < 8; i++)
      {
        String nextWeekDay = getNextDay(startdate, i);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(1, Integer.parseInt(nextWeekDay.substring(0, 4)));
        gc.set(2, Integer.parseInt(nextWeekDay.substring(4, 6)) - 1);
        gc.set(5, Integer.parseInt(nextWeekDay.substring(6)));
        int week = gc.get(7);
        if (week == xh)
        {
          weekday = nextWeekDay;
          break;
        }
      }
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return weekday;
  }
  
  public static String getLastWeekDay(String enddate, int xh)
  {
    String weekday = "";
    if (enddate == null) {
      return weekday;
    }
    enddate = enddate.replaceAll(" ", "");
    if (enddate.length() < 8) {
      return weekday;
    }
    try
    {
      for (int i = 1; i < 8; i++)
      {
        String nextWeekDay = getNextDay(enddate, -i);
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(1, Integer.parseInt(nextWeekDay.substring(0, 4)));
        gc.set(2, Integer.parseInt(nextWeekDay.substring(4, 6)) - 1);
        gc.set(5, Integer.parseInt(nextWeekDay.substring(6)));
        int week = gc.get(7);
        if (week == xh)
        {
          weekday = nextWeekDay;
          break;
        }
      }
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return weekday;
  }
  
  public static String[] getWeek(String year, int weekxh, int xh)
  {
    String yearfirstdate = "0101";
    String yearlastdate = "1231";
    
    String firstday = "";
    String lastday = "";
    if (weekxh == 0)
    {
      firstday = year + yearfirstdate;
      lastday = getNextWeekDay(firstday, xh);
    }
    else if (weekxh == 52)
    {
      lastday = year + yearlastdate;
      firstday = getLastWeekDay(lastday, xh + 1);
    }
    else
    {
      String startday = getNextWeekDay(year + yearfirstdate, xh + 1);
      
      int sep = weekxh - 1;
      firstday = getNextDay(startday, sep * 7);
      
      lastday = getNextWeekDay(firstday, xh);
    }
    String[] weekzone = getDateByinterval(firstday, lastday);
    return weekzone;
  }
  
  public static String[] getDateByinterval(String startdate, String enddate)
  {
    if ((startdate == null) || (enddate == null)) {
      return null;
    }
    startdate = startdate.replaceAll(" ", "");
    enddate = enddate.replaceAll(" ", "");
    if ((startdate.length() < 8) || (enddate.length() < 8)) {
      return null;
    }
    String[] date = (String[])null;
    try
    {
      GregorianCalendar startgc = new GregorianCalendar();
      startgc.set(1, Integer.parseInt(startdate.substring(0, 4)));
      startgc.set(2, Integer.parseInt(startdate.substring(4, 6)) - 1);
      startgc.set(5, Integer.parseInt(startdate.substring(6)));
      GregorianCalendar endgc = new GregorianCalendar();
      endgc.set(1, Integer.parseInt(enddate.substring(0, 4)));
      endgc.set(2, Integer.parseInt(enddate.substring(4, 6)) - 1);
      endgc.set(5, Integer.parseInt(enddate.substring(6)));
      
      long startlong = startgc.getTime().getTime();
      long endlong = endgc.getTime().getTime();
      long sec = endlong - startlong;
      if (sec < 0L) {
        return null;
      }
      long k = sec / 86400000L;
      long p = sec % 86400000L;
      if (p != 0L) {
        k += 1L;
      }
      date = new String[(int)k + 1];
      date[0] = startdate;
      long dateL = startlong;
      for (int i = 1; i < date.length; i++)
      {
        dateL += 86400000L;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        date[i] = sf.format(new Date(dateL));
      }
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return date;
  }
  
  public static String getPrevWeekDate(String dateStr, int day)
  {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      c.setTime(sdf.parse(dateStr));
      c.add(3, -1);
      c.set(7, day + 1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return sdf.format(c.getTime());
  }
  
  public static String getPrevWeekDateFromNow(int day)
  {
    return getPrevWeekDate(
      new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
      day);
  }
  
  public static void main(String[] args)
  {
    String s = getPrevWeekDate("2012-05-13", 3);
    System.out.println(s);
  }
}
