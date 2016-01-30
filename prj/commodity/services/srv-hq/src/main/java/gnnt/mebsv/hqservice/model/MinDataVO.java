package gnnt.mebsv.hqservice.model;

public class MinDataVO
{
  public int tradeDate;
  public int time;
  public float curPrice;
  public double totalMoney;
  public long totalAmount;
  public int reserveCount;
  public float averPrice;

  public String toString()
  {
    return "分钟数据：time: " + this.time + "curPrice: " + this.curPrice + "totalAmount: " + this.totalAmount;
  }
}