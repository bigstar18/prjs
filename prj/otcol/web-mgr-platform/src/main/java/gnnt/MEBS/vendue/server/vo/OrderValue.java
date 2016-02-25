package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class OrderValue
{
  public long id;
  public String code;
  public double price;
  public long amount;
  public long totalAmount;
  public String userID;
  public Timestamp submitTime;
  public long validAmount;
  public Timestamp modifyTime;
  
  public String toString()
  {
    return this.id + ";" + this.code + ";" + this.price + ";" + this.amount + ";" + this.userID + ";" + this.submitTime;
  }
}
