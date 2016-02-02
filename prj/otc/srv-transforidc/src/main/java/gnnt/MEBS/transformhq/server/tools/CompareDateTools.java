package gnnt.MEBS.transformhq.server.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompareDateTools
{
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
  
  public static boolean compareDate(Date date, String beginTime, String endTime)
  {
    long timeNow = Long.valueOf(dateFormat.format(date)).longValue();
    if ((formatDate(beginTime) <= timeNow) && (timeNow <= formatDate(endTime))) {
      return true;
    }
    return false;
  }
  
  public static boolean compareDate(String beginTime, String endTime)
  {
    long timeNow = Long.valueOf(dateFormat.format(new Date())).longValue();
    if ((formatDate(beginTime) <= timeNow) && (timeNow <= formatDate(endTime))) {
      return true;
    }
    return false;
  }
  
  public static long formatDate(String time)
  {
    return Long.valueOf(time.replace(":", "")).longValue();
  }
}
