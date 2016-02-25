package gnnt.MEBS.vendue.server.beans.busbeans;

import java.io.PrintStream;

public class ShareObjectOfBroadcast
{
  private BroadcastPack broadcastPack = new BroadcastPack();
  private static ShareObjectOfBroadcast singleInstance = new ShareObjectOfBroadcast();
  
  private ShareObjectOfBroadcast()
  {
    System.out.println("===============hello, 共享区单例广播对象初始化===========");
  }
  
  public static ShareObjectOfBroadcast getSingleInstance()
  {
    return singleInstance;
  }
  
  public synchronized BroadcastPack getBroadcastPack()
  {
    return this.broadcastPack;
  }
  
  public synchronized void setBroadcastPack(BroadcastPack paramBroadcastPack)
  {
    this.broadcastPack = paramBroadcastPack;
  }
}
