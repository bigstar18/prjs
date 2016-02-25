package gnnt.MEBS.vendue.server.bus;

import java.sql.Timestamp;

public class BroadcastService
  implements IBroadcast
{
  public String getLastXML(Timestamp paramTimestamp)
  {
    BroadcastCacheService localBroadcastCacheService = new BroadcastCacheService();
    return localBroadcastCacheService.getLastXML(paramTimestamp);
  }
}
