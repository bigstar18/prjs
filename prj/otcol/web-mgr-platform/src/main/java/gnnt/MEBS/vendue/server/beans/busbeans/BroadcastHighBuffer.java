package gnnt.MEBS.vendue.server.beans.busbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BroadcastHighBuffer
  implements Cloneable
{
  private List highBufferCheckTimeList = null;
  private Map highBufferBroadcastMap = null;
  
  public Map getHighBufferBroadcastMap()
  {
    return this.highBufferBroadcastMap;
  }
  
  public void setHighBufferBroadcastMap(Map paramMap)
  {
    this.highBufferBroadcastMap = paramMap;
  }
  
  public List getHighBufferCheckTimeList()
  {
    return this.highBufferCheckTimeList;
  }
  
  public void setHighBufferCheckTimeList(List paramList)
  {
    this.highBufferCheckTimeList = paramList;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    BroadcastHighBuffer localBroadcastHighBuffer = (BroadcastHighBuffer)super.clone();
    localBroadcastHighBuffer.highBufferCheckTimeList = ((ArrayList)((ArrayList)this.highBufferCheckTimeList).clone());
    return localBroadcastHighBuffer;
  }
}
