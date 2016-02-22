package gnnt.MEBS.trade.model.vo;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;

public class NotTradeDayVO
  extends Clone
{
  private Long id;
  private String week;
  private String[] weeks;
  private String day;
  private int tradeTimeType;
  
  public Integer getTradeTimeType()
  {
    return Integer.valueOf(this.tradeTimeType);
  }
  
  public void setTradeTimeType(int tradeTimeType)
  {
    this.tradeTimeType = tradeTimeType;
  }
  
  @ClassDiscription(name="非交易日ID", key=true, keyWord=true)
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  @ClassDiscription(name="星期")
  public String[] getWeeks()
  {
    return this.weeks;
  }
  
  public void setWeeks(String[] weeks)
  {
    this.weeks = weeks;
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
  
  @ClassDiscription(name="星期")
  public String getWeek()
  {
    return this.week;
  }
  
  public void setWeek(String week)
  {
    this.week = week;
  }
  
  public void setPrimary(String primary)
  {
    this.id = Long.valueOf(Long.parseLong(primary));
  }
}
