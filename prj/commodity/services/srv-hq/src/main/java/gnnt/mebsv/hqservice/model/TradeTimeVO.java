package gnnt.mebsv.hqservice.model;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeTimeVO
{
  private static Log log = LogFactory.getLog(TradeTimeVO.class);
  public String marektID;
  public int orderID;
  public int beginDate;
  public int beginTime;
  public int endDate;
  public int endTime;
  public int status;
  public int tradeDate;
  public Date modifytime = new Date();

  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("OrderID:" + this.orderID + str);
    localStringBuffer.append("beginDate:" + this.beginDate + str);
    localStringBuffer.append("BeginTime:" + this.beginTime + str);
    localStringBuffer.append("endDate:" + this.endDate + str);
    localStringBuffer.append("EndTime:" + this.endTime + str);
    localStringBuffer.append("Status:" + this.status + str);
    localStringBuffer.append("Modifytime:" + this.modifytime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }

  public static String HHMMSSIntToString(int paramInt)
  {
    String str = "";
    for (str = paramInt + ""; str.length() < 6; str = "0" + str);
    str = str.substring(0, 2) + ":" + str.substring(2, 2) + ":" + str.substring(4);
    return str;
  }

  public static String HHMMIntToString(int paramInt)
  {
    String str = "";
    for (str = paramInt + ""; str.length() < 4; str = "0" + str);
    str = str.substring(0, 2) + ":" + str.substring(2);
    return str;
  }

  public static String timeIntToString(int paramInt)
  {
    return paramInt / 100 + ":" + paramInt % 100;
  }

  public static int timeStringToInt(String paramString)
  {
    paramString.replaceAll(":", "");
    return Integer.parseInt(paramString);
  }

  public static Calendar HHmmToDateTime(int paramInt1, int paramInt2)
  {
    String str1 = "";
    String str2 = "";
    for (str1 = paramInt1 + ""; str1.length() < 6; str1 = "0" + str1);
    for (str2 = paramInt2 + ""; str2.length() < 4; str2 = "0" + str2);
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    try
    {
      localCalendar.setTime(localSimpleDateFormat.parse(str1 + str2));
      return localCalendar;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static Calendar HHmmssToDateTime(int paramInt1, int paramInt2)
  {
    String str1 = "";
    String str2 = "";
    for (str1 = paramInt1 + ""; str1.length() < 6; str1 = "0" + str1);
    for (str2 = paramInt2 + ""; str2.length() < 6; str2 = "0" + str2);
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    try
    {
      localCalendar.setTime(localSimpleDateFormat.parse(str1 + str2));
      return localCalendar;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static int GetTotalMinute(TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      Calendar localCalendar1 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].beginDate, paramArrayOfTradeTimeVO[j].beginTime);
      Calendar localCalendar2 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].endDate, paramArrayOfTradeTimeVO[j].endTime);
      long l = localCalendar2.getTimeInMillis() - localCalendar1.getTimeInMillis();
      i = (int)(i + l / 60000L);
    }
    return i;
  }

  public static int GetTimeFromIndex(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = paramInt + 1;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      Calendar localCalendar1 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].beginDate, paramArrayOfTradeTimeVO[j].beginTime);
      Calendar localCalendar2 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].endDate, paramArrayOfTradeTimeVO[j].endTime);
      long l = localCalendar2.getTimeInMillis() - localCalendar1.getTimeInMillis();
      int k = (int)l / 60000;
      if (k < i)
      {
        i -= k;
      }
      else
      {
        localCalendar1.add(12, i);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HHmm");
        return timeStringToInt(localSimpleDateFormat.format(localCalendar1.getTime()));
      }
    }
    return -1;
  }

  public static Calendar GetDateTimeFromIndex(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    int i = paramInt + 1;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      Calendar localCalendar2 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].beginDate, paramArrayOfTradeTimeVO[j].beginTime);
      Calendar localCalendar3 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].endDate, paramArrayOfTradeTimeVO[j].endTime);
      long l = localCalendar3.getTimeInMillis() - localCalendar2.getTimeInMillis();
      int k = (int)l / 60000;
      if (k < i)
      {
        i -= k;
      }
      else
      {
        localCalendar2.add(12, i);
        return localCalendar2;
      }
    }
    return localCalendar1;
  }

  public static int GetIndexFromTime(int paramInt1, int paramInt2, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = -1;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      Calendar localCalendar1 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].beginDate, paramArrayOfTradeTimeVO[j].beginTime);
      Calendar localCalendar2 = HHmmToDateTime(paramArrayOfTradeTimeVO[j].endDate, paramArrayOfTradeTimeVO[j].endTime);
      Calendar localCalendar3 = HHmmToDateTime(paramInt1, paramInt2);
      long l = localCalendar1.getTimeInMillis() - localCalendar3.getTimeInMillis();
      if (l > 0L)
        return i;
      if ((localCalendar2.getTimeInMillis() - localCalendar3.getTimeInMillis() >= 0L) && (localCalendar1.getTimeInMillis() - localCalendar3.getTimeInMillis() <= 0L))
      {
        i += (int)(localCalendar3.getTimeInMillis() - localCalendar1.getTimeInMillis()) / 60000;
        break;
      }
      i += (int)(localCalendar2.getTimeInMillis() - localCalendar1.getTimeInMillis()) / 60000;
    }
    if (i < 0)
      i = 0;
    return i;
  }

  public static int GetIndexFromTime(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = -1;
    if (paramArrayOfTradeTimeVO != null)
      for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
      {
        if (paramInt < paramArrayOfTradeTimeVO[j].beginTime)
          return i;
        if ((paramArrayOfTradeTimeVO[j].endTime >= paramInt) && (paramInt >= paramArrayOfTradeTimeVO[j].beginTime))
          i += paramInt / 100 * 60 + paramInt % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
        else
          i += paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
      }
    if (i < 0)
      i = 0;
    return i;
  }

  public static int GetIndexFromTimeAndYear(int paramInt1, int paramInt2, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = -1;
    if (paramArrayOfTradeTimeVO != null)
      for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
      {
        if ((paramInt1 == paramArrayOfTradeTimeVO[j].beginDate) && (paramInt2 < paramArrayOfTradeTimeVO[j].beginTime))
          return i;
        if (paramArrayOfTradeTimeVO[j].endDate > paramArrayOfTradeTimeVO[j].beginDate)
        {
          int k = 1440 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
          if (paramInt1 > paramArrayOfTradeTimeVO[j].beginDate)
          {
            if (paramInt2 < paramArrayOfTradeTimeVO[j].endTime)
              i += k + (paramInt2 / 100 * 60 + paramInt2 % 100);
            else
              i += k + (paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100);
          }
          else
            i += paramInt2 / 100 * 60 + paramInt2 % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
        }
        else if ((paramArrayOfTradeTimeVO[j].endTime >= paramInt2) && (paramInt2 >= paramArrayOfTradeTimeVO[j].beginTime))
        {
          i += paramInt2 / 100 * 60 + paramInt2 % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
        }
        else
        {
          i += paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
        }
      }
    if (i < 0)
      i = 0;
    return i;
  }

  public static void main(String[] paramArrayOfString)
  {
    TradeTimeVO[] arrayOfTradeTimeVO = new TradeTimeVO[1];
    arrayOfTradeTimeVO[0] = new TradeTimeVO();
    arrayOfTradeTimeVO[0].beginTime = 930;
    arrayOfTradeTimeVO[0].endTime = 1800;
    arrayOfTradeTimeVO[0].orderID = 0;
    System.out.println(GetTimeFromIndex(0, arrayOfTradeTimeVO));
  }
}