package gnnt.MEBS.vendue.server.bus;

import java.sql.Timestamp;

public abstract interface IBroadcast
{
  public abstract String getLastXML(Timestamp paramTimestamp);
}
