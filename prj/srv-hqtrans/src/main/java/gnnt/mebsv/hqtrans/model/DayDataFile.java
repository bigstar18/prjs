package gnnt.mebsv.hqtrans.model;

public class DayDataFile
{
  public long date;
  public float openPrice;
  public float highPrice;
  public float lowPrice;
  public float closePrice;
  public int reserveCount;
  public float balancePrice;
  public float totalMoney;
  public long totalAmount;
  
  public String toString()
  {
    return "date:" + this.date + "\r\nopenPrice:" + this.openPrice + "\r\nhighPrice:" + this.highPrice + "\r\nlowPrice:" + this.lowPrice + "\r\nclosePrice:" + this.closePrice + "\r\ntotalAmount:" + this.totalAmount + "\r\ntotalMoney:" + this.totalMoney + "\r\nbalancePirce:" + this.balancePrice + "\r\nreserveCount:" + this.reserveCount;
  }
}
