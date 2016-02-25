package gnnt.MEBS.vendue.server.beans.dtobeans;

import java.sql.Timestamp;

public class SysCurStatus
{
  private Long tradePartition;
  private Long status;
  private Long section;
  private Timestamp modifyTime;
  private Timestamp startTime;
  private Timestamp endTime;
  
  public Timestamp getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(Timestamp paramTimestamp)
  {
    this.endTime = paramTimestamp;
  }
  
  public Timestamp getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Timestamp paramTimestamp)
  {
    this.modifyTime = paramTimestamp;
  }
  
  public Long getSection()
  {
    return this.section;
  }
  
  public void setSection(Long paramLong)
  {
    this.section = paramLong;
  }
  
  public Timestamp getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(Timestamp paramTimestamp)
  {
    this.startTime = paramTimestamp;
  }
  
  public Long getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Long paramLong)
  {
    this.status = paramLong;
  }
  
  public Long getTradePartition()
  {
    return this.tradePartition;
  }
  
  public void setTradePartition(Long paramLong)
  {
    this.tradePartition = paramLong;
  }
}
