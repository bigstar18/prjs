package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class TradeCommodityM1
{
  public int tradePartition;
  public String code;
  public long commodityid;
  public int lpFlag;
  public Timestamp modifytime;
  public double lastPrice = 0.0D;
  public String lastUserID = " ";
  public Timestamp lastTime = null;
  public long amount = 0L;
  public double tradeUnit;
  public double s_security;
  public double b_security;
  public double s_fee;
  public double b_fee;
  public int section = -1;
  public String userID;
  public String str1;
  public String str2;
  public String str3;
  public String str4;
  public String str5;
  public String str7;
  public String str11;
  public String str12;
  public String str16;
  public String str17;
  public long totalAmount;
  public double beginPrice;
  public double stepPrice;
  public double alertPrice;
  public int bargainFlag;
  public int tradeMode;
  public long lastSubmitID = -1L;
  public long hqID;
  public int countDownTime = -1;
  
  public void setValues(long paramLong1, double paramDouble, String paramString, Timestamp paramTimestamp, long paramLong2)
  {
    this.amount = paramLong1;
    this.lastPrice = paramDouble;
    this.lastUserID = paramString;
    this.lastTime = paramTimestamp;
    this.lastSubmitID = paramLong2;
  }
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("amount:" + this.amount + str);
    localStringBuffer.append("bargainFlag:" + this.bargainFlag + str);
    localStringBuffer.append("beginPrice:" + this.beginPrice + str);
    localStringBuffer.append("code:" + this.code + str);
    localStringBuffer.append("commodityid:" + this.commodityid + str);
    localStringBuffer.append("countDownTime:" + this.countDownTime + str);
    localStringBuffer.append("s_fee:" + this.s_fee + str);
    localStringBuffer.append("b_fee:" + this.b_fee + str);
    localStringBuffer.append("hqID:" + this.hqID + str);
    localStringBuffer.append("tradeMode:" + this.tradeMode + str);
    localStringBuffer.append("lastPrice:" + this.lastPrice + str);
    localStringBuffer.append("lastSubmitID:" + this.lastSubmitID + str);
    localStringBuffer.append("lastUserID:" + this.lastUserID + str);
    localStringBuffer.append("lpFlag:" + this.lpFlag + str);
    localStringBuffer.append("section:" + this.section + str);
    localStringBuffer.append("s_security:" + this.s_security + str);
    localStringBuffer.append("b_security:" + this.b_security + str);
    localStringBuffer.append("userID:" + this.userID + str);
    localStringBuffer.append("stepPrice:" + this.stepPrice + str);
    localStringBuffer.append("totalAmount:" + this.totalAmount + str);
    localStringBuffer.append("tradePartition:" + this.tradePartition + str);
    localStringBuffer.append("tradeUnit:" + this.tradeUnit + str);
    localStringBuffer.append("lastTime:" + this.lastTime + str);
    localStringBuffer.append("modifytime:" + this.modifytime + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
