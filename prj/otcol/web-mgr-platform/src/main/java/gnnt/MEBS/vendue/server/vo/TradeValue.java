package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class TradeValue
{
  public long submitID;
  public String code;
  public long contractID;
  public double price;
  public long amount;
  public String userID;
  public Timestamp tradeDate;
  public int section;
  public double b_bail;
  public double s_bail;
  public double b_poundage;
  public double s_poundage;
  public int status;
  public long amountTotal;
  
  public String toString()
  {
    return this.submitID + ";" + this.code + ";" + this.contractID + ";" + this.price + ";" + this.amount + ";" + this.userID + ";" + this.tradeDate + ";" + this.section + ";" + this.b_bail + ";" + this.s_bail + ";" + this.b_poundage + ";" + this.s_poundage + ";" + this.status;
  }
}
