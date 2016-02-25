package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class TradeStatusValueImpl
  implements TradeStatusValue
{
  private static final long serialVersionUID = 1000L;
  public int curStatus = 5;
  public int lastPartID = 1;
  public int nextPartID;
  public Timestamp partStartTime;
  public Timestamp partEndTime;
  public Timestamp partLastEndTime;
  public boolean loaded = false;
  public int partitionID;
  public long durativeTime;
  public long spaceTime;
  public int countdownStart;
  public int countdownTime;
  public boolean isMaxTradeOrder = false;
  
  public int getCurStatus()
  {
    return this.curStatus;
  }
  
  public int getLastPartID()
  {
    return this.lastPartID;
  }
  
  public int getNextPartID()
  {
    return this.nextPartID;
  }
  
  public Timestamp getPartStartTime()
  {
    return this.partStartTime;
  }
  
  public Timestamp getPartEndTime()
  {
    return this.partEndTime;
  }
  
  public Timestamp getPartLastEndTime()
  {
    return this.partLastEndTime;
  }
  
  public boolean getLoaded()
  {
    return this.loaded;
  }
  
  public int getPartitionID()
  {
    return this.partitionID;
  }
  
  public long getDurativeTime()
  {
    return this.durativeTime;
  }
  
  public long getSpaceTime()
  {
    return this.spaceTime;
  }
  
  public int getCountdownStart()
  {
    return this.countdownStart;
  }
  
  public int getCountdownTime()
  {
    return this.countdownTime;
  }
  
  public boolean getIsMaxTradeOrder()
  {
    return this.isMaxTradeOrder;
  }
  
  public void setCurStatus(int paramInt)
  {
    this.curStatus = paramInt;
  }
  
  public void setLastPartID(int paramInt)
  {
    this.lastPartID = paramInt;
  }
  
  public void setNextPartID(int paramInt)
  {
    this.nextPartID = paramInt;
  }
  
  public void setPartStartTime(Timestamp paramTimestamp)
  {
    this.partStartTime = paramTimestamp;
  }
  
  public void setPartEndTime(Timestamp paramTimestamp)
  {
    this.partEndTime = paramTimestamp;
  }
  
  public void setPartLastEndTime(Timestamp paramTimestamp)
  {
    this.partLastEndTime = paramTimestamp;
  }
  
  public void setLoaded(boolean paramBoolean)
  {
    this.loaded = paramBoolean;
  }
  
  public void setPartitionID(int paramInt)
  {
    this.partitionID = paramInt;
  }
  
  public void setDurativeTime(long paramLong)
  {
    this.durativeTime = paramLong;
  }
  
  public void setSpaceTime(long paramLong)
  {
    this.spaceTime = paramLong;
  }
  
  public void setCountdownStart(int paramInt)
  {
    this.countdownStart = paramInt;
  }
  
  public void setCountdownTime(int paramInt)
  {
    this.countdownTime = paramInt;
  }
  
  public void setIsMaxTradeOrder(boolean paramBoolean)
  {
    this.isMaxTradeOrder = paramBoolean;
  }
}
