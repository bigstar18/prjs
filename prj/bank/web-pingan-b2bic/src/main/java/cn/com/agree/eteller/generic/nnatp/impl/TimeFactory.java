package cn.com.agree.eteller.generic.nnatp.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFactory
{
  public static String getCurrentTime()
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss ");
    Date dateTime = new Date();
    String time = dateFormat.format(dateTime);
    return time;
  }
}
