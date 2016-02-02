package gnnt.MEBS.timebargain.plugin.condition.model;

import java.util.Date;

public class SystemStatus
{
  private Date tradeDate;
  private Integer status;
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date paramDate)
  {
    this.tradeDate = paramDate;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public boolean equals(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof SystemStatus)) && (this.status != null) && (this.status.equals(((SystemStatus)paramObject).getStatus())) && (this.tradeDate != null) && (this.tradeDate.equals(((SystemStatus)paramObject).getTradeDate()));
  }
}
