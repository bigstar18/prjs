package cn.com.agree.eteller.generic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserInfoMap
{
  public static HashMap map = new HashMap();
  private static final Log logger = LogFactory.getLog(UserInfoMap.class);
  
  public static HashMap getMap()
  {
    return map;
  }
  
  public static void setMap(HashMap map)
  {
    map = map;
  }
  
  public static String analyzeMap(String key, int position)
  {
    if (getMap().containsKey(key))
    {
      String[] value = getMap().get(key).toString().split("_");
      
      return value[position];
    }
    return null;
  }
  
  public static String[] analyzeMap(String key)
  {
    if (getMap().containsKey(key))
    {
      String[] value = getMap().get(key).toString().split("_");
      
      return value;
    }
    return null;
  }
  
  public static String getCurrentDateTimebysep()
  {
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    String SingleDate = sf.format(new Date());
    return SingleDate;
  }
  
  public static long getTimeCHA(String Ldate, String Sdate)
  {
    Date date1 = new Date();
    Date date2 = new Date();
    try
    {
      date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(Ldate);
      date2 = new SimpleDateFormat("yyyyMMddHHmmss").parse(Sdate);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    long l = (date1.getTime() - date2.getTime()) / 1000L;
    logger.info("JSP定时签退功能线程检测：判断时间差为 " + l + "秒");
    return l;
  }
  
  public static void PrintJSPLog(String tmp)
  {
    logger.info(tmp);
  }
  
  public static boolean existKey(String key)
  {
    if (getMap().containsKey(key)) {
      return true;
    }
    return false;
  }
}
