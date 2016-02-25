package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class TradeQuotationVO
{
  public int tradePartition;
  public long iD;
  public long submitID;
  public int section;
  public String code;
  public double price;
  public long vaidAmount;
  public String userID;
  public Timestamp lastTime;
  public Timestamp countdownStart;
  public int countdownTime;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("ID:" + this.iD + str);
    localStringBuffer.append("submitID:" + this.submitID + str);
    localStringBuffer.append("section:" + this.section + str);
    localStringBuffer.append("code:" + this.code + str);
    localStringBuffer.append("price:" + this.price + str);
    localStringBuffer.append("vaidAmount:" + this.vaidAmount + str);
    localStringBuffer.append("userID:" + this.userID + str);
    localStringBuffer.append("lastTime:" + this.lastTime + str);
    localStringBuffer.append("countdownStart:" + this.countdownStart + str);
    localStringBuffer.append("countdownTime:" + this.countdownTime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
