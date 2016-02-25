package gnnt.trade.bank.vo;

import java.io.Serializable;

public class FundsAndInterests
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmid;
  public double balance;
  public double RuntimeMargin;
  public double RuntimeSettleMargin;
  public double RuntimeFL;
  public double floatingloss;
  public double fundsInterests;
  
  public double getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(double balance)
  {
    this.balance = balance;
  }
  
  public String getFirmid()
  {
    return this.firmid;
  }
  
  public void setFirmid(String firmid)
  {
    this.firmid = firmid;
  }
  
  public double getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(double floatingloss)
  {
    this.floatingloss = floatingloss;
  }
  
  public double getRuntimeFL()
  {
    return this.RuntimeFL;
  }
  
  public void setRuntimeFL(double runtimeFL)
  {
    this.RuntimeFL = runtimeFL;
  }
  
  public double getRuntimeMargin()
  {
    return this.RuntimeMargin;
  }
  
  public void setRuntimeMargin(double runtimeMargin)
  {
    this.RuntimeMargin = runtimeMargin;
  }
  
  public double getRuntimeSettleMargin()
  {
    return this.RuntimeSettleMargin;
  }
  
  public void setRuntimeSettleMargin(double runtimeSettleMargin)
  {
    this.RuntimeSettleMargin = runtimeSettleMargin;
  }
  
  public double getFundsInterests()
  {
    return this.fundsInterests;
  }
  
  public void setFundsInterests(double fundsInterests)
  {
    this.fundsInterests = fundsInterests;
  }
}
