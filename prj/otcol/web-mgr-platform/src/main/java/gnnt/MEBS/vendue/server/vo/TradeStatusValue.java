package gnnt.MEBS.vendue.server.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract interface TradeStatusValue
  extends Serializable
{
  public abstract int getCurStatus();
  
  public abstract int getLastPartID();
  
  public abstract int getNextPartID();
  
  public abstract Timestamp getPartStartTime();
  
  public abstract Timestamp getPartEndTime();
  
  public abstract Timestamp getPartLastEndTime();
  
  public abstract boolean getLoaded();
  
  public abstract int getPartitionID();
  
  public abstract long getDurativeTime();
  
  public abstract long getSpaceTime();
  
  public abstract int getCountdownStart();
  
  public abstract int getCountdownTime();
  
  public abstract boolean getIsMaxTradeOrder();
  
  public abstract void setCurStatus(int paramInt);
  
  public abstract void setLastPartID(int paramInt);
  
  public abstract void setNextPartID(int paramInt);
  
  public abstract void setPartStartTime(Timestamp paramTimestamp);
  
  public abstract void setPartEndTime(Timestamp paramTimestamp);
  
  public abstract void setPartLastEndTime(Timestamp paramTimestamp);
  
  public abstract void setLoaded(boolean paramBoolean);
  
  public abstract void setPartitionID(int paramInt);
  
  public abstract void setDurativeTime(long paramLong);
  
  public abstract void setSpaceTime(long paramLong);
  
  public abstract void setCountdownStart(int paramInt);
  
  public abstract void setCountdownTime(int paramInt);
  
  public abstract void setIsMaxTradeOrder(boolean paramBoolean);
}
