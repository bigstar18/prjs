package gnnt.MEBS.vendue.server.vo;

public class FlowControlVO
{
  public int tradePartition;
  public int unitID;
  public int unitType;
  public int startMode;
  public String startTime;
  public long durativeTime;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("unitID:" + this.unitID + str);
    localStringBuffer.append("unitType:" + this.unitType + str);
    localStringBuffer.append("startMode:" + this.startMode + str);
    localStringBuffer.append("startTime:" + this.startTime + str);
    localStringBuffer.append("durativeTime:" + this.durativeTime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
