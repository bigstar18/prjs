package cn.com.agree.eteller.generic.utils;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DateUtil
{
  public static String getIndexDate()
  {
    Calendar c = Calendar.getInstance();
    Date date = new Date();
    c.setTime(date);
    
    String day = null;
    switch (c.get(7))
    {
    case 1: 
      day = "日"; break;
    case 2: 
      day = "一"; break;
    case 3: 
      day = "二"; break;
    case 4: 
      day = "三"; break;
    case 5: 
      day = "四"; break;
    case 6: 
      day = "五"; break;
    case 7: 
      day = "六";
    }
    return new SimpleDateFormat("yyyy年MM月dd日").format(date) + "\t星期" + day;
  }
  
  public static List<String> getDateStrByStartAndEnd(String startday, String endday)
  {
    List<String> dateStrList = new LinkedList();
    try
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      
      Calendar startDay = Calendar.getInstance();
      startDay.setTime(sdf.parse(startday));
      
      Calendar endDay = Calendar.getInstance();
      endDay.setTime(sdf.parse(endday));
      while (startDay.compareTo(endDay) <= 0)
      {
        int dayOfWeek = startDay.get(7);
        boolean isRestDay = (dayOfWeek == 7) || (dayOfWeek == 1);
        if (!isRestDay) {
          dateStrList.add(sdf.format(startDay.getTime()));
        }
        startDay.add(5, 1);
      }
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return dateStrList;
  }
  
  public static void main(String[] args)
  {
    Calendar c = Calendar.getInstance();
    System.out.println(c.get(7));
    System.out.println(c.get(7) == 4);
  }
}
