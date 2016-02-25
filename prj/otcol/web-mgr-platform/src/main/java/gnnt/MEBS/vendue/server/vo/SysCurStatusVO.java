package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class SysCurStatusVO
{
  public int tradePartition;
  public int status;
  public int section;
  public Timestamp modifytime;
  public Timestamp starttime;
  public Timestamp endtime;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("status:" + this.status + str);
    localStringBuffer.append("section:" + this.section + str);
    localStringBuffer.append("modifytime:" + this.modifytime + str);
    localStringBuffer.append("starttime:" + this.starttime + str);
    localStringBuffer.append("endtime:" + this.endtime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
