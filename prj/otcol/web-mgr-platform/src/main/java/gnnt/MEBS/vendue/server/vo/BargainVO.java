package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class BargainVO
{
  public int tradePartition;
  public long submitID;
  public String code;
  public long commodityID;
  public long contractID;
  public double price;
  public long amount;
  public String userID;
  public Timestamp tradeDate;
  public int section;
  public double s_bail;
  public double b_bail;
  public double s_poundage;
  public double b_poundage;
  public int status;
  public String contractContent;
  public double actualAmount;
  public double fellBackAmount;
  public int patchStatus;
  public int result;
  public double s_lastBail;
  public double b_lastBail;
  public long lastAmount;
  public String note;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("submitID:" + this.submitID + str);
    localStringBuffer.append("code:" + this.code + str);
    localStringBuffer.append("commodityID:" + this.commodityID + str);
    localStringBuffer.append("contractID:" + this.contractID + str);
    localStringBuffer.append("price:" + this.price + str);
    localStringBuffer.append("amount:" + this.amount + str);
    localStringBuffer.append("userID:" + this.userID + str);
    localStringBuffer.append("tradeDate:" + this.tradeDate + str);
    localStringBuffer.append("section:" + this.section + str);
    localStringBuffer.append("s_bail:" + this.s_bail + str);
    localStringBuffer.append("b_bail:" + this.b_bail + str);
    localStringBuffer.append("s_poundage:" + this.s_poundage + str);
    localStringBuffer.append("b_poundage:" + this.b_poundage + str);
    localStringBuffer.append("status:" + this.status + str);
    localStringBuffer.append("contractContent:" + this.contractContent + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
