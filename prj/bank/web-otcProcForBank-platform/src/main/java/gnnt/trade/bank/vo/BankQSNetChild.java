package gnnt.trade.bank.vo;

public class BankQSNetChild
{
  public String bankName;
  public String bankId;
  public double lastDayQY;
  public double lastKY;
  public double todayKY;
  public double toDayQY;
  public double InMoney;
  public double OutMoney;
  public double todayFee;
  public double sellerPayment;
  public double BuyerPayment;
  public double Profit;
  public double Loss;
  public double QYchange;
  public double KYchange;
  public String firmID;
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    String step = "\n";
    sb.append(this.bankName + step);
    sb.append(this.bankId + step);
    sb.append(this.lastDayQY + step);
    sb.append(this.toDayQY + step);
    sb.append(this.lastKY + step);
    sb.append(this.todayKY + step);
    sb.append(this.InMoney + step);
    sb.append(this.OutMoney + step);
    sb.append(this.todayFee + step);
    sb.append(this.sellerPayment + step);
    sb.append(this.BuyerPayment + step);
    sb.append(this.Profit + step);
    sb.append(this.Loss + step);
    sb.append(this.QYchange + step);
    sb.append(this.KYchange + step);
    sb.append(this.firmID + step);
    return sb.toString();
  }
}
