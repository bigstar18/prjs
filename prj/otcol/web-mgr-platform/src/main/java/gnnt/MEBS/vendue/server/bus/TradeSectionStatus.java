package gnnt.MEBS.vendue.server.bus;

public abstract interface TradeSectionStatus
{
  public static final int READY = 1;
  public static final int TRADING = 2;
  public static final int RELAXING = 3;
  public static final int PAUSE = 4;
  public static final int CLOSED = 5;
  public static final int END = 6;
  public static final int OPENBYHAND = 7;
}
