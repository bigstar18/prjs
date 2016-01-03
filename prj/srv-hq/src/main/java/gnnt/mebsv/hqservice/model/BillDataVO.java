package gnnt.mebsv.hqservice.model;

public class BillDataVO
{
  public String commodityID;
  public String marketID;
  public int tradeDate;
  public int time;
  public float curPrice;
  public float balancePrice;
  public double totalMoney;
  public long totalAmount;
  public int reserveCount;
  public float buyPrice;
  public float sellPrice;
  public int openAmount;
  public int closeAmount;
  public int tradeCue;
  public String obligate = "123";

  public String toString()
  {
    return "成交明细VO:  time: " + this.time + " curPrice: " + this.curPrice + " totalAmount: " + this.totalAmount + " reserveCount: " + this.reserveCount + " obligate: " + this.obligate;
  }
}