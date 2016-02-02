package gnnt.MEBS.timebargain.server.model.quotation;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TradeTimeVO
{
  public String marketID;
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
    localStringBuffer.append("BeginTime:" + this.beginTime + str);
    localStringBuffer.append("EndTime:" + this.endTime + str);
    localStringBuffer.append("Status:" + this.status + str);
    localStringBuffer.append("Modifytime:" + this.modifytime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
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
  
  public static int GetTotalMinute(TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++) {
      if (paramArrayOfTradeTimeVO[j].endTime < paramArrayOfTradeTimeVO[j].beginTime) {
        i += paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 + 1440 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
      } else {
        i += paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
      }
    }
    return i;
  }
  
  public static int GetTimeFromIndex(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = paramInt + 1;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      int k = paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
      if (k < i)
      {
        i -= k;
      }
      else
      {
        int m = paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100 + i;
        m = m / 60 * 100 + m % 60;
        return m;
      }
    }
    return -1;
  }
  
  public static Calendar GetDateFromIndex(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = paramInt + 1;
    Calendar localCalendar = Calendar.getInstance();
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      Date localDate1 = int2Date(paramArrayOfTradeTimeVO[j].beginDate, paramArrayOfTradeTimeVO[j].beginTime);
      Date localDate2 = int2Date(paramArrayOfTradeTimeVO[j].endDate, paramArrayOfTradeTimeVO[j].endTime);
      long l1 = localDate2.getTime() - localDate1.getTime();
      long l2 = l1 / 1000L / 60L;
      if (l2 < i)
      {
        i = (int)(i - l2);
      }
      else
      {
        localCalendar.setTime(localDate1);
        localCalendar.add(12, i);
        return localCalendar;
      }
    }
    return localCalendar;
  }
  
  public static Date int2Date(int paramInt1, int paramInt2)
  {
    Date localDate = null;
    try
    {
      localDate = new SimpleDateFormat("yyyyMMddHHmm").parse("" + (paramInt1 * 10000L + paramInt2));
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return localDate;
  }
  
  public static int GetIndexFromTime(int paramInt, TradeTimeVO[] paramArrayOfTradeTimeVO)
  {
    int i = -1;
    for (int j = 0; j < paramArrayOfTradeTimeVO.length; j++)
    {
      if (paramInt < paramArrayOfTradeTimeVO[j].beginTime) {
        return i;
      }
      if ((paramArrayOfTradeTimeVO[j].endTime >= paramInt) && (paramInt >= paramArrayOfTradeTimeVO[j].beginTime))
      {
        i += paramInt / 100 * 60 + paramInt % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
        return i;
      }
      i += paramArrayOfTradeTimeVO[j].endTime / 100 * 60 + paramArrayOfTradeTimeVO[j].endTime % 100 - (paramArrayOfTradeTimeVO[j].beginTime / 100 * 60 + paramArrayOfTradeTimeVO[j].beginTime % 100);
    }
    if (i < 0) {
      i = 0;
    }
    return i;
  }
  
  public static String formatStr(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      String str = "";
      if (paramString.indexOf(":") != -1)
      {
        String[] arrayOfString = paramString.split(":");
        for (int i = 0; i < arrayOfString.length; i++) {
          str = str + arrayOfString[i];
        }
      }
      return str;
    }
    return null;
  }
  
  public static String formatStr2(String paramString)
  {
    String str = paramString.replaceAll("-", "");
    return str;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    TradeTimeVO[] arrayOfTradeTimeVO = new TradeTimeVO[1];
    arrayOfTradeTimeVO[0] = new TradeTimeVO();
    arrayOfTradeTimeVO[0].beginTime = 930;
    arrayOfTradeTimeVO[0].endTime = 1800;
    arrayOfTradeTimeVO[0].orderID = 0;
    System.out.println(GetIndexFromTime(931, arrayOfTradeTimeVO));
    System.out.println(GetTimeFromIndex(0, arrayOfTradeTimeVO));
  }
}
