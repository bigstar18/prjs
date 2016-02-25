package gnnt.MEBS.vendue.server.vo;

public class FundInfoValue
{
  public String userCode;
  public String userName;
  public double overdraft;
  public double feecut;
  public double balance;
  public double frozenCapital;
  public double totalSecurity;
  public double paymentForGoods;
  public long curTradeAmount;
  public double deductedRatingBail;
  
  public String toString()
  {
    return this.userCode + ";" + this.overdraft + ";" + this.feecut + ";" + this.balance + ";" + this.frozenCapital + ";" + this.totalSecurity;
  }
}
