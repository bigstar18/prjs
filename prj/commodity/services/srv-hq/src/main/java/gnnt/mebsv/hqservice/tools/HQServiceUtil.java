package gnnt.mebsv.hqservice.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HQServiceUtil
{
  public static Date getDate(int paramInt1, int paramInt2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(14, 0);
    int i = paramInt1 / 10000;
    int j = (paramInt1 - i * 10000) / 100;
    int k = paramInt1 - i * 10000 - j * 100;
    int m = paramInt2 / 10000;
    int n = (paramInt2 - m * 10000) / 100;
    int i1 = paramInt2 - m * 10000 - n * 100;
    localCalendar.set(i, j - 1, k, m, n, i1);
    return localCalendar.getTime();
  }

  public static int dateToHHmmss(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(11) * 10000 + localCalendar.get(12) * 100 + localCalendar.get(13);
  }

  public static long dateToLong(Date paramDate)
  {
    String str = new SimpleDateFormat("yyyyMMddHHmmss").format(paramDate);
    return Long.parseLong(str);
  }
}