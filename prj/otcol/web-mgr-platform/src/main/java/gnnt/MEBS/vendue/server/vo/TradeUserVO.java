package gnnt.MEBS.vendue.server.vo;

public class TradeUserVO
{
  public String userCode;
  public String password;
  public int status;
  public double overdraft;
  public double feecut;
  public double feecutfee;
  public double balance;
  public double frozenCapital;
  public int category1;
  public int category2;
  public int tradeCount;
  public double totalSecurity;
  public String keycode;
  public double paymentForGoods;
  public String name;
  public String address;
  public String postid;
  public String tradername;
  public String traderemail;
  public String tdtele;
  public String tdfax;
  public String tdmobile;
  public String validperiod;
  public String bank;
  public String account;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("userCode:" + this.userCode + str);
    localStringBuffer.append("password:" + this.password + str);
    localStringBuffer.append("status:" + this.status + str);
    localStringBuffer.append("Overdraft:" + this.overdraft + str);
    localStringBuffer.append("feecut:" + this.feecut + str);
    localStringBuffer.append("feecutfee:" + this.feecutfee + str);
    localStringBuffer.append("balance:" + this.balance + str);
    localStringBuffer.append("frozenCapital:" + this.frozenCapital + str);
    localStringBuffer.append("category1:" + this.category1 + str);
    localStringBuffer.append("category1:" + this.category1 + str);
    localStringBuffer.append("tradeCount:" + this.tradeCount + str);
    localStringBuffer.append("totalSecurity:" + this.totalSecurity + str);
    localStringBuffer.append("keycode:" + this.keycode + str);
    localStringBuffer.append("paymentForGoods:" + this.paymentForGoods + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
