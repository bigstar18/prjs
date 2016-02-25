package gnnt.MEBS.vendue.server.vo;

public class QuotationValue
{
  public String code;
  public long amount;
  public double beginPrice;
  public double stepPrice;
  public double lastPrice = 0.0D;
  public long hqID;
  public int countDownTime = -1;
  public String str1;
  public String str2;
  public String str3;
  public String str4;
  public String str5;
  public String str6;
  public String str7;
  public String str8;
  public String str9;
  public String str10;
  public String str11;
  public String str12;
  public String str13;
  public String str14;
  public String str15;
  public String str16;
  public String str17;
  public String str18;
  public String str19;
  public String str20;
  
  public String toString()
  {
    return this.code + ";" + this.str1 + ";" + this.str2 + ";" + this.amount + ";" + this.str3 + ";" + this.str4 + ";" + this.beginPrice + ";" + this.stepPrice + ";" + this.lastPrice + ";" + this.countDownTime;
  }
}
