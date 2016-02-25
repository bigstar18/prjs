package gnnt.MEBS.vendue.server.vo;

public class SysPropertyVO
{
  public int tradePartition;
  public long durativeTime;
  public long spaceTime;
  public String countdownStart;
  public String countdownTime;
  public int optMode;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("durativeTime:" + this.durativeTime + str);
    localStringBuffer.append("spaceTime:" + this.spaceTime + str);
    localStringBuffer.append("countdownStart:" + this.countdownStart + str);
    localStringBuffer.append("countdownTime:" + this.countdownTime + str);
    localStringBuffer.append("optMode:" + this.optMode + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
