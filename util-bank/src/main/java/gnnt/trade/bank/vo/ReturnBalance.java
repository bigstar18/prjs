package gnnt.trade.bank.vo;

import java.io.Serializable;

public class ReturnBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public double ptBalance;
  public double bankBalance;

  public double getPtBalance()
  {
    return this.ptBalance;
  }

  public void setPtBalance(double ptBalance) {
    this.ptBalance = ptBalance;
  }

  public double getBankBalance() {
    return this.bankBalance;
  }

  public void setBankBalance(double bankBalance) {
    this.bankBalance = bankBalance;
  }
}