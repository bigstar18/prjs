package gnnt.mebsv.hqtrans.tools;

import java.util.Calendar;
import java.util.Date;

public class TransDate
{
  private static TransDate instance;
  
  public static TransDate getInstance()
  {
    if (instance == null) {
      instance = new TransDate();
    }
    return instance;
  }
  
  public int dateToyyMMdd(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return (localCalendar.get(1) - 1997) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
  }
  
  public int dateToyyMMddHHmm(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return (localCalendar.get(1) - 1997) * 100000000 + (localCalendar.get(2) + 1) * 1000000 + localCalendar.get(5) * 10000 + localCalendar.get(11) * 100 + localCalendar.get(12);
  }
  
  public int dateToyyyyMMdd(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
  }
  
  public Date yyyyMMddToDate(int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(paramInt / 10000, paramInt / 100 % 100 - 1, paramInt % 100, 0, 0, 0);
    localCalendar.set(14, 0);
    return localCalendar.getTime();
  }
  
  public Date yyyyMMddHHmmToDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() != 12)) {
      return new Date(0L);
    }
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(Integer.parseInt(paramString.substring(0, 4)), Integer.parseInt(paramString.substring(4, 6)) - 1, Integer.parseInt(paramString.substring(6, 8)), Integer.parseInt(paramString.substring(8, 10)), Integer.parseInt(paramString.substring(10, 12)), 0);
    localCalendar.set(14, 0);
    return localCalendar.getTime();
  }
}
