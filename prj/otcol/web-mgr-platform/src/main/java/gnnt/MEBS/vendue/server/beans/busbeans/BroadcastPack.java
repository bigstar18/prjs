package gnnt.MEBS.vendue.server.beans.busbeans;

import gnnt.MEBS.vendue.server.beans.dtobeans.Broadcast;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BroadcastPack
  implements Cloneable
{
  private List broadCastList = null;
  private BroadcastHighBuffer broadcastHighBuffer = new BroadcastHighBuffer();
  private Timestamp newestCheckTime;
  
  public void setBroadcastHighBuffer(BroadcastHighBuffer paramBroadcastHighBuffer)
  {
    this.broadcastHighBuffer = paramBroadcastHighBuffer;
  }
  
  public void setBroadCastList(List paramList)
  {
    this.broadCastList = paramList;
  }
  
  public void setNewestCheckTime(Timestamp paramTimestamp)
  {
    this.newestCheckTime = paramTimestamp;
  }
  
  public BroadcastHighBuffer getBroadcastHighBuffer()
  {
    return this.broadcastHighBuffer;
  }
  
  public Timestamp getNewestCheckTime()
  {
    return this.newestCheckTime;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    BroadcastPack localBroadcastPack = (BroadcastPack)super.clone();
    localBroadcastPack.broadCastList = ((ArrayList)((ArrayList)this.broadCastList).clone());
    for (int i = 0; i < localBroadcastPack.broadCastList.size(); i++)
    {
      Broadcast localBroadcast = (Broadcast)localBroadcastPack.broadCastList.get(i);
      localBroadcastPack.broadCastList.set(i, localBroadcast.clone());
    }
    localBroadcastPack.broadcastHighBuffer = ((BroadcastHighBuffer)this.broadcastHighBuffer.clone());
    return localBroadcastPack;
  }
  
  public List getBroadCastList()
  {
    return this.broadCastList;
  }
}
