package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class NotTradeDay
  extends Clone
{
  private Long id;
  private String week;
  private String day;
  private String weekForLog;
  
  @ClassDiscription(name="非交易日编号", key=true, keyWord=true)
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getWeek()
  {
    return this.week;
  }
  
  public void setWeek(String week)
  {
    this.week = week;
  }
  
  @ClassDiscription(name="日")
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String day)
  {
    this.day = day;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
  
  @ClassDiscription(name="星期")
  public String getWeekForLog()
  {
    String log = "";
    if (this.week != null)
    {
      String[] weeks = this.week.split(",");
      if ((weeks != null) && (weeks.length > 0)) {
        for (String week : weeks)
        {
          String weekStr = "";
          if (!"".equals(week)) {
            weekStr = Integer.parseInt(week) - 1;
          }
          if ("0".equals(weekStr)) {
            weekStr = "天";
          }
          log = log + "星期" + weekStr + ",";
        }
      }
      if (log.length() > 0) {
        log = log.substring(0, log.length() - 1);
      }
    }
    if ("星期".equals(log)) {
      log = "";
    }
    return log;
  }
  
  public void setWeekForLog(String weekForLog)
  {
    this.weekForLog = weekForLog;
  }
}
