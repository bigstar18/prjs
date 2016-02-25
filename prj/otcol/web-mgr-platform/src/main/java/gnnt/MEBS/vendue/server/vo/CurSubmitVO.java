package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class CurSubmitVO
{
  public int tradePartition;
  public long iD;
  public String code;
  public double price;
  public long amount;
  public String userID;
  public String traderID;
  public Timestamp submitTime;
  public int tradeFlag;
  public long validAmount;
  public long totalamount = 0L;
  public Timestamp modifytime;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("ID:" + this.iD + str);
    localStringBuffer.append("code:" + this.code + str);
    localStringBuffer.append("price:" + this.price + str);
    localStringBuffer.append("amount:" + this.amount + str);
    localStringBuffer.append("userID:" + this.userID + str);
    localStringBuffer.append("submitTime:" + this.submitTime + str);
    localStringBuffer.append("tradeFlag:" + this.tradeFlag + str);
    localStringBuffer.append("validAmount:" + this.validAmount + str);
    localStringBuffer.append("modifytime:" + this.modifytime + str);
    localStringBuffer.append("totalamount:" + this.totalamount + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
