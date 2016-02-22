package gnnt.MEBS.trade.model.vo;

public class BarginCalendarVO
{
  private String date;
  private String day;
  private int status = 2;
  private int week;
  private boolean isToday = false;
  private int month;
  private String year;
  
  public Integer getMonth()
  {
    return Integer.valueOf(this.month);
  }
  
  public void setMonth(int month)
  {
    this.month = month;
  }
  
  public String getYear()
  {
    return this.year;
  }
  
  public void setYear(String year)
  {
    this.year = year;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String day)
  {
    this.day = day;
  }
  
  public Integer getStatus()
  {
    return Integer.valueOf(this.status);
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Integer getWeek()
  {
    return Integer.valueOf(this.week);
  }
  
  public void setWeek(int week)
  {
    this.week = week;
  }
  
  public Boolean getIsToday()
  {
    return Boolean.valueOf(this.isToday);
  }
  
  public void setToday(boolean isToday)
  {
    this.isToday = isToday;
  }
}
